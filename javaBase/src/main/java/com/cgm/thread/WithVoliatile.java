package com.cgm.thread;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class WithVoliatile {

    volatile List lists = new ArrayList<>();

    public void add(Object o) {
        lists.add(o);
    }

    public int size() {
        return lists.size();
    }

    public static void main(String[] args) {
        WithVoliatile nl = new WithVoliatile();


        new Thread(() -> {
            while (true) {
                if (nl.size() == 5) {
                    break;
                }
            }
            System.out.println("线程t2 end");
        }, "t2").start();

        Thread t1 = new Thread(() -> {

            for (int i = 0; i < 10; i++) {
                nl.add(new Object());
                try {
                    TimeUnit.SECONDS.sleep(1);
                } catch (InterruptedException e) {
                    //发生中断异常 jvm会清空标志位位
                    Thread.currentThread().isInterrupted();
                }
                System.out.println("线程1 add " + nl.size());

            }


        }, "t1");
        t1.start();

        try {
            TimeUnit.SECONDS.sleep(2);
        } catch (
                InterruptedException e) {
            e.printStackTrace();
        }




    }
}
