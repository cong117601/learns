package com.cgm.designPatterns.adapter.OBJ;

import com.cgm.designPatterns.adapter.Player;
import com.cgm.designPatterns.adapter.Translator;
import com.cgm.designPatterns.adapter.ZH_JPTranslator;

public class JPMovicePlayer implements Player {

    private Player player;
    private Translator translator = new ZH_JPTranslator();

    public JPMovicePlayer(Player player) {
        this.player = player;
    }

    @Override
    public String player() {
        String player = this.player.player();
        String translator = this.translator.translator(player);
        return translator;
    }
}
