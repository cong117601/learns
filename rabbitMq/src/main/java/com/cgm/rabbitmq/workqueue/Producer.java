package com.cgm.rabbitmq.workqueue;

import com.cgm.rabbitmq.utils.RabbitMqConst;
import com.cgm.rabbitmq.utils.RabbitMqUtils;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

import java.util.Scanner;

public class Producer {

    public static void main(String[] args) throws Exception {


        try (Channel channel = RabbitMqUtils.getChannel();) {
            channel.queueDeclare(RabbitMqConst.QUEUE_NAME_HELLO, false, false, false, null);
            //从控制台当中接受信息
            Scanner scanner = new Scanner(System.in);
            while (scanner.hasNext()) {
                String message = scanner.next();
                channel.basicPublish("", RabbitMqConst.QUEUE_NAME_HELLO, null, message.getBytes());
                System.out.println("发送消息完成:" + message);
            }
        }
    }

}
