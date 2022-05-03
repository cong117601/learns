package com.cgm.springboot.test;

import com.cgm.springboot.bean.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
public class TestController {
    @Autowired
    User user;

    @PreAuthorize("hasAnyRole('cgm1')")
    @RequestMapping("/main")
    public String getUser() {
//        System.out.println(user.getId());
        System.out.println("toMain");
        return "redirect:main.html";
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
    public void test() {
        System.out.println("all Look");
    }


    //@PreAuthorize("hasRole('add')")
    @PreAuthorize("hasAuthority('add')")
    @RequestMapping("add")
    @ResponseBody
    public void add() {
        System.out.println("执行add method");
    }
}
