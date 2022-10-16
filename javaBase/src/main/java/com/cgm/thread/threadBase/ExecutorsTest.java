package com.cgm.thread.threadBase;

import java.util.Date;
import java.util.concurrent.*;

public class ExecutorsTest {


    public static void main(String[] args)  {
        ExecutorService executor = Executors.newFixedThreadPool(2, (r) -> {
            Thread thread = new Thread(r);
            thread.setUncaughtExceptionHandler((t, e) -> {
                System.out.println(t.getName() + ":" + e.getMessage());
            });
            return thread;
        });
//        executor.execute(()->{
//            System.out.println("Hello!");
//        });

        Future<?> submit = executor.submit(() -> {
            System.out.println("Hello!");
            System.out.println(1 / 0);
        });
//        try {
//            Object o = submit.get();
//            executor.shutdown();
//            executor.awaitTermination(1, TimeUnit.MINUTES);
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        } catch (ExecutionException e) {
//            e.printStackTrace();
//        }

    }
}
