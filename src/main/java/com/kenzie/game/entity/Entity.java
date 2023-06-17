package com.kenzie.game.entity;

import com.kenzie.game.GamePanel;
import com.kenzie.game.UtilityTool;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Objects;
import java.util.Random;

public class Entity {

    GamePanel gp;
    public BufferedImage up1, up2, down1, down2, left1, left2, right1, right2;
    public BufferedImage attackUp1, attackUp2, attackDown1, attackDown2,
            attackLeft1, attackLeft2, attackRight1, attackRight2,
            guardUp, guardDown, guardLeft, guardRight;
    public BufferedImage image, image2, image3;
    public Rectangle solidArea = new Rectangle(0,0,48,48);
    public Rectangle attackArea = new Rectangle(0,0,0,0);
    public  int solidAreaDefaultX, solidAreaDefaultY;
    public String[][] dialogues = new String[20][20];
    public Entity attacker;
    public Entity linkedEntity;
    public boolean temp = false;

    // State
    public int worldX, worldY;
    public String direction = "down";
    public int spriteNum = 1;
    public int dialogueSet = 0;
    public int dialogueIndex = 0;
    public boolean collisionOn = false;
    public boolean invincible = false;
    public boolean attacking = false;
    public boolean alive = true;
    public boolean dying = false;
    public boolean hpBarOn = false;
    public boolean onPath = false;
    public boolean knockBack = false;
    public String knockBackDirection;
    public boolean guarding = false;
    public boolean transparent = false;
    public boolean offBalance = false;
    public Entity loot;
    public boolean opened = false;
    public boolean inRage = false;
    public boolean sleep = false;
    public boolean drawing = true;


    // Counters
    public int spriteCounter = 0;
    public int actionLockCounter = 0;
    public int invincibleCounter = 0;
    public int shotAvailableCount = 0;
    int dyingCounter = 0;
    public int hpBarCounter = 0;
    int knockBackCounter = 0;
    public int guardCounter = 0;
    int offBalanceCounter = 0;

    // Character Attributes
    public String name;
    public int defaultSpeed;
    public int maxLife;

    public int speed;
    public Entity currentLight;
    public boolean collision = false;

    // Type
    public int type;
    public final int TYPE_PLAYER = 0;
    public final int TYPE_NPC = 1;
    public final int TYPE_MONSTER = 2;
    public final int TYPE_SWORD = 3;
    public final int TYPE_AXE = 4;
    public final int TYPE_SHIELD = 5;
    public final int TYPE_CONSUMABLE = 6;
    public final int TYPE_PICKUP_ONLY = 7;
    public final int TYPE_OBSTACLE = 8;
    public final int TYPE_LIGHT = 9;
    public final int TYPE_PICKAXE = 10;

    // Character Status

    public int life;
    public int maxMana;
    public int mana;
    public int ammo;
    public int level;
    public int strength;
    public int dexterity;
    public int attack;
    public int defense;
    public int exp;
    public int nextLevelExp;
    public int coin;
    public int motion1_duration;
    public int motion2_duration;
    public Entity currentWeapon;
    public Entity currentShield;
    public Projectile projectile;
    public boolean boss;

    // Item Attributes
    public ArrayList<Entity> inventory = new ArrayList<>();
    public final int maxInventorySize = 20;
    public int value;
    public int attackValue;
    public int defenseValue;
    public String description = "";
    public int useCost;
    public int price;

    public int knockBackPower = 0;
    public boolean stackable = false;
    public int amount = 1;
    public int lightRadius;

    public Entity(GamePanel gp){
        this.gp = gp;
    }

    public int getScreenX(){
        return worldX - gp.player.worldX + gp.player.screenX;
    }

    public int getScreenY(){
        return worldY - gp.player.worldY + gp.player.screenY;
    }

    public int getLeftX(){
        return worldX + solidArea.x;
    }

    public int getRightX(){ return worldX + solidArea.x + solidArea.width;}

    public int getTopY(){ return worldY + solidArea.y;}

    public int getBottomY(){ return worldY + solidArea.y + solidArea.height;}

    public int getCol(){ return (worldX + solidArea.x) / gp.tileSize; }

    public int getRow(){ return (worldY + solidArea.y) / gp.tileSize; }

    public int getCenterX(){
        return worldX + left1.getWidth();
    }

    public int getCenterY(){
        return worldY + up1.getHeight();
    }

    public int getXDistance(Entity target) {
        return Math.abs(getCenterX() - target.getCenterX());
    }

    public int getYDistance(Entity target) {
        return Math.abs(getCenterY() - target.getCenterY());
    }

