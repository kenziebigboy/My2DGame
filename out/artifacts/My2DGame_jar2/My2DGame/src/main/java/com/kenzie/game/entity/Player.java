package com.kenzie.game.entity;

import com.kenzie.game.GamePanel;
import com.kenzie.game.KeyHandler;
import com.kenzie.game.UtilityTool;
import com.kenzie.game.object.*;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;


public class Player extends Entity {


    KeyHandler keyH;

    public final int screenX;
    public final int screenY;
    int standCounter = 0;
    public boolean attackCanceled = false;
    public boolean lightUpdated = false;


    //public int hasKey = 0;

    public Player(GamePanel gp, KeyHandler keyH) {
        super(gp);

        this.keyH = keyH;

        screenX = gp.screenWidth / 2 - (gp.tileSize / 2);
        screenY = gp.screenHeight / 2 - (gp.tileSize / 2);
        if(blueBoy) {
            solidArea = new Rectangle(8, 16, 32, 32);
        } else {
            solidArea = new Rectangle(24, 16, 32, 32);
        }

        solidAreaDefaultX = solidArea.x;
        solidAreaDefaultY = solidArea.y;

//        attackArea.width = 36;
//        attackArea.height = 36;

        setDefaultValues();
        setDefaultPositions();


    }

    public void setDefaultValues() {

        defaultSpeed = 4;

        speed = defaultSpeed;
        direction = "down";

        // Player Status
        level = 1;
        maxLife = 32;
        life = maxLife;

        maxMana = 4;
        mana = maxMana;
        ammo = 10;

        strength = 1; // The more strength he has, the more damage he gives.
        dexterity = 1; // The more dexterity he has, the less damage he receives.
        exp = 0;
        nextLevelExp = 5;
        coin = 500;
        currentWeapon = new OBJ_Sword_Normal(gp);
        //currentWeapon = new OBJ_Axe(gp);
        currentShield = new OBJ_Shield_Wood(gp);
        currentLight = null;
        projectile = new OBJ_Fireball(gp);
        //projectile = new OBJ_Rock(gp);
        attack = getAttack(); // The total attack value is decided by strength and weapon
        defense = getDefense(); // The total defense value is decided by dexterity and shield

        if(blueBoy) {
            getImage();
            getAttackImage();
            getGuardImage();
        } else {
            UtilityTool uTools = new UtilityTool();
            getCharacterImages(uTools.rnd.nextInt(30) + 1);
        }


        setItems();
        setDialogue();

    }

    public void setDefaultPositions(){

        gp.currentMap = 0;
        worldX = gp.tileSize * 23;
        worldY = gp.tileSize * 21;
        direction = "down";


    }

    public void setDialogue(){
        dialogues[0][0] = "You are level " + level + " now!\nYou feel stronger!";
    }

    public void restoreStatus(){

        life = maxLife;
        mana = maxMana;
        speed = defaultSpeed;
        invincible = false;
        transparent = false;
        attacking = false;
        guarding = false;
        knockBack = false;
        lightUpdated = true;
    }

    public void setItems(){
        inventory.clear();
        inventory.add(currentWeapon);
        inventory.add(currentShield);

        Entity key1 = new OBJ_Key(gp);
        key1.amount = 1;
        inventory.add(key1);
        inventory.add(new OBJ_Lantern(gp));


    }

    public int getAttack() {

        attackArea = currentWeapon.attackArea;
        motion1_duration = currentWeapon.motion1_duration;
        motion2_duration = currentWeapon.motion2_duration;
       return strength * currentWeapon.attackValue;


    }

    public int getDefense(){
        if(currentShield.defenseValue > 0) {
            return dexterity * currentShield.defenseValue;
        }
        return dexterity;
    }

    public int getCurrentWeaponSlot(){
        int currentWeaponSlot = 0;
        for(int i = 0; i < inventory.size(); i++){
            if(inventory.get(i) == currentWeapon){
                currentWeaponSlot = i;
            }
        }
        return currentWeaponSlot;
    }

    public int getCurrentShieldSlot(){
        int currentShieldSlot = 0;
        for(int i = 0; i < inventory.size(); i++){
            if(inventory.get(i) == currentShield){
                currentShieldSlot = i;
            }
        }
        return currentShieldSlot;
    }

