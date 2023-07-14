package com.kenzie.game.object;

import com.kenzie.game.GamePanel;
import com.kenzie.game.entity.Entity;

public class OBJ_Pickaxe extends Entity {

    public static final String OBJ_NAME = "Pickaxe";

    public OBJ_Pickaxe(GamePanel gp) {
        super(gp);

        type = TYPE_PICKAXE;
        name = OBJ_NAME;
        down1 = setup("/objects/pickaxe", gp.tileSize, gp.tileSize);
        attackValue = 2;
        attackArea.width = 30;
        attackArea.height = 30;
        description = "[" + name + "]\nYou will dig it!";
        price = 75;
        knockBackPower = 10;
        motion1_duration = 10;
        motion2_duration = 20;
    }
}
