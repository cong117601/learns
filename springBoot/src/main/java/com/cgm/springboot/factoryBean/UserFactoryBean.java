package com.cgm.springboot.factoryBean;

import com.cgm.springboot.bean.User;
import org.springframework.beans.factory.FactoryBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.stereotype.Component;

//@EnableConfigurationProperties
//@Component
//public class UserFactoryBean implements FactoryBean {
//
//    @Value("${userId}")
//    private String id;
//    @Value("${name}")
//    private String name;
//
//    @Override
//    public Object getObject() throws Exception {
//        return new User(id, name);
//    }
//
//    @Override
//    public Class<?> getObjectType() {
//        return User.class;
//    }
//
//}
