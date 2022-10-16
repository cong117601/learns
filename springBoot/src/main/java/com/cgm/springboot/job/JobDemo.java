package com.cgm.springboot.job;

import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class JobDemo {


    @Scheduled(cron = "0/1 * * * * ?")
    public void test1() {
        // 每秒执行一次
        System.out.println("scheduler1 执行: " + Thread.currentThread() + "-" +new Date());
        try {
            Thread.sleep(5*1000);  // 5s
        } catch (Exception e) {
            System.out.println(e.toString());
        }
    }
    @Scheduled(cron = "0/2 * * * * ?")
    public void test2() {
        // 每2s执行一次
        System.out.println("scheduler2 执行: " + Thread.currentThread() + "-" + new Date());
    }
}
