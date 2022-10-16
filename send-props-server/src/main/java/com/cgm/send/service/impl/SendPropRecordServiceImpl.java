package com.cgm.send.service.impl;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cgm.send.entiy.SendPropRecord;
import com.cgm.send.mapper.SendPropRecordMapper;
import com.cgm.send.moke.MokeData;
import com.cgm.send.service.SendPropRecordService;
import com.cgm.send.vo.R;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SendPropRecordServiceImpl extends ServiceImpl<SendPropRecordMapper, SendPropRecord> implements SendPropRecordService {

    @Autowired
    private SendPropRecordMapper sendPropRecordMapper;
    @Autowired
    private MokeData mokeData;

    @Override
    public R saveSendProps(SendPropRecord sendPropRecord) {

        synchronized (this) {
            QueryWrapper<SendPropRecord> wrapper = new QueryWrapper<>();
            wrapper.eq("order_no", sendPropRecord.getOrderNo());
            SendPropRecord selectOne = sendPropRecordMapper.selectOne(wrapper);
            if (selectOne != null) {
                return R.ok().setMsg("该订单已发货");
            }

            sendPropRecord.setPropId(sendPropRecord.getPropId());
            sendPropRecord.setPropNum(mokeData.getConfig().get(sendPropRecord.getPropId()));
            sendPropRecord.setUId(1);
            sendPropRecord.setPropName("砖石");
            //更新缓存
            //更新道具表
            //插入记录
            sendPropRecordMapper.insert(sendPropRecord);
        }


        return R.ok().setMsg("发货成功");
    }


}
