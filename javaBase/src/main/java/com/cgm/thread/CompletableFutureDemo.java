package com.cgm.thread;

import java.util.concurrent.*;

/**
 * java8
 * CompletionStage 阶段 一个阶段完成之后，可以接着干下一个阶段
 * <p>
 * runAsync 无返回值
 * supplyAsync 有返回值
 */
public class CompletableFutureDemo {


    public static void main(String[] args) throws ExecutionException, InterruptedException {
        CompletableFuture<String> completableFuture = CompletableFuture.supplyAsync(() -> {
            System.out.println(Thread.currentThread().getName() + " completableFuture ");
            try {
                TimeUnit.SECONDS.sleep(3);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            //int i = 1/0;
            return "ok";
            //使用上一步的结果 并返回
        }).thenApply(f -> {
            return f + "  stage";
            //完成之后的回调函数
        }).whenComplete((v, e) -> {
            if (e == null) {
                System.out.println("---result:" + v);
            }
            //出现异常时的处理
        }).exceptionally(e -> {
            return "出现异常";
        });

        System.out.println("main");
        //System.out.println(completableFuture.get());
        //与get相比 只是join没有抛出异常

        System.out.println(completableFuture.join());
        //主线程不要立刻结束，否则CompletableFuture默认使用的线程池会立刻关闭:暂停3秒钟线程
        TimeUnit.SECONDS.sleep(5);
    }

    public static void m1() throws ExecutionException, InterruptedException {
        ThreadPoolExecutor threadPool = new ThreadPoolExecutor(2, 2, 5, TimeUnit.SECONDS, new ArrayBlockingQueue<>(10));
        CompletableFuture<Void> completableFuture = CompletableFuture.runAsync(() -> {
            System.out.println(Thread.currentThread().getName() + "-----completableFuture");
        });
        CompletableFuture<Void> completableFuture2 = CompletableFuture.runAsync(() -> {

            System.out.println(Thread.currentThread().getName() + "-----completableFuture2");
        }, threadPool);
        CompletableFuture<String> completableFuture3 = CompletableFuture.supplyAsync(() -> {

            System.out.println(Thread.currentThread().getName() + "-----completableFuture2");
            return "supply completableFuture3";
        }, threadPool);


        System.out.println("主线程");

        System.out.println(completableFuture.get());
        System.out.println(completableFuture2.get());
        System.out.println(completableFuture3.get());
        threadPool.shutdown();
    }
}
