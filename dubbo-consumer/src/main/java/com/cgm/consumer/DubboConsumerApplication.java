package com.cgm.consumer;

import com.cgm.interfaces.HelloService;
import org.apache.dubbo.config.annotation.DubboReference;
import org.apache.dubbo.config.spring.context.annotation.EnableDubbo;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

@SpringBootApplication(exclude = DataSourceAutoConfiguration.class)
@EnableDubbo
public class DubboConsumerApplication {




    public static void main(String[] args) {
        SpringApplication.run(DubboConsumerApplication.class, args);


    }

}
