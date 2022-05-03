package com.cgm.springboot.security;

import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * 403异常处理
 */
@Component
public class MyAccessDeniedHandler implements AccessDeniedHandler {


    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response,
                       AccessDeniedException accessDeniedException) throws IOException, ServletException {
        response.setStatus(HttpServletResponse.SC_FORBIDDEN);
        // 设置响应头
        response.setHeader("Content-Type", "application/json;charset=utf-8");
        PrintWriter printWriter = response.getWriter();
        // 向页面中写内容
        printWriter.write("{\"status\":\"error\",\"msg\":\"权限不足，请联系管理员！\"}");
        printWriter.flush();
        printWriter.close();
    }

}
