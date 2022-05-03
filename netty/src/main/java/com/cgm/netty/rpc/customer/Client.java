package com.cgm.netty.rpc.customer;

import com.alibaba.fastjson.JSONObject;
import com.cgm.netty.rpc.HttpUtils;
import com.cgm.netty.rpc.framework.Invocation;
import com.cgm.netty.rpc.framework.ProxyFactory;
import com.cgm.netty.rpc.provider.service.HelloService;


public class Client {

    public static void main(String[] args) {


        HelloService proxyClass = ProxyFactory.getProxyClass(HelloService.class);
        String result = proxyClass.hello("cggggg");
        System.out.println(result);



//        //发送http请求到本地tomcat (移动到代理对象中)
//        Invocation invocation = new Invocation();
//        invocation.setInterFaceName(HelloService.class.getName());
//        invocation.setMethodName("hello");
//        invocation.setParamsType(new Class[]{String.class});
//        invocation.setParams(new Object[]{"CGM"});
//
//        String s = HttpUtils.sendPost("http://127.0.0.1:8080", JSONObject.toJSONString(invocation));
//
//        System.out.println(s);


    }
}
