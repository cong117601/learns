package com.cgm.service;

import com.cgm.task.Task1;
import com.cgm.task.Task2;
import com.cgm.task.Task3;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

@Component
public class InitTask implements InitializingBean {
    private static ArrayList<Runnable> list = new ArrayList();

    private static ThreadPoolExecutor executor = new ThreadPoolExecutor(2, 2, 200, TimeUnit.SECONDS, new LinkedBlockingDeque<>(100));

    @Override
    public void afterPropertiesSet() throws Exception {

        list.add(new Task1());
        list.add(new Task2());
        list.add(new Task3());


        list.forEach(item -> {
            executor.execute(item);
        });
    }

    @PostConstruct
    public void initMethod() {
        System.out.println("initMehod .....");
    }

}
