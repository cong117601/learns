package com.cgm.thread;

import java.util.concurrent.Semaphore;
import java.util.concurrent.TimeUnit;

public class SemaphoreDemo {


    public static void main(String[] args) {
        Semaphore semaphore = new Semaphore(2);



        new Thread(()->{
            try {
                semaphore.acquire();
                System.out.println("T1 run");
                TimeUnit.SECONDS.sleep(2);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }finally {
                semaphore.release();
            }

        }).start();

        new Thread(()->{
            try {
                semaphore.acquire();
                System.out.println("T2 run");
                TimeUnit.SECONDS.sleep(2);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }finally {
                semaphore.release();
            }
        }).start();


        new Thread(()->{
            try {
                semaphore.acquire();
                System.out.println("T3 run");
                TimeUnit.SECONDS.sleep(2);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }finally {
                semaphore.release();
            }
        }).start();
    }

}