    public void getCharacterImages(int characterID){

        BufferedImage image = null;

        String name = "!Character";
        if(characterID < 10){
            name += "0" + characterID;
        } else {
            name += characterID;
        }

        name += ".png";

        try {

            image = ImageIO.read(new File("./resources/characters/" + name));

        } catch (IOException e){
            e.printStackTrace();
        }

        // initalizing rows and columns
        int rows = 8;
        int columns = 12;

        BufferedImage imgs[] = new BufferedImage[96];

        // Equally dividing original image into subimages
        int subimage_Width = image.getWidth() / columns;
        int subimage_Height = image.getHeight() / rows;

        int current_img = 0;
        UtilityTool uTool = new UtilityTool();

        // iterating over rows and columns for each sub-image
        for (int i = 0; i < rows; i++)
        {
            for (int j = 0; j < columns; j++)
            {
                // Creating sub image
                imgs[current_img] = new BufferedImage(subimage_Width, subimage_Height, image.getType());
                Graphics2D img_creator = imgs[current_img].createGraphics();

                // coordinates of source image
                int src_first_x = subimage_Width * j;
                int src_first_y = subimage_Height * i;

                // coordinates of sub-image
                int dst_corner_x = subimage_Width * j + subimage_Width;
                int dst_corner_y = subimage_Height * i + subimage_Height;

                img_creator.drawImage(image, 0, 0, subimage_Width, subimage_Height, src_first_x, src_first_y, dst_corner_x, dst_corner_y, null);

                current_img++;
            }
        }

        for(int i = 0; i < imgs.length; i++){
            imgs[i] = uTool.scaleImage(imgs[i], gp.tileSize * 2, gp.tileSize * 2);
        }

        down[0] = imgs[0];
        down[1] = imgs[1];
        down[2] = imgs[2];
        left[0] = imgs[12];
        left[1] = imgs[13];
        left[2] = imgs[14];
        right[0] = imgs[24];
        right[1] = imgs[25];
        right[2] = imgs[26];
        up[0] = imgs[36];
        up[1] = imgs[37];
        up[2] = imgs[38];

        sword_Down[0] = imgs[3];
        sword_Down[1] = imgs[4];
        sword_Down[2] = imgs[5];
        sword_Left[0] = imgs[15];
        sword_Left[1] = imgs[16];
        sword_Left[2] = imgs[17];
        sword_Right[0] = imgs[27];
        sword_Right[1] = imgs[28];
        sword_Right[2] = imgs[29];
        sword_Up[0] = imgs[39];
        sword_Up[1] = imgs[40];
        sword_Up[2] = imgs[41];

        axe_Down[0] = imgs[6];
        axe_Down[1] = imgs[7];
        axe_Down[2] = imgs[8];
        axe_Left[0] = imgs[18];
        axe_Left[1] = imgs[19];
        axe_Left[2] = imgs[20];
        axe_Right[0] = imgs[30];
        axe_Right[1] = imgs[31];
        axe_Right[2] = imgs[32];
        axe_Up[0] = imgs[42];
        axe_Up[1] = imgs[43];
        axe_Up[2] = imgs[44];

        pickaxe_Down[0] = imgs[9];
        pickaxe_Down[1] = imgs[10];
        pickaxe_Down[2] = imgs[11];
        pickaxe_Left[0] = imgs[21];
        pickaxe_Left[1] = imgs[22];
        pickaxe_Left[2] = imgs[23];
        pickaxe_Right[0] = imgs[33];
        pickaxe_Right[1] = imgs[34];
        pickaxe_Right[2] = imgs[35];
        pickaxe_Up[0] = imgs[45];
        pickaxe_Up[1] = imgs[46];
        pickaxe_Up[2] = imgs[47];

        up1 = up[0];
        down1 = down[0];
        left1 = left[0];
        right1 = right[0];

        System.out.println(left[0].getWidth());
    }

    public void getImage() {

        up1 = setup("/player/boy_up_1", gp.tileSize, gp.tileSize);
        up2 = setup("/player/boy_up_2", gp.tileSize, gp.tileSize);
        down1 = setup("/player/boy_down_1", gp.tileSize, gp.tileSize);
        down2 = setup("/player/boy_down_2", gp.tileSize, gp.tileSize);
        left1 = setup("/player/boy_left_1", gp.tileSize, gp.tileSize);
        left2 = setup("/player/boy_left_2", gp.tileSize, gp.tileSize);
        right1 = setup("/player/boy_right_1", gp.tileSize, gp.tileSize);
        right2 = setup("/player/boy_right_2", gp.tileSize, gp.tileSize);

    }

