package com.kenzie.game.tile_interactive;

import com.kenzie.game.GamePanel;
import com.kenzie.game.entity.Entity;

public class InteractiveTile extends Entity {

    GamePanel gp;
    public boolean destructible = false;

    public InteractiveTile(GamePanel gp, int col, int row) {
        super(gp);
        this.gp = gp;


    }

    public boolean isCorrectItem(Entity entity){

        boolean isCorrectItem = false;

        return isCorrectItem;

    }

    public void playSE(){

    }

    public InteractiveTile getDestroyedForm(){
        return null;
    }

    public void update(){

        if(invincible){
            invincibleCounter++;
            if(invincibleCounter > 20){
                invincible = false;
                invincibleCounter = 0;
            }
        }

    }
}
