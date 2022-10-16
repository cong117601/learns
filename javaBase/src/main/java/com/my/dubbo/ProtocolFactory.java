package com.my.dubbo;

import com.my.dubbo.protocol.dubbo.DubboProtocol;
import com.my.dubbo.protocol.http.HttpProtocol;
import org.apache.commons.lang3.StringUtils;

public class ProtocolFactory {


    public static Protocol getProtocol() {


        String name = System.getProperty("protocolName");

        if (StringUtils.isBlank(name)) name = "http";
        switch (name) {
            case "http":
                return new HttpProtocol();
            case "dubbo":
                return new DubboProtocol();
            default:
                break;
        }
        return null;
    }
}
