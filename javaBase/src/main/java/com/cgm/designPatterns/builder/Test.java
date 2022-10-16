package com.cgm.designPatterns.builder;

public class Test {


    public static void main(String[] args) {

        AbstractBuilder xiaomiBuilder = new XiaomiBuilder();
        xiaomiBuilder.customCpu("intel");
        xiaomiBuilder.customLenth("12");
        xiaomiBuilder.customColor("白色");
        xiaomiBuilder.customMemory("4g");
        Phone phone = xiaomiBuilder.gerProduct();
        AbstractBuilder abstractBuilder = new XiaomiBuilder().customCpu("1").customMemory("2").customColor("11").customLenth("12");
        Phone phone1 = abstractBuilder.gerProduct();

        System.out.println(phone1);
    }
}
