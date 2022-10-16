package com.cgm.springboot.strategy;

import org.springframework.stereotype.Service;

import java.util.Map;

@Service("weixin")
public class WeixinService implements SDKService {

    @Override
    public String getType() {
        return "weixin";
    }
    @Override
    public Object createOrder(Map<String, Object> map) {
        return "Weixin createOrder";
    }

    @Override
    public Object callBack(Map<String, Object> map) {
        return "Weixin callBack";
    }
}
