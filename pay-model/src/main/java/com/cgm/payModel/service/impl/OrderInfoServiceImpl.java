package com.cgm.payModel.service.impl;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cgm.payModel.entity.OrderInfo;
import com.cgm.payModel.entity.Product;
import com.cgm.payModel.enums.OrderStatus;
import com.cgm.payModel.mapper.OrderInfoMapper;
import com.cgm.payModel.mapper.ProductMapper;
import com.cgm.payModel.service.OrderInfoService;
import com.cgm.payModel.untils.OrderNoUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Duration;
import java.time.Instant;
import java.util.List;

@Service
public class OrderInfoServiceImpl extends ServiceImpl<OrderInfoMapper, OrderInfo> implements OrderInfoService {

    @Autowired
    private ProductMapper productMapper;

    @Autowired
    private OrderInfoMapper orderInfoMapper;

    @Transactional
    @Override
    public OrderInfo createOrderByProductId(Long productId) {

        Product product = productMapper.selectById(productId);
        if (product != null) {
            //创建订单
            OrderInfo orderInfo = new OrderInfo();
            orderInfo.setTitle(product.getTitle());
            orderInfo.setOrderNo(OrderNoUtils.getOrderNo());
            orderInfo.setTotalFee(product.getPrice());
            orderInfo.setUserId(1l);
            orderInfo.setOrderStatus(OrderStatus.NOTPAY.getType());
            orderInfo.setProductId(productId);
            orderInfoMapper.insert(orderInfo);
            return orderInfo;
        }


        return null;
    }

    @Override
    public OrderInfo selectOrderMsgByUidAndProductId(Long productId, long usrId) {
        QueryWrapper<OrderInfo> wrapper = new QueryWrapper<>();
        wrapper.eq("user_id", usrId);
        wrapper.eq("product_id", productId);
        wrapper.eq("order_status",OrderStatus.NOTPAY.getType());
        return orderInfoMapper.selectOne(wrapper);
    }

    @Override
    public void saveCoderUrl(String codeUrl, String orderNo) {
        QueryWrapper<OrderInfo> wrapper = new QueryWrapper<>();
        wrapper.eq("order_no", orderNo);
        OrderInfo orderInfo = new OrderInfo();
        orderInfo.setCodeUrl(codeUrl);
        orderInfoMapper.update(orderInfo, wrapper);
    }

    @Override
    public List<OrderInfo> getOrderList(Long userId) {
        QueryWrapper<OrderInfo> wrapper = new QueryWrapper<>();
        wrapper.eq("user_id", userId).orderByDesc("create_time");
        return orderInfoMapper.selectList(wrapper);
    }

    @Override
    public void updateStatus(String orderNo, String success) {
        OrderInfo orderInfo = new OrderInfo();
        orderInfo.setOrderStatus(success);
        orderInfoMapper.update(orderInfo,new QueryWrapper<OrderInfo>().eq("order_no",orderNo));
    }

    @Override
    public OrderInfo getOrderStatus(String orderNo) {
        return orderInfoMapper.selectOne(new QueryWrapper<OrderInfo>().eq("order_no", orderNo));
    }


    /**
     * 查询超过5分钟未支付的订单
     * @param i
     * @return
     */
    @Override
    public List<OrderInfo> getNoPayByDuration(int i) {

       //当前时间减去5min
        Instant minus = Instant.now().minus(Duration.ofMinutes(i));

        QueryWrapper<OrderInfo> wrapper = new QueryWrapper<>();
        wrapper.eq("order_status",OrderStatus.NOTPAY.getType());
        //小于五分钟之前
        wrapper.le("create_time",minus);
        List<OrderInfo> orderInfos = orderInfoMapper.selectList(wrapper);
        return orderInfos;
    }
}
