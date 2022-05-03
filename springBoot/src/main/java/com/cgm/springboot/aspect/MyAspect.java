package com.cgm.springboot.aspect;

import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
@Component
@Aspect
public class MyAspect {

    @Pointcut("execution(public void com.cgm.springboot.service.UserService.eat())")
    public void logPointCut() {

    }


    @Before("logPointCut()")
    public void doBefore() {
        System.out.println("开始执行");
    }


    @After("logPointCut()")
    public void doAfter() {
        System.out.println("执行完成");
    }
}
