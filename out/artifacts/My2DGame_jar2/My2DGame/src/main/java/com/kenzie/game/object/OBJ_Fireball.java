package com.kenzie.game.object;

import com.kenzie.game.GamePanel;
import com.kenzie.game.entity.Entity;
import com.kenzie.game.entity.Projectile;

import java.awt.*;

public class OBJ_Fireball extends Projectile {

    GamePanel gp;
    public static final String OBJ_NAME = "Fireball";

    public OBJ_Fireball(GamePanel gp) {
        super(gp);
        this.gp = gp;

        name = OBJ_NAME;
        speed = 5;
        maxLife = 80;
        life = maxLife;
        attack = 1;
        useCost = 1;
        alive = false;
        getImage();
    }

    public void getImage(){


        up1 = setup("/projectile/fireball_up_1",gp.tileSize, gp.tileSize);
        up2 = setup("/projectile/fireball_up_2",gp.tileSize, gp.tileSize);

        down1 = setup("/projectile/fireball_down_1",gp.tileSize, gp.tileSize);
        down2 = setup("/projectile/fireball_down_2",gp.tileSize, gp.tileSize);

        left1 = setup("/projectile/fireball_left_1",gp.tileSize, gp.tileSize);
        left2 = setup("/projectile/fireball_left_2",gp.tileSize, gp.tileSize);

        right1 = setup("/projectile/fireball_right_1",gp.tileSize, gp.tileSize);
        right2 = setup("/projectile/fireball_right_2",gp.tileSize, gp.tileSize);

    }

    public boolean haveResource(Entity user){

        boolean haveResource = user.mana >= useCost;

        return haveResource;
    }

    public void subtractResource(Entity user){
        user.mana -= useCost;
    }

    public Color getParticleColor(){
        return new Color(245, 50, 0);
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