    public int getTileDistance(Entity target){
        return (getXDistance(target) + getXDistance(target) / gp.tileSize);
    }

    public int getGoalCol(Entity target){
        return (target.worldX + target.solidArea.x) / gp.tileSize;
    }

    public int getGoalRow(Entity target){
        return (target.worldY + target.solidArea.y) / gp.tileSize;
    }

    public void resetCounter(){

        spriteCounter = 0;
        actionLockCounter = 0;
        invincibleCounter = 0;
        shotAvailableCount = 0;
        dyingCounter = 0;
        hpBarCounter = 0;
        knockBackCounter = 0;
        guardCounter = 0;
        offBalanceCounter = 0;
    }

    public void setLoot(Entity loot){ }

    public void setAction(){

    }

    public void move(String direction){

    }

    public void damageReaction(){

    }

    public void speak(){}

    public void startDialogue(Entity entity, int setNum){


        gp.gameState = gp.DIALOGUE_STATE;
        gp.ui.npc = entity;
        dialogueSet = setNum;

    }

    public void facePlayer(){

        switch (gp.player.direction){
            case "up" -> direction = "down";
            case "down" -> direction = "up";
            case "left" -> direction = "right";
            case "right" -> direction = "left";
        }
    }

    public void interact(){

    }

    public boolean use(Entity entity) { return false; }

    public void checkDrop(){

    }

    public void dropItem(Entity droppedItem){

        for(int i = 0; i < gp.obj[1].length; i++){

            if(gp.obj[gp.currentMap][i] == null){
                gp.obj[gp.currentMap][i] = droppedItem;
                gp.obj[gp.currentMap][i].worldX = worldX;
                gp.obj[gp.currentMap][i].worldY = worldY;
                break;
            }
        }

    }

    public Color getParticleColor(){
        return null;
    }

    public int getParticleSize(){
        return  0; // 6 pixels
    }

    public int getParticleSpeed(){
        return 0;
    }

    public int getParticleMaxLife(){
        return 0;
    }

    public void generateParticle(Entity generator, Entity target){

        Color color = generator.getParticleColor();
        int size = generator.getParticleSize();
        int speed = generator.getParticleSpeed();
        int maxLife = generator.getParticleMaxLife();

        Particle p1 = new Particle(gp, target, color, size, speed, maxLife, -2,-1);
        Particle p2 = new Particle(gp, target, color, size, speed, maxLife, 2,-1);
        Particle p3 = new Particle(gp, target, color, size, speed, maxLife, -2,1);
        Particle p4 = new Particle(gp, target, color, size, speed, maxLife, 2,1);
        gp.particleList.add(p1);
        gp.particleList.add(p2);
        gp.particleList.add(p3);
        gp.particleList.add(p4);
    }

    public void checkCollision(){

        collisionOn = false;
        gp.cChecker.checkTile(this);
        gp.cChecker.checkObject(this,false);
        gp.cChecker.checkEntity(this, gp.npc);
        gp.cChecker.checkEntity(this, gp.monster);
        gp.cChecker.checkEntity(this, gp.iTile);
        boolean contactPlayer =  gp.cChecker.checkPlayer(this);

        if(this.type == TYPE_MONSTER && contactPlayer){
            damagePlayer(attack);
        }

    }

    public void update(){

        if(!sleep) {
            if (knockBack) {

                checkCollision();
                if (collisionOn) {
                    knockBackCounter = 0;
                    knockBack = false;
                    speed = defaultSpeed;
                } else {

                    switch (knockBackDirection) {
                        case "up" -> worldY -= speed;
                        case "down" -> worldY += speed;
                        case "left" -> worldX -= speed;
                        case "right" -> worldX += speed;
                    }
                }

                knockBackCounter++;
                if (knockBackCounter == 10) {
                    knockBackCounter = 0;
                    knockBack = false;
                    speed = defaultSpeed;
                }
            } else if (attacking) {
                attacking();

            } else {

                setAction();
                checkCollision();

                // If Collision is false, player can move
                if (!collisionOn) {

                    switch (direction) {
                        case "up" -> worldY -= speed;
                        case "down" -> worldY += speed;
                        case "left" -> worldX -= speed;
                        case "right" -> worldX += speed;
                    }
                }

                spriteCounter++;
                if (spriteCounter > 24) {
                    if (spriteNum == 1) {
                        spriteNum = 2;
                    } else if (spriteNum == 2) {
                        spriteNum = 1;
                    }
                    spriteCounter = 0;
                }
            }


            if (invincible) {
                invincibleCounter++;
                if (invincibleCounter > 40) {
                    invincible = false;
                    invincibleCounter = 0;
                }
            }

            if (shotAvailableCount < 30) {
                shotAvailableCount++;
            }

            if (offBalance) {
                offBalanceCounter++;
                if (offBalanceCounter > 60) {
                    offBalance = false;
                    offBalanceCounter = 0;
                }
            }
        }
    }

