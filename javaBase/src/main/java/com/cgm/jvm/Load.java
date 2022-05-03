package com.cgm.jvm;

public class Load {

    public static void main(String[] args) throws ClassNotFoundException {
        Class<?> aClass = Load.class.getClassLoader().loadClass("com.cgm.jvm.People");
        System.out.println(aClass.getName());
    }
}
