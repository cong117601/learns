package com.cgm.proxy.jdk;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

public class JDKProxy {


    public static Object getProxyClass(Object object) {  //object.getClass().getInterfaces()
        Object o1 = Proxy.newProxyInstance(object.getClass().getClassLoader(), object.getClass().getInterfaces(), new InvocationHandler() {
            @Override
            public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
                System.out.println("洗脸"+(String)args[0]);
                Object invoke = method.invoke(object, args);
                System.out.println("睡觉");
                return invoke;
            }
        });
        return o1;
    }

}
