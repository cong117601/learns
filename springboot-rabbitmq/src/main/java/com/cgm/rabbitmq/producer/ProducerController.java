package com.cgm.rabbitmq.producer;

import com.cgm.rabbitmq.config.ConfirmConfig;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.MessagePostProcessor;
import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;

@Slf4j
@RestController
public class ProducerController {

    @Autowired
    private RabbitTemplate rabbitTemplate;

    @RequestMapping("/send/{msg}")
    public void sendMsg(@PathVariable("msg") String msg) {
        log.info("当前时间：{}，发送消息到TTL队列：{}", new Date().toString(), msg);

        rabbitTemplate.convertAndSend("X", "XA", "消息来自10s的队列" + msg);
        rabbitTemplate.convertAndSend("X", "XB", "消息来自40s的队列" + msg);
    }

    //增加消息的过期时间
    @RequestMapping("/send2/{msg}")
    public void sendMsg2(@PathVariable("msg") String msg) {
        log.info("当前时间：{}，发送消息到QC队列：{}", new Date().toString(), msg);
        MessagePostProcessor msfProcessor = (message) -> {
            message.getMessageProperties().setExpiration("10000");
            return message;
        };
        rabbitTemplate.convertAndSend("X", "XC", "消息来自XC的队列" + msg, msfProcessor);
    }


    //发送确认 测试交换机接收不到消息
    @RequestMapping("/confirm/{msg}")
    public void confirm(@PathVariable("msg") String msg) {
        CorrelationData correlationData = new CorrelationData(msg);
        rabbitTemplate.convertAndSend(ConfirmConfig.exchange_name, ConfirmConfig.routing_key
                , msg, correlationData);
        log.info("发送消息到confirm_queue队列：{}", msg);
    }

}
