package com.cgm.rabbitmq.config;

import org.springframework.amqp.core.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;

@Configuration
public class RabbtiMqConfig {

    //普通交换机
    public static String X_EXCHANGE = "X";
    //死信交换机
    public static String Y_DEAD_LETTER_EXCHANGE = "Y";
    //普通队列
    public static String QUEUE_A = "QA";
    //普通队列
    public static String QUEUE_B = "QB";
    //死信队列
    public static String DEAD_LETTER_QUEUE = "QD";

    public static String QUEUE_C = "QC";

    @Bean
    public DirectExchange xExchange() {
        return new DirectExchange(X_EXCHANGE);
    }

    @Bean
    public DirectExchange yExchange() {
        return new DirectExchange(Y_DEAD_LETTER_EXCHANGE);
    }

    //声明队列 TTL 10S
    @Bean
    public Queue queueA() {
        HashMap<String, Object> map = new HashMap<>();
        map.put("x-dead-letter-exchange", Y_DEAD_LETTER_EXCHANGE);
        map.put("x-dead-letter-routing-key", "YD");
        map.put("x-message-ttl", 10000);
        return QueueBuilder.durable(QUEUE_A).withArguments(map).build();
    }

    //TTL 40S
    @Bean
    public Queue queueB() {
        HashMap<String, Object> map = new HashMap<>();
        map.put("x-dead-letter-exchange", Y_DEAD_LETTER_EXCHANGE);
        map.put("x-dead-letter-routing-key", "YD");
        map.put("x-message-ttl", 40000);
        return QueueBuilder.durable(QUEUE_B).withArguments(map).build();
    }


    @Bean
    public Queue queueD() {
        return QueueBuilder.durable(DEAD_LETTER_QUEUE).build();
    }


    @Bean
    public Queue queueC() {
        HashMap<String, Object> map = new HashMap<>();
        map.put("x-dead-letter-exchange", Y_DEAD_LETTER_EXCHANGE);
        map.put("x-dead-letter-routing-key", "YD");
        return QueueBuilder.durable(QUEUE_C).withArguments(map).build();
    }

    //绑定关系
    @Bean
    public Binding queueABindX(Queue queueA, DirectExchange xExchange) {
        return BindingBuilder.bind(queueA).to(xExchange).with("XA");
    }

    //绑定关系
    @Bean
    public Binding queueBBindX(Queue queueB, DirectExchange xExchange) {
        return BindingBuilder.bind(queueB).to(xExchange).with("XB");
    }


    //绑定关系
    @Bean
    public Binding queueCBindX(Queue queueC, DirectExchange xExchange) {
        return BindingBuilder.bind(queueC).to(xExchange).with("XC");
    }

    //绑定关系
    @Bean
    public Binding queueDBindY(Queue queueD, DirectExchange yExchange) {
        return BindingBuilder.bind(queueD).to(yExchange).with("YD");
    }



}
