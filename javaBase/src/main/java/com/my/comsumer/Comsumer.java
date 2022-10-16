package com.my.comsumer;

import com.my.dubbo.Invocation;
import com.my.dubbo.ProxyFactory;
import com.my.dubbo.protocol.http.HttpClient;
import com.my.provider.api.HelloService;



public class Comsumer {


    public static void main(String[] args) {



        HelloService helloService = ProxyFactory.getProxy(HelloService.class,"2.0");

        String cgm = helloService.say("CGM");
        System.out.println(cgm);


    }
}
