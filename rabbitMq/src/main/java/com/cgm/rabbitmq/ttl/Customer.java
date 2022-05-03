package com.cgm.rabbitmq.ttl;

import com.cgm.rabbitmq.utils.RabbitMqConst;
import com.cgm.rabbitmq.utils.RabbitMqUtils;
import com.rabbitmq.client.BuiltinExchangeType;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.DeliverCallback;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * 消息ttl过期，过期就不能消费
 * 消息无法消费
 * 队列满了。无法再添加数据到mq中国
 * 消息被拒绝
 */
public class Customer {

    public static void main(String[] args) throws IOException {
        Channel channel = RabbitMqUtils.getChannel();

        channel.exchangeDeclare(RabbitMqConst.NORMAL_EXCHANGE, BuiltinExchangeType.DIRECT);
        channel.exchangeDeclare(RabbitMqConst.DEAD_EXCHANGE, BuiltinExchangeType.DIRECT);

        Map<String, Object> map = new HashMap<>();
        //可以设置，也可以在发送消息时设置消息的过期时间
        //1。队列中10s没有消费，进入死信队列
        //2.也可以在发送消息时设定
        //map.put("x-message-ttl", 10000);
        map.put("x-dead-letter-exchange", RabbitMqConst.DEAD_EXCHANGE);
        map.put("x-dead-letter-routing-key", "lisi");

        //设置队列的长度，如果装满了 则剩下的进入死信
        map.put("x-max-length", 6);
        //消息拒绝

        //普通队列
        channel.queueDeclare(RabbitMqConst.QUEUE_NORMAL, false, false, false, map);
        //死信队列
        channel.queueDeclare(RabbitMqConst.QUEUE_DEAD, false, false, false, null);

        channel.queueBind(RabbitMqConst.QUEUE_NORMAL, RabbitMqConst.NORMAL_EXCHANGE, "zhangsan");
        channel.queueBind(RabbitMqConst.QUEUE_DEAD, RabbitMqConst.DEAD_EXCHANGE, "lisi");

        DeliverCallback deliverCallback = (onsumerTag, message) -> {
            String msg = new String(message.getBody());
            //拒绝接收消息,进入死信
            if (msg.equals("1")) {
                channel.basicReject(message.getEnvelope().getDeliveryTag(), false);
            } else {
                System.out.println(msg);
                channel.basicAck(message.getEnvelope().getDeliveryTag(), false);
            }


        };
        channel.basicConsume(RabbitMqConst.QUEUE_NORMAL, false, deliverCallback, consumerTag -> {
        });
    }
}
