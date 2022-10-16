package com.cgm.designPatterns.adapter;

public class MoviePlayer implements Player {
    @Override
    public String player() {
        System.out.println("播放电影");
        String context = "你好";
        System.out.println(context);
        return context;
    }
}
