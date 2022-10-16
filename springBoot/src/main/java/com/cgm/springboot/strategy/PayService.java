package com.cgm.springboot.strategy;

import com.cgm.springboot.Application;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;

@Service
public class PayService  {

    //@Autowired
    //private SDKServiceIssueRoute sdkServiceIssueRoute;

    @Autowired
    ApplicationContext application;

//    @Autowired
//    SDKService sdkService;

    public Object pay(String type) {
        SDKService qqservice = (SDKService)application.getBean(type);
//        SDKService sdkServiceSelect = sdkServiceIssueRoute.getSDKServiceSelect(type);
        Object order = qqservice.createOrder(null);
        return order;

    }


}
