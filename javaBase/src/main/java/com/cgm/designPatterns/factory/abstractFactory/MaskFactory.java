package com.cgm.designPatterns.factory.abstractFactory;

public abstract class MaskFactory extends AbstractMainFactory {
    @Override
    public AbstractCar newCar() {
        return null;
    }

    @Override
    public abstract AbstractCar newMask();
}
