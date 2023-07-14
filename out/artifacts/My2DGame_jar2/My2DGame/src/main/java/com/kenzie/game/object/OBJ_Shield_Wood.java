package com.kenzie.game.object;

import com.kenzie.game.GamePanel;
import com.kenzie.game.entity.Entity;

public class OBJ_Shield_Wood extends Entity {

    public static final String OBJ_NAME = "Wood Shield";

    public OBJ_Shield_Wood(GamePanel gp) {
        super(gp);

        type = TYPE_SHIELD;
        name = OBJ_NAME;
        down1 = setup("/objects/shield_wood", gp.tileSize,gp.tileSize);
        defenseValue = 1;
        description = "[" + name + "]\nMade by wood.";
        price = 35;

    }
}
