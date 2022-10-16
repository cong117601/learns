package com.cgm.designPatterns.builder;

public abstract class AbstractBuilder {


    Phone phone;

    abstract AbstractBuilder customCpu(String cpu);

    abstract AbstractBuilder customMemory(String memory);

    abstract AbstractBuilder customColor(String color);

    abstract AbstractBuilder customLenth(String lenth);

    Phone gerProduct() {
        return phone;
    }
}
