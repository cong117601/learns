package com.cgm.io;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.SocketOptions;
import java.net.StandardSocketOptions;
import java.nio.ByteBuffer;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.LinkedList;

/**
 * 简单NIO
 */
public class NIO {


    public static void main(String[] args) {     LinkedList<SocketChannel> clients = new LinkedList<>();

        try {
            ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();


            serverSocketChannel.bind(new InetSocketAddress(9000));
            //设置服务端ssc 非阻塞
            serverSocketChannel.configureBlocking(false);

//            serverSocketChannel.setOption(StandardSocketOptions.TCP_NODELAY, Boolean.TRUE); ?
            while (true) {
                //客户端 socket
                SocketChannel socketChannel = serverSocketChannel.accept();
                if (socketChannel == null) {
//                    System.out.println("wait connection....");
                } else {
                    socketChannel.configureBlocking(false);

                    System.out.println("clinet connection port:" + socketChannel.socket().getPort());
                    //添加到容器中
                    clients.add(socketChannel);
                }
                //分配在jvm上的应用程序堆内缓冲区
                ByteBuffer buffer = ByteBuffer.allocate(2048);
                for (SocketChannel client : clients) {
                    int read = client.read(buffer); //每次都要系统调用 读数据 ，可能内核还没有数据，切换状态太多了，所以有了selector 选择器
                    if (read > 0) {
                        buffer.flip();//切换读模式
                        byte[] bytes = new byte[buffer.limit()];
                        buffer.get(bytes);
                        System.out.println(client.socket().getPort() + ":" + new String(bytes));
                        buffer.clear();
                    }

                }

            }

        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}

