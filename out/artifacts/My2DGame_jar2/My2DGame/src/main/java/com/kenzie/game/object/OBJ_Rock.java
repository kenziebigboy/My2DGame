package com.kenzie.game.object;

import com.kenzie.game.GamePanel;
import com.kenzie.game.entity.Entity;
import com.kenzie.game.entity.Projectile;

import java.awt.*;

public class OBJ_Rock extends Projectile {

    GamePanel gp;
    public static final String OBJ_NAME = "Rock";

    public OBJ_Rock(GamePanel gp) {
        super(gp);
        this.gp = gp;

        name = OBJ_NAME;
        speed = 8;
        maxLife = 80;
        life = maxLife;
        attack = 2;
        useCost = 1;
        alive = false;
        getImage();
    }

    public void getImage(){


        up1 = setup("/projectile/rock_down_1",gp.tileSize, gp.tileSize);
        up2 = setup("/projectile/rock_down_1",gp.tileSize, gp.tileSize);

        down1 = setup("/projectile/rock_down_1",gp.tileSize, gp.tileSize);
        down2 = setup("/projectile/rock_down_1",gp.tileSize, gp.tileSize);

        left1 = setup("/projectile/rock_down_1",gp.tileSize, gp.tileSize);
        left2 = setup("/projectile/rock_down_1",gp.tileSize, gp.tileSize);

        right1 = setup("/projectile/rock_down_1",gp.tileSize, gp.tileSize);
        right2 = setup("/projectile/rock_down_1",gp.tileSize, gp.tileSize);

    }

    public boolean haveResource(Entity user){

        boolean haveResource = user.ammo >= useCost;

        return haveResource;
    }

    public void subtractResource(Entity user){
        user.ammo -= useCost;
    }

    public Color getParticleColor(){
        return new Color(40, 50, 0);
    }

    public int getParticleSize(){
        return  10; // 6 pixels
    }

    public int getParticleSpeed(){
        return 1;
    }

    public int getParticleMaxLife(){
        return 20;
    }
}
