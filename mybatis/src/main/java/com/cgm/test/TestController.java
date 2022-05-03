package com.cgm.test;

import com.alibaba.fastjson.JSON;
import com.cgm.dao.master.UserMapper;
import com.cgm.dao.salve.StudentMapper;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
public class TestController {

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private StudentMapper studentMapper;

    @RequestMapping("/master")
    @Transactional
    public String getCount() {
        Map<String, String> userById = userMapper.getUserById(1);
        Map<String, String> userById1 = userMapper.getUserById(1);
        Map<String, String> userById2 = userMapper.getUserById(1);
        return JSON.toJSONString(userById1);
    }

    @RequestMapping("/salve")
    public String getFromSalve() {
        return studentMapper.getStudentCount() + "";

    }


    @RequestMapping("/page")
    public String getList() {
        PageHelper.startPage(2,10);
        List<Map<String, String>> userById = userMapper.getUserList();
        PageInfo<Map<String, String>> objectPageInfo = new PageInfo<>(userById);
        return JSON.toJSONString(objectPageInfo);
    }

}
