package com.cgm.nio;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

public class NioFileChannel01 {


    public static void main(String[] args) throws Exception {
//        String str = "aaaaaaaaaasadas";
//
//        FileOutputStream fileOutputStream = new FileOutputStream("d:\\file01.txt");
//
//        FileChannel channel = fileOutputStream.getChannel();
//
//        ByteBuffer byteBuffer = ByteBuffer.allocate(1024);
//
//        byteBuffer.put(str.getBytes());
//
//        byteBuffer.flip();
//
//        channel.write(byteBuffer);
//
//        fileOutputStream.close();
//
//
//        FileInputStream fileInputStream = new FileInputStream("D:\\file01.txt");
//
//        FileChannel channel1 = fileInputStream.getChannel();
//
//        ByteBuffer byteBuffer1 = ByteBuffer.allocate(1024);
//        byteBuffer1.put(str.getBytes());
//
//        byteBuffer1.flip();
//        int read = channel1.read(byteBuffer1);
//        if (read != -1) {
//            System.out.println(new String(byteBuffer1.array()));
//        }
//
//
//        fileInputStream.close();
        fileCopy("D:\\file01.txt", "D:\\file02.txt");
    }


    public static void fileCopy(String sourePath, String toPath) throws Exception {

        FileInputStream fileInputStream = new FileInputStream(sourePath);

        FileOutputStream fileOutputStream = new FileOutputStream(toPath);

        FileChannel c1 = fileInputStream.getChannel();

        FileChannel c2 = fileOutputStream.getChannel();

        ByteBuffer byteBuffer = ByteBuffer.allocate(10);

        while (true) {
            byteBuffer.clear();
            int read = c1.read(byteBuffer);
            if (read == -1) {
                break;
            }
            //反转反冲区
            byteBuffer.flip();
            c2.write(byteBuffer);
        }

        fileInputStream.close();
        fileOutputStream.close();

    }


}
