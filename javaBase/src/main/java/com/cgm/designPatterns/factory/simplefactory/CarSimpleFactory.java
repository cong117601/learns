package com.cgm.designPatterns.factory.simplefactory;

public class CarSimpleFactory {

    //如果更多的产品，就要更多if else,不合适
    public AbstractCar getCar(String type) throws ClassNotFoundException, InstantiationException, IllegalAccessException {

        Class<?> aClass = Class.forName(type);
        AbstractCar o = (AbstractCar)aClass.newInstance();
//        if ("van".equals(type)) {
//            return new VanCar();
//        } else if ("mini".equals(type)) {
//            return new MiniCar();
//        }
        return o;
    }
}
