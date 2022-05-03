package com.cgm.springboot.edit;

import com.cgm.springboot.bean.Address;
import org.springframework.stereotype.Component;

import java.beans.PropertyEditorSupport;

public class MyEdit extends PropertyEditorSupport {
    @Override
    public void setAsText(String text) throws IllegalArgumentException {
        String[] s = text.split("_");
        Address address = new Address();
        address.setProvince(s[0]);
        address.setCity(s[1]);
        address.setTown(s[2]);
        this.setValue(address);
    }
}
