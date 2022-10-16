package com.cgm.designPatterns.builder;

public class Phone {


    protected String cpu;

    protected String memory;

    protected String color;

    protected  String lenth;


    public String getCpu() {
        return cpu;
    }

    public String getMemory() {
        return memory;
    }

    public String getColor() {
        return color;
    }

    public String getLenth() {
        return lenth;
    }

    @Override
    public String toString() {
        return "Phone{" +
                "cpu='" + cpu + '\'' +
                ", memory='" + memory + '\'' +
                ", color='" + color + '\'' +
                ", lenth='" + lenth + '\'' +
                '}';
    }
}
