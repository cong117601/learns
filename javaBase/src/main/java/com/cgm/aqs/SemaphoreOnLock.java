package com.cgm.aqs;

import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class SemaphoreOnLock {


    private static int count;

    private Lock lock = new ReentrantLock();

    private Condition condition = lock.newCondition();

    SemaphoreOnLock(int count) {
        lock.lock();
        this.count = count;
        lock.unlock();

    }


    public void acquire() {
        lock.lock();
        try {
            while (count <= 0) {
                condition.await();
            }
            --count;
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        lock.unlock();
    }


    public void release() {
        lock.lock();
        ++count;
        condition.signal();
        lock.unlock();
    }


    public static void main(String[] args) {
        SemaphoreOnLock semaphoreOnLock = new SemaphoreOnLock(2);
        Thread t1 = new Thread(() -> {
            semaphoreOnLock.acquire();
            for (int i = 0; i < 10; i++) {
                if (i % 2 != 0) {
                    System.out.println("t1:"+i);
                }
            }
            semaphoreOnLock.release();

        }, "t1");

        Thread t2 = new Thread(() -> {
            semaphoreOnLock.acquire();
            for (int i = 0; i < 10; i++) {
                if (i % 2 != 0) {
                    System.out.println("t2:"+i);
                }
            }
            semaphoreOnLock.release();
        }, "t2");

        Thread t3 = new Thread(() -> {
            semaphoreOnLock.acquire();
            for (int i = 0; i < 10; i++) {
                if (i % 2 != 0) {
                    System.out.println("t3:"+i);
                }
            }
            semaphoreOnLock.release();
        }, "t3");
        t1.start();
        t2.start();
        t3.start();
    }
}
