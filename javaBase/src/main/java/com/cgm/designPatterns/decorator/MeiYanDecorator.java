package com.cgm.designPatterns.decorator;

public class MeiYanDecorator implements TikTokDecorator{


    private ManTikTok manTikTok;

    public MeiYanDecorator(ManTikTok manTikTok){
        this.manTikTok = manTikTok;
    }

    @Override
    public void enableMinYan() {
        System.out.println("美艳开启");
        manTikTok.player();
    }

    @Override
    public void player() {
        enableMinYan();
    }
}
