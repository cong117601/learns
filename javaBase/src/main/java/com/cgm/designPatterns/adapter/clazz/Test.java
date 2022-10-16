package com.cgm.designPatterns.adapter.clazz;

import com.cgm.designPatterns.adapter.MoviePlayer;
import com.cgm.designPatterns.adapter.Player;

public class Test {

    public static void main(String[] args) {
        MoviePlayer moviePlayer = new MoviePlayer();

        JPMovicePlayer jpMovicePlayer = new JPMovicePlayer(moviePlayer);
        String player = jpMovicePlayer.player();
        System.out.println("翻译后：" + player);
    }
}
