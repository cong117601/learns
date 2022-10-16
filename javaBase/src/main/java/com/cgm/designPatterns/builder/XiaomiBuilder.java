package com.cgm.designPatterns.builder;

public class XiaomiBuilder extends AbstractBuilder {
    public XiaomiBuilder() {
        this.phone = new Phone();
    }

    @Override
    AbstractBuilder customCpu(String cpu) {
        this.phone.cpu = cpu;
        return this;
    }

    @Override
    AbstractBuilder customMemory(String memory) {
        this.phone.memory = memory;
        return this;
    }

    @Override
    AbstractBuilder customColor(String color) {
        this.phone.color = color;
        return this;
    }

    @Override
    AbstractBuilder customLenth(String lenth) {
        this.phone.lenth = lenth;
        return this;
    }
}
