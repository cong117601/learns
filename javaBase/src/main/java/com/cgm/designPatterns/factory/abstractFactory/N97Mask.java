package com.cgm.designPatterns.factory.abstractFactory;

public class N97Mask extends AbstractMask {
    public N97Mask() {
        this.price = "97.7";
    }

    @Override
    public void protectedMe() {
        System.out.println("n97..");
    }
}
