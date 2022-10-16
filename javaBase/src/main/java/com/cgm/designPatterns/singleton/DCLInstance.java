package com.cgm.designPatterns.singleton;

public class DCLInstance {

    private static volatile DCLInstance instance;


    public static DCLInstance getInstance() {


        if (instance == null) {
            synchronized (DCLInstance.class) {
                if (instance == null) {
                    instance = new DCLInstance();
                }
            }

        }
        return instance;
    }


}
