package com.cgm;


import com.cgm.service.HelloService;
import org.apache.dubbo.config.annotation.DubboReference;
import org.apache.dubbo.config.annotation.Reference;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

@SpringBootApplication
public class CustomerApplication {
    @DubboReference(version = "default")
    private HelloService helloService;

    public static void main(String[] args) {
        ConfigurableApplicationContext run = SpringApplication.run(CustomerApplication.class);
        HelloService demoService = run.getBean(HelloService.class);
        System.out.println(demoService);

        //System.out.println((demoService.sayHello("cgm")));
    }
}
