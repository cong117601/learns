package com.cgm.payModel.controller;

import com.cgm.payModel.config.WxPayConfig;
import com.cgm.payModel.enums.OrderStatus;
import com.cgm.payModel.service.WxPayService;
import com.cgm.payModel.untils.HttpUtils;
import com.cgm.payModel.untils.WechatPay2ValidatorFoRequest;
import com.cgm.payModel.vo.R;
import com.google.gson.Gson;
import com.wechat.pay.contrib.apache.httpclient.auth.ScheduledUpdateCertificatesVerifier;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.ThreadUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

@RestController
@RequestMapping("/api/wx-pay")
@Api(tags = "微信支付API")
@Slf4j
public class WxPayController {

    @Autowired
    private WxPayService wxPayService;

    @Autowired
    private ScheduledUpdateCertificatesVerifier verifier;

    @ApiOperation("调用统一下单API,生成支付二维码")
    @PostMapping("native/{productId}")
    public R nativePay(@PathVariable Long productId) throws IOException {

        log.info("发起支付请求");
        Map<String, Object> map = wxPayService.nativePay(productId);
        return R.ok().setData(map);
    }

    /**
     * 支付结果回调接口
     *
     * @param request
     * @param response
     * @return
     */
    @PostMapping("/native/notify")
    public String nativeNotity(HttpServletRequest request, HttpServletResponse response) {
        Gson gson = new Gson();
        Map<String, String> respMap = new HashMap<>();
        try {
            String body = HttpUtils.readData(request);
            HashMap bodyMap = gson.fromJson(body, HashMap.class);
            String requestId = String.valueOf(bodyMap.get("id"));
            log.info("支付通知的body ==>{}", body);
            //验签 微信公钥（在证书里，为什么要使用微信公钥呢，因为需要保证这个请求是微信发过来的,确保是真数据）
            WechatPay2ValidatorFoRequest wechatPay2ValidatorFoRequest = new WechatPay2ValidatorFoRequest(verifier, requestId, body);
            //回调超时 5min 验证不通过 =>false ?
//            try {
//                TimeUnit.MINUTES.sleep(5);
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            }

            boolean validate = wechatPay2ValidatorFoRequest.validate(request);
            if (!validate) {
                log.info("回调参数签名验证失败");
                response.setStatus(500);
                respMap.put("code", "fail");
                respMap.put("message", "回调参数签名验证失败");
                return gson.toJson(respMap);
            }

            log.info("签名验证成功,参数解密");


            //处理订单
            //TODO 需要模拟 支付回调失败
            wxPayService.processOrder(bodyMap);


            log.info("订单处理完成");


            //发送应答
            response.setStatus(200);
            respMap.put("code", "SUCCESS");
            respMap.put("message", "成功");
            return gson.toJson(respMap);
        } catch (Exception e) {
            response.setStatus(500);
            respMap.put("code", "fail");
            respMap.put("message", "失败");
            return gson.toJson(respMap);
        }


    }

    /**
     * 查询远程订单状态
     *
     * @param orderNo
     * @return
     */
    @ApiOperation("查询远程订单状态")
    @GetMapping("/query/{orderNo}")
    public R queryOrderStatus2Api(@PathVariable("orderNo") String orderNo) throws IOException {
        String resp = wxPayService.queryOrderStatus2Api(orderNo);
        return R.ok().setMsg(resp);
    }


    @RequestMapping("/cancel/{orderNo}")
    public R cannel(@PathVariable String orderNo) {
        R r = R.ok();
        log.info("取消订单:{}", orderNo);
        return wxPayService.cancelOrder(orderNo) ? r.setMsg("取消成功") : r.setMsg("取消失败");

    }


    /**
     * 退款
     *
     * @param orderNo
     * @param reason
     * @return
     */
    @PostMapping("refunds/{orderNo}/{reason}")
    public R refundsOrder(@PathVariable String orderNo, @PathVariable String reason) {

        boolean b = wxPayService.refundsOrder(orderNo, reason);

        return b ? R.ok().setMsg("申请退款成功"):R.error("申请退款失败");
    }


    /**
     * 退款回调
     * @param
     */
    @PostMapping("/refunds/notify")
    public String refundsNotity(HttpServletRequest request, HttpServletResponse response) {
        Gson gson = new Gson();
        Map<String, String> respMap = new HashMap<>();
        try {
            String body = HttpUtils.readData(request);
            HashMap bodyMap = gson.fromJson(body, HashMap.class);
            String requestId = String.valueOf(bodyMap.get("id"));
            //验签 微信公钥（在证书里，为什么要使用微信公钥呢，因为需要保证这个请求是微信发过来的,确保是真数据）
            WechatPay2ValidatorFoRequest wechatPay2ValidatorFoRequest = new WechatPay2ValidatorFoRequest(verifier, requestId, body);


            boolean validate = wechatPay2ValidatorFoRequest.validate(request);
            if (!validate) {
                log.info("回调参数签名验证失败");
                response.setStatus(500);
                respMap.put("code", "fail");
                respMap.put("message", "回调参数签名验证失败");
                return gson.toJson(respMap);
            }

            log.info("签名验证成功,参数解密");


            //处理退款单
            wxPayService.processRefund(bodyMap);


            log.info("订单处理完成");


            //发送应答
            response.setStatus(200);
            respMap.put("code", "SUCCESS");
            respMap.put("message", "成功");
            return gson.toJson(respMap);
        } catch (Exception e) {
            response.setStatus(500);
            respMap.put("code", "fail");
            respMap.put("message", "失败");
            return gson.toJson(respMap);
        }


    }


    public static void main(String[] args) {

        System.out.println(OrderStatus.NOTPAY.equals(null));
    }

}
