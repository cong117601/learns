package com.cgm.thread.threadPool;

import com.sun.org.apache.bcel.internal.generic.NEW;
import sun.nio.ch.ThreadPool;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;

public class Test {
    static ArrayList<Integer> lists = new ArrayList<>(100000);
    static int count = 0;
    static int count2 = 0;

    static {

        for (int i = 0; i < 10000000; i++) {
            lists.add(i);
            count2 += i;
        }

        System.out.println(count2);
    }

    public static void main(String[] args) throws ExecutionException, InterruptedException {
        doReadList(lists, 16);
        System.out.println(count);
        threadPool.shutdown();
    }


    static int core = 2;
    static String threadName = "waring";
    static ThreadPoolExecutor threadPool = new ThreadPoolExecutor(core, 10, 100, TimeUnit.SECONDS,
            new ArrayBlockingQueue<>(100), new MyThreadFactory(threadName), new ThreadPoolExecutor.DiscardOldestPolicy());

    /**
     * @param list   需要处理的list
     * @param sunSum 将集合切分的段数
     * @throws InterruptedException
     * @throws ExecutionException
     */
    private static void doReadList(List<Integer> list, int sunSum) throws InterruptedException, ExecutionException {


        /**接收集合各段的 执行的返回结果**/
        List<Future<Integer>> futureList = new ArrayList<Future<Integer>>();

        /**集合总条数**/
        int size = list.size();
        /**将集合切分的段数**/
        //int sunSum = 10;
        int listStart, listEnd;
        /***当总条数不足10条时 用总条数 当做线程切分值**/
        if (sunSum > size) {
            sunSum = size;
        }
        CountDownLatch countDownLatch = new CountDownLatch(sunSum);
        /**定义子线程**/
        WaringTask sunCallable = null;
        /**将list 切分10份 多线程执行**/
        for (int i = 0; i < sunSum; i++) {
            /***计算切割  开始和结束**/
            listStart = size / sunSum * i;
            listEnd = size / sunSum * (i + 1);
            /**最后一段线程会 出现与其他线程不等的情况**/
            if (i == sunSum - 1) {
                listEnd = size;
            }
            /**线程切断**/
            List<Integer> sunList = list.subList(listStart, listEnd);
            /**子线程初始化**/
            sunCallable = new WaringTask(i, sunList, countDownLatch);
            /***多线程执行***/
            futureList.add(threadPool.submit(sunCallable));
        }
        countDownLatch.await();
        /**对各个线程段结果进行解析**/
        for (Future<Integer> future : futureList) {
            count += future.get();
        }

    }
}