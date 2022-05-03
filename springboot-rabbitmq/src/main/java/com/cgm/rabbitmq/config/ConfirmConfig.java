package com.cgm.rabbitmq.config;

import org.springframework.amqp.core.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ConfirmConfig {

    //交换机
    public static String exchange_name = "confirm_exchange";
    public static String confirm_queue = "confirm_queue";
    public static String routing_key = "key1";


    //备份交换机
    public static String backup_exchange = "backup_exchange";
    public static String backup_queue = "backup_queue";

    public static String warning_queue = "warning_queue";

    @Bean
    public DirectExchange confirmExchange() {
        //return ExchangeBuilder.directExchange(exchange_name).durable(true).withArgument("alternate-exchange", backup_exchange).build();
        return new DirectExchange(exchange_name);
    }

    @Bean
    public Queue confirmQueue() {
        return new Queue(confirm_queue);
    }

    @Bean
    public Binding queueBindingExchange(Queue confirmQueue, DirectExchange confirmExchange) {
        return BindingBuilder.bind(confirmQueue).to(confirmExchange).with(routing_key);
    }

    //    备份
//    @Bean
//    public FanoutExchange backUpExchange() {
//        return new FanoutExchange(backup_exchange);
//    }

//    @Bean
//    public Queue backUpQueue() {
//        return new Queue(backup_queue);
//    }
//
//    @Bean
//    public Queue warningQueue() {
//        return new Queue(warning_queue);
//    }
//
//    @Bean
//    public Binding backUpQueueBindingBackUpExchange(Queue backUpQueue, FanoutExchange backUpExchange) {
//        return BindingBuilder.bind(backUpQueue).to(backUpExchange);
//    }
//
//    @Bean
//    public Binding waringQueueBindingBackUpExchange(Queue warningQueue, FanoutExchange backUpExchange) {
//        return BindingBuilder.bind(warningQueue).to(backUpExchange);
//    }
}
