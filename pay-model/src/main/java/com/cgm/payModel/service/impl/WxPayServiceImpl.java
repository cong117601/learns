package com.cgm.payModel.service.impl;

import com.cgm.payModel.config.WxPayConfig;
import com.cgm.payModel.entity.OrderInfo;
import com.cgm.payModel.entity.RefundInfo;
import com.cgm.payModel.entity.SendPropRecord;
import com.cgm.payModel.enums.OrderStatus;
import com.cgm.payModel.enums.PayType;
import com.cgm.payModel.enums.wxpay.WxApiType;
import com.cgm.payModel.enums.wxpay.WxNotifyType;
import com.cgm.payModel.enums.wxpay.WxRefundStatus;
import com.cgm.payModel.enums.wxpay.WxTradeState;
import com.cgm.payModel.mapper.OrderInfoMapper;
import com.cgm.payModel.service.OrderInfoService;
import com.cgm.payModel.service.PaymentInfoService;
import com.cgm.payModel.service.RefundInfoService;
import com.cgm.payModel.service.WxPayService;
import com.cgm.payModel.untils.OrderNoUtils;
import com.cgm.payModel.vo.R;
import com.google.gson.Gson;
import com.sun.org.apache.bcel.internal.generic.NEW;
import com.wechat.pay.contrib.apache.httpclient.WechatPayHttpClientBuilder;
import com.wechat.pay.contrib.apache.httpclient.util.AesUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.util.EntityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;
import springfox.documentation.spring.web.json.Json;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.charset.StandardCharsets;
import java.security.GeneralSecurityException;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

@Service
@Slf4j
public class WxPayServiceImpl implements WxPayService {
    @Autowired
    WxPayConfig wxPayConfig;

    @Autowired
    CloseableHttpClient wxPayClient;


    @Autowired
    OrderInfoService orderInfoService;

    @Autowired
    PaymentInfoService paymentInfoService;

    @Autowired
    RefundInfoService refundInfoService;

    @Autowired
    RestTemplate restTemplate;

    @Value("${propServer.sendPropUrl}")
    String sendPropUrl;


    @Override
    @Transactional
    public Map<String, Object> nativePay(Long productId) throws IOException {
        //检查该用户是否有未支付的该产品的订单信息
        OrderInfo orderInfo = orderInfoService.selectOrderMsgByUidAndProductId(productId, 1l);
        if (orderInfo == null) {
            //创建订单
            orderInfo = orderInfoService.createOrderByProductId(productId);
            if (orderInfo == null) {
                throw new RuntimeException("没有相关产品");
            }
        }

        //发送请求

        HttpPost httpPost = new HttpPost(wxPayConfig.getDomain().concat(WxApiType.NATIVE_PAY.getType()));
        // 请求body参数
        Gson gson = new Gson();
        HashMap map = new HashMap();
        map.put("appid", wxPayConfig.getAppid());
        map.put("mchid", wxPayConfig.getMchId());
        map.put("description", orderInfo.getTitle());
        map.put("out_trade_no", orderInfo.getOrderNo());
        map.put("notify_url", wxPayConfig.getNotifyDomain().concat(WxNotifyType.NATIVE_NOTIFY.getType()));

        HashMap amountMap = new HashMap();
        amountMap.put("total", orderInfo.getTotalFee());
        amountMap.put("currency", "CNY");
        map.put("amount", amountMap);
        String jsonParams = gson.toJson(map);

        StringEntity entity = new StringEntity(jsonParams, "utf-8");
        entity.setContentType("application/json");
        httpPost.setEntity(entity);
        httpPost.setHeader("Accept", "application/json");

        //完成签名并执行请求
        CloseableHttpResponse response = wxPayClient.execute(httpPost);

        try {
            String resp = EntityUtils.toString(response.getEntity());
            int statusCode = response.getStatusLine().getStatusCode();
            if (statusCode == 200) { //处理成功
                Map<String, String> resultMap = gson.fromJson(resp, HashMap.class);
                String codeUrl = resultMap.get("code_url");
                HashMap<String, Object> m = new HashMap<>();
                m.put("codeUrl", codeUrl);
                m.put("orderNo", orderInfo.getOrderNo());

                //更新付款码
                orderInfoService.saveCoderUrl(codeUrl, orderInfo.getOrderNo());

                return m;
            } else {
                //System.out.println("failed,resp code = " + statusCode + ",return body = " + resp);
                throw new IOException("request failed");
            }


        } finally {
            response.close();
        }

    }

