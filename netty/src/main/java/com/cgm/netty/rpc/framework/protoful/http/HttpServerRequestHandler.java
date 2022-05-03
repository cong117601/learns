package com.cgm.netty.rpc.framework.protoful.http;

import com.alibaba.fastjson.JSONObject;
import com.cgm.netty.rpc.framework.Invocation;
import com.cgm.netty.rpc.framework.register.LocalRegister;
import org.apache.catalina.util.IOTools;
import org.apache.commons.io.IOUtils;


import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.lang.reflect.Method;

public class HttpServerRequestHandler {

    public void handler(HttpServletRequest req, HttpServletResponse resp) {
        try {

            System.out.println("1111");

            Invocation invocation = JSONObject.parseObject(req.getInputStream(), Invocation.class);

            String interfaceName = invocation.getInterFaceName();
            String methodName = invocation.getMethodName();
            Class[] paramTypes = invocation.getParamsType();
            Object[] params = invocation.getParams();

            //通过接口名 能创建出对象吗 ？ 不能 所以需要 在服务启动时 保存一下映射关系
            Class aClass = LocalRegister.get(interfaceName);
            Method method = aClass.getDeclaredMethod(methodName, paramTypes);
            String result = (String) method.invoke(aClass.newInstance(), params); //得到结果
            //写回去
            IOUtils.write(result, resp.getOutputStream());
        } catch (Exception e) {
           e.printStackTrace();
        }


    }
}
