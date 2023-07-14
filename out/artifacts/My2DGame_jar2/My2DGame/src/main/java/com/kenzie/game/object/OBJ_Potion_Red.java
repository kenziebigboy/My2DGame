package com.kenzie.game.object;

import com.kenzie.game.GamePanel;
import com.kenzie.game.entity.Entity;

public class OBJ_Potion_Red extends Entity {

    GamePanel gp;
    public static final String OBJ_NAME = "Red Potion";

    public OBJ_Potion_Red(GamePanel gp) {
        super(gp);

        this.gp = gp;

        type = TYPE_CONSUMABLE;
        name = OBJ_NAME;
        value = 5;
        down1 = setup("/objects/potion_red", gp.tileSize, gp.tileSize);
        description = "[" + name + "]\nHeals your life by" + value + ".";
        price = 100;
        stackable = true;
        setDialogue();

    }
    public void setDialogue(){
        dialogues[0][0] = "You drink the " + name + "!\n Your life has been recovered by " + value + ".";

    }

    public boolean use(Entity entity){

   startDialogue(this, 0);
        entity.life += value;

        gp.playSE(2);
        return true;

    }
}
