package com.cgm.task;

public class Task2 implements Runnable {
    @Override
    public void run() {
        System.out.println(Thread.currentThread().getName() + "------task2....");
        try {
            Thread.sleep(100);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
