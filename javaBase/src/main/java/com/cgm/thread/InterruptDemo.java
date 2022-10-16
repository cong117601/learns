package com.cgm.thread;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

public class InterruptDemo {


    public static void main(String[] args) throws InterruptedException {

        Thread t1 = new Thread(() -> {

            while (true) {
                if (Thread.currentThread().isInterrupted()) {
                    System.out.println("被打断。。");
                    break;
                }
//                try {
//                    TimeUnit.SECONDS.sleep(3);
//                } catch (InterruptedException e) {
////                    //发生 中断异常 jvm会重置中断标志位位false,故需要再次进行设置中断标志位
//                    Thread.currentThread().interrupt();
////                    e.printStackTrace();
//
//                }

                System.out.println("执行内容");
            }


        });
        t1.start();


        TimeUnit.SECONDS.sleep(2);
        t1.interrupt();
        System.out.println("打断t1");
        System.out.println("**************" + t1.isInterrupted());

    }



    public void m1() {

        HashMap<Object, Object> map = new HashMap<>();
        map.put("k1", "v1");
        map.put("k2", "v2");


    }
}
