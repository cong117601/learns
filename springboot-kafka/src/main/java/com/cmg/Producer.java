package com.cmg;

import com.alibaba.fastjson.JSON;
import com.bean.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.xml.ws.Action;

//@RestController
public class Producer {
   //my-partitions
    private final static String TOPIC_NAME = "test2";
    @Autowired
    KafkaTemplate<String, String> kafkaTemplate;

    @RequestMapping("/send")
    public String sendMsg() {
        for (int i = 0; i < 10; i++) {
            User user = new User();
            user.setId(i);
            int name = (int) Math.random() * 100;
            user.setName(String.valueOf(name));
            int address = (int) Math.random() * 1000;
            user.setAddress(String.valueOf(address));
            int className = (int) Math.random() * 10;
            user.setClassName(String.valueOf(className));
            kafkaTemplate.send(TOPIC_NAME, 0, String.valueOf(i), JSON.toJSONString(user));
        }
        return "ok";
    }

}
