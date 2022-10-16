package com.my.provider;

import com.my.dubbo.Protocol;
import com.my.dubbo.ProtocolFactory;
import com.my.dubbo.URL;
import com.my.dubbo.protocol.dubbo.DubboProtocol;
import com.my.dubbo.protocol.dubbo.NettyServer;

import com.my.dubbo.register.LocalRegister;
import com.my.dubbo.register.RemoteMapRegister;
import com.my.provider.api.HelloService;
import com.my.provider.impl.HelloServiceImpl;
import com.my.provider.impl.HelloServiceImpl2;

public class Provider {


    public static void main(String[] args) {


        LocalRegister.register(HelloService.class.getName(),"1.0", HelloServiceImpl.class);
        LocalRegister.register(HelloService.class.getName(),"2.0", HelloServiceImpl2.class);
        //注册中心
        URL u1 = new URL("localhost", 9999);
        URL u2 = new URL("localhost", 9998);
        RemoteMapRegister.regist(HelloService.class.getName(),"1.0", u1);
        RemoteMapRegister.regist(HelloService.class.getName(),"2.0", u1);
      //  RemoteMapRegister.regist(HelloService.class.getName(),"2.0", u2);

//        HttpServer httpServer = new HttpServer();
        Protocol protocol = ProtocolFactory.getProtocol();
        protocol.start(u1);
        //protocol.start(u2);
    }
}
