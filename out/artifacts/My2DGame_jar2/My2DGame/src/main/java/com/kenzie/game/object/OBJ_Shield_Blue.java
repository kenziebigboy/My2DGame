package com.kenzie.game.object;

import com.kenzie.game.GamePanel;
import com.kenzie.game.entity.Entity;

public class OBJ_Shield_Blue extends Entity {

    public static final String OBJ_NAME = "Blue Shield";

    public OBJ_Shield_Blue(GamePanel gp) {
        super(gp);

        type = TYPE_SHIELD;
        name = OBJ_NAME;
        down1 = setup("/objects/shield_blue", gp.tileSize,gp.tileSize);
        defenseValue = 2;
        description = "[" + name + "]\nA shiny blue shield.";

    }
}
