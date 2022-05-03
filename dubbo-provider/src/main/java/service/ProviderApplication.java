package service;


import com.alibaba.dubbo.config.annotation.Reference;
import com.cgm.service.HelloService;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import service.impl.HelloServiceImpl;

@SpringBootApplication
public class ProviderApplication {


    public static void main(String[] args) {

//        System.setProperty("zookeeper.sasl.client", "false");
        SpringApplication.run(ProviderApplication.class);
    }
}
