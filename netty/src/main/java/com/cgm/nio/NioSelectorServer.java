package com.cgm.nio;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Iterator;
import java.util.Set;

/**
 * selector server nio
 */
public class NioSelectorServer {


    public static void main(String[] args) throws IOException {

        ServerSocketChannel serverSocket = ServerSocketChannel.open();  //fd4
        /**
         * 如果是epoll 则调用 epoll_create  创建一个fd4
         */
        Selector selector = Selector.open();


        serverSocket.socket().bind(new InetSocketAddress(9999));

        serverSocket.configureBlocking(false);
        /**
         * select poll jvm里开辟一个数组 fd放进去
         * epoll: epoll_ctl(fd3,add,fd4,epollin)
         */
        serverSocket.register(selector, SelectionKey.OP_ACCEPT);

        System.out.println("服务端启动成功。。。。");


        while (true) {

            //阻塞等待需要处理的事件
            /**
             * epoll: epoll_wait
             * select poll  select(fd4)
             */
            selector.select();


            //返回有状态的fd集合
            //这也是解决了 nio中自己去循环所有的fd,检查r w, 使用 多路复用就调用一次select 就 返回了 所有有状态的r w fd了

            Set<SelectionKey> selectionKeys = selector.selectedKeys();
            Iterator<SelectionKey> iterator = selectionKeys.iterator();
            while (iterator.hasNext()) {
                SelectionKey key = iterator.next();
                //如果是op_Accept 则进行连续获取和事件注册
                if (key.isAcceptable()) {
                    ServerSocketChannel server = (ServerSocketChannel) key.channel();
                    SocketChannel socketChannel = server.accept();
                    socketChannel.configureBlocking(false);

                    socketChannel.register(selector, SelectionKey.OP_READ);
                    System.out.println("客户端连接成功");

                } else if (key.isReadable()) {
                    SocketChannel socketChannel = (SocketChannel) key.channel();
                    ByteBuffer byteBuffer = ByteBuffer.allocate(128);
                    int len = socketChannel.read(byteBuffer);
                    if (len > 0) {
                        System.out.println("接收到消息：" + new String(byteBuffer.array()));
                    } else if (len == -1) { // 如果客户端断开连接，关闭Socket
                        System.out.println("客户端断开连接");
                        socketChannel.close();
                    }


                }
                //从事件中删除本次处理的key，防止下次select重复处理
                iterator.remove();
            }

        }

    }
}
