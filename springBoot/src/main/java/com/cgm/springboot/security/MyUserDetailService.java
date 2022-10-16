package com.cgm.springboot.security;

import com.cgm.springboot.dao.UserDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

/**
 *  认证的数据源配置
 */
@Component
public class MyUserDetailService implements UserDetailsService {

    @Autowired
    private UserDao userDao;

    @Override
    public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException {


        LoginUser user = userDao.selectUserByUserName(userName);
        if (user == null) {
            throw new RuntimeException("系统用户不存在");
        }
        //权限
        user.setPermissions(null);
        return user;
    }


}
