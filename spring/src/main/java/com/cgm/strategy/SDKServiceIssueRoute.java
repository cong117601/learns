package com.cgm.strategy;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 分发路由
 */
@Service
public class SDKServiceIssueRoute {


    @Autowired
    SDKService[] services;


    public SDKService getSDKServiceSelect(String channelType) {
        //对渠道验证
        //选择实现
        for (SDKService service : services) {

            if (service.getType().equals(channelType)) {
                return service;
            }
        }
        return null;
    }
}
