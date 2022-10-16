package com.cgm.thread.threadBase;

import java.util.concurrent.locks.LockSupport;
import java.util.concurrent.locks.ReentrantLock;

public class PrintABC_withWait {


    static char[] A = {'A', 'B', 'C'};
    static char[] a = {'a', 'b', 'c'};
    static Thread t1 = null;
    static Thread t2 = null;

    public static void main(String[] args) {
        Object o = new Object();
        t1 = new Thread(() -> {
            synchronized (o) {
                for (int i = 0; i < A.length; i++) {
                    System.out.print(A[i]);
                    try {

                        o.notify();
                        o.wait();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                o.notify();
            }

        });
        t1.start();
        t2 = new Thread(() -> {
            synchronized (o) {
                for (int i = 0; i < a.length; i++) {

                    System.out.print(a[i]);

                    try {
                        o.notify();
                        o.wait();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                o.notify();
            }
        });


        t2.start();

    }
}
