package com.cgm.consumer;

import com.cgm.interfaces.HelloService;
import org.apache.dubbo.config.annotation.DubboReference;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
@Controller
public class TestController {


    @DubboReference(mock = "true",timeout = 200,loadbalance = "random")
    private  HelloService helloService;



    @RequestMapping("/get")
    @ResponseBody
    public String getOrder() {
        String result = helloService.say("msg");
        return result;
    }

}
