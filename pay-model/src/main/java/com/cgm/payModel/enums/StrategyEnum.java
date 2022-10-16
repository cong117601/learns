package com.cgm.payModel.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum StrategyEnum  {

    ZfbPayStrategy("com.example.designdemo.pay.strategy.ZfbPayStrategy"),

    WxPayStrategy("com.example.designdemo.pay.strategy.WxPayStrategy"),

    BankPayStrategy("com.example.designdemo.pay.strategy.BankPayStrategy");

    String value;


}

