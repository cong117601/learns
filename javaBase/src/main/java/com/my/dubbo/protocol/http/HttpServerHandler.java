package com.my.dubbo.protocol.http;

import com.my.dubbo.Invocation;
import com.my.dubbo.register.LocalRegister;
import org.apache.commons.io.IOUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.ObjectInputStream;
import java.lang.reflect.Method;

public class HttpServerHandler {


    public void handler(HttpServletRequest request, HttpServletResponse response) {
        try {
            Invocation invocation = (Invocation) new ObjectInputStream(request.getInputStream()).readObject();


            Class aClass = LocalRegister.get(invocation.getInterfaceName()+invocation.getVersion());

            Method method = aClass.getMethod(invocation.getMethodName(), invocation.getParamTypes());
            String result = (String) method.invoke(aClass.newInstance(), invocation.getParams());
            response.setCharacterEncoding("utf-8");
            IOUtils.write(result, response.getWriter());

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
