package com.cgm.designPatterns.factory.abstractFactory;

public class N95Mask extends AbstractMask {
    public N95Mask() {
        this.price = "99.9";
    }

    @Override
    public void protectedMe() {
        System.out.println("n95..");
    }
}
