package com.cgm.thread;

import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;

public class CyclicBarrierDemo {


    public static void main(String[] args) {

        CyclicBarrier cyclicBarrier = new CyclicBarrier(3,()->{
            System.out.println("发车！");
        });


//        for (int i = 0; i < 3; i++) {
//
//
//
//        }

       new Thread(()->{
                System.out.println("取mysql");
           try {
               cyclicBarrier.await();
           } catch (InterruptedException e) {
               e.printStackTrace();
           } catch (BrokenBarrierException e) {
               e.printStackTrace();
           }
            }).start();
        new Thread(()->{
            System.out.println("取mysql");
            try {
                cyclicBarrier.await();
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (BrokenBarrierException e) {
                e.printStackTrace();
            }
        }).start();
        new Thread(()->{
            System.out.println("取mysql");
            try {
                cyclicBarrier.await();
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (BrokenBarrierException e) {
                e.printStackTrace();
            }
        }).start();
    }
}
