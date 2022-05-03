package com.cgm.thread.threadBase;

import com.sun.org.apache.bcel.internal.generic.NEW;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.LockSupport;

/**
 * 线程的状态
 * 1、new 线程刚刚创建还没启动                             yield()
 * 2、runnable 可运行状态，由调度器可以安排执行   -- ready   <=====   running
 * 3、waiting 等待被唤醒
 * 4、timed waiting 隔一段时间后自动唤醒
 * 5、blocked 被阻塞，正在等待锁
 * 6、terminated 线程结束
 */
public class ThreadStatus {
    public static void main(String[] args) throws InterruptedException {

        Thread t1 = new Thread(() -> {
            System.out.println("2: " + Thread.currentThread().getState());
            for (int i = 0; i < 3; i++) {
                SleepHelper.sleep(i);
                System.out.print(i + " ");
            }
            System.out.println();
        });
        System.out.println("1: " + t1.getState());
        t1.start();
        t1.join();
        System.out.println("3: " + t1.getState());


        Thread t2 = new Thread(() -> {

            LockSupport.park();
            System.out.println("t2 go");
            SleepHelper.sleep(5);
            System.out.println("t2 我水好了");
        });

        t2.start();
        SleepHelper.sleep(1);
        System.out.println("4: " + t2.getState());

        LockSupport.unpark(t2);
        SleepHelper.sleep(1);
        System.out.println("5: " + t2.getState());


        Thread t3 = new Thread(()->{
           synchronized (ThreadStatus.class){
               System.out.println("线程t3 获取了当前类的锁");
           }
        });

        new Thread(()->{
            synchronized (ThreadStatus.class){
                SleepHelper.sleep(5);
            }
        }).start();
        t3.start();
        SleepHelper.sleep(1);
        System.out.println("6: " + t3.getState());
    }


}

class SleepHelper {
    static void sleep(int ms) {
        try {
            TimeUnit.SECONDS.sleep(ms);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
