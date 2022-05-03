package com.cgm.springboot.bean;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
@Component
@Scope(scopeName = "prototype")
public class A {
}
