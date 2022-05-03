package com.cgm.rabbitmq.workqueue;

import com.cgm.rabbitmq.utils.RabbitMqConst;
import com.cgm.rabbitmq.utils.RabbitMqUtils;
import com.rabbitmq.client.CancelCallback;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.DeliverCallback;

import java.io.IOException;

public class Work2 {


    public static void main(String[] args) throws IOException {


        Channel channel = RabbitMqUtils.getChannel();

        DeliverCallback deliverCallback = (consumerTag, message) -> {
            System.out.println(Thread.currentThread().getName() + "接收到的消息：" + new String(message.getBody()));
        };

        //取消消费的一个回调接口 如在消费的时候队列被删除掉了
        CancelCallback cancelCallback = (consumerTag) -> {
            System.out.println(consumerTag + "消费者取消消息 回调");
        };

        channel.basicConsume(RabbitMqConst.QUEUE_NAME_HELLO, true, deliverCallback, cancelCallback);

    }
}
