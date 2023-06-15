package com.kenzie.game.object;

import com.kenzie.game.GamePanel;
import com.kenzie.game.entity.Entity;

public class OBJ_Sword_Normal extends Entity {

    public static final String OBJ_NAME = "Normal Sword";


    public OBJ_Sword_Normal(GamePanel gp) {
        super(gp);

        type = TYPE_SWORD;
        name = OBJ_NAME;
        down1 = setup("/objects/sword_normal", gp.tileSize,gp.tileSize);
        attackValue = 1;
        attackArea.width = 36;
        attackArea.height = 36;
        description = "[" + name + "]\nAn old sword.";
        price = 25;
        knockBackPower = 2;
        motion1_duration = 5;
        motion2_duration = 25;


    }
}
