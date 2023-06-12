package com.kenzie.game.object;

import com.kenzie.game.GamePanel;
import com.kenzie.game.entity.Entity;

import javax.imageio.ImageIO;
import java.io.IOException;
import java.util.Objects;

public class OBJ_Chest extends Entity {

    GamePanel gp;
    Entity loot;
    boolean opened = false;

    public OBJ_Chest(GamePanel gp, Entity loot) {
        super(gp);
        this.gp = gp;
        this.loot = loot;

        type = type_obstacle;
        name = "Chest";
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

    public void interact(){

        gp.gameState = gp.dialogueState;

        if(!opened){
            gp.playSE(3);

            StringBuilder sb = new StringBuilder();
            sb.append("You open the chest and find a ").append(loot.name).append("!");

            if(gp.player.inventory.size() == gp.player.maxInventorySize){
                sb.append("\n... But you cannot carry any more!");
            } else {
                sb.append("\nYou obtain the ").append(loot.name).append("!");
                gp.player.inventory.add(loot);
                down1 = image2;
                opened = true;
            }

            gp.ui.currentDialouge = sb.toString();
        } else {
            gp.ui.currentDialouge = "It's empty";
        }
    }
}
