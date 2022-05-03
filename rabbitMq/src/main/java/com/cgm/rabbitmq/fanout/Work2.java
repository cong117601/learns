package com.cgm.rabbitmq.fanout;

import com.cgm.rabbitmq.utils.RabbitMqConst;
import com.cgm.rabbitmq.utils.RabbitMqUtils;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.DeliverCallback;

public class Work2 {

    public static void main(String[] args) throws Exception {
        Channel channel = RabbitMqUtils.getChannel();
        channel.exchangeDeclare(RabbitMqConst.EXCHANGE_NAME, "fanout");

        //绑定交换机与队列
        channel.queueBind(RabbitMqConst.QUEUE_FANOUT_LOG_2, RabbitMqConst.EXCHANGE_NAME, "");
        System.out.println("等待接收消息");

        //接收消息
        DeliverCallback deliverCallback = (consumerTag, delivery) -> {
            String message = new String(delivery.getBody());
            System.out.println(message);
        };

        channel.basicConsume(RabbitMqConst.QUEUE_FANOUT_LOG_2, true, deliverCallback, consumerTag -> {
        });

    }
}
