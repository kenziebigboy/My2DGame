package com.kenzie.game.object;

import com.kenzie.game.GamePanel;
import com.kenzie.game.entity.Entity;

public class OBJ_ManaCrystal extends Entity {

    GamePanel gp;
    public static final String OBJ_NAME = "Mana Crystal";

    public OBJ_ManaCrystal(GamePanel gp) {
        super(gp);
        this.gp = gp;

        type = TYPE_PICKUP_ONLY;
        name = OBJ_NAME;
        value = 1;
        image = setup("/objects/manacrystal_full", gp.tileSize, gp.tileSize);
        image2 = setup("/objects/manacrystal_blank", gp.tileSize, gp.tileSize);
        down1 = image;
    }

    public boolean use(Entity entity){

        gp.playSE(2);
        gp.ui.addMessage("Mana + " + value);
        entity.mana += value;

        return true;

    }

}
