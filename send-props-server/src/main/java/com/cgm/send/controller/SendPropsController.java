package com.cgm.send.controller;

import com.cgm.send.entiy.SendPropRecord;
import com.cgm.send.moke.MokeData;
import com.cgm.send.service.SendPropRecordService;
import com.cgm.send.vo.R;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
public class SendPropsController {

    @Autowired
    private SendPropRecordService sendPropRecordService;


    @Autowired
    private MokeData mokeData;

    @PostMapping("/sendProp")
    public R sendProps(@RequestBody SendPropRecord sendPropRecord){

        return sendPropRecordService.saveSendProps(sendPropRecord);
    }

    @GetMapping("/test")
    public void  sendProps(){

        System.out.println(mokeData.getConfig().get(1));
    }
}
