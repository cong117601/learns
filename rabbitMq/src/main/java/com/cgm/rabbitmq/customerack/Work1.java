package com.cgm.rabbitmq.customerack;

import com.cgm.rabbitmq.utils.RabbitMqConst;
import com.cgm.rabbitmq.utils.RabbitMqUtils;
import com.rabbitmq.client.CancelCallback;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.DeliverCallback;

import java.io.IOException;

public class Work1 {


    public static void main(String[] args) throws IOException {

        System.out.println("c1接收到消息睡1s");
        Channel channel = RabbitMqUtils.getChannel();

        DeliverCallback deliverCallback = (consumerTag, message) -> {
            try {
                Thread.sleep(30000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println(Thread.currentThread().getName() + "接收到的消息：" + new String(message.getBody()));
            //消费完消息 手动应答
            //1.消息标志
            //2.是否批量
            channel.basicAck(message.getEnvelope().getDeliveryTag(), false);
        };
        //1不公平分发 ，也可以指定消费多少条（预取值）
        channel.basicQos(5);
        //取消消费的一个回调接口 如在消费的时候队列被删除掉了
        CancelCallback cancelCallback = (consumerTag) -> {
            System.out.println(consumerTag + "消费者取消消息 回调");
        };


        //手动应答
        boolean ack = false;
        channel.basicConsume(RabbitMqConst.ACK_QUEUE, ack, deliverCallback, cancelCallback);

    }
}
