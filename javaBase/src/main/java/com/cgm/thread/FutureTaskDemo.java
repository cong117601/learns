package com.cgm.thread;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;
import java.util.concurrent.TimeUnit;

public class FutureTaskDemo {


    public static void main(String[] args) throws ExecutionException, InterruptedException {

        FutureTask<String> future = new FutureTask<>(() -> {
            TimeUnit.SECONDS.sleep(5);
            return "ok";
        });
        new Thread(future).start();
        System.out.println("主线程执行");

        while (true) {

            if (future.isDone()) {
                String s = future.get();
                System.out.println(s);
                break;
            } else {
                TimeUnit.SECONDS.sleep(3);
                System.out.println("还在执行");
            }


        }


    }
}

