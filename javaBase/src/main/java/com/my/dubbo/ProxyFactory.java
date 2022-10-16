package com.my.dubbo;

import com.my.dubbo.protocol.dubbo.DubboProtocol;
import com.my.dubbo.protocol.dubbo.NettyClient;
import com.my.dubbo.protocol.http.HttpClient;
import com.my.dubbo.register.RemoteMapRegister;
import com.my.provider.api.HelloService;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.List;

public class ProxyFactory {


    public static <T> T getProxy(Class interFaceClass,String version) {


        return (T) Proxy.newProxyInstance(interFaceClass.getClassLoader(), new Class[]{interFaceClass}, new InvocationHandler() {
            @Override
            public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
                Invocation invocation = new Invocation();
                invocation.setInterfaceName(interFaceClass.getName());
                invocation.setMethodName(method.getName());
                invocation.setParamTypes(method.getParameterTypes());
                invocation.setParams(args);
                invocation.setVersion(version);
                //HttpClient httpClient = new HttpClient();
                //NettyClient httpClient = new NettyClient<>();
                Protocol protocol = ProtocolFactory.getProtocol();
                List<URL> urls = RemoteMapRegister.get(interFaceClass.getName()+version);
                //负载均衡
                URL url = LoadBalance.getUrl(urls);

                String result = protocol.send(url, invocation);
                return result;
            }
        });
    }
}
