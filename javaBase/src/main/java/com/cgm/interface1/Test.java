package com.cgm.interface1;

public class Test {


    public static void main(String[] args) {

    }


}


abstract class A {
    int a = 100;

    public void test() {

    }

    public abstract void method();
}
interface D{

}

interface B extends D{
    public static final  int  a = 100;

    public void test();

    default void method() {

    }
}

abstract class C implements B {
    @Override
    public void test() {

    }

    @Override
    public void method() {
        B.super.method();
    }
}
