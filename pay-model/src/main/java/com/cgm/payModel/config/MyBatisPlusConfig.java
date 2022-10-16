package com.cgm.payModel.config;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Configuration
@MapperScan("com.cgm.payModel.mapper")
@EnableTransactionManagement
public class MyBatisPlusConfig {
}
