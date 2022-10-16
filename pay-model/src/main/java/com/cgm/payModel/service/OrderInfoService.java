package com.cgm.payModel.service;


import com.baomidou.mybatisplus.extension.service.IService;
import com.cgm.payModel.entity.OrderInfo;
import com.cgm.payModel.enums.OrderStatus;

import java.util.List;

public interface OrderInfoService extends IService<OrderInfo> {


    OrderInfo createOrderByProductId(Long productId);


    OrderInfo selectOrderMsgByUidAndProductId(Long productId, long usrId);

    void saveCoderUrl(String codeUrl, String orderNo);

    List<OrderInfo> getOrderList(Long userId);

    void updateStatus(String orderNo, String success);

    OrderInfo getOrderStatus(String orderNo);

    List<OrderInfo> getNoPayByDuration(int i);
}
