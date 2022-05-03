package com.cgm.springboot.security;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.Collection;
@Service
public class MyServiceImpl implements MyService{
    @Override
    public boolean hasPermission(HttpServletRequest request, Authentication authentication) {


        // 获取当前主体
        Object obj = authentication.getPrincipal();
        // 如果属于UserDetails
        if (obj instanceof UserDetails) {
            // 下转型
            UserDetails userDetails = (UserDetails) obj;
            // 获取主体的所有权限
            Collection<? extends GrantedAuthority> authorities = userDetails
                    .getAuthorities();
            SimpleGrantedAuthority simpleGrantedAuthority = new SimpleGrantedAuthority(
                    request.getRequestURI());
            boolean res = authorities.contains(simpleGrantedAuthority);
            return res;
        }
        return false;

    }
}
