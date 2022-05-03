package com.cgm.dao.master;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;


public interface UserMapper {


    Map<String,String> getUserById(@Param("id") Integer id);

    List<Map<String, String>> getUserList();
}
