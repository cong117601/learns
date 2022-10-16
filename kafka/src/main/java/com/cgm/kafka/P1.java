package com.cgm.kafka;

import com.alibaba.fastjson.JSON;
import com.cgm.entiy.Order;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.clients.producer.RecordMetadata;
import org.apache.kafka.common.serialization.StringSerializer;

import java.util.Properties;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutionException;

public class P1 {


    private final static String TOPIC_NAME = "my-test-s";
    public static void main(String[] args) throws InterruptedException {
        Properties properties = new Properties();

        properties.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, "192.168.139.138:9092,192.168.139.140:9092,192.168.139.141:9092");
        //把发送的key从字符串序列化为字节数组
        properties.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());
        //把发送消息value从字符串序列化为字节数组
        properties.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());
        KafkaProducer<String, String> producer = new KafkaProducer<>(properties);
        properties.put(ProducerConfig.ACKS_CONFIG, "1");
        int msgNum = 5;

        //异步发送
//        final CountDownLatch countDownLatch = new CountDownLatch(msgNum);
//        for (int i = 1; i <= msgNum; i++) {
//            Order order = new Order(i, 100 + i);
//
//            ProducerRecord<String, String> producerRecord = new ProducerRecord<String, String>(TOPIC_NAME
//                    , String.valueOf(order.getId()), JSON.toJSONString(order));
//            //异步回调方式发送消息
//            producer.send(producerRecord, (metadata, exception) -> {
//                if (exception != null) {
//                    //1.记录发送失败的消息
//                    System.err.println("发送消息失败：" + exception.getStackTrace());
//
//                }
//                if (metadata != null) {
//                    System.out.println("异步方式发送消息结果：" + "topic-" + metadata.topic() + "|partition-"
//                            + metadata.partition() + "|offset-" + metadata.offset());
//                }
//                countDownLatch.countDown();
//            });
//
//        }
//        countDownLatch.await();

        //同步发送

        for (int i = 1; i <= msgNum; i++) {
            Order order = new Order(i, 100 + i);

            ProducerRecord<String, String> producerRecord = new ProducerRecord<String, String>(TOPIC_NAME,0
                    , String.valueOf(order.getId()), JSON.toJSONString(order));
            try {
                RecordMetadata metadata = producer.send(producerRecord).get();
                System.out.println("同步发送消息成功：" + "topic-" + metadata.topic() + "|partition-"
                            + metadata.partition() + "|offset-" + metadata.offset());
            } catch (ExecutionException e) {
                //1.记录失败的消息 / 重发
                try {
                    RecordMetadata metadata = producer.send(producerRecord).get();
                } catch (ExecutionException ex) {
                    //2.人工处理
                }
            }

        }

    }
}
