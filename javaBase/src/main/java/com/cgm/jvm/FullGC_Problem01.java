package com.cgm.jvm;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class FullGC_Problem01 {


    private static class CardInfo{
        BigDecimal price = new BigDecimal(0.0);
        String name = "cmg";
        int age = 5;
        Date birthdate = new Date();
        char ch = '中';
        public void m(){

        }
    }


     static ScheduledThreadPoolExecutor executor =
            new ScheduledThreadPoolExecutor(50,new ThreadPoolExecutor.DiscardOldestPolicy());


    public static void main(String[] args) throws InterruptedException {
        executor.setMaximumPoolSize(50);
        for (;;){
            List<CardInfo> allCardInfo = getAllCardInfo();
            allCardInfo.forEach(info ->{
                executor.scheduleWithFixedDelay(()->{
                    info.m();
                },2,3, TimeUnit.SECONDS);
            });

            Thread.sleep(100);
        }
    }




    private static List<CardInfo> getAllCardInfo(){
        ArrayList<CardInfo> taskList = new ArrayList<>();
        for (int i = 0; i < 100; i++) {
            CardInfo cardInfo = new CardInfo();
            taskList.add(cardInfo);
        }
        return taskList;
    }
}
