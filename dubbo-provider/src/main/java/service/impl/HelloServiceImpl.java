package service.impl;

import com.cgm.service.HelloService;
import org.apache.dubbo.config.annotation.DubboService;


@DubboService(version = "default" )
public class HelloServiceImpl implements HelloService {
    @Override
    public String sayHello(String msg) {

        return msg;
    }
}
