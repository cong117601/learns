package com.cgm.payModel.payStrategy;


import com.sun.javafx.collections.MappingChange;

import java.util.Map;

public interface Pay {

    Map<String,Object> createOrder();

    /**
     * 接收第三方支付回调
     * @return
     */
    String callBack();

}
