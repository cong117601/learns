package com.cgm.springboot.edit;

import com.cgm.springboot.bean.Address;
import org.springframework.beans.PropertyEditorRegistrar;
import org.springframework.beans.PropertyEditorRegistry;

import org.springframework.stereotype.Component;

public class AddressPropertyEditorRegistrar implements PropertyEditorRegistrar {
    @Override
    public void registerCustomEditors(PropertyEditorRegistry registry) {
        registry.registerCustomEditor(Address.class, new MyEdit());
    }
}
