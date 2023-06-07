package com.kenzie.game.object;

import com.kenzie.game.GamePanel;
import com.kenzie.game.entity.Entity;

public class OBJ_Key extends Entity {

    public OBJ_Key(GamePanel gp) {
        super(gp);

        name = "Key";
        down1 = setup("/objects/key", gp.tileSize, gp.tileSize);
        description = "[" + name + "]\nIt opens a door.";


    }
}
