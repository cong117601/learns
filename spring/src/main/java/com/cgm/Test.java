package com.cgm;

import com.cgm.service.InitTask;
import com.cgm.service.UserService;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.test.context.support.AnnotationConfigContextLoader;

public class Test {


    public static void main(String[] args) {

        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(AppConfig.class);
        System.out.println(context.getBean("&cgmFactoryBean"));
        System.out.println(context.getBean("cgmFactoryBean"));
        System.out.println(context.getBean("cgmFactoryBean"));


        Object user = context.getBean("user");
        System.out.println(user);


        for (; ; ) {
        }

    }
}
