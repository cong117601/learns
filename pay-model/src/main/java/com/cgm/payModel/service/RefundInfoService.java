package com.cgm.payModel.service;


import com.baomidou.mybatisplus.extension.service.IService;
import com.cgm.payModel.entity.RefundInfo;

public interface RefundInfoService extends IService<RefundInfo> {

    RefundInfo createRefundByOrderNo(String orderNo,String reason);

    void updateRefund(String bodyString);
}
