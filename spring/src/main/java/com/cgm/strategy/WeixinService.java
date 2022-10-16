package com.cgm.strategy;

import org.springframework.stereotype.Service;

import java.util.Map;

@Service
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
