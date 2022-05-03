package com.cgm.service;

import com.cgm.bean.User;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.BeanFactoryAware;
import org.springframework.beans.factory.BeanNameAware;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

@Component
public class UserService implements BeanNameAware, BeanFactoryAware, ApplicationContextAware {


    @Autowired
    private User user;


    private String beanName;


    public User getUser() {
        System.out.println(beanName);
        return user;
    }

    @Override
    public void setBeanName(String s) {
        this.beanName = s;
    }

    @Override
    public void setBeanFactory(BeanFactory beanFactory) throws BeansException {

    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {

    }


    public void getUserServiceInit() {
        System.out.println("UserService init.....");
    }
}