    public void getSleepingImage(BufferedImage image){

        up1 = image;
        up2 = image;
        down1 = image;
        down2 = image;
        left1 = image;
        left2 = image;
        right1 = image;
        right2 = image;

    }

    public void getAttackImage() {

        if(currentWeapon.type == TYPE_SWORD) {
            attackUp1 = setup("/player/boy_attack_up_1", gp.tileSize, gp.tileSize * 2);
            attackUp2 = setup("/player/boy_attack_up_2", gp.tileSize, gp.tileSize * 2);
            attackDown1 = setup("/player/boy_attack_down_1", gp.tileSize, gp.tileSize * 2);
            attackDown2 = setup("/player/boy_attack_down_2", gp.tileSize, gp.tileSize * 2);
            attackLeft1 = setup("/player/boy_attack_left_1", gp.tileSize * 2, gp.tileSize);
            attackLeft2 = setup("/player/boy_attack_left_2", gp.tileSize * 2, gp.tileSize);
            attackRight1 = setup("/player/boy_attack_right_1", gp.tileSize * 2, gp.tileSize);
            attackRight2 = setup("/player/boy_attack_right_2", gp.tileSize * 2, gp.tileSize);
        }

        if(currentWeapon.type == TYPE_AXE) {
            attackUp1 = setup("/player/boy_axe_up_1", gp.tileSize, gp.tileSize * 2);
            attackUp2 = setup("/player/boy_axe_up_2", gp.tileSize, gp.tileSize * 2);
            attackDown1 = setup("/player/boy_axe_down_1", gp.tileSize, gp.tileSize * 2);
            attackDown2 = setup("/player/boy_axe_down_2", gp.tileSize, gp.tileSize * 2);
            attackLeft1 = setup("/player/boy_axe_left_1", gp.tileSize * 2, gp.tileSize);
            attackLeft2 = setup("/player/boy_axe_left_2", gp.tileSize * 2, gp.tileSize);
            attackRight1 = setup("/player/boy_axe_right_1", gp.tileSize * 2, gp.tileSize);
            attackRight2 = setup("/player/boy_axe_right_2", gp.tileSize * 2, gp.tileSize);
        }

        if(currentWeapon.type == TYPE_PICKAXE) {
            attackUp1 = setup("/player/boy_pick_up_1", gp.tileSize, gp.tileSize * 2);
            attackUp2 = setup("/player/boy_pick_up_2", gp.tileSize, gp.tileSize * 2);
            attackDown1 = setup("/player/boy_pick_down_1", gp.tileSize, gp.tileSize * 2);
            attackDown2 = setup("/player/boy_pick_down_2", gp.tileSize, gp.tileSize * 2);
            attackLeft1 = setup("/player/boy_pick_left_1", gp.tileSize * 2, gp.tileSize);
            attackLeft2 = setup("/player/boy_pick_left_2", gp.tileSize * 2, gp.tileSize);
            attackRight1 = setup("/player/boy_pick_right_1", gp.tileSize * 2, gp.tileSize);
            attackRight2 = setup("/player/boy_pick_right_2", gp.tileSize * 2, gp.tileSize);
        }


    }

    public void getGuardImage(){

        guardUp = setup("/player/boy_guard_up", gp.tileSize, gp.tileSize);
        guardDown = setup("/player/boy_guard_down", gp.tileSize, gp.tileSize);
        guardLeft = setup("/player/boy_guard_left", gp.tileSize, gp.tileSize);
        guardRight = setup("/player/boy_guard_right", gp.tileSize, gp.tileSize);


    }

