package com.cgm.springboot.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private PasswordEncoder passwordEncoder;

    /*
     * 重写loadUserByUsername方法
     */
    @Override
    public UserDetails loadUserByUsername(String username)
            throws UsernameNotFoundException {
        //实际是根据用户名去数据库查，这里就直接用静态数据了
        if (!username.equals("cgm")) {
            throw new UsernameNotFoundException("用户名不存在！");
        }
        //比较密码，匹配成功会返回UserDetails，实际上也会去数据库查
        String password = passwordEncoder.encode("cgm");
        User user = new User(username, password, AuthorityUtils.
                commaSeparatedStringToAuthorityList("cgm,admin,ROLE_cgm,/main.html"));
        return user;
    }

}
