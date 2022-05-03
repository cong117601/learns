package com.cgm.thread.threadPool;

import java.io.BufferedReader;
import java.io.File;

import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class Test4 {

    static List<String> urlList = new ArrayList<>();
    static int core = 2;
    static String threadName = "downLoad";
    static ThreadPoolExecutor threadPool = new ThreadPoolExecutor(core, 10, 100, TimeUnit.SECONDS,
            new ArrayBlockingQueue<>(100), new MyThreadFactory(threadName), new ThreadPoolExecutor.DiscardOldestPolicy());

    static {
        File file = new File("C:\\Users\\cong1\\Desktop\\1.txt");

        BufferedReader br = null;
        try {
            br = new BufferedReader(new FileReader(file));
            String line = null;

            while ((line = br.readLine()) != null) {

                urlList.add(line);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }


    }


    public static void main(String[] args) {
        try {
            int size = urlList.size();
            int page = size % 10 != 0 ? ((size / 10) + 1) : size % 10;
            List<Thread> threads = new ArrayList<>(page);
            //开始时间
            long start = System.currentTimeMillis();
            //循环启动3个线程,每个线程下10个文件夹的图片
            int listStart, listEnd;
            for (int i = 0; i < page; i++) {
                /***计算切割  开始和结束**/
                listStart = i * 10;
                listEnd = (i + 1) * 10;
                if (listEnd > size) {
                    listEnd = size;
                }
                /**线程切断**/
                List<String> sunList = urlList.subList(listStart, listEnd);
                /**子线程初始化**/

                DownUrlTask downUrlTask = new DownUrlTask(i, sunList);
                threadPool.execute(downUrlTask);


//                Thread thread = new Thread(downUrlTask);
//                threads.add(thread);
            }

            threadPool.shutdown();
            threadPool.awaitTermination(1, TimeUnit.SECONDS);
//            for (Thread thread : threads) {
//                thread.start();
//            }
//            for (Thread thread : threads) {
//                thread.join();
//            }
            //结束时间
            long end = System.currentTimeMillis();
            System.out.println("所有下载已完成,本次共用时" + (end - start) / 1000 + "s");
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
