package com.cgm.springboot.service;

import com.cgm.springboot.bean.A;
import com.cgm.springboot.bean.User;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Lookup;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Service;

@Service
public class UserService implements ApplicationContextAware {

    @Autowired
    private User user;

//    @Autowired
//    private A a;

    //@Autowired
    ApplicationContext context;

    @Lookup
    public A get() {
        return null;
    }

    ;

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.context = applicationContext;
    }


    public void eat() {
        System.out.println("eat....");
    }
}
