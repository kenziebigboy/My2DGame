package com.kenzie.game.entity;

import com.kenzie.game.GamePanel;

import java.awt.*;

public class Particle extends Entity{

    Entity generator;
    Color  color;
    int size;
    int xd;
    int yd;

    public Particle(GamePanel gp, Entity generator, Color color, int size, int speed, int maxLife, int xd, int yd) {
        super(gp);
        this.generator = generator;
        this.color = color;
        this.size = size;
        this.speed = speed;
        this.xd = xd;
        this.yd = yd;

        life = maxLife;
        int offfset = gp.tileSize / 2 - size / 2;
        worldX = generator.worldX + offfset;
        worldY = generator.worldY + offfset;
    }

    public void update(){

        life--;

        if(life < maxLife / 3){
            yd++;
        }

        worldX += xd * speed;
        worldY += yd * speed;

        if(life == 0){
            alive = false;
        }

    }

    public void draw(Graphics2D g2){

        int screenX = worldX - gp.player.worldX + gp.player.screenX;
        int screenY = worldY - gp.player.worldY + gp.player.screenY;

        g2.setColor(color);
        g2.fillRect(screenX, screenY, size, size);
    }
}
