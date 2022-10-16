package com.cgm.designPatterns.factory.abstractFactory;

public abstract class CarFactory extends AbstractMainFactory {
    @Override
    public abstract AbstractCar newCar();

    @Override
    public AbstractCar newMask() {
        return null;
    }
}