    public void update() {

        if(knockBack) {

            // Check Tile Collision
            collisionOn = false;
            gp.cChecker.checkTile(this);

            // Check object collision
            gp.cChecker.checkObject(this, true);

            // Check NPC Collision
            gp.cChecker.checkEntity(this, gp.npc);

            // Check Monster Collision
            gp.cChecker.checkEntity(this, gp.monster);

            // Check Interactive Tile Collision
            gp.cChecker.checkEntity(this, gp.iTile);

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
        } else if(attacking) {
            attacking();
        } else if(keyH.spacePressed) {
            guarding = true;
            guardCounter++;

        } else if (keyH.upPressed || keyH.downPressed || keyH.leftPressed || keyH.rightPressed || keyH.enterPressed) {

            if (keyH.upPressed) {
                direction = "up";

            } else if (keyH.downPressed) {
                direction = "down";

            } else if (keyH.leftPressed) {
                direction = "left";

            } else if (keyH.rightPressed) {
                direction = "right";

            }

            // Check Tile Collision
            collisionOn = false;
            gp.cChecker.checkTile(this);

            // Check object collision
            int objIndex = gp.cChecker.checkObject(this, true);
            pickUpObject(objIndex);

            // Check NPC Collision
            int npcIndex = gp.cChecker.checkEntity(this, gp.npc);
            interactNPC(npcIndex);

            // Check Monster Collision
            int monsterIndex = gp.cChecker.checkEntity(this, gp.monster);
            contactMonster(monsterIndex);

            // Check Interactive Tile Collision
            gp.cChecker.checkEntity(this, gp.iTile);

            // Check Event
            gp.eHandler.checkEvent();


            // If Collision is false, player can move
            if (!collisionOn && !keyH.enterPressed) {

                switch (direction) {
                    case "up" -> worldY -= speed;
                    case "down" -> worldY += speed;
                    case "left" -> worldX -= speed;
                    case "right" -> worldX += speed;
                }
            }

            if(keyH.enterPressed && !attackCanceled){
                gp.playSE(7);
                attacking = true;
                spriteCounter = 0;
            }

            attackCanceled = false;
            gp.keyH.enterPressed = false;
            guarding = false;
            guardCounter = 0;

            spriteCounter++;
            if(blueBoy) {
                if (spriteCounter > 12) {
                    if (spriteNum == 1) {
                        spriteNum = 2;
                    } else if (spriteNum == 2) {
                        spriteNum = 1;
                    }
                    spriteCounter = 0;
                }
            } else {
                if(spriteCounter > 10){
                    spriteNum++;

                    if(spriteNum == 3){
                        spriteNum = 0;
                    }

                    spriteCounter = 0;
                }
            }
        } else {
            standCounter++;
            if(standCounter == 20){
                if(blueBoy) {
                    spriteNum = 1;
                } else {
                    spriteNum = 0;
                }
                standCounter = 0;
            }
            guarding = false;
            guardCounter = 0;
        }

        if(gp.keyH.shotKeyPressed && !projectile.alive &&
                shotAvailableCount == 30 && projectile.haveResource(this)){

            projectile.set(worldX, worldY, direction, true, this);

            for(int i = 0; i < gp.projectile[i].length; i++){
                if(gp.projectile[gp.currentMap][i] == null){
                    gp.projectile[gp.currentMap][i] = projectile;
                    break;
                }
            }

            // Subtract the cost (Mana, Ammo, Etc.)
            projectile.subtractResource(this);

            shotAvailableCount = 0;
            gp.playSE(10);
        }

        if (invincible) {
            invincibleCounter++;
            if (invincibleCounter > 60) {
                invincible = false;
                transparent = false;
                invincibleCounter = 0;
            }
        }

        if(shotAvailableCount < 30){
            shotAvailableCount++;
        }

        if(life > maxLife){
            life = maxLife;
        }

        if(mana > maxMana){
            mana = maxMana;
        }
        if(!keyH.godModeOn) {
            if (life <= 0) {
                gp.playSE(12);
                gp.ui.commandNum = -1;
                gp.stopMusic();
                gp.gameState = gp.GAME_OVER_STATE;
            }
        }
    }

    public void pickUpObject(int i) {

        if (i != 999) {

            // Pickup only Items
            if(gp.obj[gp.currentMap][i].type == TYPE_PICKUP_ONLY){
                gp.obj[gp.currentMap][i].use(this);
                gp.obj[gp.currentMap][i] = null;
            } else if(gp.obj[gp.currentMap][i].type == TYPE_OBSTACLE) {

                if(gp.keyH.enterPressed){
                    attackCanceled = true;
                    gp.obj[gp.currentMap][i].interact();

                }
            } else {

                // Inventory Items
                String text;
                if (canObtainItem(gp.obj[gp.currentMap][i])) {

                    gp.playSE(1);
                    text = "Got a " + gp.obj[gp.currentMap][i].name;
                } else {
                    text = "You cannot carry any more.";
                }

                gp.ui.addMessage(text);
                gp.obj[gp.currentMap][i] = null;
            }
        }
    }

    public void interactNPC(int i) {

        if (i != 999) {

            if (gp.keyH.enterPressed) {
                attackCanceled = true;
                gp.npc[gp.currentMap][i].speak();

            }

            gp.npc[gp.currentMap][i].move(direction);
        }
    }