    public void checkAttackOrNot(int rate, int straight, int horizontal){

        boolean targetInRange = false;
        int xDis = getXDistance(gp.player);
        int yDis = getYDistance(gp.player);

        switch (direction) {
            case "up" -> {
                if (gp.player.getCenterY() < getCenterY() && yDis < straight && xDis < horizontal) {
                    targetInRange = true;
                }
            }
            case "down" -> {
                if (gp.player.getBottomY() > getCenterY() && yDis < straight && xDis < horizontal) {
                    targetInRange = true;
                }
            }
            case "left" -> {
                if (gp.player.getCenterX() < getCenterX() && xDis < straight && yDis < horizontal) {
                    targetInRange = true;
                }
            }
            case "right" -> {
                if (gp.player.getCenterX() > getCenterX() && xDis < straight && yDis < horizontal) {
                    targetInRange = true;
                }
            }
        }

        if(targetInRange){
            // Check if it initiates an attack
            int i = new Random().nextInt(rate);
            if(i == 0){
                attacking = true;
                spriteNum = 1;
                spriteCounter = 0;
                shotAvailableCount = 0;
            }
        }

    }

    public void checkShootOrNot(int rate, int shotInterval){

        int i = new Random().nextInt(rate) ;
        if(i == 0 && !projectile.alive && shotAvailableCount == shotInterval){
            projectile.set(worldX, worldY, direction, true, this);

            for(int ii = 0; ii < gp.projectile[ii].length; ii++){
                if(gp.projectile[gp.currentMap][ii] == null){
                    gp.projectile[gp.currentMap][ii] = projectile;
                    break;
                }
            }

            shotAvailableCount = 0;
        }
    }

    public void checkStartChasingOrNot(Entity target, int distance, int rate){

        if(getTileDistance(target) < distance){
            int i = new Random().nextInt(rate);
            if(i == 0){
                onPath = true;
            }
        }

    }

    public void checkStopChasingOrNot(Entity target, int distance, int rate){

        if(getTileDistance(target) > distance){
            int i = new Random().nextInt(rate);
            if(i == 0){
                onPath = false;
            }
        }

    }

    public void getRandomDirection(int interval){
        actionLockCounter++;

        if (actionLockCounter > interval) {

            Random random = new Random();
            int i = random.nextInt(100) + 1; // pick up number from 1 to 100

            if (i <= 25) { direction = "up";}
            if (i > 25 && i <= 50) { direction = "down";}
            if (i > 50 && i <= 75) { direction = "left";}
            if (i > 75 && i <= 100) { direction = "right";}

            actionLockCounter = 0;
        }
    }

    public void moveTowardPlay(int interval){

        actionLockCounter++;
        if (actionLockCounter > interval) {
            if(getXDistance(gp.player) > getYDistance(gp.player)){
                if(gp.player.getCenterX() < getCenterX()){
                    direction = "left";
                } else {
                    direction = "right";
                }
            } else if(getXDistance(gp.player) < getYDistance(gp.player)){
                if(gp.player.getCenterY() < getCenterY()){
                    direction = "up";
                } else {
                    direction = "down";
                }
            }
            actionLockCounter = 0;
        }


    }

    public String getOppositeDirection(String direction){

        String oppositeDirection = "";

        switch (direction){
            case "up" -> {oppositeDirection = "down";}
            case "down" -> {oppositeDirection = "up";}
            case "left" -> {oppositeDirection = "right";}
            case "right" -> {oppositeDirection = "left";}
        }

        return oppositeDirection;
    }

