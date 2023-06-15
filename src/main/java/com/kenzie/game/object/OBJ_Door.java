package com.kenzie.game.object;

import com.kenzie.game.GamePanel;
import com.kenzie.game.entity.Entity;

public class OBJ_Door extends Entity {

    GamePanel gp;
    public static final String OBJ_NAME = "Door";

    public OBJ_Door(GamePanel gp) {
        super(gp);
        this.gp = gp;

        type = type_obstacle;
        name = OBJ_NAME;
        down1 = setup("/objects/door", gp.tileSize, gp.tileSize);

        collision = true;

        solidArea.x = 0;
        solidArea.y = 16;
        solidArea.width = 48;
        solidArea.height = 32;
        solidAreaDefaultX = solidArea.x;
        solidAreaDefaultY = solidArea.y;
        setDialogue();

    }

    public void setDialogue(){
        dialogues[0][0] = "You need a key to open this.";
    }

    public void interact(){

        startDialogue(this, 0);


    }
}
