package com.cgm.netty.rpc.provider.impl;

import com.cgm.netty.rpc.provider.service.HelloService;

public class HelloServiceImpl implements HelloService {
    @Override
    public String hello(String msg) {
        System.out.println("收到客户端消息： " + msg);
        return msg;
    }
}
