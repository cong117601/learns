package com.cmg;

import com.alibaba.fastjson.JSON;
import com.bean.User;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.Acknowledgment;
import org.springframework.stereotype.Component;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;


@Component
public class Consumer {


    @KafkaListener(topics = {"my-partitions"})
    public void consumer(ConsumerRecord<String, String> consumerRecord, Acknowledgment ack) {
        String value = consumerRecord.value();

        User user = (User) JSON.parse(value);


        ExecutorService executorService = Executors.newFixedThreadPool(10);

        executorService.submit(new Runnable() {
            @Override
            public void run() {

                //插入数据库


            }
        });


        executorService.shutdown();

        System.out.println(value);
        System.out.println(consumerRecord);
        ack.acknowledge();

    }
}
