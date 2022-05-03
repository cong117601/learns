package com.cgm.thread.threadPool;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class Test2 {

    public static void main(String[] args) {
        List<Integer> list = new ArrayList<>();
        for (int i = 10000000; i >= 1; i--) {
            list.add(0);
        }
        System.out.println("源集合数量：" + list.size());
        List<Integer> newList = new ArrayList<>();
        long start = System.currentTimeMillis();

        ExecutorService executor = Executors.newFixedThreadPool(100);
        for (Integer integer : list) {
            executor.execute(() -> {
                newList.add(integer + 1);
            });
        }
        executor.shutdown();
        try {
            executor.awaitTermination(6, TimeUnit.MINUTES);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        long end = System.currentTimeMillis();
        System.out.println("时间:" + (end - start) + "ms");

        System.out.println("新集合数量:" + newList.size());

    }


}
