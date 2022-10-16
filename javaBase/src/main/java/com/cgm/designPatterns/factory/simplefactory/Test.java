package com.cgm.designPatterns.factory.simplefactory;

public class Test {


    public static void main(String[] args) throws ClassNotFoundException, InstantiationException, IllegalAccessException {
        CarSimpleFactory carSimpleFactory = new CarSimpleFactory();
        AbstractCar van = carSimpleFactory.getCar("com.cgm.designPatterns.simplefactory.VanCar");
        AbstractCar mini = carSimpleFactory.getCar("com.cgm.designPatterns.simplefactory.MiniCar");
        mini.run();
        van.run();
    }
}
