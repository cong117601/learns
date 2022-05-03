package com.cgm.bio;

import java.io.BufferedOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.charset.StandardCharsets;

/**
 * bio 服务端
 */
public class BioServer {


    public static void main(String[] args) throws IOException {

        ServerSocket serverSocket = new ServerSocket(9999);
        while (true) {
            //阻塞方法，接收客户端连接
            Socket accept = serverSocket.accept();

            handler(accept);
//            new Thread(() -> {
//                handler(accept);
//
//            }).start();

        }


    }


    public static void handler(Socket client) {
        try {
            while (true) {
                byte[] bytes = new byte[1024];

                //阻塞方法 没有数据可读时阻塞
                int read = client.getInputStream().read(bytes);
                if (read != -1) {
                    System.out.println("接收客户端数据：" + new String(bytes, 0, read));
                }

                client.getOutputStream().write("hello Client".getBytes(StandardCharsets.UTF_8));
                client.getOutputStream().flush();
            }
        } catch (Exception e) {

        } finally {
            try {
                client.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

    }
}
