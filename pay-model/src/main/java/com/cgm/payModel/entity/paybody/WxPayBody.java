package com.cgm.payModel.entity.paybody;

import com.cgm.payModel.entity.PayBody;
import org.springframework.stereotype.Component;

@Component
public class WxPayBody extends PayBody {

    @Override
    public int getType() {
        return 1;
    }
}
