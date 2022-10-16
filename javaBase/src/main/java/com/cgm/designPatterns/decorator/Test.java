package com.cgm.designPatterns.decorator;

public class Test {
    public static void main(String[] args) {
        MyTikTok myTikTok = new MyTikTok();

        MeiYanDecorator meiYanDecorator = new MeiYanDecorator(myTikTok);
        meiYanDecorator.player();

    }
}
