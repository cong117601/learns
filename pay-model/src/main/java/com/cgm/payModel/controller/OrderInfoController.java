package com.cgm.payModel.controller;

import com.cgm.payModel.entity.OrderInfo;
import com.cgm.payModel.entity.SendPropRecord;
import com.cgm.payModel.enums.OrderStatus;
import com.cgm.payModel.mapper.OrderInfoMapper;
import com.cgm.payModel.mapper.PaymentInfoMapper;
import com.cgm.payModel.service.OrderInfoService;
import com.cgm.payModel.service.PaymentInfoService;
import com.cgm.payModel.service.WxPayService;
import com.cgm.payModel.vo.R;
import com.google.gson.Gson;
import org.apache.http.util.EntityUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import javax.annotation.Resource;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/order-info/")
public class OrderInfoController {

    @Resource
    OrderInfoService orderInfoService;

    @Resource
    WxPayService wxPayService;


    @Resource
    RestTemplate restTemplate;

    @Resource
    PaymentInfoService paymentInfoService;

    @Resource
    OrderInfoMapper orderInfoMapper;


    @Value("${propServer.sendPropUrl}")
    String sendPropUrl;

    @RequestMapping("list/{userId}")
    public R getOrderList(@PathVariable("userId") Long userId) {


        List<OrderInfo> list = orderInfoService.getOrderList(userId);
        return R.ok().data("list", list);

    }

    /**
     * 查询本地订单状态  前端主动轮询
     * 不应该只查 自己数据库中的订单状态，还要查一下远程的订单状态；
     * 可能会 用户已经支付成功，但是在回调方法中失败 或者没有进入回调方法（干的事情是 修改订单状态为已支付，插入支付记录），这样调用远程 应以远程为准
     *
     * @return
     */
    @RequestMapping("query-order-status/{orderNo}/{productId}")
    public R queryOrderStatus(@PathVariable String orderNo,@PathVariable Long productId) throws IOException {
        String bodyString = wxPayService.queryOrderStatus2Api(orderNo);
        Gson gson = new Gson();
        HashMap hashMap = gson.fromJson(bodyString, HashMap.class);
        if ("SUCCESS".equals(hashMap.get("trade_state"))) {
            orderInfoService.updateStatus(orderNo, OrderStatus.SUCCESS.getType());
            //补充支付记录  唯一索引保证防止多插入支付日志
            paymentInfoService.insertPayLog(bodyString);

            //发送道具;
            SendPropRecord sendPropRecord = new SendPropRecord();
            sendPropRecord.setOrderNo(orderNo);
            sendPropRecord.setPropId(productId);
            restTemplate.postForEntity(sendPropUrl, sendPropRecord, R.class);

            return R.ok().setMsg("支付成功");
        }
        return R.ok().setCode(204).setMsg("支付中..");
    }






}
