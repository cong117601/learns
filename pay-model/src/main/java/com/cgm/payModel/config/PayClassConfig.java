package com.cgm.payModel.config;

import com.cgm.payModel.entity.PayClass;
import com.cgm.payModel.service.PayClassService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * 容器启动把所有的支付策略加载进来
 */
@Component
@Slf4j
public class PayClassConfig implements InitializingBean {


    @Autowired
    private PayClassService payClassService;


    @Override
    public void afterPropertiesSet() throws Exception {
        payClassService.getPayClass();
    }

}
