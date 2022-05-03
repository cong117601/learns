package com.cgm.thread.threadBase;

import java.util.Date;
import java.util.concurrent.*;

public class ExecutorsTest {


    public static void main(String[] args) throws InterruptedException, ExecutionException {
        ExecutorService executorService = Executors.newFixedThreadPool(2);
        executorService.execute(() -> {
            System.out.println(Thread.currentThread() + "1111");

        });

//        executorService.execute(() -> {
//            System.out.println(Thread.currentThread() + "1111");
//            try {
//                int i = 1 / 0;
//            }catch (Exception e){
//
//            }
//        });
//        executorService.execute(() -> {
//            System.out.println(Thread.currentThread() + "1111");
//            try {
//                int i = 1 / 0;
//            }catch (Exception e){
//
//            }
//        });
//        executorService.execute(() -> {
//            System.out.println(Thread.currentThread() + "1111");
//            try {
//                int i = 1 / 0;
//            }catch (Exception e){
//
//            }
//        });
//        executorService.execute(() -> {
//            System.out.println(Thread.currentThread() + "1111");
//            try {
//                int i = 1 / 0;
//            }catch (Exception e){
//
//            }
//        });


        ScheduledThreadPoolExecutor scheduledThreadPoolExecutor = new ScheduledThreadPoolExecutor(1);

        scheduledThreadPoolExecutor.schedule(() -> {
            System.out.println("hello");
        }, 1, TimeUnit.SECONDS);

        scheduledThreadPoolExecutor.scheduleAtFixedRate(() -> {
            System.out.println(new Date());
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }, 0, 2, TimeUnit.SECONDS);

        scheduledThreadPoolExecutor.scheduleWithFixedDelay(() -> {
            System.out.println(new Date());
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }, 0, 2, TimeUnit.SECONDS);

    }
}
