package com.cgm.springboot.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.rememberme.PersistentTokenRepository;
import org.springframework.stereotype.Component;

@Configuration
@EnableGlobalMethodSecurity(securedEnabled = true ,prePostEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Bean
    public PasswordEncoder getPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }
    @Autowired
    private UserDetailsServiceImpl userDetailsServiceImpl;
    @Autowired
    private MyAccessDeniedHandler myAccessDeniedHandler;
    @Autowired
    private PersistentTokenRepository persistentTokenRepository;
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }


    @Override
    public void configure(WebSecurity web) throws Exception {
        web.ignoring().antMatchers("/static/**"); //放行static/static下的所有文件
    }


    @Override
    protected void configure(HttpSecurity http) throws Exception {
        //表单提交
        http.formLogin().loginPage("/login.html")//自定义登录页面
                //必须和表单提交的接口路径一致，会去执行自定义登录逻辑
                .loginProcessingUrl("/login")
                //登录成功后跳转到的页面，只接受post请求
                .successHandler(new MyAuthenticationSuccessHandler("/main.html"))
                .failureHandler(new MyAuthenticationFailureHandler("/error.html"));
        //自定义异常处理
        http.exceptionHandling().accessDeniedHandler(myAccessDeniedHandler);


        //授权
//        http.authorizeRequests()
//                .antMatchers("/login.html").permitAll()//放行的路径
//                .antMatchers("/error.html").permitAll()
//                .regexMatchers(HttpMethod.GET, "/test").permitAll()
//                //.antMatchers("/main1.html").hasAnyAuthority("111")
//                .antMatchers("/main1.html").hasAnyRole("cgm1")
//
//                .anyRequest().authenticated();//除了放行路径，其他路径都需要授权


        //基于表达式授权
        http.authorizeRequests().antMatchers("/login.html").access("permitAll")//放行的路径
                .antMatchers("/error.html").access("permitAll").regexMatchers(HttpMethod.GET, "/test").access("permitAll")
                //.antMatchers("/main1.html").hasAnyAuthority("111")
                .antMatchers("/main1.html").access("hasRole('cgm1')");

        //.anyRequest().authenticated();//除了放行路径，其他路径都需要授权
        //.anyRequest().access("@myServiceImpl.hasPermission(request,authentication)");

        //关闭csrf防护
        http.csrf().disable();


        //记住我功能
        http.rememberMe()
                //参数名，和表单中的一样
                .rememberMeParameter("rememberMe")
                //持久层对象
                .tokenRepository(persistentTokenRepository)
                //登录逻辑设置
                .userDetailsService(userDetailsServiceImpl)
                //失效时间，默认为两周，这里设为60秒
                .tokenValiditySeconds(30);

    }

}
