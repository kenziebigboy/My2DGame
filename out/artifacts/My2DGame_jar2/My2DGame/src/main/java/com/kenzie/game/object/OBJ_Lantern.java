package com.kenzie.game.object;

import com.kenzie.game.GamePanel;
import com.kenzie.game.entity.Entity;

public class OBJ_Lantern extends Entity {

    public static final String OBJ_NAME = "Lantern";

    public OBJ_Lantern(GamePanel gp) {
        super(gp);

        type = TYPE_LIGHT;
        name = OBJ_NAME;
        down1 = setup("/objects/lantern", gp.tileSize, gp.tileSize);
        description = "[" + name + "]\nIlluminates your \nsurroundings.";
        price = 200;
        lightRadius = 250;
    }
}
