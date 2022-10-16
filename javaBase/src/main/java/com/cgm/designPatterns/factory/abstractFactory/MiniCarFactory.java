package com.cgm.designPatterns.factory.abstractFactory;


import com.cgm.designPatterns.factory.factorymethod.AbstractCar;
import com.cgm.designPatterns.factory.factorymethod.AbstractCarFactory;
import com.cgm.designPatterns.factory.factorymethod.MiniCar;

public class MiniCarFactory extends AbstractCarFactory {
    @Override
    public AbstractCar newCar() {
        return new MiniCar();
    }
}
