package com.cgm.springbootdocker;

import com.google.gson.Gson;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
@Slf4j
public class DockerController {

    @Autowired
    JdbcTemplate jdbcTemplate;

    @Autowired
    RedisTemplate redisTemplate;

    @RequestMapping("/getDocker")
    public String getDocker() {
        log.info("获取getDocker：docker");
        return "hello spring docker";
    }


    @RequestMapping("redis")
    public Long getRedis() {
        Long a = redisTemplate.opsForValue().increment("a");
        log.info("redis数据：" + a);
        return a;

    }


    @RequestMapping("getMysql")
    public String getMysql() {
        String sql = "select * from person";
        List<Map<String, Object>> maps = jdbcTemplate.queryForList(sql);
        log.info("mysql数据：" + maps);
        return new Gson().toJson(maps);
    }

}
