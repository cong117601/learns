package com.cgm.payModel.service.impl;


import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cgm.payModel.entity.PaymentInfo;
import com.cgm.payModel.enums.PayType;
import com.cgm.payModel.mapper.PaymentInfoMapper;
import com.cgm.payModel.service.PaymentInfoService;
import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class PaymentInfoServiceImpl extends ServiceImpl<PaymentInfoMapper, PaymentInfo> implements PaymentInfoService {

    @Autowired
    PaymentInfoMapper paymentInfoMapper;

    @Override
    public void insertPayLog(String orderMsg) {
        Gson gson = new Gson();
        HashMap hashMap = gson.fromJson(orderMsg, HashMap.class);
        PaymentInfo paymentInfo = new PaymentInfo();
        paymentInfo.setOrderNo((String)hashMap.get("out_trade_no"));
        paymentInfo.setTransactionId((String)hashMap.get("transaction_id"));
        paymentInfo.setTradeType((String)hashMap.get("trade_type"));
        paymentInfo.setPaymentType(PayType.WXPAY.getType());
        paymentInfo.setTradeState((String)hashMap.get("trade_state"));
        Map amount = (Map)hashMap.get("amount");
        Integer payerTotal = ((Double) amount.get("payer_total")).intValue();
        paymentInfo.setPayerTotal(payerTotal);
        paymentInfo.setContent(orderMsg);
        paymentInfoMapper.insertPayLog2(paymentInfo);

    }
}
