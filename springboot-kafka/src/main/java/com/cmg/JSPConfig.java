package com.cmg;

import org.springframework.boot.web.embedded.tomcat.TomcatServletWebServerFactory;
import org.springframework.boot.web.servlet.server.AbstractServletWebServerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.io.File;


public class JSPConfig {


    @Bean
    public AbstractServletWebServerFactory embeddedServletContainerFactory(){
        TomcatServletWebServerFactory tomcatServletWebServerFactory = new TomcatServletWebServerFactory();
        tomcatServletWebServerFactory.setDocumentRoot(new File("E:\\learns-dev\\springboot-kafka\\src\\main\\webapp"));
        return tomcatServletWebServerFactory;
    }

}
