package com.cgm.thread.threadBase;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.LockSupport;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 打印AaBbCc
 */
public class ThreadPrintABC_withParkAndUnPark {
    static char[] A = {'A','B','C'};
    static char[] a = {'a','b','c'};
    static Thread t1 = null;
    static Thread t2 = null;
    public static void main(String[] args) {
         t1 = new Thread(()->{

            for (int i = 0; i < A.length; i++) {
                System.out.print(A[i]);
                LockSupport.unpark(t2);
                LockSupport.park();
            }
        });

         t2 = new Thread(()->{

            for (int i = 0; i < a.length; i++) {
                LockSupport.park();
                System.out.print(a[i]);
                LockSupport.unpark(t1);
            }
        });

        t1.start();
        t2.start();

        ReentrantLock lock = new ReentrantLock();
        lock.lock();
        lock.unlock();
    }
}
