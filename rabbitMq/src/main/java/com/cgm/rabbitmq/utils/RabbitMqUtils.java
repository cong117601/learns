package com.cgm.rabbitmq.utils;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

public class RabbitMqUtils {

    private static String QUEUE_NAME = "hello_queue";
    private static String address = "192.168.139.140";
    private static String username = "admin";
    private static String pwd = "123";


    public static Channel getChannel() {
        try {
            //创建一个连接工厂
            ConnectionFactory factory = new ConnectionFactory();
            factory.setHost(address);
            factory.setUsername(username);
            factory.setPassword(pwd);
            Connection connection = factory.newConnection();
            Channel channel = connection.createChannel();
            return channel;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }


}
