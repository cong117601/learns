package com.cgm.designPatterns.singleton;

public class SingletonInstence {


    private static SingletonInstence singletonInstence = new SingletonInstence();



    public static SingletonInstence getInstance(){
        return singletonInstence;
    }

}
