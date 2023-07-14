package com.kenzie.game.tile_interactive;

import com.kenzie.game.GamePanel;
import com.kenzie.game.entity.Entity;
import com.kenzie.game.object.OBJ_Coin_Bronze;
import com.kenzie.game.object.OBJ_Heart;
import com.kenzie.game.object.OBJ_ManaCrystal;

import java.awt.*;
import java.util.Random;

public class IT_DestructibleWall extends InteractiveTile{

    GamePanel gp;

    public IT_DestructibleWall(GamePanel gp, int col, int row) {
        super(gp, col, row);
        this.gp = gp;

        this.worldX = gp.tileSize * col;
        this.worldY = gp.tileSize * row;

        down1 = setup("/tiles_interactive/destructiblewall", gp.tileSize, gp.tileSize);
        destructible = true;
        life = 3;
    }

    public boolean isCorrectItem(Entity entity){

        return entity.currentWeapon.type == TYPE_PICKAXE;

    }

    public void playSE(){
        gp.playSE(20);
    }

    public InteractiveTile getDestroyedForm(){
        return null;
    }

    public Color getParticleColor(){
        return new Color(65, 65, 65);
    }

    public int getParticleSize(){
        return  6; // 6 pixels
    }

    public int getParticleSpeed(){
        return 1;
    }

    public int getParticleMaxLife(){
        return 20;
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
