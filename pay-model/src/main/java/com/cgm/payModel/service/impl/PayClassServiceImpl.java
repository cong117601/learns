package com.cgm.payModel.service.impl;


import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cgm.payModel.entity.PayClass;
import com.cgm.payModel.mapper.PayClassMapper;
import com.cgm.payModel.service.PayClassService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;

@Service
public class PayClassServiceImpl extends ServiceImpl<PayClassMapper, PayClass> implements PayClassService {

    @Autowired
    private PayClassMapper payClassMapper;

    @Override
    public void getPayClass() {
        List<PayClass> payClasses = payClassMapper.selectByMap(new HashMap<>());
        System.out.println(payClasses.size());
    }
}
