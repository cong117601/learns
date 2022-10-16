package com.cgm.payModel.mapper;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.cgm.payModel.entity.PaymentInfo;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;

public interface PaymentInfoMapper extends BaseMapper<PaymentInfo> {

    @Insert("    insert into t_payment_info" +
            "            (order_no," +
            "             transaction_id," +
            "             payment_type," +
            "             trade_type," +
            "             trade_state," +
            "             payer_total," +
            "             content,create_time,update_time)" +
            "    values" +
            "            (#{paymentInfo.orderNo}," +
            "            #{paymentInfo.transactionId}," +
            "            #{paymentInfo.paymentType}," +
            "            #{paymentInfo.tradeType}," +
            "            #{paymentInfo.tradeState},#{paymentInfo.payerTotal},#{paymentInfo.content}," +
            "    now()," +
            "    now()" +
            "         )" +
            "    ON DUPLICATE KEY UPDATE" +
            "    trade_state = values(trade_state),content = values(content)"
             )
    void insertPayLog2(@Param("paymentInfo") PaymentInfo paymentInfo);


}
