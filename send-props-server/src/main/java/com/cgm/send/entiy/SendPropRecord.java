package com.cgm.send.entiy;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@Data
@TableName("send_prop_record") //发送道具记录
public class SendPropRecord{
    //定义主键策略：跟随数据库的主键自增
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    private Integer uId;

    private Integer propId;

    private String propName;

    private Integer propNum;

    private String orderNo;

}
