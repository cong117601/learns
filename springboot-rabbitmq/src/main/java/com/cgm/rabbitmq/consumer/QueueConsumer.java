package com.cgm.rabbitmq.consumer;

import com.rabbitmq.client.Channel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import java.util.Date;

@Slf4j
@Component
public class QueueConsumer {


    @RabbitListener(queues = "QD")
    public void receviceMsg(Message msg, Channel channel) {
        String m = new String(msg.getBody());
        log.info("当前时间：{}，接收到消息：{}", new Date().toString(), m);
    }


    @RabbitListener(queues = "QA")
    public void receviceMsg2(Message msg, Channel channel) {
        String m = new String(msg.getBody());
        log.info("当前时间：{}，接收到消息：{}", new Date().toString(), m);
    }
}
