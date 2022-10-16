package com.cgm.send.service;


import com.baomidou.mybatisplus.extension.service.IService;
import com.cgm.send.entiy.SendPropRecord;
import com.cgm.send.vo.R;

public interface SendPropRecordService extends IService<SendPropRecord> {

    R saveSendProps(SendPropRecord sendPropRecord);
}
