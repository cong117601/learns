package com.cgm.netty.rpc.provider;

import com.cgm.netty.rpc.framework.Url;
import com.cgm.netty.rpc.framework.protoful.http.TomcatServer;
import com.cgm.netty.rpc.framework.register.LocalRegister;
import com.cgm.netty.rpc.framework.register.RemoteMapRegister;
import com.cgm.netty.rpc.provider.impl.HelloServiceImpl;
import com.cgm.netty.rpc.provider.service.HelloService;
import org.apache.catalina.LifecycleException;

public class ServerBootStap {


    public static void main(String[] args) {
        try {


            Url url = new Url("127.0.0.1", 8080);
            LocalRegister.regist(HelloService.class.getName(), HelloServiceImpl.class);
            RemoteMapRegister.regist(HelloService.class.getName(), url);

            TomcatServer.start0Tomcat(url.getHostName(), url.getPort());
        } catch (LifecycleException e) {
            e.printStackTrace();
        }
    }
}
