package com.kenzie.game.object;

import com.kenzie.game.GamePanel;
import com.kenzie.game.entity.Entity;

public class OBJ_Lantern extends Entity {


    public OBJ_Lantern(GamePanel gp) {
        super(gp);

        type = type_light;
        name = "Lantern";
        down1 = setup("/objects/lantern", gp.tileSize, gp.tileSize);
        description = "[" + name + "]\nIlluminates your \nsurroundings.";
        price = 200;
        lightRadius = 250;
    }
}
