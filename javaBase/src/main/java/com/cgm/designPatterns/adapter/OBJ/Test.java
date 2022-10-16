package com.cgm.designPatterns.adapter.OBJ;


import com.cgm.designPatterns.adapter.MoviePlayer;

public class Test {

    public static void main(String[] args) {
        MoviePlayer moviePlayer = new MoviePlayer();

        JPMovicePlayer jpMovicePlayer = new JPMovicePlayer(moviePlayer);
        String player = jpMovicePlayer.player();
        System.out.println("翻译后：" + player);
    }
}