    public void attacking(){

        spriteCounter++;

        if(spriteCounter <= motion1_duration){
            spriteNum = 1;
        }

        if(spriteCounter > motion1_duration && spriteCounter <= motion2_duration){
            spriteNum = 2;

            // Save the current worldX, worldY, and solidArea
            int currentWorldX = worldX;
            int currentWorldY = worldY;
            int solidAreaWidth = solidArea.width;
            int solidAreaHeight = solidArea.height;

            // Adjust player's world X/Y for the attackArea
            switch (direction) {
                case "up" -> worldY -= attackArea.height;
                case "down" -> worldY += attackArea.height;
                case "left" -> worldX -= attackArea.width;
                case "right" -> worldX += attackArea.width;
            }

            // AttackArea becomes solidArea
            solidArea.width = attackArea.width;
            solidArea.height = attackArea.height;

            if(type == TYPE_MONSTER){
                if(gp.cChecker.checkPlayer(this)){
                    damagePlayer(attack);
                }

            } else {

                // Check monster collision with updated worldX, worldY, and solidArea
                int monsterIndex = gp.cChecker.checkEntity(this, gp.monster);
                gp.player.damageMonster(monsterIndex, this, attack, currentWeapon.knockBackPower);

                int iTileIndex = gp.cChecker.checkEntity(this, gp.iTile);
                gp.player.damageInteractiveTile(iTileIndex);

                int projectileIndex = gp.cChecker.checkEntity(this, gp.projectile);
                gp.player.damageProjectile(projectileIndex);
            }

            // After checking collision, resotre the original data.
            worldX = currentWorldX;
            worldY = currentWorldY;
            solidArea.width = solidAreaWidth;
            solidArea.height = solidAreaHeight;

        }

        if(spriteCounter > motion2_duration){
            spriteNum = 1;
            spriteCounter = 0;
            attacking = false;
        }

    }

    public void damagePlayer(int attack){

        if(!gp.player.invincible){

            int damage = attack - gp.player.defense;

            // Get an opposite direction of this attacker
            String canGuardDirection = getOppositeDirection(direction);
            if(gp.player.guarding && gp.player.direction.equals(canGuardDirection)){

                // Parry
                if(gp.player.guardCounter < 10){
                    damage = 0;
                    gp.playSE(16);
                    setKnockBack(this, gp.player, knockBackPower);
                    offBalance = true;
                    spriteCounter =- 60;
                } else {
                    // Normal guard
                    damage /= 3;
                    gp.playSE(15);
                }
            } else {
                // Not guarding
                gp.playSE(6);
                if(damage < 1){
                    damage = 1;
                }
            }

            if(damage != 0){
                gp.player.transparent = true;
                setKnockBack(gp.player, this, knockBackPower);
            }

            gp.player.life -= damage;
            gp.player.invincible = true;
        }
    }

    public void setKnockBack(Entity target, Entity attacker,  int knockBackPower){

        this.attacker = attacker;
        target.knockBackDirection = attacker.direction;
        target.speed += knockBackPower;
        target.knockBack = true;

    }

    public boolean inCamera(){

        return worldX + gp.tileSize * 5 > gp.player.worldX - gp.player.screenX &&
                worldX - gp.tileSize < gp.player.worldX + gp.player.screenX &&
                worldY + gp.tileSize * 5 > gp.player.worldY - gp.player.screenY &&
                worldY - gp.tileSize < gp.player.worldY + gp.player.screenY;
    }

    public void draw(Graphics2D g2){

        BufferedImage image = null;

        if(inCamera()){


            int tempScreenX = getScreenX();
            int tempScreenY = getScreenY();

            switch (direction) {
                case "up" -> {
                    if(!attacking) {
                        if (spriteNum == 1) {image = up1;}
                        if (spriteNum == 2) {image = up2;}
                    } else {
                        tempScreenY = getScreenY()  - up1.getHeight();
                        if (spriteNum == 1) {image = attackUp1;}
                        if (spriteNum == 2) {image = attackUp2;}
                    }
                }
                case "down" -> {
                    if (!attacking) {
                        if (spriteNum == 1) {image = down1;}
                        if (spriteNum == 2) {image = down2;}
                    } else {
                        if (spriteNum == 1) {image = attackDown1;}
                        if (spriteNum == 2) {image = attackDown2;}
                    }
                }
                case "left" -> {
                    if(!attacking) {
                        if (spriteNum == 1) {image = left1;}
                        if (spriteNum == 2) {image = left2;}
                    } else {
                        tempScreenX = getScreenX() - left1.getWidth();
                        if (spriteNum == 1) {image = attackLeft1;}
                        if (spriteNum == 2) {image = attackLeft2;}
                    }
                }
                case "right" -> {
                    if(!attacking) {
                        if (spriteNum == 1) {image = right1;}
                        if (spriteNum == 2) {image = right2;}
                    } else {
                        if (spriteNum == 1) {image = attackRight1;}
                        if (spriteNum == 2) {image = attackRight2;}
                    }
                }
            }



            if (invincible) {
                hpBarOn = true;
                hpBarCounter = 0;
                changeAlpha(g2, 0.4f);
            }

            if(dying){
                dyingAnimation(g2);
            }

            g2.drawImage(  image, tempScreenX, tempScreenY,null);

            changeAlpha(g2, 1f);
        }

    }

