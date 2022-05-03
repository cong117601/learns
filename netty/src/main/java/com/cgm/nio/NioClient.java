package com.cgm.nio;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;
import java.util.Scanner;

public class NioClient {

    public static void main(String[] args) throws IOException {

        //获取通道
        SocketChannel socketChannel = SocketChannel.open();

        socketChannel.configureBlocking(false);

        boolean connect = socketChannel.connect(new InetSocketAddress(9999));
        if (!connect) {
            while (!socketChannel.finishConnect()) {
                System.out.println("未连接成功，客户端没阻塞");
            }
        }

        Scanner in = new Scanner(System.in);

        ByteBuffer wrap = ByteBuffer.wrap(in.next().getBytes());

        socketChannel.write(wrap);

    }
}
