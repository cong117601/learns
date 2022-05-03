package com.cgm.rabbitmq.producer;

import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

/**
 * 交换机的回调，如果交换机 收到了 但是不可路由到队列，那此时消息不会走ack=false的逻辑。
 * 这条消息就丢失了,此时需要配置mandatory,并 实现ReturnCallback接口 把消息回退给生产者
 * 也可以把这个交换机备份到 备份交换机上,之后不可路由的消息会转发到 备份队列中或者报警队列中 进行排查
 * 备份交换机的优先级高
 */
@Slf4j
@Component
public class ConfirmCallback implements RabbitTemplate.ConfirmCallback, RabbitTemplate.ReturnCallback {
    @Autowired
    private RabbitTemplate rabbitTemplate;

    @PostConstruct
    public void init() {
        rabbitTemplate.setConfirmCallback(this);
        rabbitTemplate.setReturnCallback(this);
    }

    /**
     * 交换机回调确认方法
     * 1.发消息，交换机接收到了，也会回调
     * correlationData：保存回调消息的ID及相关信息
     * 2。消失发送失败了
     */
    @Override
    public void confirm(CorrelationData correlationData, boolean ack, String cause) {
        if (ack) {
            log.info("交换机收到消息：{}", correlationData.getId());
        } else {
            log.info("交换机没有接收到消息，原因：{}", cause);
        }
    }

    @Override
    public void returnedMessage(Message message, int replyCode, String replyText, String exchange, String routingKey) {
        log.error("消息：{},被交换机{}回退，返回原因：{}，routingkey为：{}", message.getBody(), exchange, replyText, routingKey);
    }
}
