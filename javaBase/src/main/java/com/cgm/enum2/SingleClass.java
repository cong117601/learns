package com.cgm.enum2;

public class SingleClass {

    public String name;

    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }

    //随便一个方法
    public void printname(){
        System.out.println("SingleClass的属性name=" + name);
    }
}
