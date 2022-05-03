package com.cgm.jvm.singleton;

public class StaticInnerClass {

    private StaticInnerClass() {

    }

    private static class StaticInnerClass_1 {
        private static final StaticInnerClass instance = new StaticInnerClass();

    }


    public static StaticInnerClass getSingle() {
        return StaticInnerClass_1.instance;
    }


    public static void main(String[] args) {

        String str = "999,111";

        System.out.println(str.split(",").length);

    }
}
