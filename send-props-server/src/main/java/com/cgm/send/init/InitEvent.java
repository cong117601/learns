package com.cgm.send.init;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Component;

@Component
public class InitEvent implements InitializingBean {


    @Override
    public void afterPropertiesSet() throws Exception {
        new Thread(() -> {
           //从道具队列中拿取上报来的玩家道具信息


        }).start();
    }


}
