package com.cgm.payModel.service;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.Map;

public interface WxPayService {
    /**
     * 吊起支付，生成二维码
     * @param productId
     * @return
     * @throws IOException
     */
    Map<String, Object> nativePay(Long productId) throws IOException;

    /**
     * 主动查询支付订单状态
     * @param orderNo
     * @return
     * @throws IOException
     */
    String queryOrderStatus2Api(String orderNo) throws IOException;

    /**
     * 回调 验签之后，处理订单
     * @param bodyMap
     */
    void processOrder(HashMap bodyMap);

    /**
     * 取消订单
     * @param orderNo
     * @return
     */
    boolean cancelOrder(String orderNo);

    /**
     * 退款申请
     * @param orderNo
     * @param reason
     * @return
     */
    boolean refundsOrder(String orderNo, String reason);

    /**
     * 退款回调 处理退款订单
     * @param bodyMap
     */
    void processRefund(HashMap bodyMap);
}
