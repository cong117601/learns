package com.cgm.thread.threadBase;

import java.util.concurrent.*;

/**
 * 创建线程的方式
 */
public class CreateThreadMethods {


    static class Thread1 extends Thread {
        @Override
        public void run() {
            System.out.println("thread_1");
        }
    }

    static class Thread2 implements Runnable {
        @Override
        public void run() {
            System.out.println("thread_2");
        }
    }

    static class Thread3 implements Callable<String> {

        @Override
        public String call() throws Exception {
            return "thread_3";
        }
    }

    public static void main(String[] args) throws ExecutionException, InterruptedException {

        new Thread1().start();

        Thread thread2 = new Thread(new Thread2());
        thread2.start();

        new Thread(()->{
            System.out.println("thread_4");
        }).start();

        FutureTask<String> stringFutureTask = new FutureTask<>(new Thread3());
        Thread thread3 = new Thread(stringFutureTask);
        thread3.start();
        String s = stringFutureTask.get();
        System.out.println(s);

        ExecutorService executorService = Executors.newCachedThreadPool();
        executorService.execute(()->{
            System.out.println("thread_5");
        });



        Future<String> submit = executorService.submit(new Thread3());
        System.out.println(submit.get());

        executorService.shutdown();
    }
}
