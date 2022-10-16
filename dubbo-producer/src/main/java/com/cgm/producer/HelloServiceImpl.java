package com.cgm.producer;

import com.cgm.interfaces.HelloService;
import org.apache.dubbo.config.annotation.DubboService;

@DubboService
public class HelloServiceImpl implements HelloService {


    @Override
    public String say(String s) {
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        //int i = 1/0;
        return s;
    }
}
