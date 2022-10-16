package com.cgm.payModel.vo;

import lombok.Data;
import lombok.experimental.Accessors;

import java.util.HashMap;
import java.util.Map;

@Data
@Accessors(chain = true)
public class R {

    private Integer code;
    private String msg;
    private Map<String, Object> data = new HashMap<>();


    public static R ok() {
        R r = new R();
        r.setCode(0);
        r.setMsg("成功");
        return r;
    }

    public static R error(String msg) {
        R r = new R();
        r.setMsg(msg);
        r.setCode(-1);
        return r;
    }

    public R data(String key, Object value) {
        this.data.put(key, value);
        return this;

    }

}
