package com.cgm.enum2;

import com.cgm.io.NIO;

public enum Test {


    D(10),

    E(11),

    F(12);


    private Test() {
    }


    private int num;

    private Test(int num) {
        this.num = num;
    }

}
