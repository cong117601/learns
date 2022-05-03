package com.cgm.nio;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.nio.ByteBuffer;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * NIO server
 */
public class NioServer {

    // 保存客户端连接
    static List<SocketChannel> channelList = new ArrayList<>();

    public static void main(String[] args) throws IOException {

        ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();
        serverSocketChannel.socket().bind(new InetSocketAddress(9999));

        serverSocketChannel.configureBlocking(false);
        System.out.println("服务端启动。。。");
        while (true) {
            SocketChannel accept = serverSocketChannel.accept();
            if (accept != null) {
                accept.configureBlocking(false);
                channelList.add(accept);
            }

            // 遍历连接进行数据读取
            Iterator<SocketChannel> iterator = channelList.iterator();

            while (iterator.hasNext()) {
                SocketChannel sc = iterator.next();
                ByteBuffer buffer = ByteBuffer.allocate(128);
                int len = sc.read(buffer);

                //如果有数据 打印
                if (len > 0) {
                    System.out.println("接收到消息：" + new String(buffer.array()));
                } else if (len == -1) {
                    iterator.remove();
                }

            }
        }

    }
}
