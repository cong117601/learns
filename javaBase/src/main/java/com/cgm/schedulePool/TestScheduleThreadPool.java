package com.cgm.schedulePool;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class TestScheduleThreadPool {


    public static void main(String[] args) {

        ScheduledExecutorService threadPool = Executors.newScheduledThreadPool(1);
        //threadPool.scheduleAtFixedRate(new Task(),0, 2, TimeUnit.SECONDS);
        System.out.println(System.currentTimeMillis() / 1000 + "----start");
        threadPool.schedule(new Task(),5, TimeUnit.SECONDS);
    }

}

class Task implements Runnable {

    @Override
    public void run() {
//        try {
//            Thread.sleep(3000);
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }
        System.out.println(System.currentTimeMillis() / 1000 + "----Task");
    }
}



