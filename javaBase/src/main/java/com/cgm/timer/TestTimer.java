package com.cgm.timer;

import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

public class TestTimer {

    public static void main(String[] args) {

        Timer timer = new Timer();

        TimerTask fooTimerTask = new FooTimerTask();
        //必须要指定什么时候执行。如果执行中有延迟，会丢失执行次数，并且不方便对于一些场景
        timer.schedule(fooTimerTask, new Date(), 2000);

    }


}


class FooTimerTask extends TimerTask {

    @Override
    public void run() {
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println(System.currentTimeMillis() / 1000 + "----timer Task");
    }

}