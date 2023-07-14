package com.kenzie.game.object;

import com.kenzie.game.GamePanel;
import com.kenzie.game.entity.Entity;

public class OBJ_Heart extends Entity {

    GamePanel gp;
    public static final String OBJ_NAME = "Heart";

    public OBJ_Heart(GamePanel gp) {
        super(gp);
        this.gp = gp;

        type = TYPE_PICKUP_ONLY;
        name = OBJ_NAME;
        value = 2;
        image = setup("/objects/heart_full", gp.tileSize, gp.tileSize);
        image2 = setup("/objects/heart_half", gp.tileSize, gp.tileSize);
        image3 = setup("/objects/heart_blank", gp.tileSize, gp.tileSize);
        down1 = image;

    }

    public boolean use(Entity entity){

        gp.playSE(2);
        gp.ui.addMessage("Live + " + value);
        entity.life += value;

        return true;

    }
}
