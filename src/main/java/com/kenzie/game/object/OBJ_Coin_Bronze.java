package com.kenzie.game.object;

import com.kenzie.game.GamePanel;
import com.kenzie.game.entity.Entity;

public class OBJ_Coin_Bronze extends Entity {

    public static final String OBJ_NAME = "Bronze Coin";

    GamePanel gp;

    public OBJ_Coin_Bronze(GamePanel gp) {
        super(gp);
        this.gp = gp;

        type = TYPE_PICKUP_ONLY;
        name = OBJ_NAME;
        value = 1;
        down1 = setup("/objects/coin_bronze", gp.tileSize, gp.tileSize);

    }

    public boolean use(Entity entity){

        gp.playSE(1);
        gp.ui.addMessage("Coin + " + value);
        gp.player.coin += value;

        return true;
    }
}
