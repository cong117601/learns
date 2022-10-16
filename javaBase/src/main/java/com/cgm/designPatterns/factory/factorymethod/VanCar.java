package com.cgm.designPatterns.factory.factorymethod;


public class VanCar extends AbstractCar {

    public VanCar() {
        this.engine = "van 八缸";
    }

    @Override
    public void run() {
        System.out.println("van run ");
    }
}
