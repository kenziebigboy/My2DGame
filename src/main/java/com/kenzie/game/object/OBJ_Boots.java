package com.kenzie.game.object;

import com.kenzie.game.GamePanel;
import com.kenzie.game.entity.Entity;


public class OBJ_Boots extends Entity {

    public static final String OBJ_NAME = "Boots";

    public OBJ_Boots(GamePanel gp) {
        super(gp);

        name = OBJ_NAME;
        down1 = setup("/objects/boots", gp.tileSize, gp.tileSize);

    }
}
