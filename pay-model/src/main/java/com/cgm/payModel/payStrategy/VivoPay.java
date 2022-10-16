package com.cgm.payModel.payStrategy;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.Map;
@Component("vivo_pay")
@ConfigurationProperties(prefix = "channel.vivo")
public class VivoPay implements Pay{

    @Value("${appId}")
    String appId;
    @Value("${appKey}")
    String appKey;

    @Override
    public Map<String, Object> createOrder() {
        return null;
    }

    @Override
    public String callBack() {
        return null;
    }
}
