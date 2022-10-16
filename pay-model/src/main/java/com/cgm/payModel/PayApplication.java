package com.cgm.payModel;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.PropertySource;
import org.springframework.web.client.RestTemplate;

@SpringBootApplication
@PropertySource("classpath:channel.yml") //读取配置文件
public class PayApplication {

    public static void main(String[] args) {
        ConfigurableApplicationContext run = SpringApplication.run(PayApplication.class, args);
        System.out.println(run.getBean("payClassConfig"));
    }


    @Bean
    public RestTemplate restTemplate(){
        return new RestTemplate();
    }
}
