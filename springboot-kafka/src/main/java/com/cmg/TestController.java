package com.cmg;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class TestController {
    @RequestMapping("/hello")
    public String toIndex() {
        System.out.println("ada");
        return "hello";
    }
}
