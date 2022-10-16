package com.cgm.springboot.strategy;

import java.util.Map;

/**
 * 抽象接口，主要有生成订单，和支付回调
 */
public interface SDKService {

    public String getType();
    public Object createOrder(Map<String, Object> map);

    public Object callBack(Map<String, Object> map);
}
