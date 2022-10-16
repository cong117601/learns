package com.cgm.payModel.entity.paybody;

import com.cgm.payModel.entity.PayBody;
import org.springframework.stereotype.Component;

@Component
public class ZFBPayBody extends PayBody {
    @Override
    public int getType() {
        return 2;
    }
}
