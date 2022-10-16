package com.cgm.objectLayout;

import org.openjdk.jol.info.ClassLayout;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.CountDownLatch;

public class ObjectDemo {


    public static void main(String[] args) throws InterruptedException {
        Object o = new MyObject();
        MyObject[] o1 = new MyObject[4];
        System.out.println(ClassLayout.parseInstance(o1).toPrintable());

    }
}

class MyObject {

    int a = 16;
    char c = 'c';
}
