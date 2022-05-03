package com.cgm.rabbitmq.publishack;

import com.cgm.rabbitmq.utils.RabbitMqConst;
import com.cgm.rabbitmq.utils.RabbitMqUtils;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.ConfirmCallback;
import org.apache.commons.lang3.StringUtils;

import java.io.IOException;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.ConcurrentNavigableMap;
import java.util.concurrent.ConcurrentSkipListMap;

/**
 * 生产者 发布确认机制
 * 1.单个确认
 * 2.批量确认
 * 3.异步确认
 */
public class Producer {

    private static int count = 100;
    private static int batch = 100;

    public static void main(String[] args) throws Exception {
        //publishMessageAck("one");
        //publishMessageAck("batch");
        publishMessageAsync();
    }


    public static void publishMessageAck(String flag) throws Exception {
        Channel channel = RabbitMqUtils.getChannel();
        channel.queueDeclare(RabbitMqConst.PUBLISH_ACK, true, false
                , false, null);

        //开启发布确认
        channel.confirmSelect();

        long start = System.currentTimeMillis();

        for (int i = 0; i < count; i++) {
            channel.basicPublish("", RabbitMqConst.PUBLISH_ACK
                    , null, String.valueOf(i).getBytes());

            //单个确认
            if (flag.equals("one")) {
                boolean b = channel.waitForConfirms();
                if (b) {
                    System.out.println("success: " + i);

                }
            }
            if (flag.equals("batch") && i != 0 && i % 100 == 0) {
                boolean b = channel.waitForConfirms();
                if (b) {
                    System.out.println("success: " + i);

                }
            }
        }
        long end = System.currentTimeMillis();

        System.out.println(end - start);
    }


    //异步确认
    public static void publishMessageAsync() throws Exception {
        Channel channel = RabbitMqUtils.getChannel();
        channel.queueDeclare(RabbitMqConst.PUBLISH_ACK, true, false
                , false, null);


        //开启发布确认
        channel.confirmSelect();

        //消息监听器，监听消失成功 或失败
        ConfirmCallback ackCallback = (deliveryTag, multiple) -> {

            System.out.println("success:" + deliveryTag);
        };
        ConfirmCallback nackCallback = (deliveryTag, multiple) -> {
            System.out.println("未确认的消息:" + deliveryTag);
        };

        channel.addConfirmListener(ackCallback, nackCallback);


        long start = System.currentTimeMillis();

        for (int i = 0; i < count; i++) {

            String msg = String.valueOf(i);
            channel.basicPublish("", RabbitMqConst.PUBLISH_ACK
                    , null, msg.getBytes());

        }

        long end = System.currentTimeMillis();
        System.out.println(end - start);
    }
}
