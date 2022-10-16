package com.cgm.send.moke;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Data
@Configuration
@PropertySource("classpath:moke.properties") //读取配置文件
@ConfigurationProperties(prefix = "prop")
public class MokeData {


    private Map<Integer, Integer> config = new HashMap<>();


}
