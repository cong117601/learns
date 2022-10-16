package com.cgm.aqs;

import java.util.concurrent.locks.ReentrantLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

public class RWLook {

     private static int a;

     public static int  getA(){
         return a;
     }

     public static void setA(){
         a++;
     }


    public static void main(String[] args) {
        ReentrantLock lock = new ReentrantLock();

        final ReentrantReadWriteLock rwl = new ReentrantReadWriteLock();
        ReentrantReadWriteLock.ReadLock readLock = rwl.readLock();
        ReentrantReadWriteLock.WriteLock writeLock = rwl.writeLock();
        lock.lock();
        lock.unlock();

//        Thread t1 = new Thread(() -> {
//
//            readLock.lock();
//            getA();
//            readLock.unlock();
//        });
//
//        Thread t2 = new Thread(() -> {
//            readLock.lock();
//            getA();
//            readLock.unlock();
//        });
//
//        Thread t3 = new Thread(() -> {
//            writeLock.lock();
//            setA();
//            writeLock.unlock();
//        });
//
//
//        t1.start();
//        t2.start();
//        t3.start();
    }
}