    @Override
    public String queryOrderStatus2Api(String orderNo) throws IOException {
        log.info("从wx查询订单状态");
        String url = wxPayConfig.getDomain().concat(String.format(WxApiType.ORDER_QUERY_BY_NO.getType(), orderNo)).concat("?mchid=").concat(wxPayConfig.getMchId());
        HttpGet httpGet = new HttpGet(url);
        httpGet.setHeader("Accept", "application/json");

        CloseableHttpResponse response = wxPayClient.execute(httpGet);

        try {
            Gson gson = new Gson();
            String bodyString = EntityUtils.toString(response.getEntity());
            log.info("查询微信订单状态:{}", bodyString);
            int statusCode = response.getStatusLine().getStatusCode();
            if (statusCode == 200) {
                HashMap hashMap = gson.fromJson(bodyString, HashMap.class);
                return bodyString;
            } else {
                //失败
                throw new IOException("request failed");
            }
        } finally {
            response.close();
        }

    }

    /**
     * 解密回调 body
     *
     * @param bodyMap
     * @return
     */
    @Override
    @Transactional
    public void processOrder(HashMap bodyMap) {
        String orderMsg = this.decryptOrder(bodyMap);
        Gson gson = new Gson();

        Map orderMsgMap = gson.fromJson(orderMsg, Map.class);

        String orderNo = (String) orderMsgMap.get("out_trade_no");

        //处理重复通知
        OrderInfo orderInfo = orderInfoService.getOrderStatus(orderNo);
        //防止通知并发 重复插入日志
        //回调
        synchronized (this) {
            if (orderInfo == null || (!StringUtils.equalsIgnoreCase(orderInfo.getOrderStatus(), OrderStatus.NOTPAY.getType()))) {
                return;
            }
            //        try {
            //             TimeUnit.SECONDS.sleep(5);
            //        } catch (InterruptedException e) {
            //            e.printStackTrace();
            //        }
            if (orderMsgMap.get("trade_state").equals(WxTradeState.SUCCESS.getType())) {
                log.info("更新订单为支付成功");
                orderInfoService.updateStatus(orderNo, OrderStatus.SUCCESS.getType());
                //记录支付日志
                paymentInfoService.insertPayLog(orderMsg);
                log.info("插入支付记录成功");

                //发送道具
                int i = 1 / 0;
                SendPropRecord sendPropRecord = new SendPropRecord();
                sendPropRecord.setOrderNo(orderNo);
                sendPropRecord.setPropId(orderInfo.getProductId());
                restTemplate.postForEntity(sendPropUrl, sendPropRecord, R.class);
            }

        }


    }

    @Override
    public boolean cancelOrder(String orderNo) {

        //调用微信 关单接口
        try {
            boolean b = this.closeOrder(orderNo);
            if (!b) {
                return false;
            }
            //更新订单状态
            orderInfoService.updateStatus(orderNo, OrderStatus.CANCEL.getType());
            return true;
        } catch (IOException e) {
            throw new RuntimeException("关单异常");
        }

    }

