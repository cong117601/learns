package com.cgm.payModel.service;


import com.baomidou.mybatisplus.extension.service.IService;
import com.cgm.payModel.entity.PayClass;
import com.cgm.payModel.entity.RefundInfo;

public interface PayClassService extends IService<PayClass> {


    void getPayClass();
}
