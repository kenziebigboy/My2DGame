package com.kenzie.game.object;

import com.kenzie.game.GamePanel;
import com.kenzie.game.entity.Entity;

public class OBJ_Potion_Red extends Entity {

    GamePanel gp;
    int value = 5;

    public OBJ_Potion_Red(GamePanel gp) {
        super(gp);

        this.gp = gp;

        type = type_comsumable;
        name = "Red Potion";
        down1 = setup("/objects/potion_red", gp.tileSize, gp.tileSize);
        description = "[" + name + "]\nHeals your life by" + value + ".";

    }

    public void use(Entity entity){

        gp.gameState = gp.dialogueState;
        gp.ui.currentDialouge = "You drink the " + name + "!\n Your life has been recovered by " + value + ".";
        entity.life += value;
        if(gp.player.life > gp.player.maxLife){
            gp.player.life = gp.player.maxLife;
        }
        gp.playSE(2);
    }
}