package com.cgm.payModel.entity;

import lombok.Data;
import org.springframework.stereotype.Component;

@Component
@Data
public class PayBody {

    //支付类型
    private int type;
}
