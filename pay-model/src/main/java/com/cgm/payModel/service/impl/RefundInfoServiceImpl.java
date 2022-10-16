package com.cgm.payModel.service.impl;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cgm.payModel.entity.OrderInfo;
import com.cgm.payModel.entity.RefundInfo;
import com.cgm.payModel.mapper.RefundInfoMapper;
import com.cgm.payModel.service.OrderInfoService;
import com.cgm.payModel.service.RefundInfoService;
import com.cgm.payModel.untils.OrderNoUtils;
import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class RefundInfoServiceImpl extends ServiceImpl<RefundInfoMapper, RefundInfo> implements RefundInfoService {


    @Autowired
    RefundInfoMapper refundInfoMapper;

    @Autowired
    OrderInfoService orderInfoService;


    @Override
    public RefundInfo createRefundByOrderNo(String orderNo,String reason) {

        OrderInfo orderInfo = orderInfoService.getOrderStatus(orderNo);

        RefundInfo refundInfo = new RefundInfo();
        refundInfo.setOrderNo(orderNo);
        refundInfo.setRefundNo(OrderNoUtils.getRefundNo());
        refundInfo.setTotalFee(orderInfo.getTotalFee());
        refundInfo.setRefund(orderInfo.getTotalFee());
        refundInfo.setReason(reason);
        refundInfoMapper.insert(refundInfo);
        return refundInfo;
    }

    @Override
    public void updateRefund(String bodyString) {
        Gson gson = new Gson();
        Map<String,String> hashMap = gson.fromJson(bodyString, HashMap.class);

        QueryWrapper<RefundInfo> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("refund_no",hashMap.get("out_refund_no"));

        //设置要修改的字段
        RefundInfo refundInfo = new RefundInfo();
        refundInfo.setRefundId(hashMap.get("refund_id"));
        if(hashMap.get("status")!=null){
            refundInfo.setRefundStatus(hashMap.get("status")); //退款状态'
            refundInfo.setContentReturn(bodyString);
        }
        //该参数在回调中使用
        if(hashMap.get("refund_status")!=null){
            refundInfo.setRefundStatus(hashMap.get("refund_status")); //退款状态
            refundInfo.setContentNotify(bodyString);
        }
        refundInfoMapper.update(refundInfo,queryWrapper);

    }
}
