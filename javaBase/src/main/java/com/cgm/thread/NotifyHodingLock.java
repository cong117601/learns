package com.cgm.thread;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class NotifyHodingLock {

    List lists = new ArrayList<>();

    public void add(Object o) {
        lists.add(o);
    }

    public int size() {
        return lists.size();
    }


    public static void main(String[] args) {
        NotifyHodingLock nl = new NotifyHodingLock();
        Object o = new Object();
        Thread t1 = new Thread(() -> {
            synchronized (o) {
                for (int i = 0; i < 20; i++) {
                    System.out.println(i);
                    //  在添加元素的过程中 检查中断标志位
                    while (true) {
                        if (Thread.currentThread().isInterrupted()) {
                            break;
                        }

                        nl.add(new Object());
                        try {
                            TimeUnit.SECONDS.sleep(1);
                        } catch (InterruptedException e) {
                            //发生中断异常 jvm会清空标志位位
                            Thread.currentThread().isInterrupted();
                        }
                        System.out.println("线程1 add " + nl.size());
                        if (nl.size() == 5) {
                            o.notify(); //不会释放锁
                            try {
                                o.wait(); //释放锁
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                        }

                    }
                }
            }


        }, "t1");
        t1.start();
        //t1 先启动
        try {
            TimeUnit.SECONDS.sleep(2);
        } catch (
                InterruptedException e) {
            e.printStackTrace();
        }

        new Thread(() -> {
            synchronized (o) {
                if (nl.size() != 5) {
                    try {
                        o.wait();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                t1.interrupt();
                o.notify();

            }
            System.out.println("线程t2 end");
        }, "t2").start();


    }

}
