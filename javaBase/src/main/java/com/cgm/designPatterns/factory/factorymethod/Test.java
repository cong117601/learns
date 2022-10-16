package com.cgm.designPatterns.factory.factorymethod;

public class Test {

    public static void main(String[] args) {

        MiniCarFactory miniCarFactory = new MiniCarFactory();
        AbstractCar abstractCar = miniCarFactory.newCar();
        abstractCar.run();

    }

}
