package com.cgm.springboot.test;

import com.cgm.springboot.bean.User;
import com.cgm.springboot.dao.UserDao;
import com.cgm.springboot.strategy.PayService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
public class TestController {
    @Autowired
    User user;

    @Autowired
    PayService payService;


    @Autowired
    private AuthenticationManager authenticationManager;

    @RequestMapping("/login")
    public String toLogin(String username,String password){
        System.out.println("login");
        return "main";
    }

    //@PreAuthorize("hasAnyRole('cgm1')")
    @RequestMapping("/main")
    public String getUser() {
//        System.out.println(user.getId());
        System.out.println("toMain");
       // return "redirect:main.html";
        return "main";
    }

    //加了模板 类才能这样写，不然还是使用上面的方式
    @RequestMapping("/toError")
    public String toError() {
//        System.out.println(user.getId());
        System.out.println("toError");
        return "error";
    }


    @GetMapping("/test")
    @ResponseBody
    public String test() {
        System.out.println("all Look");
        return "test ok";
    }
    @GetMapping("/test2")
    @ResponseBody
    public String test2() {
        System.out.println("all Look2");
        return "test2 ok";
    }


    //@PreAuthorize("hasRole('add')")
    @PreAuthorize("hasAuthority('add')")
    @RequestMapping("add")
    @ResponseBody
    public void add() {
        System.out.println("执行add method");
    }


    @RequestMapping("/pay/{type}")
    public void pay(@PathVariable("type") String type) {
        Object qq = payService.pay(type);
        System.out.println(qq);
    }

    @RequestMapping("/")
    public String toLoginPage(){
        System.out.println("11");
        return "login";
    }

    @Autowired
    private UserDao userDao;

    @RequestMapping("/data")
    @ResponseBody
    public int getCount(){
        int studentCount = userDao.getStudentCount();
        return studentCount;
    }

}
