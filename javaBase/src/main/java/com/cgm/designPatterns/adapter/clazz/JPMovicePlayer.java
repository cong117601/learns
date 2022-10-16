package com.cgm.designPatterns.adapter.clazz;

import com.cgm.designPatterns.adapter.Player;
import com.cgm.designPatterns.adapter.ZH_JPTranslator;

public class JPMovicePlayer extends ZH_JPTranslator implements Player {

    private Player player;

    public JPMovicePlayer(Player player) {
        this.player = player;
    }

    @Override
    public String player() {
        String player = this.player.player();
        String translator = translator(player);
        return translator;
    }
}
