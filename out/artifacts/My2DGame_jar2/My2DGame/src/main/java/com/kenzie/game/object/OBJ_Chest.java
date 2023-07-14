package com.kenzie.game.object;

import com.kenzie.game.GamePanel;
import com.kenzie.game.entity.Entity;

public class OBJ_Chest extends Entity {

    public static final String OBJ_NAME = "Chest";

    GamePanel gp;


    public OBJ_Chest(GamePanel gp) {
        super(gp);
        this.gp = gp;


        type = TYPE_OBSTACLE;
        name = OBJ_NAME;
        image = setup("/objects/chest", gp.tileSize, gp.tileSize);
        image2 = setup("/objects/chest_opened", gp.tileSize, gp.tileSize);
        down1 = image;
        collision = true;

        solidArea.x = 4;
        solidArea.y = 16;
        solidArea.width = 40;
        solidArea.height = 32;
        solidAreaDefaultX = solidArea.x;
        solidAreaDefaultY = solidArea.y;


    }

    public void setLoot(Entity loot){
        this.loot = loot;
        setDialogue();
    }

    public void setDialogue(){
        dialogues[0][0] = "You open the chest and find a " + loot.name + "!\n... But you cannot carry any more!";
        dialogues[1][0] = "You open the chest and find a " + loot.name + "!\nYou obtain the " + loot.name + "!";
        dialogues[2][0] = "It's empty";
    }

    public void interact(){



        if(!opened){
            gp.playSE(3);


            if(!gp.player.canObtainItem(loot)){
                startDialogue(this, 0);
            } else {
                startDialogue(this, 1);
                down1 = image2;
                opened = true;
            }


        } else {
            startDialogue(this, 2);
        }
    }
}
