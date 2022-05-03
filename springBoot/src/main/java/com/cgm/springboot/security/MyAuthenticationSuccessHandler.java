package com.cgm.springboot.security;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 登录成功 重定向处理器 ，默认是转发
 */

public class MyAuthenticationSuccessHandler implements AuthenticationSuccessHandler {

    private String url;

    // 构造方法
    public MyAuthenticationSuccessHandler(String url) {
        this.url = url;
    }

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
                                        Authentication authentication) throws IOException, ServletException {
        // 获取用户
        User user = (User) authentication.getPrincipal();
        // 打印用户名
        System.out.println(user.getUsername());
        // 密码，出于安全考虑，Spring Security这里会返回一个Null
        System.out.println(user.getPassword());
        // 权限
        System.out.println(user.getAuthorities());
        // 这里使用跳转
        response.sendRedirect(url);

    }

}
