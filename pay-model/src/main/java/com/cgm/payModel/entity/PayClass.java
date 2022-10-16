package com.cgm.payModel.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@Data
@TableName("t_pay_class")
public class PayClass {

    private Integer id;

    private String payType;


    private String payClass;
}
