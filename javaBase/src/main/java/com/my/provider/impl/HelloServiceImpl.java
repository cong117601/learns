package com.my.provider.impl;

import com.my.provider.api.HelloService;

import java.net.InetAddress;
import java.net.UnknownHostException;

public class HelloServiceImpl implements HelloService {
    @Override
    public String say(String msg) {

        return "1"+msg;

    }
}
