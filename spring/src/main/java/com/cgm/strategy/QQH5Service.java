package com.cgm.strategy;

import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class QQH5Service implements SDKService {

    @Override
    public String getType() {
        return "qq";
    }



    @Override
    public Object createOrder(Map<String, Object> map) {
        return "QQ createOrder";
    }

    @Override
    public Object callBack(Map<String, Object> map) {
        return "QQ callBack";
    }
}
