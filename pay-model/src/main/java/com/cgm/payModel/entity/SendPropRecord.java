package com.cgm.payModel.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@Data
@TableName("send_prop_record") //发送道具记录
public class SendPropRecord {

    private Integer id;

    private Integer uId;

    private Long propId;

    private String propName;

    private Integer propNum;

    private String orderNo;

}
