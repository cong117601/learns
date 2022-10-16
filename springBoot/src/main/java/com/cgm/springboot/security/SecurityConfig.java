package com.cgm.springboot.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.parameters.P;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.authentication.RememberMeServices;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.logout.LogoutHandler;
import org.springframework.security.web.authentication.rememberme.JdbcTokenRepositoryImpl;
import org.springframework.security.web.authentication.rememberme.PersistentTokenBasedRememberMeServices;
import org.springframework.security.web.authentication.rememberme.PersistentTokenRepository;
import org.springframework.security.web.session.HttpSessionEventPublisher;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.security.web.util.matcher.OrRequestMatcher;
import org.springframework.web.context.request.DestructionCallbackBindingListener;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSessionBindingListener;
import javax.sql.DataSource;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    //自定义的认证数据原，默认是内存中的
    @Autowired
    private MyUserDetailService userDetailsService;

    //模拟数据源
    public UserDetailsService userDetailsService() {
        InMemoryUserDetailsManager inMemoryUserDetailsManager = new InMemoryUserDetailsManager();
        inMemoryUserDetailsManager.createUser(User.withUsername("123").password("{noop}123").roles("admin").build());
        return inMemoryUserDetailsManager;

    }

    //自定义 userSerivice 配置认知数据原
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService).passwordEncoder(bCryptPasswordEncoder());
    }

    /**
     * 把AuthenticationManager 暴露出去
     *
     * @return
     * @throws Exception
     */
    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }
    /**
     * 强散列哈希加密实现
     */
    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }




    @Autowired
    private DataSource dataSource;
    //持久化令牌
    @Bean
    public PersistentTokenRepository persistentTokenRepository() {
        JdbcTokenRepositoryImpl jdbcTokenRepository = new JdbcTokenRepositoryImpl();
        jdbcTokenRepository.setDataSource(dataSource);
        jdbcTokenRepository.setCreateTableOnStartup(false);
        return jdbcTokenRepository;
    }

    //web 开发配置
  //  @Override
//    protected void configure(HttpSecurity httpSecurity) throws Exception {
//        httpSecurity.authorizeRequests()
//                .antMatchers("/test", "/").permitAll()
//                .anyRequest().authenticated()
//                .and()
//                //自定义登录页面
//                .formLogin()
//                .loginPage("/")
//                .loginProcessingUrl("/doLogin")
//                .successForwardUrl("/main")  //转发
//                //.defaultSuccessUrl("/main")  //重定向
//                .failureForwardUrl("/toError")
//                //.failureUrl("/toError")
////                .successHandler(myLoginSuccessHandler)
////                .failureHandler(myLoginFailureHandler)
//                .and()
//                .logout()
////                .logoutRequestMatcher(new OrRequestMatcher(
////                        new AntPathRequestMatcher("/logout2","GET"),
////                        new AntPathRequestMatcher("/logout3","POST")
////                ))
//                .logoutUrl("/logout")
//                //.addLogoutHandler()
//                .invalidateHttpSession(true)
//                .clearAuthentication(true)
//                .logoutSuccessUrl("/") //注销之后回到 登录页面从
//                // .addLogoutHandler()
//                .and()
//                .rememberMe()
////                .tokenRepository(persistentTokenRepository())
//                .rememberMeServices(webrememberMeServices())
//                //csrf
//                .and()
//                .csrf().disable()
//                .sessionManagement()
//                .maximumSessions(1)
//                .expiredUrl("/"); //最大并发
//
//
////                .failureForwardUrl("/toError")
//    }

    //传统web记住我
    @Bean
    public RememberMeServices webrememberMeServices() {

        return new PersistentTokenBasedRememberMeServices(UUID.randomUUID().toString(), userDetailsService, persistentTokenRepository());
    }

    @Bean
    public HttpSessionEventPublisher httpSessionEventPublisher() {
        return new HttpSessionEventPublisher();
    }


    @Autowired
    private MyLoginSuccessHandler myLoginSuccessHandler;

    @Autowired
    private MyLoginFailureHandler myLoginFailureHandler;


    //前后端分离登录过滤器，主要处理json参数而不是 表单
    @Bean
    public LoginFilter loginFilter() throws Exception {
        LoginFilter loginFilter = new LoginFilter();
        loginFilter.setFilterProcessesUrl("/doLogin");
        loginFilter.setAuthenticationManager(authenticationManagerBean());
        loginFilter.setAuthenticationSuccessHandler(myLoginSuccessHandler);
        loginFilter.setRememberMeServices(rememberMeServices());
//        loginFilter.setAuthenticationSuccessHandler((request, response, authentication) -> {
//
//                    Map<String, Object> map = new HashMap<>();
//                    map.put("msg", "登录成功");
//                    map.put("status", 200);
//                    map.put("authentication", authentication.getPrincipal());
//                    response.setContentType("application/json;charset=utf-8");
//                    String s = new ObjectMapper().writeValueAsString(map);
//                    response.getWriter().println(s);
//                }
//        );
        loginFilter.setAuthenticationFailureHandler(myLoginFailureHandler);
        return loginFilter;
    }

    //前后端分离开发
    @Override
    protected void configure(HttpSecurity httpSecurity) throws Exception {
        httpSecurity.authorizeRequests()
                .anyRequest().authenticated() //所有请求认证
                .and()
                //自定义登录页面
                .formLogin()
                .and()
                .logout()
                .logoutSuccessHandler((request, response, authentication) -> {
                    Map<String, Object> map = new HashMap<>();
                    map.put("msg", "注销成功");
                    map.put("status", 200);
                    map.put("authentication", authentication.getPrincipal());
                    response.setContentType("application/json;charset=utf-8");
                    String s = new ObjectMapper().writeValueAsString(map);
                    response.getWriter().println(s);
                })
                .and()
                .exceptionHandling()
                .authenticationEntryPoint((req, resp, e) -> {
                    Map<String, Object> map = new HashMap<>();
                    map.put("msg", "未被认证");
                    map.put("status", 401);
                    resp.setContentType("application/json;charset=utf-8");
                    String s = new ObjectMapper().writeValueAsString(map);
                    resp.getWriter().println(s);
                })
                .and()
                .rememberMe() //开启记住我，1认证成功保存记住我 cookie到客户端，2只有cookie写入客户端成功才能实现自动登录功能
                .rememberMeServices(rememberMeServices())
                .and()
                .csrf().disable()
                .sessionManagement()
                .maximumSessions(1) //最大并发;
                .expiredSessionStrategy(strategy->{
                    HttpServletResponse response = strategy.getResponse();
                    response.setContentType("application/json;charset=utf-8");
                    Map<String, Object> map = new HashMap<>();
                    map.put("msg", "当前会话失效，请重新登录");
                    map.put("status", 500);
                    String s = new ObjectMapper().writeValueAsString(map);
                    response.getWriter().println(s);
                }).maxSessionsPreventsLogin(true); //禁止再次登录
        //at：用来替换过滤器链中的某个filter
        //before: 放在某个过滤器 之前
        //after：放在某个过滤器之后
        httpSecurity.addFilterAt(loginFilter(), UsernamePasswordAuthenticationFilter.class);
    }
    //前后端分离时的记住我
//    @Bean
    public MyRememberMeServices rememberMeServices() {
        return new MyRememberMeServices(UUID.randomUUID().toString(), userDetailsService, persistentTokenRepository());
    }

    public static void main(String[] args) {


        BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
        String encode = bCryptPasswordEncoder.encode("123");
        System.out.println(encode);
    }
}
