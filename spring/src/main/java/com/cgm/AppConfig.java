package com.cgm;

import com.cgm.bean.User;
import com.cgm.service.InitTask;
import com.cgm.service.UserService;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Component;

@Component
@ComponentScan("com.cgm")
public class AppConfig {


    @Bean
    public static User user() {
        return new User();
    }
}
