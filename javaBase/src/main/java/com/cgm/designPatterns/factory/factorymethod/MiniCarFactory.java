package com.cgm.designPatterns.factory.factorymethod;

public class MiniCarFactory extends AbstractCarFactory{
    @Override
    public AbstractCar newCar() {
        return new MiniCar();
    }
}
