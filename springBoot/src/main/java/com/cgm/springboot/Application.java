package com.cgm.springboot;

import com.cgm.springboot.bean.A;
import com.cgm.springboot.bean.Address;
import com.cgm.springboot.bean.Customer;
import com.cgm.springboot.bean.User;
import com.cgm.springboot.edit.AddressPropertyEditorRegistrar;
import com.cgm.springboot.jdbc.OrderService;
import com.cgm.springboot.service.UserService;
import org.apache.catalina.core.ApplicationContext;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.PropertyEditorRegistrar;
import org.springframework.beans.factory.config.CustomEditorConfigurer;
import org.springframework.boot.SpringApplication;

import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.client.RestTemplate;


@SpringBootApplication
@MapperScan("com.cgm.springboot.dao")
@EnableTransactionManagement
@EnableScheduling
public class Application {

    public static void main(String[] args) {
        ConfigurableApplicationContext run = SpringApplication.run(Application.class, args);
//        User user = (User) run.getBean("user");
//        System.out.println(user.getAge());
//        UserService userService = (UserService) run.getBean("userService");
//        System.out.println(userService.get());
//        System.out.println(userService.get());
//
//        UserService u1 = (UserService) run.getBean("userService");
//        System.out.println(u1.get());


//        userService.eat();
////        System.out.println(userService.get());


//        OrderService orderService = (OrderService) run.getBean("orderService");
//        orderService.test();

//
//        Customer bean = run.getBean(Customer.class);
//        Address address = bean.getAddress();
//        System.out.println(address);

    }


    @Bean
    public CustomEditorConfigurer customEditorConfigurer() {
        CustomEditorConfigurer customEditorConfigurer = new CustomEditorConfigurer();
        customEditorConfigurer.setPropertyEditorRegistrars(new PropertyEditorRegistrar[]{new AddressPropertyEditorRegistrar()});
        return customEditorConfigurer;
    }


}
