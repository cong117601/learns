package com.cgm.springboot.strategy;

import org.springframework.stereotype.Service;

import java.util.Map;

@Service("qq")
public class QQH5Service implements SDKService {
   //规范，使用枚举！
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