    @Override
    @Transactional
    public boolean refundsOrder(String orderNo, String reason) {

        log.info("创建退款单:{}", orderNo);
        RefundInfo refundInfo = refundInfoService.createRefundByOrderNo(orderNo, reason);

        String url = wxPayConfig.getDomain().concat(WxApiType.DOMESTIC_REFUNDS.getType());

        HttpPost httpPost = new HttpPost(url);
        Gson gson = new Gson();
        //组装退款参数
        HashMap paramsMap = new HashMap();
        paramsMap.put("out_trade_no", orderNo);
        paramsMap.put("out_refund_no", refundInfo.getRefundNo());
        paramsMap.put("reason", reason);
        paramsMap.put("notify_url", wxPayConfig.getNotifyDomain().concat(WxNotifyType.REFUND_NOTIFY.getType()));

        HashMap amountMap = new HashMap();
        amountMap.put("refund", refundInfo.getRefund());
        amountMap.put("total", refundInfo.getTotalFee());
        amountMap.put("currency", "CNY");
        paramsMap.put("amount", amountMap);
        String paramStr = gson.toJson(paramsMap);

        StringEntity entity = new StringEntity(paramStr, "utf-8");
        entity.setContentType("application/json");
        httpPost.setEntity(entity);
        httpPost.setHeader("Accept", "application/json");
        try {
            CloseableHttpResponse response = wxPayClient.execute(httpPost);

            String bodyString = EntityUtils.toString(response.getEntity());
            int statusCode = response.getStatusLine().getStatusCode();
            if (statusCode == 200) {

                //更新订单状态
                orderInfoService.updateStatus(orderNo, OrderStatus.REFUND_PROCESSING.getType());
                //更新退款单状态
                refundInfoService.updateRefund(bodyString);
                return true;
            } else {
                return false;
            }
        } catch (IOException e) {
            return false;
        }

    }

    @Override
    @Transactional
    public void processRefund(HashMap bodyMap) {
        log.info("退款单");
        String plainText = this.decryptOrder(bodyMap);
        log.info("退款回调解密：" + plainText);
        Gson gson = new Gson();
        HashMap plainTextMap = gson.fromJson(plainText, HashMap.class);
        String orderNo = (String) plainTextMap.get("out_trade_no");

        synchronized (this) {
            OrderInfo orderInfo = orderInfoService.getOrderStatus(orderNo);
            //订单不是正在退款  则返回
            if (!orderInfo.getOrderStatus().equals(OrderStatus.REFUND_PROCESSING.getType())) {
                return;
            }
            //在支付中 则获取通知的状态
            if (plainTextMap.get("refund_status").equals(WxRefundStatus.SUCCESS.getType())) {
                orderInfoService.updateStatus(orderNo, OrderStatus.REFUND_SUCCESS.getType());
                //同时更新 退款单状态
                refundInfoService.updateRefund(plainText);
            }

        }

    }


    private boolean closeOrder(String orderNo) throws IOException {
        String url = String.format(wxPayConfig.getDomain().concat(WxApiType.CLOSE_ORDER_BY_NO.getType()), orderNo);
        HttpPost httpPost = new HttpPost(url);
        Gson gson = new Gson();
        String jsonMap = gson.toJson(Collections.singletonMap("mchid", wxPayConfig.getMchId()));
        StringEntity entity = new StringEntity(jsonMap, "utf-8");
        entity.setContentType("application/json");
        httpPost.setEntity(entity);
        httpPost.setHeader("Accept", "application/json");
        CloseableHttpResponse response = wxPayClient.execute(httpPost);
        try {
            int statusCode = response.getStatusLine().getStatusCode();
            if (statusCode == 204) { //处理成功
                return true;
            } else {
                return false;
            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            response.close();
        }
        return false;
    }


    /**
     * 解密
     *
     * @param bodyMap
     * @return
     */
    private String decryptOrder(HashMap bodyMap) {
        Map<String, String> resourceMap = (Map) bodyMap.get("resource");
        //取出随机字符串
        String nonce = resourceMap.get("nonce");
        //附加数据
        String associatedData = resourceMap.get("associated_data");
        //数据密文
        String ciphertext = resourceMap.get("ciphertext");


        AesUtil aesUtil = new AesUtil(wxPayConfig.getApiV3Key().getBytes(StandardCharsets.UTF_8));
        try {
            String data = aesUtil.decryptToString(associatedData.getBytes(StandardCharsets.UTF_8), nonce.getBytes(StandardCharsets.UTF_8), ciphertext);
            log.info("回调请求参数解密成功");
            return data;
        } catch (GeneralSecurityException e) {
            throw new RuntimeException("订单解密失败！");
        }
    }
}
