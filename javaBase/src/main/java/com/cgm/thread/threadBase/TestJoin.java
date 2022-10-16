package com.cgm.thread.threadBase;

public class TestJoin {


    public static void main(String[] args) throws InterruptedException {
        Thread t1 = new Thread(() -> {
            System.out.println("t1");
        });
        Thread t2 = new Thread(() -> {
            System.out.println("t2");
        });
        Thread t3 = new Thread(() -> {
            System.out.println("t3");
        });

        t1.start();
        t1.join();
        t2.start();
        t2.join();
        t3.start();
        t3.join();
    }
}
