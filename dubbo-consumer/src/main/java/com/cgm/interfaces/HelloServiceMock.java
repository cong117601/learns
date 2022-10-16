package com.cgm.interfaces;

import com.cgm.interfaces.HelloService;

/**
 * 服务降级，需在消费者端 提供与 服务接口相同的包名 +类名+mock的类，且mock 配置为true
 */
public class HelloServiceMock implements HelloService {
    @Override
    public String say(String s) {
        return "fail";
    }
}