    public void contactMonster(int i) {

        if (i != 999) {

            if (!invincible && !gp.monster[gp.currentMap][i].dying) {
                gp.playSE(6);

                int damage = gp.monster[gp.currentMap][i].attack - defense;
                if(damage < 1){
                    damage = 1;
                }

                life -= damage;
                invincible = true;
                transparent = true;
            }
        }

    }

    public void damageMonster(int i, Entity attacker,  int attack, int knockBackPower){

        if(i != 999){

            if(!gp.monster[gp.currentMap][i].invincible){
                gp.playSE(5);

                if(knockBackPower > 0) {
                    setKnockBack(gp.monster[gp.currentMap][i], attacker,  knockBackPower);
                }

                if(gp.monster[gp.currentMap][i].offBalance){
                    attack *= 5;
                }

                int damage = attack - gp.monster[gp.currentMap][i].defense;
                if(damage < 0){
                    damage =0;
                }
                gp.monster[gp.currentMap][i].life -= 1;

                gp.ui.addMessage(damage + " damage!");
                gp.monster[gp.currentMap][i].invincible = true;
                gp.monster[gp.currentMap][i].damageReaction();

                if(gp.monster[gp.currentMap][i].life <= 0){
                    gp.monster[gp.currentMap][i].dying = true;
                    gp.ui.addMessage("killed the " + gp.monster[gp.currentMap][i].name + "!");
                    gp.ui.addMessage("Exp + " + gp.monster[gp.currentMap][i].exp);
                    exp += gp.monster[gp.currentMap][i].exp;
                    checkLevelUp();
                }
            }
        }
    }

    public void damageInteractiveTile(int i){

        if(i != 999 && gp.iTile[gp.currentMap][i].destructible && gp.iTile[gp.currentMap][i].isCorrectItem(this)
                && !gp.iTile[gp.currentMap][i].invincible){

            gp.iTile[gp.currentMap][i].playSE();
            gp.iTile[gp.currentMap][i].life--;
            gp.iTile[gp.currentMap][i].invincible = true;

            // Generate Particle
            generateParticle(gp.iTile[gp.currentMap][i], gp.iTile[gp.currentMap][i]);

            if(gp.iTile[gp.currentMap][i].life == 0) {
                //gp.iTile[gp.currentMap][i].checkDrop();
                gp.iTile[gp.currentMap][i] = gp.iTile[gp.currentMap][i].getDestroyedForm();
            }
        }

    }

    public void damageProjectile(int i){

        if(i != 999){

            Entity projectile = gp.projectile[gp.currentMap][i];
            projectile.alive = false;
            generateParticle(projectile, projectile);
        }

    }

    public void checkLevelUp(){
        if(exp >= nextLevelExp){

            level++;
            nextLevelExp = nextLevelExp * 2;
            maxLife += 2;
            strength++;
            dexterity++;
            attack = getAttack();
            defense = getDefense();
            gp.playSE(8);
            gp.gameState = gp.DIALOGUE_STATE;
            setDialogue();
            startDialogue(this, 0);
        }
    }

    public void selectItem(){

        int itemIndex = gp.ui.getItemIndexOnSlot(gp.ui.playerSlotCol, gp.ui.playerSlotRow);
        if(itemIndex < inventory.size()){
            Entity selectedItem = inventory.get(itemIndex);

            if(selectedItem.type == TYPE_SWORD || selectedItem.type == TYPE_AXE || selectedItem.type == TYPE_PICKAXE){

                currentWeapon = selectedItem;
                attack = getAttack();
                getAttackImage();
            }

            if(selectedItem.type == TYPE_SHIELD){
                currentShield = selectedItem;
                defense = getDefense();
            }

            if(selectedItem.type == TYPE_LIGHT){
                if(currentLight == selectedItem){
                    currentLight = null;
                } else {
                    currentLight = selectedItem;


                }

                lightUpdated = true;
            }

            if(selectedItem.type == TYPE_CONSUMABLE){
               if(selectedItem.use(this)) {
                   if(selectedItem.amount > 1){
                       selectedItem.amount--;
                   } else {
                       inventory.remove(itemIndex);
                   }
               }
            }
        }
    }

    public int searchItemInInventory(String itemName){

        int itemIndex = 999;

        for(int i = 0; i < inventory.size(); i++){
            if(inventory.get(i).name.equals(itemName)){
                itemIndex = i;
                break;
            }
        }

        return itemIndex;
    }

