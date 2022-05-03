package com.cgm.netty.rpc.framework;

import com.alibaba.fastjson.JSONObject;
import com.cgm.netty.rpc.HttpUtils;
import com.cgm.netty.rpc.framework.register.RemoteMapRegister;
import com.cgm.netty.rpc.provider.service.HelloService;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.List;

public class ProxyFactory {


    public static <T> T getProxyClass(Class infaceName) {
        return (T) Proxy.newProxyInstance(infaceName.getClassLoader(), new Class[]{infaceName}, new InvocationHandler() {
            @Override
            public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {


                Invocation invocation = new Invocation();
                invocation.setInterFaceName(infaceName.getName());
                invocation.setMethodName(method.getName());
                invocation.setParamsType(method.getParameterTypes());
                invocation.setParams(args);
                List<Url> urls = RemoteMapRegister.get(infaceName.getName());
                //可以负载均衡
                Url url = urls.get(0);  //这个注册中心 为null,两个jvm，故 需要使用第三方存储
                String address = "http://" + url.getHostName() + ":" + url.getPort();
                String s = HttpUtils.sendPost(address, JSONObject.toJSONString(invocation));

                return s;
            }
        });

    }
}
