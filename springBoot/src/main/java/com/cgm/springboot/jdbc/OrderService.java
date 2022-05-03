package com.cgm.springboot.jdbc;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Component
public class OrderService {


    @Autowired
    JdbcTemplate jdbcTemplate;

    @Autowired
    OrderService orderService;

    @Transactional
    public void test() {
        jdbcTemplate.execute("INSERT INTO `test`.`class`(`id`, `name`) VALUES (2, '3.3');");
        orderService.a();
    }

    /**
     * 在本类中调用该方法，相当于代理对象的 普通对象orderService调用该方法，故 事务失效
     * 解决：拆分出来一个类，或者把orderSerivce注入此类中。
     *
     */
    @Transactional(propagation = Propagation.NEVER)
    public void a() {

    }
}
