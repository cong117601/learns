package com.cgm.test;

import com.MyApplication;
import com.alibaba.fastjson.JSON;
import com.cgm.bean.User;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.clients.producer.RecordMetadata;
import org.apache.kafka.common.protocol.Message;

import org.junit.Test;
import org.junit.runner.RunWith;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.Acknowledgment;
import org.springframework.kafka.support.SendResult;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.util.concurrent.ListenableFuture;
import org.springframework.util.concurrent.ListenableFutureCallback;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = MyApplication.class)
public class KafkaProducer {
    private static final Logger log = LoggerFactory.getLogger(KafkaProducer.class);
    @Autowired
    KafkaTemplate<String, String> kafkaTemplate;
    private final static String TOPIC_NAME = "test2";


    @Test
    public void test1() {
        for (int i = 0; i < 10; i++) {
            User user = new User();
            user.setId(i);
            int name = (int) Math.random() * 100;
            user.setName(String.valueOf(name));
            int address = (int) Math.random() * 1000;
            user.setAddress(String.valueOf(address));
            int className = (int) Math.random() * 10;
            user.setClassName(String.valueOf(className));
            //同步发送
            SendResult<String, String> result = null;
            try {
                result = kafkaTemplate.send(TOPIC_NAME, 0, String.valueOf(i), JSON.toJSONString(user)).get();
                RecordMetadata recordMetadata = result.getRecordMetadata();
                //记录日志
                log.info("同步方式发送消息结果：topic-{},partition-{} ,offset-{}",recordMetadata.topic(),recordMetadata.partition(),recordMetadata.offset() );
            } catch (InterruptedException e) {
            } catch (ExecutionException e) {
                try {
                //重发
                SendResult<String, String> retryResult = kafkaTemplate.send(TOPIC_NAME, 0, String.valueOf(i), JSON.toJSONString(user)).get();
                }catch (ExecutionException | InterruptedException ex){
                    //人工处理
                    log.error("发送失败人工处理");
                }

            }

        }
    }

}
