package com.cgm.quartz;

import org.quartz.*;
import org.springframework.scheduling.annotation.Scheduled;

import java.util.Date;
@DisallowConcurrentExecution
public class MyJob implements Job {
    @Override
    public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("定时器任务执行" + new Date(System.currentTimeMillis()));
        JobDataMap map=jobExecutionContext.getMergedJobDataMap();
        System.out.println("参数值"+map.get("uname"));
    }

}
