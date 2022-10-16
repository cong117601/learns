package com.cgm.springboot.dao;

import com.cgm.springboot.security.LoginUser;
import com.cgm.springboot.security.SysUser;

public interface UserDao {

    int getStudentCount();

    LoginUser selectUserByUserName(String userName);
}
