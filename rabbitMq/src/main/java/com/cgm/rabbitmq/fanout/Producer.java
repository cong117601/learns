package com.cgm.rabbitmq.fanout;

import com.cgm.rabbitmq.utils.RabbitMqConst;
import com.cgm.rabbitmq.utils.RabbitMqUtils;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.MessageProperties;

import java.util.Scanner;

/**
 * 发布订阅
 * 广播模式 交换机
 */
public class Producer {


    public static void main(String[] args) throws Exception {
        Channel channel = RabbitMqUtils.getChannel();
        channel.exchangeDeclare(RabbitMqConst.EXCHANGE_NAME, "fanout");
        //从控制台当中接受信息
        Scanner scanner = new Scanner(System.in);
        while (scanner.hasNext()) {
            String message = scanner.next();
            channel.basicPublish(RabbitMqConst.EXCHANGE_NAME, "", null, message.getBytes());
            System.out.println("发送消息完成:" + message);
        }


    }
}
