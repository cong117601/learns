package com.cgm.payModel.job;

import com.cgm.payModel.entity.OrderInfo;
import com.cgm.payModel.enums.OrderStatus;
import com.cgm.payModel.enums.wxpay.WxTradeState;
import com.cgm.payModel.mapper.PaymentInfoMapper;
import com.cgm.payModel.service.OrderInfoService;
import com.cgm.payModel.service.PaymentInfoService;
import com.cgm.payModel.service.WxPayService;
import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

//@EnableScheduling
@Component
public class WxTask {

   @Autowired
   private OrderInfoService orderInfoService;

   @Autowired
   private WxPayService wxPayService;

   @Autowired
   private PaymentInfoService paymentInfoService;

    /**
     * 再次检查超时未支付的订单
     * @throws IOException
     */
    //@Scheduled(cron = "0/10 * * * * ?")
    @Transactional
    public void test() throws IOException {
        List<OrderInfo> orderList =  orderInfoService.getNoPayByDuration(5);
        if(orderList!=null && !orderList.isEmpty()){
            for (OrderInfo orderInfo : orderList) {
                String orderNo = orderInfo.getOrderNo();
                //从微信拉拉取  查看订单状态
                String body = wxPayService.queryOrderStatus2Api(orderNo);
                Gson gson = new Gson();
                HashMap resultMap = gson.fromJson(body, HashMap.class);
                Object tradeState = resultMap.get("trade_state");
                //如果订单支付了,则更新状态插入支付记录，发送道具
                if(WxTradeState.SUCCESS.getType().equals(tradeState)){
                    orderInfoService.updateStatus(orderNo, OrderStatus.SUCCESS.getType());
                    paymentInfoService.insertPayLog(body);
                    //发送道具


                }else{
                    //否则 调用关单接口，修改订单状态为 取消支付
                    wxPayService.cancelOrder(orderNo);
                    orderInfoService.updateStatus(orderNo, OrderStatus.CANCEL.getType());
                }

            }
        }

    }
}
