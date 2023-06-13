package com.kenzie.game.object;

import com.kenzie.game.GamePanel;
import com.kenzie.game.entity.Entity;

public class OBJ_Tent extends Entity {

    GamePanel gp;

    public OBJ_Tent(GamePanel gp) {
        super(gp);
        this.gp = gp;

        type = type_consumable;
        name = "Tent";
        down1 = setup("/objects/tent", gp.tileSize, gp.tileSize);
        description = "[" + name + "]\nYou can sleep until\nnext morning.";
        price = 300;
        stackable = true;
    }

    public boolean use(Entity entity){

        gp.gameState = gp.sleepstate;
        gp.playSE(14);
        gp.player.life = gp.player.maxLife;
        gp.player.mana = gp.player.maxMana;
        gp.player.getSleepingImage(down1);
        return true;
    }
}
