package com.cgm.kafka;

import com.alibaba.fastjson.JSON;

import com.cgm.bean.User;
import org.apache.kafka.clients.consumer.Consumer;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.annotation.PartitionOffset;
import org.springframework.kafka.annotation.TopicPartition;
import org.springframework.kafka.support.Acknowledgment;
import org.springframework.stereotype.Component;

import java.util.Optional;


@Component
public class Customer {
    private static final Logger log = LoggerFactory.getLogger(KafkaProducer.class);

    //@KafkaListener(groupId="userInfoGroup", topicPartitions = {@TopicPartition(topic = "test2",partitionOffsets = @PartitionOffset(partition = "0",initialOffset = "5"))})
    public void consumer(ConsumerRecord<String, String> consumerRecord, Consumer consumer, Acknowledgment ack) {

        String value = consumerRecord.value();
        User user = JSON.parseObject(value,User.class);
        try {

            log.info("消费消息：{}",user.toString());
            // 同步提交
            //consumer.commitSync();

            //ack.acknowledge();
        } catch (Exception e) {
            log.info("消费消息失败：{}",user.toString());
        } finally {
            try {
               // consumer.commitSync();
            } finally {
               // consumer.close();
            }
        }

    }
    @KafkaListener(groupId="userInfoGroup2", topicPartitions = {@TopicPartition(topic = "test2",partitionOffsets = @PartitionOffset(partition = "0",initialOffset = "5"))})
    public void consumer2(ConsumerRecord<String, String> consumerRecord, Acknowledgment ack) {


        try {
            String value = consumerRecord.value();
            User user = JSON.parseObject(value,User.class);
            log.info("消费消息：{}",user.toString());
            ack.acknowledge();

        } catch (Exception e) {

        } finally {

        }

    }


   // @KafkaListener(topics = {"test2"},groupId="userInfoGroup3")
    void listen(ConsumerRecord<String, String> record, final Acknowledgment ack) {
        Optional<String> kafkaMessage = Optional.ofNullable(record.value());
        // Kafka send 未实现 Serializable 的实体 此处会接收到空的 Optional
        if (kafkaMessage.isPresent()) {
            String r = kafkaMessage.get();
            log.info("消费 [{}]", r);
            // 发送 ACK
            ack.acknowledge();
        }
    }


}
