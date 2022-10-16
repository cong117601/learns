package com.cgm.designPatterns.decorator;

public class MyTikTok implements ManTikTok {
    @Override
    public void player() {
        System.out.println("我在直播");
    }
}
