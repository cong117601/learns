package com.cgm.designPatterns.factory.simplefactory;

public class MiniCar extends AbstractCar {

    public MiniCar() {
        this.engine = "mini 四缸";
    }

    @Override
    public void run() {
        System.out.println("mini run ");
    }
}
