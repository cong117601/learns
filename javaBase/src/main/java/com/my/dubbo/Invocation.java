package com.my.dubbo;

import lombok.Data;

import java.io.Serializable;

@Data
public class Invocation implements Serializable {


    private String interfaceName;

    private String methodName;

    private Class[] paramTypes;

    private Object[] params;


    private String version;

}
