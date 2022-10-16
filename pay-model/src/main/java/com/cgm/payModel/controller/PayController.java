package com.cgm.payModel.controller;

import com.cgm.payModel.payStrategy.Pay;
import com.cgm.payModel.untils.SpringUtils;
import com.cgm.payModel.vo.R;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.swing.*;
import java.util.Map;

@RestController
public class PayController {

    /**
     * 该接口主要用于createOrder,接收app请求，因为每个sdk的提交支付的参数不一样，所以该接口需要去选择不同的支付策略
     *
     * @return
     */
    @RequestMapping("create_order/{payType}")
    public R createOrder(@PathVariable String payType) {


        Pay payService = SpringUtils.getBean(payType);

        System.out.println(payService);

        Map<String, Object> order = payService.createOrder();

        return R.ok();
    }

    /**
     * 接收回调
     * 需要知道 哪一个支付渠道的回掉，在提交sdk支付请求时，有个额外自定义参数，从哪里获取
     * @param request
     * @param response
     */
    @RequestMapping("callBack")
    public void callBack(HttpServletRequest request, HttpServletResponse response) {
        String payType = String.valueOf(request.getAttribute("pay_type"));
        Pay payService = SpringUtils.getBean(payType);
        payService.callBack();

    }

}
