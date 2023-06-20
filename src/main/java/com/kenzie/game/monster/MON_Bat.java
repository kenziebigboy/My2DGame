package com.kenzie.game.monster;

import com.kenzie.game.GamePanel;
import com.kenzie.game.entity.Entity;
import com.kenzie.game.object.OBJ_Coin_Bronze;
import com.kenzie.game.object.OBJ_Heart;
import com.kenzie.game.object.OBJ_ManaCrystal;
import com.kenzie.game.object.OBJ_Rock;

import java.util.Random;

public class MON_Bat extends Entity {

    GamePanel gp;
    public static final String MONSTER_NAME = "Bat";

    public MON_Bat(GamePanel gp) {
        super(gp);
        this.gp = gp;

        type = TYPE_MONSTER;
        name = MONSTER_NAME;
        defaultSpeed = 4;
        speed = defaultSpeed;
        maxLife = 4;
        life = maxLife;
        attack = 7;
        defense = 0;
        exp = 7;


        solidArea.x = 3;
        solidArea.y = 15;
        solidArea.width = 42;
        solidArea.height = 21;
        solidAreaDefaultX = solidArea.x;
        solidAreaDefaultY = solidArea.y;

        getImage();
    }

    public void  getImage(){

        up1 = setup("/monster/bat_down_1", gp.tileSize, gp.tileSize);
        up2 = setup("/monster/bat_down_2", gp.tileSize, gp.tileSize);
        down1 = setup("/monster/bat_down_1", gp.tileSize, gp.tileSize);
        down2 = setup("/monster/bat_down_2", gp.tileSize, gp.tileSize);
        left1 = setup("/monster/bat_down_1", gp.tileSize, gp.tileSize);
        left2 = setup("/monster/bat_down_2", gp.tileSize, gp.tileSize);
        right1 = setup("/monster/bat_down_1", gp.tileSize, gp.tileSize);
        right2 = setup("/monster/bat_down_2", gp.tileSize, gp.tileSize);

    }

    public void setAction(){

        if(onPath){

//            // Check if it stops chasing
//            checkStopChasingOrNot(gp.player, 15, 100);
//
//            // Search the direction to go
//            searchPath(getGoalCol(gp.player), getGoalRow(gp.player));
//
//            // Check if it shoots a projectile
//            checkShootOrNot(200, 30);

        } else {
//
//            // Check if it starts chasing
//            checkStartChasingOrNot(gp.player, 5, 100);

            // Get a random direction
            getRandomDirection(10);
        }
    }

    @Override
    public void damageReaction() {

        actionLockCounter = 0;
        //direction = gp.player.direction;
        onPath = true;
    }

    public void checkDrop(){

        // Cast a dies
        int i = new Random().nextInt(100) + 1;

        if(i < 50){
            dropItem(new OBJ_Coin_Bronze(gp));
        }
        if(i >= 50 && i < 75){
            dropItem(new OBJ_Heart(gp));
        }

        if(i >= 75 && i < 100){
            dropItem(new OBJ_ManaCrystal(gp));
        }
    }
}
