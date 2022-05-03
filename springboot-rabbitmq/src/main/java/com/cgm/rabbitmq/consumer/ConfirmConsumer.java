package com.cgm.rabbitmq.consumer;

import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class ConfirmConsumer {


    @RabbitListener(queues = "confirm_queue")
    public void receiveMsg(Message message) {
        log.info("接收消息为：{}", message);
    }
}