    public boolean canObtainItem(Entity item){

        boolean canObtain = false;

        Entity netItem = gp.eGenerator.getObject(item.name);

        // Check if stackable
        if(netItem.stackable){

            int index = searchItemInInventory(netItem.name);

            if(index != 999){
                inventory.get(index).amount++;
                canObtain = true;
            } else {
                if(inventory.size() != maxInventorySize){
                    inventory.add(netItem);
                    canObtain = true;
                }
            }

        } else {
            if(inventory.size() != maxInventorySize){
                inventory.add(netItem);
                canObtain = true;
            }
        }

        return canObtain;
    }

    public void draw(Graphics2D g2) {
      /*  g2.setColor(Color.WHITE);
        g2.fillRect(x,y,gp.tileSize, gp.tileSize);*/
        System.out.println("Yes Here");
        BufferedImage image = null;
        int tempScreenX = screenX;
        int tempScreenY = screenY;

        switch (direction) {
            case "up" -> {
                if(blueBoy) {
                    if (!attacking) {
                        if (spriteNum == 1) {image = up1;}
                        if (spriteNum == 2) {image = up2;}
                    } else {
                        tempScreenY = screenY - gp.tileSize;
                        if (spriteNum == 1) {image = attackUp1;}
                        if (spriteNum == 2) {image = attackUp2;}
                    }
                    if (guarding) {image = guardUp;}
                } else {
                    if(!attacking){
                        image = up[spriteNum];
                    } else {
                        switch (currentWeapon.type){
                            case TYPE_SWORD -> image = sword_Up[spriteNum];
                            case TYPE_AXE -> image = axe_Up[spriteNum];
                            case TYPE_PICKAXE -> image = pickaxe_Up[spriteNum];
                        }
                    }
                }
            }
            case "down" -> {
                if(blueBoy) {
                    if (!attacking) {
                        if (spriteNum == 1) {image = down1;}
                        if (spriteNum == 2) {image = down2;}
                    } else {
                        if (spriteNum == 1) {image = attackDown1;}
                        if (spriteNum == 2) {image = attackDown2;}
                    }
                    if (guarding) {image = guardDown;}
                } else {
                    if(!attacking){
                        image = down[spriteNum];
                    } else {
                        switch (currentWeapon.type){
                            case TYPE_SWORD -> image = sword_Down[spriteNum];
                            case TYPE_AXE -> image = axe_Down[spriteNum];
                            case TYPE_PICKAXE -> image = pickaxe_Down[spriteNum];
                        }
                    }
                }
            }
            case "left" -> {
                if(blueBoy) {
                    if (!attacking) {
                        if (spriteNum == 1) {image = left1;}
                        if (spriteNum == 2) {image = left2;}
                    } else {
                        tempScreenX = screenX - gp.tileSize;
                        if (spriteNum == 1) {image = attackLeft1;}
                        if (spriteNum == 2) {image = attackLeft2;}
                    }
                    if (guarding) {image = guardLeft;}
                } else {
                    if(!attacking){
                        image = left[spriteNum];
                        System.out.println(spriteNum);
                    } else {
                        switch (currentWeapon.type){
                            case TYPE_SWORD -> image = sword_Left[spriteNum];
                            case TYPE_AXE -> image = axe_Left[spriteNum];
                            case TYPE_PICKAXE -> image = pickaxe_Left[spriteNum];
                        }
                    }
                }
            }
            case "right" -> {
                if(blueBoy) {
                    if (!attacking) {
                        if (spriteNum == 1) {image = right1;}
                        if (spriteNum == 2) {image = right2;}
                    } else {
                        if (spriteNum == 1) {image = attackRight1;}
                        if (spriteNum == 2) {image = attackRight2;}
                    }
                    if (guarding) {image = guardRight;}
                } else {
                    if(!attacking){
                        image = right[spriteNum];
                    } else {
                        switch (currentWeapon.type){
                            case TYPE_SWORD -> image = sword_Right[spriteNum];
                            case TYPE_AXE -> image = axe_Right[spriteNum];
                            case TYPE_PICKAXE -> image = pickaxe_Right[spriteNum];
                        }
                    }
                }
            }
        }

        if (transparent) {
            g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.3f));
        }

        if(drawing) {
            g2.drawImage(image, tempScreenX, tempScreenY, null);
        }

        // Reset alpha
        g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1f));


        // Debug
  /*      g2.setFont(new Font("Arial", Font.PLAIN, 26));
        g2.setColor(Color.WHITE);
        g2.drawString("Invincible: " + invincibleCounter, 10,400);*/
    }
}
