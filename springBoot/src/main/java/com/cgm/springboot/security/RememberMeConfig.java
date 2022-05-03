package com.cgm.springboot.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.web.authentication.rememberme.JdbcTokenRepositoryImpl;
import org.springframework.security.web.authentication.rememberme.PersistentTokenRepository;

import javax.sql.DataSource;


@Configuration
public class RememberMeConfig {

    // 注入数据源
    @Autowired
    private DataSource dataSource;

    // 注入Bean
    @Bean
    public PersistentTokenRepository getPersistentTokenRepository() {
        JdbcTokenRepositoryImpl jdbcTokenRepositoryImpl = new JdbcTokenRepositoryImpl();
        // 设置数据源
        jdbcTokenRepositoryImpl.setDataSource(dataSource);
        // 自动建表，第一次运行设为true，以后都设为false
        jdbcTokenRepositoryImpl.setCreateTableOnStartup(false);
        return jdbcTokenRepositoryImpl;
    }

}
