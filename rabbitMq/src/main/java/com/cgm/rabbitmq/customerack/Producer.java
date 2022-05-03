package com.cgm.rabbitmq.customerack;

import com.cgm.rabbitmq.utils.RabbitMqConst;
import com.cgm.rabbitmq.utils.RabbitMqUtils;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.MessageProperties;

import java.util.Scanner;

public class Producer {


    public static void main(String[] args) {

        try (Channel channel = RabbitMqUtils.getChannel();) {
            //队列持久化
            channel.queueDeclare(RabbitMqConst.ACK_QUEUE, true, false, false, null);
            //从控制台当中接受信息
            Scanner scanner = new Scanner(System.in);
            while (scanner.hasNext()) {
                String message = scanner.next();
                //消息持久化
                channel.basicPublish("", RabbitMqConst.ACK_QUEUE, MessageProperties.PERSISTENT_TEXT_PLAIN, message.getBytes());
                System.out.println("发送消息完成:" + message);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


}
