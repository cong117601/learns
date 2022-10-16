package com.cgm.annocation;

import com.cgm.enums.DataType;

import java.lang.annotation.ElementType;
import java.lang.annotation.Target;

@Target(ElementType.METHOD)
public @interface DataSource {


    DataType value() default DataType.MASTER;



}
