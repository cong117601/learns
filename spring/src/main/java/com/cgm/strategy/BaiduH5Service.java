package com.cgm.strategy;

import org.springframework.stereotype.Service;

import java.util.Map;

/**
 * 百度支付 策略
 */
@Service
public class BaiduH5Service implements SDKService {

    @Override
    public String getType() {
        return "baiduh5";
    }


    @Override
    public Object createOrder(Map<String, Object> map) {

        return "Baidu createOrder";
    }

    @Override
    public Object callBack(Map<String, Object> map) {
        return "Baidu CallBack";
    }
}
