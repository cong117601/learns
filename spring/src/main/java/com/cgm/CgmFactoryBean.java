package com.cgm;

import com.cgm.bean.A;
import org.springframework.beans.factory.FactoryBean;
import org.springframework.stereotype.Component;

@Component
public class CgmFactoryBean implements FactoryBean {
    @Override
    public Object getObject() throws Exception {
        return new A();
    }

    @Override
    public Class<?> getObjectType() {
        return A.class;
    }
}
