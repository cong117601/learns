package com.my.provider.impl;

import com.my.provider.api.HelloService;

public class HelloServiceImpl2 implements HelloService {
    @Override
    public String say(String msg) {

        return "2"+msg;

    }
}
