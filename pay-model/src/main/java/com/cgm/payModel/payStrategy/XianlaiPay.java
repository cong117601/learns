package com.cgm.payModel.payStrategy;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component("xianlai_pay")
@ConfigurationProperties(prefix = "channel.xianlai")
public class XianlaiPay implements Pay {


    @Value("${appId}")
    String appId;

    @Value("${appKey}")
    String appKey;

    @Value("${others}")
    String others;

    @Override
    public Map<String, Object> createOrder() {
        System.out.println("appId:" + appId);
        return null;
    }

    @Override
    public String callBack() {
        return null;
    }
}
