package com.cgm.netty.rpc.framework;

import java.io.Serializable;

/**
 * 传输协议
 */
public class Invocation implements Serializable {



    private String interFaceName;


    private String methodName;


    private Class[] paramsType;

    private Object[] params;

    public Invocation() {
    }

    public Invocation(String interFaceName, String methodName, Class[] paramsType, Object[] params) {
        this.interFaceName = interFaceName;
        this.methodName = methodName;
        this.paramsType = paramsType;
        this.params = params;
    }

    public String getInterFaceName() {
        return interFaceName;
    }

    public void setInterFaceName(String interFaceName) {
        this.interFaceName = interFaceName;
    }

    public String getMethodName() {
        return methodName;
    }

    public void setMethodName(String methodName) {
        this.methodName = methodName;
    }

    public Class[] getParamsType() {
        return paramsType;
    }

    public void setParamsType(Class[] paramsType) {
        this.paramsType = paramsType;
    }

    public Object[] getParams() {
        return params;
    }

    public void setParams(Object[] params) {
        this.params = params;
    }
}