    public void dyingAnimation(Graphics2D g2){

        dyingCounter++;
        int i = 5;

        if(dyingCounter <= i){changeAlpha(g2,0);}
        if(dyingCounter > i && dyingCounter <= i * 2){changeAlpha(g2,1);}
        if(dyingCounter > i * 2 && dyingCounter <= i * 3){changeAlpha(g2,0);}
        if(dyingCounter > i * 3 && dyingCounter <= i * 4){changeAlpha(g2,1);}
        if(dyingCounter > i * 4 && dyingCounter <= i * 5){changeAlpha(g2,0);}
        if(dyingCounter > i * 5 && dyingCounter <= i * 6){changeAlpha(g2,1);}
        if(dyingCounter > i * 6 && dyingCounter <= i * 7){changeAlpha(g2,1);}
        if(dyingCounter > i * 7 && dyingCounter <= i * 8){changeAlpha(g2,1);}

        if(dyingCounter > i * 8){

            alive = false;
        }


    }

    public void changeAlpha(Graphics2D g2, float alphaValue){
        g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, alphaValue));
    }

    public BufferedImage setup(String imagePath, int width, int height){

        UtilityTool uTool = new UtilityTool();
        BufferedImage image = null;

        try{
            image = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream(imagePath + ".png")));
            image = uTool.scaleImage(image, width, height);

        }catch (IOException e){
            e.printStackTrace();
        }

        return image;
    }

    public void searchPath(int goalCol, int goalRow){

        int startCol = (worldX + solidArea.x) / gp.tileSize;
        int startRow = (worldY + solidArea.y) / gp.tileSize;

        gp.pFinder.setNodes(startCol, startRow, goalCol, goalRow);

        if(gp.pFinder.search()){

            // Next worldX & worldY
            int nextX = gp.pFinder.pathList.get(0).col * gp.tileSize;
            int nextY = gp.pFinder.pathList.get(0).row * gp.tileSize;

            // Entity's solidArea position
            int enLeftX = worldX + solidArea.x;
            int enRightX = worldX + solidArea.x + solidArea.width;
            int enTopY = worldY + solidArea.y;
            int enBottomY = worldY + solidArea.y + solidArea.height;

            if(enTopY > nextY && enLeftX >= nextX && enRightX < nextX + gp.tileSize){
                direction = "up";
            } else if(enTopY < nextY && enLeftX >= nextX && enRightX < nextX + gp.tileSize){
                direction = "down";
            } else if(enTopY >= nextY && enBottomY < nextY + gp.tileSize){
                // Left or Right
                if(enLeftX > nextX){
                    direction = "left";
                }
                if(enLeftX < nextX){
                    direction = "right";
                }
            } else if(enTopY > nextY && enLeftX > nextX){
                // Up or Left
                direction = "up";
                checkCollision();
                if(collisionOn){
                    direction = "left";
                }
            } else if(enTopY > nextY && enLeftX < nextX){
                // Up or Right
                direction = "up";
                checkCollision();
                if(collisionOn){
                    direction = "right";
                }
            } else if(enTopY < nextY && enLeftX > nextX){
                // Down or Left
                direction = "down";
                checkCollision();
                if(collisionOn){
                    direction = "left";
                }
            } else if(enTopY < nextY && enLeftX < nextX){
                // Down or Right
                direction = "down";
                checkCollision();
                if(collisionOn){
                    direction = "right";
                }
            }



            // If reaches the goal, stop the search
//            int nextCol = gp.pFinder.pathList.get(0).col;
//            int nextRow = gp.pFinder.pathList.get(0).row;
//
//            if(nextCol == goalCol && nextRow == goalRow){
//                onPath = false;
//            }
        }
    }

    public int getDetected(Entity user, Entity[][] target, String targetName){

        int index = 999;

        // Check the surrounding object
        int nextWorldX = user.getLeftX();
        int nextWorldY = user.getTopY();

        switch (user.direction){
            case "up" -> nextWorldY = user.getTopY() - gp.player.speed;
            case "down" -> nextWorldY = user.getBottomY() + gp.player.speed;
            case "left" -> nextWorldX = user.getLeftX() - gp.player.speed;
            case "right" -> nextWorldX = user.getRightX() + gp.player.speed;
        }

        int col = nextWorldX / gp.tileSize;
        int row = nextWorldY / gp.tileSize;

        for(int i = 0; i < target[1].length; i++){
            if(target[gp.currentMap][i] != null){

                if(target[gp.currentMap][i].getCol() == col &&
                   target[gp.currentMap][i].getRow() == row &&
                   target[gp.currentMap][i].name.equals(targetName)){

                    index = i;
                    break;
                }

            }
        }

        return index;

    }

}
