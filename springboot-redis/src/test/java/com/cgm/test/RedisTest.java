package com.cgm.test;

import com.MyApplication;
import com.cgm.utils.RedisUtils;
import org.junit.After;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = MyApplication.class)
public class RedisTest {


    @Autowired
    private RedisUtils redisUtils;

    @Test
    public void test1(){
       redisUtils.set("k3","中国2222222");
    }
}
