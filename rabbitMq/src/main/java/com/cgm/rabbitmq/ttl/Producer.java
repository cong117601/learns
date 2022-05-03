package com.cgm.rabbitmq.ttl;

import com.cgm.rabbitmq.utils.RabbitMqConst;
import com.cgm.rabbitmq.utils.RabbitMqUtils;
import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.ConfirmCallback;

import java.io.IOException;

public class Producer {


    public static void main(String[] args) throws IOException {

        Channel channel = RabbitMqUtils.getChannel();
        //消息过期时间，过期进入死信队列
        //AMQP.BasicProperties build = new AMQP.BasicProperties.Builder().expiration("10000").build();

        for (int i = 0; i < 11; i++) {
            String msg = String.valueOf(i);
//            channel.basicPublish(RabbitMqConst.NORMAL_EXCHANGE, "zhangsan",
//                    build, msg.getBytes());
            channel.basicPublish(RabbitMqConst.NORMAL_EXCHANGE, "zhangsan",
                    null, msg.getBytes());
        }


    }
}
