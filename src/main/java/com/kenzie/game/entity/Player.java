package com.kenzie.game.entity;

import com.kenzie.game.GamePanel;
import com.kenzie.game.KeyHandler;
import com.kenzie.game.UtilityTool;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.sql.SQLOutput;
import java.util.Objects;

public class Player extends Entity{


    KeyHandler keyH;

    public final int screenX;
    public final int screenY;

    //public int hasKey = 0;

    public Player(GamePanel gp, KeyHandler keyH){
        super(gp);

        this.keyH = keyH;

        screenX = gp.screenWidth / 2 - (gp.tileSize / 2);
        screenY = gp.screenHeight / 2 - (gp.tileSize / 2);

        solidArea = new Rectangle(8,16,32,32);

        solidAreaDefaultX = solidArea.x;
        solidAreaDefaultY = solidArea.y;

        setDefaultValues();
        getPlayerImage();

    }

    public void setDefaultValues(){
         //worldX = gp.tileSize * 23;
         //worldY = gp.tileSize * 21;

        worldX = gp.tileSize * 10;
        worldY = gp.tileSize * 13;

         speed = 4;
         direction = "down";

         // Player Status
        maxLife = 6;
        life = maxLife;
    }

    public void  getPlayerImage(){

        up1 = setup("/player/boy_up_1");
        up2 = setup("/player/boy_up_2");
        down1 = setup("/player/boy_down_1");
        down2 = setup("/player/boy_down_2");
        left1 = setup("/player/boy_left_1");
        left2 = setup("/player/boy_left_2");
        right1 = setup("/player/boy_right_1");
        right2 = setup("/player/boy_right_2");

    }



    public void update(){

        if(keyH.upPressed || keyH.downPressed || keyH.leftPressed || keyH.rightPressed){

            if(keyH.upPressed){
                direction = "up";

            } else if (keyH.downPressed){
                direction = "down";

            } else if (keyH.leftPressed){
                direction = "left";

            } else if (keyH.rightPressed){
                direction = "right";

            }

            // Check Tile Collision
            collisionOn = false;
            gp.cChecker.checkTile(this);

            // Check object collision
            int objIndex =  gp.cChecker.checkObject(this, true);
            pickUpObject(objIndex);

            // Check NPC Collision
            int npcIndex = gp.cChecker.checkEntity(this, gp.npc);
            interactNPC(npcIndex);

            // Check Monster Collision
            int monsterIndex = gp.cChecker.checkEntity(this, gp.monster);
            contactMonster(monsterIndex);


            // Check Event
            gp.eHandler.checkEvent();

            gp.keyH.enterPressed = false;

            // If Collision is false, player can move
            if(!collisionOn){

                switch (direction) {
                    case "up" -> worldY -= speed;
                    case "down" -> worldY += speed;
                    case "left" -> worldX -= speed;
                    case "right" -> worldX += speed;
                }
            }


            spriteCounter++;
            if(spriteCounter > 12){
                if(spriteNum == 1) {
                    spriteNum = 2;
                } else if(spriteNum == 2){
                    spriteNum = 1;
                }
                spriteCounter = 0;
            }
        } else {

        }
        if(invincible){
            invincibleCounter++;
            if(invincibleCounter > 60){
                invincible = false;
                invincibleCounter = 0;
            }
        }

    }

    public void pickUpObject(int i){

        if(i != 999){


        }
    }

    public void interactNPC(int i){

        if(i != 999) {
            if(gp.keyH.enterPressed) {
                gp.gameState = gp.dialoguState;
                gp.npc[i].speak();
            }
        }
    }

    public void contactMonster(int i){

        if(i != 999){

            if(!invincible) {
                life -= 1;
                invincible = true;
            }
        }

    }

    public void draw(Graphics2D g2){
      /*  g2.setColor(Color.WHITE);
        g2.fillRect(x,y,gp.tileSize, gp.tileSize);*/

        BufferedImage image = null;

        switch (direction){
            case "up":
                if(spriteNum == 1){
                    image = up1;
                }
                if(spriteNum == 2){
                    image = up2;
                }

                break;
            case "down":
                if(spriteNum == 1){
                    image = down1;
                }
                if(spriteNum == 2){
                    image = down2;
                }

                break;
            case "left":
                if(spriteNum == 1){
                    image = left1;
                }
                if(spriteNum == 2){
                    image = left2;
                }
                break;
            case "right":
                if(spriteNum == 1){
                    image = right1;
                }
                if(spriteNum == 2){
                    image = right2;
                }
        }

        if(invincible){
            g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.3f));
        }

        g2.drawImage(image, screenX, screenY, null);

        // Reset alpha
        g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1f));

        // Debug
  /*      g2.setFont(new Font("Arial", Font.PLAIN, 26));
        g2.setColor(Color.WHITE);
        g2.drawString("Invincible: " + invincibleCounter, 10,400);*/
    }
}
