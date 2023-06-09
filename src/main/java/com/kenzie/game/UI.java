package com.kenzie.game;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.kenzie.database.MyHttpClient;
import com.kenzie.game.entity.Entity;
import com.kenzie.game.object.*;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

public class UI {

    GamePanel gp;
    Graphics2D g2;
    public Font maruMonica, purisaB;
    BufferedImage heart_full, heart_half, heart_blank, crystal_full, crystal_blank, coin, tempPlaler;

    public boolean actionDone = false;
    public int getAction = 0;
    public int getActionNumber = 0;
    public boolean messaageOn = false;
    public boolean showError = false;
    // public String message = "";
    // int messageCounter = 0;

    ArrayList<String> message = new ArrayList<>();
    ArrayList<Integer> messageCounter = new ArrayList<>();

    public boolean gameFinished = false;
    public String currentDialouge = "";
    public int commandNum = 0;
    public int titleScreenState = 0;
    public int playerSlotCol = 0;
    public int playerSlotRow = 0;
    public int npcSlotCol = 0;
    public int npcSlotRow = 0;
    int subState = 0;
    int counter = 0;
    public Entity npc;
    int charIndex = 0;
    String combinedText = "";

    int count = 0;
    int image = 1;


    public UI(GamePanel gp) {
        this.gp = gp;

        try {

            InputStream is = getClass().getResourceAsStream("/font/x12y16pxMaruMonica.ttf");
            maruMonica = Font.createFont(Font.TRUETYPE_FONT, is);
            is = getClass().getResourceAsStream("/font/Purisa Bold.ttf");
            purisaB = Font.createFont(Font.TRUETYPE_FONT, is);

        } catch (FontFormatException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        // Create HUD Objects
        Entity heart = new OBJ_Heart(gp);
        heart_full = heart.image;
        heart_half = heart.image2;
        heart_blank = heart.image3;

        Entity crystal = new OBJ_ManaCrystal(gp);
        crystal_full = crystal.image;
        crystal_blank = crystal.image2;

        Entity bronzeCoin = new OBJ_Coin_Bronze(gp);
        coin = bronzeCoin.down1;
    }

    public void addMessage(String text) {


        message.add(text);
        messageCounter.add(0);
    }

    public void draw(Graphics2D g2) {
        this.g2 = g2;

        g2.setFont(maruMonica);
        g2.setColor(Color.WHITE);

        // Title State
        if (gp.gameState == gp.TITLE_STATE) {
            drawTitleScreen();
        }


        // Play State
        if (gp.gameState == gp.PLAY_STATE) {
            drawPlayerLife();
            drawMonsterLife();
            drawMessage();

        }

        // Pause State
        if (gp.gameState == gp.PAUSE_STATE) {
            drawPlayerLife();
            drawPauseScreen();
        }

        // Dialogue State
        if (gp.gameState == gp.DIALOGUE_STATE) {

            drawDialogueScreen();
        }

        // Character State
        if (gp.gameState == gp.CHARACTER_STATE) {
            drawCharacterScreen();
            drawInventory(gp.player, true);
        }

        // Options State
        if (gp.gameState == gp.OPTIONS_STATE) {
            drawOptionsScreen();
        }

        // Game Over State
        if (gp.gameState == gp.GAME_OVER_STATE) {
            drawGameOverScreen();
        }

        // Transition State
        if (gp.gameState == gp.TRANSITION_STATE) {
            drawTransition();
        }

        // Trade State
        // Transition State
        if (gp.gameState == gp.TRADE_STATE) {
            drawTradeScreen();
        }

        // Sleep State
        if (gp.gameState == gp.SLEEP_STATE) {
            drawSleepScreen();
        }

        // Login
        if(gp.gameState == gp.LOG_IN_STATE){
            loginScreen();
        }

        // Character Selector State
        if(gp.gameState == gp.CHARACTER_SELECTOR_STATE){
            characterSector();
        }
    }

    public void drawGameOverScreen() {

        g2.setColor(new Color(0, 0, 0, 150));
        g2.fillRect(0, 0, gp.screenWidth, gp.screenHeight);

        int x;
        int y;
        String text;
        g2.setFont(g2.getFont().deriveFont(110f));

        text = "Game Over";

        // Shadow
        g2.setColor(Color.BLACK);
        x = getXforCenteredText(text);
        y = gp.tileSize * 4;
        g2.drawString(text, x, y);

        // Main Text
        g2.setColor(Color.WHITE);
        g2.drawString(text, x - 4, y - 4);

        //Retry
        g2.setFont(g2.getFont().deriveFont(50f));
        text = "Retry";
        x = getXforCenteredText(text);
        y += gp.tileSize * 4;
        g2.drawString(text, x - 4, y - 4);
        if (commandNum == 0) {
            g2.drawString(">", x - 40, y);
        }

        // Back to the title screen
        text = "Quit";
        x = getXforCenteredText(text);
        y += 55;
        g2.drawString(text, x - 4, y - 4);
        if (commandNum == 1) {
            g2.drawString(">", x - 40, y);
        }


    }

    public void drawPlayerLife() {

        int x = gp.tileSize / 2;
        int y = gp.tileSize / 2;
        int i = 0;
        int iconSize = 32;
        int manaStartX = (gp.tileSize / 2) - 5;
        int manaStartY = 0;

        // Draw Blank Heart
        while (i < gp.player.maxLife / 2) {

            g2.drawImage(heart_blank, x, y, iconSize, iconSize, null);
            i++;
            x += iconSize;
            manaStartY = y + 32;

            if (i % 8 == 0) {
                x = gp.tileSize / 2;
                y += iconSize;
            }
        }

        // Reset
        x = gp.tileSize / 2;
        y = gp.tileSize / 2;
        i = 0;

        // Draw Current Life
        while (i < gp.player.life) {

            g2.drawImage(heart_half, x, y, iconSize, iconSize, null);
            i++;
            if (i < gp.player.life) {
                g2.drawImage(heart_full, x, y, iconSize, iconSize, null);
            }
            i++;
            x += iconSize;
            manaStartY = y + 32;

            if (i % 16 == 0) {
                x = gp.tileSize / 2;
                y += iconSize;
            }
        }

        // Draw Max Mana
        x = gp.tileSize / 2 - 5;
        y = (int) (gp.tileSize * 2);
        i = 0;
        while (i < gp.player.maxMana) {
            g2.drawImage(crystal_blank, x, y, null);
            i++;
            x += 35;
        }

        // Draw Mana
        x = gp.tileSize / 2 - 5;
        y = (int) (gp.tileSize * 2);
        i = 0;

        while (i < gp.player.mana) {
            g2.drawImage(crystal_full, x, y, null);
            i++;
            x += 35;
        }

    }

    public void drawMonsterLife() {

        for (int i = 0; i < gp.monster[1].length; i++) {

            Entity monster = gp.monster[gp.currentMap][i];

            if (monster != null && monster.inCamera()) {

                if (monster.hpBarOn && !monster.boss) {
                    System.out.println("yes");
                    double oneScale = (double) gp.tileSize / monster.maxLife;
                    double hpBarValue = oneScale * monster.life;

                    g2.setColor(new Color(35, 35, 35));
                    g2.fillRect(monster.getScreenX() - 1, monster.getScreenY() - 16, gp.tileSize + 2, 12);

                    g2.setColor(new Color(255, 0, 30));
                    g2.fillRect(monster.getScreenX(), monster.getScreenY() - 15, (int) hpBarValue, 10);

                    monster.hpBarCounter++;
                    if (monster.hpBarCounter > 600) {
                        monster.hpBarCounter = 0;
                        monster.hpBarOn = false;
                    }
                }

                if (monster.boss) {

                    double oneScale = (double) gp.tileSize * 8 / monster.maxLife;
                    double hpBarValue = oneScale * monster.life;

                    int x = gp.screenWidth / 2 - gp.tileSize * 4;
                    int y = gp.tileSize * 10;

                    g2.setColor(new Color(35, 35, 35));
                    g2.fillRect(x - 1, y - 1, gp.tileSize * 8 + 2, 12);


                    g2.setColor(new Color(255, 0, 30));
                    g2.fillRect(x, y, (int) hpBarValue, 20);

                    g2.setFont(g2.getFont().deriveFont(Font.BOLD, 24f));
                    g2.setColor(Color.WHITE);
                    g2.drawString(monster.name, x + 4, y - 10);
                }
            }
        }


    }

    public void drawMessage() {

        int messageX = gp.tileSize;
        int messageY = gp.tileSize * 4;
        g2.setFont(g2.getFont().deriveFont(Font.BOLD, 32F));

        for (int i = 0; i < message.size(); i++) {
            if (message.get(i) != null) {

                g2.setColor(Color.BLACK);
                g2.drawString(message.get(i), messageX + 2, messageY + 2);

                g2.setColor(Color.WHITE);
                g2.drawString(message.get(i), messageX, messageY);

                int counter = messageCounter.get(i) + 1;
                messageCounter.set(i, counter);
                messageY += 50;

                if (messageCounter.get(i) > 180) {
                    message.remove(i);
                    messageCounter.remove(i);
                }
            }
        }
    }

    public void drawTitleScreen() {


        if (titleScreenState == 0) {

            count++;

            g2.setFont(g2.getFont().deriveFont(Font.BOLD, 96F));
            String text = "Quest for the JAVA gods";
            int x = getXforCenteredText(text);
            int y = gp.tileSize * 3;

            // Shadow
            g2.setColor(Color.GRAY);
            g2.drawString(text, x + 5, y + 5);

            // Main Color
            g2.setColor(Color.WHITE);
            g2.drawString(text, x, y);

            // Add Thank You to RyiSnow
            g2.setColor(Color.YELLOW);
            g2.setFont(g2.getFont().deriveFont(Font.BOLD, 24F));
            text = "Thanks to RIYSNOW for the great game Tutorial!";
            x = getXforCenteredText(text);
            y = gp.tileSize * 4;
            g2.drawString(text, x, y);


            g2.setColor(Color.WHITE);

            // Blue Boy Image
            x = gp.screenWidth / 2 - (gp.tileSize * 2) / 2;
            y += gp.tileSize;
            if (gp.player.blueBoy) {
                if (count > 12) {
                    if (image == 1) {
                        image = 2;
                    } else {
                        image = 1;
                    }
                    count = 0;
                }

                if (image == 1) {
                    g2.drawImage(gp.player.down1, x, y, gp.tileSize * 2, gp.tileSize * 2, null);
                }
                if (image == 2) {
                    g2.drawImage(gp.player.down2, x, y, gp.tileSize * 2, gp.tileSize * 2, null);
                }

            } else {
                if(count > 10) {
                    image++;
                    if (image == 3) {
                        image = 0;
                    }
                    count = 0;
                }

                g2.drawImage(gp.player.down[image], x, y, gp.tileSize * 3, gp.tileSize * 3, null);



            }
            // Menu
            g2.setFont(g2.getFont().deriveFont(Font.BOLD, 48F));

            text = "LOGIN";
            x = getXforCenteredText(text);
            y += gp.tileSize * 3.5;
            g2.drawString(text, x, y);
            if (commandNum == 0) {
                g2.drawString(">", x - gp.tileSize, y);
            }

//            text = "LOAD GAME";
//            x = getXforCenteredText(text);
//            y += gp.tileSize;
//            g2.drawString(text, x, y);
//            if (commandNum == 1) {
//                g2.drawString(">", x - gp.tileSize, y);
//            }

            text = "QUIT";
            x = getXforCenteredText(text);
            y += gp.tileSize;
            g2.drawString(text, x, y);
            if (commandNum == 1) {
                g2.drawString(">", x - gp.tileSize, y);
            }
        } else if (titleScreenState == 1) {

            // Class Selection Screen
            g2.setColor(Color.WHITE);
            g2.setFont(g2.getFont().deriveFont(42F));

            String text = "Select your class!";
            int x = getXforCenteredText(text);
            int y = gp.tileSize * 3;
            g2.drawString(text, x, y);

            text = "Fighter";
            x = getXforCenteredText(text);
            y += gp.tileSize * 3;
            g2.drawString(text, x, y);
            if (commandNum == 0) {
                g2.drawString(">", x - gp.tileSize, y);
            }

            text = "Thief";
            x = getXforCenteredText(text);
            y += gp.tileSize;
            g2.drawString(text, x, y);
            if (commandNum == 1) {
                g2.drawString(">", x - gp.tileSize, y);
            }

            text = "Sorcerer";
            x = getXforCenteredText(text);
            y += gp.tileSize;
            g2.drawString(text, x, y);
            if (commandNum == 2) {
                g2.drawString(">", x - gp.tileSize, y);
            }

            text = "Back";
            x = getXforCenteredText(text);
            y += gp.tileSize;
            g2.drawString(text, x, y);
            if (commandNum == 3) {
                g2.drawString(">", x - gp.tileSize, y);
            }
        }


    }

    public void drawPauseScreen() {

        String text = "PAUSED";
        int x = getXforCenteredText(text);
        int y = gp.screenHeight / 2;

        g2.drawString(text, x, y);
    }

    public void drawDialogueScreen() {

        // Window
        int x = gp.tileSize * 3;
        int y = gp.tileSize / 2;
        int width = gp.screenWidth - (gp.tileSize * 6);
        int height = gp.tileSize * 4;

        drawSubWindow(x, y, width, height);

        g2.setFont(g2.getFont().deriveFont(Font.PLAIN, 32F));
        x += gp.tileSize;
        y += gp.tileSize;

        if (npc.dialogues[npc.dialogueSet][npc.dialogueIndex] != null) {
            //currentDialouge = npc.dialogues[npc.dialogueSet][npc.dialogueIndex];

            char[] characters = npc.dialogues[npc.dialogueSet][npc.dialogueIndex].toCharArray();

            if (charIndex < characters.length) {
                gp.playSE(17);
                String s = String.valueOf(characters[charIndex]);
                combinedText = combinedText + s;
                currentDialouge = combinedText;
                charIndex++;
            }

            if (gp.keyH.enterPressed) {
                charIndex = 0;
                combinedText = "";
                if (gp.gameState == gp.DIALOGUE_STATE || gp.gameState == gp.CUT_SCENE_STATE) {
                    npc.dialogueIndex++;
                    gp.keyH.enterPressed = false;
                }
            }
        } else {
            npc.dialogueIndex = 0;
            if (gp.gameState == gp.DIALOGUE_STATE) {
                gp.gameState = gp.PLAY_STATE;
            }

            if (gp.gameState == gp.CUT_SCENE_STATE) {
                gp.csManager.scenePhase++;
            }
        }


        for (String line : currentDialouge.split("\n")) {
            g2.drawString(line, x, y);
            y += 40;
        }


    }

    public void drawCharacterScreen() {

        // Create a frame
        final int frameX = gp.tileSize * 2;
        final int frameY = gp.tileSize;
        final int frameWidth = gp.tileSize * 5;
        final int frameHeight = gp.tileSize * 10;
        drawSubWindow(frameX, frameY, frameWidth, frameHeight);

        // Text
        g2.setColor(Color.WHITE);
        g2.setFont(g2.getFont().deriveFont(32f));

        int textX = frameX + 20;
        int textY = frameY + gp.tileSize;
        final int lineHeight = 35;

        // Names
        g2.drawString("Level", textX, textY);
        textY += lineHeight;
        g2.drawString("Life", textX, textY);
        textY += lineHeight;
        g2.drawString("Mana", textX, textY);
        textY += lineHeight;
        g2.drawString("Strength", textX, textY);
        textY += lineHeight;
        g2.drawString("Dexterity", textX, textY);
        textY += lineHeight;
        g2.drawString("Attack", textX, textY);
        textY += lineHeight;
        g2.drawString("Defense", textX, textY);
        textY += lineHeight;
        g2.drawString("Exp", textX, textY);
        textY += lineHeight;
        g2.drawString("Next Level", textX, textY);
        textY += lineHeight;
        g2.drawString("Coin", textX, textY);
        textY += lineHeight + 20;
        g2.drawString("Weapon", textX, textY);
        textY += lineHeight + 15;
        g2.drawString("Shield", textX, textY);

        // Values
        int tailX = (frameX + frameWidth) - 30;
        // Reset textY
        textY = frameY + gp.tileSize;
        String value;

        value = String.valueOf(gp.player.level);
        textX = getXforAlignToRight(value, tailX);
        g2.drawString(value, textX, textY);

        textY += lineHeight;
        value = String.valueOf(gp.player.life + "/" + gp.player.maxLife);
        textX = getXforAlignToRight(value, tailX);
        g2.drawString(value, textX, textY);

        textY += lineHeight;
        value = String.valueOf(gp.player.mana + "/" + gp.player.maxMana);
        textX = getXforAlignToRight(value, tailX);
        g2.drawString(value, textX, textY);

        textY += lineHeight;
        value = String.valueOf(gp.player.strength);
        textX = getXforAlignToRight(value, tailX);
        g2.drawString(value, textX, textY);

        textY += lineHeight;
        value = String.valueOf(gp.player.dexterity);
        textX = getXforAlignToRight(value, tailX);
        g2.drawString(value, textX, textY);

        textY += lineHeight;
        value = String.valueOf(gp.player.attack);
        textX = getXforAlignToRight(value, tailX);
        g2.drawString(value, textX, textY);

        textY += lineHeight;
        value = String.valueOf(gp.player.defense);
        textX = getXforAlignToRight(value, tailX);
        g2.drawString(value, textX, textY);

        textY += lineHeight;
        value = String.valueOf(gp.player.exp);
        textX = getXforAlignToRight(value, tailX);
        g2.drawString(value, textX, textY);

        textY += lineHeight;
        value = String.valueOf(gp.player.nextLevelExp);
        textX = getXforAlignToRight(value, tailX);
        g2.drawString(value, textX, textY);

        textY += lineHeight;
        value = String.valueOf(gp.player.coin);
        textX = getXforAlignToRight(value, tailX);
        g2.drawString(value, textX, textY);

        textY += lineHeight;
        g2.drawImage(gp.player.currentWeapon.down1, tailX - gp.tileSize, textY - 24, null);
        textY += gp.tileSize;
        g2.drawImage(gp.player.currentShield.down1, tailX - gp.tileSize, textY - 24, null);


    }

    public void drawInventory(Entity entity, boolean cursor) {

        int frameX = 0;
        int frameY = 0;
        int frameWidth = 0;
        int frameHeight = 0;
        int slotCol = 0;
        int slotRow = 0;

        if (entity == gp.player) {

            frameX = gp.tileSize * 12;
            frameY = gp.tileSize;
            frameWidth = gp.tileSize * 6;
            frameHeight = gp.tileSize * 5;
            slotCol = playerSlotCol;
            slotRow = playerSlotRow;

        } else {

            frameX = gp.tileSize * 2;
            frameY = gp.tileSize;
            frameWidth = gp.tileSize * 6;
            frameHeight = gp.tileSize * 5;
            slotCol = npcSlotCol;
            slotRow = npcSlotRow;

        }
        drawSubWindow(frameX, frameY, frameWidth, frameHeight);

        // Slot
        final int slotXstart = frameX + 20;
        final int slotYstart = frameY + 20;
        int slotX = slotXstart;
        int slotY = slotYstart;
        int slotSize = gp.tileSize + 3;

        // Draw Player's Items
        for (int i = 0; i < entity.inventory.size(); i++) {

            // Equip Cursor
            if (entity.inventory.get(i) == entity.currentWeapon ||
                    entity.inventory.get(i) == entity.currentShield ||
                    entity.inventory.get(i) == entity.currentLight) {

                g2.setColor(new Color(240, 190, 90));
                g2.fillRoundRect(slotX, slotY, gp.tileSize, gp.tileSize, 10, 10);
            }

            g2.drawImage(entity.inventory.get(i).down1, slotX, slotY, null);

            // Display Amount
            if (entity == gp.player && entity.inventory.get(i).amount > 1) {

                g2.setFont(g2.getFont().deriveFont(32f));
                int amountX;
                int amountY;

                String s = "" + entity.inventory.get(i).amount;
                amountX = getXforAlignToRight(s, slotX + 44);
                amountY = slotY + gp.tileSize;

                // Shadow Text
                g2.setColor(new Color(60, 60, 60));
                g2.drawString(s, amountX, amountY);

                // Number
                g2.setColor(Color.WHITE);
                g2.drawString(s, amountX - 3, amountY - 3);

            }

            slotX += slotSize;

            if (i == 4 || i == 9 || i == 14) {
                slotX = slotXstart;
                slotY += slotSize;
            }


        }

        // Cursor
        if (cursor) {
            int cursorX = slotXstart + (slotSize * slotCol);
            int cursorY = slotYstart + (slotSize * slotRow);
            int cursorWidth = gp.tileSize;
            int cursorHeight = gp.tileSize;

            // Draw Cursor
            g2.setColor(Color.WHITE);
            g2.setStroke(new BasicStroke(3));
            g2.drawRoundRect(cursorX, cursorY, cursorWidth, cursorHeight, 10, 10);

            // Description Frame
            int dFrameX = frameX;
            int dFrameY = frameY + frameHeight;
            int dFrameWidth = frameWidth;
            int dFrameHeight = gp.tileSize * 3;
            drawSubWindow(dFrameX, dFrameY, dFrameWidth, dFrameHeight);

            // Draw Description Text
            int textX = dFrameX + 20;
            int textY = dFrameY + gp.tileSize;

            g2.setFont(g2.getFont().deriveFont(28f));

            int itemIndex = getItemIndexOnSlot(slotCol, slotRow);

            if (itemIndex < entity.inventory.size()) {
                for (String line : entity.inventory.get(itemIndex).description.split("\n")) {
                    g2.drawString(line, textX, textY);
                    textY += 32;
                }
            }

        }


    }

    public void drawOptionsScreen() {

        g2.setColor(Color.WHITE);
        g2.setFont(g2.getFont().deriveFont(32F));

        // Sub Window
        int frameX = gp.tileSize * 6;
        int frameY = gp.tileSize;
        int frameWidth = gp.tileSize * 8;
        int frameHeight = gp.tileSize * 10;
        drawSubWindow(frameX, frameY, frameWidth, frameHeight);

        switch (subState) {
            case 0:
                options_top(frameX, frameY);
                break;
            case 1:
                options_fullScreenNotification(frameX, frameY);
                break;
            case 2:
                options_control(frameX, frameY);
                break;
            case 3:
                options_endGameConfirmation(frameX, frameY);
        }

        gp.keyH.enterPressed = false;
    }

    public void options_top(int frameX, int frameY) {

        int textX;
        int textY;

        // Title
        String text = "Options";
        textX = getXforCenteredText(text);
        textY = frameY + gp.tileSize;
        g2.drawString(text, textX, textY);

        // Full Screen On/Off
        textX = frameX + gp.tileSize;
        textY += gp.tileSize * 2;
        g2.drawString("Full Screen", textX, textY);
        if (commandNum == 0) {
            g2.drawString(">", textX - 25, textY);
            if (gp.keyH.enterPressed) {
                gp.fullScreenOn = !gp.fullScreenOn;
                subState = 1;
            }

        }

        // Music
        textY += gp.tileSize;
        g2.drawString("Music", textX, textY);
        if (commandNum == 1) {
            g2.drawString(">", textX - 25, textY);
        }


        // SE
        textY += gp.tileSize;
        g2.drawString("SE", textX, textY);
        if (commandNum == 2) {
            g2.drawString(">", textX - 25, textY);
        }

        // Control
        textY += gp.tileSize;
        g2.drawString("Control", textX, textY);
        if (commandNum == 3) {
            g2.drawString(">", textX - 25, textY);
            if (gp.keyH.enterPressed) {
                subState = 2;
                commandNum = 0;
            }
        }

        // End Game
        textY += gp.tileSize;
        g2.drawString("End Game", textX, textY);
        if (commandNum == 4) {
            g2.drawString(">", textX - 25, textY);
            if (gp.keyH.enterPressed) {
                subState = 3;
                commandNum = 0;
            }
        }

        // Back
        textY += gp.tileSize * 2;
        g2.drawString("Back", textX, textY);
        if (commandNum == 5) {
            g2.drawString(">", textX - 25, textY);
            if (gp.keyH.enterPressed) {
                gp.gameState = gp.PLAY_STATE;
                commandNum = 0;
            }
        }

        // Full Screen Check Box
        textX = frameX + (int) (gp.tileSize * 4.5);
        textY = frameY + gp.tileSize * 2 + 24;
        g2.setStroke(new BasicStroke(3));
        g2.drawRect(textX, textY, 24, 24);
        if (gp.fullScreenOn) {
            g2.fillRect(textX, textY, 24, 24);
        }

        // Music Volume
        textY += gp.tileSize;
        g2.drawRect(textX, textY, 120, 24); // 120 / 4 = 24
        int volumeWidth = 24 * gp.music.volumeScale;
        g2.fillRect(textX, textY, volumeWidth, 24);

        // SE Volume
        textY += gp.tileSize;
        g2.drawRect(textX, textY, 120, 24);
        volumeWidth = 24 * gp.se.volumeScale;
        g2.fillRect(textX, textY, volumeWidth, 24);

        gp.config.saveConfig();
    }

    public void options_fullScreenNotification(int frameX, int frameY) {

        int textX = frameX + gp.tileSize;
        int textY = frameY + gp.tileSize * 3;

        currentDialouge = "The change will take \neffect after restarting \nthe game.";

        for (String line : currentDialouge.split("\n")) {
            g2.drawString(line, textX, textY);
            textY += 40;
        }

        // Back
        textY = frameY + gp.tileSize * 9;
        g2.drawString("Back", textX, textY);

        if (commandNum == 0) {
            g2.drawString(">", textX - 25, textY);
            if (gp.keyH.enterPressed) {
                subState = 0;
            }
        }

    }

    public void options_control(int frameX, int frameY) {

        int textX;
        int textY;

        // Title
        String text = "Control";
        textX = getXforCenteredText(text);
        textY = frameY + gp.tileSize;

        textX = frameX + gp.tileSize;
        textY += gp.tileSize;

        g2.drawString("Move", textX, textY);
        textY += gp.tileSize;
        g2.drawString("Confirm/Attack", textX, textY);
        textY += gp.tileSize;
        g2.drawString("Shoot/Cast", textX, textY);
        textY += gp.tileSize;
        g2.drawString("Character Screen", textX, textY);
        textY += gp.tileSize;
        g2.drawString("Pause", textX, textY);
        textY += gp.tileSize;
        g2.drawString("Options", textX, textY);

        textX = frameX + gp.tileSize * 6;
        textY = frameY + gp.tileSize * 2;

        g2.drawString("WASD", textX, textY);
        textY += gp.tileSize;
        g2.drawString("ENTER", textX, textY);
        textY += gp.tileSize;
        g2.drawString("F", textX, textY);
        textY += gp.tileSize;
        g2.drawString("C", textX, textY);
        textY += gp.tileSize;
        g2.drawString("P", textX, textY);
        textY += gp.tileSize;
        g2.drawString("ESC", textX, textY);

        // Back
        textX = frameX + gp.tileSize;
        textY = frameY + gp.tileSize * 9;
        g2.drawString("Back", textX, textY);
        if (commandNum == 0) {
            g2.drawString(">", textX - 25, textY);
            if (gp.keyH.enterPressed) {
                subState = 0;
                commandNum = 3;
            }
        }

    }

    public void options_endGameConfirmation(int frameX, int frameY) {

        int textX = frameX + gp.tileSize;
        int textY = frameY + gp.tileSize * 3;

        currentDialouge = "Quit the game and \nreturn to the title screen?";

        for (String line : currentDialouge.split("\n")) {
            g2.drawString(line, textX, textY);
            textY += 40;
        }

        // Yes
        String text = "Yes";
        textX = getXforCenteredText(text);
        textY += gp.tileSize * 3;

        g2.drawString(text, textX, textY);
        if (commandNum == 0) {
            g2.drawString(">", textX - 25, textY);
            if (gp.keyH.enterPressed) {
                subState = 0;
                gp.gameState = gp.TITLE_STATE;
                gp.resetGame(true);

            }
        }

        // No
        text = "No";
        textX = getXforCenteredText(text);
        textY += gp.tileSize;

        g2.drawString(text, textX, textY);
        if (commandNum == 1) {
            g2.drawString(">", textX - 25, textY);
            if (gp.keyH.enterPressed) {
                subState = 0;
                commandNum = 4;
            }
        }


    }

    public void drawTransition() {

        counter++;
        g2.setColor(new Color(0, 0, 0, counter * 5));
        g2.fillRect(0, 0, gp.screenWidth, gp.screenHeight);

        if (counter == 50) {
            counter = 0;
            gp.gameState = gp.PLAY_STATE;
            gp.currentMap = gp.eHandler.tempMap;
            gp.player.worldX = gp.tileSize * gp.eHandler.tempCol;
            gp.player.worldY = gp.tileSize * gp.eHandler.tempRow;
            gp.eHandler.previousEventX = gp.player.worldX;
            gp.eHandler.previousEventY = gp.player.worldY;
            gp.changeArea();
        }
    }

    public void drawTradeScreen() {

        switch (subState) {
            case 0 -> trade_select();
            case 1 -> trade_buy();
            case 2 -> trade_sell();
        }

        gp.keyH.enterPressed = false;

    }

    public void trade_select() {

        npc.dialogueSet = 0;
        drawDialogueScreen();

        // Draw Window
        int x = gp.tileSize * 15;
        int y = gp.tileSize * 4;
        int width = gp.tileSize * 3;
        int height = (int) (gp.tileSize * 3.5);

        drawSubWindow(x, y, width, height);

        // Draw Texts
        x += gp.tileSize;
        y += gp.tileSize;
        g2.drawString("Buy", x, y);
        if (commandNum == 0) {
            g2.drawString(">", x - 24, y);

            if (gp.keyH.enterPressed) {

                subState = 1;
            }
        }

        y += gp.tileSize;
        g2.drawString("Sell", x, y);
        if (commandNum == 1) {
            g2.drawString(">", x - 24, y);
            if (gp.keyH.enterPressed) {
                subState = 2;
            }
        }

        y += gp.tileSize;
        g2.drawString("Leave", x, y);
        if (commandNum == 2) {
            g2.drawString(">", x - 24, y);
            if (gp.keyH.enterPressed) {
                commandNum = 0;
                npc.startDialogue(npc, 1);

            }
        }

    }

    public void trade_buy() {

        // Draw Player inventory
        drawInventory(gp.player, false);

        // Draw NPC Inventory
        drawInventory(npc, true);

        // Draw Hite Window
        int x = gp.tileSize * 2;
        int y = gp.tileSize * 9;
        int width = gp.tileSize * 6;
        int height = gp.tileSize * 2;
        drawSubWindow(x, y, width, height);
        g2.drawString("[ESC] Back", x + 24, y + 60);


        // Draw Player Coin Window
        x = gp.tileSize * 12;
        y = gp.tileSize * 9;
        width = gp.tileSize * 6;
        height = gp.tileSize * 2;
        drawSubWindow(x, y, width, height);
        g2.drawString("Your Coins" + gp.player.coin, x + 24, y + 60);

        // Draw Price Window
        int itemIndex = getItemIndexOnSlot(npcSlotCol, npcSlotRow);
        if (itemIndex < npc.inventory.size()) {

            x = (int) (gp.tileSize * 5.5);
            y = (int) (gp.tileSize * 5.5);
            width = (int) (gp.tileSize * 2.5);
            height = gp.tileSize;
            drawSubWindow(x, y, width, height);
            g2.drawImage(coin, x + 10, y + 8, 32, 32, null);

            int price = npc.inventory.get(itemIndex).price;
            String text = "" + price;
            x = getXforAlignToRight(text, gp.tileSize * 8 - 20);
            g2.drawString(text, x, y + 34);

            // Buy An Item
            if (gp.keyH.enterPressed) {
                if (price > gp.player.coin) {
                    subState = 0;

                    npc.startDialogue(npc, 2);

                } else {
                    if (gp.player.canObtainItem(npc.inventory.get(itemIndex))) {
                        gp.player.coin -= price;
                    } else {
                        subState = 0;
                        npc.startDialogue(npc, 3);
                    }

                }

            }
        }
    }

    public void trade_sell() {

        // Draw Player Inventory
        drawInventory(gp.player, true);

        int x;
        int y;
        int width;
        int height;

        // Draw Hite Window
        x = gp.tileSize * 2;
        y = gp.tileSize * 9;
        width = gp.tileSize * 6;
        height = gp.tileSize * 2;
        drawSubWindow(x, y, width, height);
        g2.drawString("[ESC] Back", x + 24, y + 60);


        // Draw Player Coin Window
        x = gp.tileSize * 12;
        y = gp.tileSize * 9;
        width = gp.tileSize * 6;
        height = gp.tileSize * 2;
        drawSubWindow(x, y, width, height);
        g2.drawString("Your Coins" + gp.player.coin, x + 24, y + 60);

        // Draw Price Window
        int itemIndex = getItemIndexOnSlot(playerSlotCol, playerSlotRow);
        if (itemIndex < gp.player.inventory.size()) {

            x = (int) (gp.tileSize * 15.5);
            y = (int) (gp.tileSize * 5.5);
            width = (int) (gp.tileSize * 2.5);
            height = gp.tileSize;
            drawSubWindow(x, y, width, height);
            g2.drawImage(coin, x + 10, y + 8, 32, 32, null);

            int price = npc.inventory.get(itemIndex).price / 2;
            String text = "" + price;
            x = getXforAlignToRight(text, gp.tileSize * 18 - 20);
            g2.drawString(text, x, y + 34);

            // Sell An Item
            if (gp.keyH.enterPressed) {

                if (gp.player.inventory.get(itemIndex) == gp.player.currentWeapon ||
                        gp.player.inventory.get(itemIndex) == gp.player.currentShield) {

                    commandNum = 0;
                    subState = 0;
                    npc.startDialogue(npc, 4);
                } else {
                    if (gp.player.inventory.get(itemIndex).amount > 1) {
                        gp.player.inventory.get(itemIndex).amount--;
                    } else {
                        gp.player.inventory.remove(itemIndex);
                    }
                    gp.player.coin += price;
                }

            }
        }

    }

    public void drawSleepScreen() {

        counter++;
        if (counter < 120) {
            gp.eManager.lighting.filterAlph += 0.01f;
            if (gp.eManager.lighting.filterAlph > 1f) {
                gp.eManager.lighting.filterAlph = 1f;
            }
        }

        if (counter >= 120) {
            gp.eManager.lighting.filterAlph -= 0.01f;
            if (gp.eManager.lighting.filterAlph <= 0f) {
                gp.eManager.lighting.filterAlph = 0f;
                counter = 0;
                gp.eManager.lighting.dayState = gp.eManager.lighting.DAY;
                gp.eManager.lighting.dayCounter = 0;
                gp.gameState = gp.PLAY_STATE;
                gp.player.getImage();
            }
        }
    }

    public int getItemIndexOnSlot(int slotCol, int slotRow) {
        return slotCol + (slotRow * 5);
    }

    public void loginScreen(){



        g2.setFont(g2.getFont().deriveFont(Font.BOLD, 96F));
        String text = "Quest for the JAVA gods";
        int x = getXforCenteredText(text);
        int y = gp.tileSize * 3;

        // Shadow
        g2.setColor(Color.GRAY);
        g2.drawString(text, x + 5, y + 5);

        // Main Color
        g2.setColor(Color.WHITE);
        g2.drawString(text, x, y);

        // Login

        g2.setColor(Color.BLUE);
        g2.setFont(g2.getFont().deriveFont(Font.BOLD, 44F));
        text = "Login";
        x = getXforCenteredText(text);
        y = gp.tileSize * 5;
        g2.drawString(text, x, y);

        g2.setColor(Color.GRAY);
        g2.setFont(g2.getFont().deriveFont(Font.BOLD, 18F));
        text = "Use Arrow Keys";
        x = getXforCenteredText(text);
        y = gp.tileSize * 5 + 30;
        g2.drawString(text, x, y);


        g2.setColor(Color.WHITE);
        g2.setFont(g2.getFont().deriveFont(Font.BOLD, 30F));
        text = "User Name: ";
        x = 200;
        y += 75;
        g2.drawString(text, x, y);
        g2.drawString(gp.userName, x + 140, y);
        if (commandNum == 0) {
            g2.drawString(">", x - gp.tileSize, y);
            gp.userName = getText(gp.userName);
        }

        text = "Password: ";
        x = 210;
        y += 50;
        g2.drawString(text, x, y);
        g2.drawString(gp.password, x + 136, y);

        if (commandNum == 1) {
            g2.drawString(">", x - gp.tileSize, y);
            gp.password = getText(gp.password);
        }

        g2.setColor(Color.GREEN);
        text = "Enter: ";
        x = 262;
        y += 50;
        g2.drawString(text, x, y);
        if (commandNum == 2) {
            g2.drawString(">", x - gp.tileSize, y);

            if(!showError) {
                if (gp.keyH.enterPressed) {

                    // send username and password to db
                MyHttpClient myHttpClient = new MyHttpClient();
                String URLString = "http://localhost:5001/user/" + gp.userName.toLowerCase();
                String results = myHttpClient.makeGETRequest(URLString);
                    System.out.println(URLString);

                    JsonElement jsonElement = JsonParser.parseString(results);

                    JsonObject jsonObject = jsonElement.getAsJsonObject();
                    gp.userID = String.valueOf(jsonObject.get("memberId"));
                    gp.player.currentCharacterID = Integer.parseInt(String.valueOf(jsonObject.get("currentCharacter").getAsInt()));
                    System.out.println(gp.userID);

                    if (!results.equals("GET request failed: 404 status code received")) {
                        // Set User data
                        System.out.println(results);
                        // get user data from results

                        gp.gameState = gp.CHARACTER_SELECTOR_STATE;

                        // get current character
                        if(gp.player.currentCharacterID != 0){

                            URLString = "http://localhost:5001/character/" + gp.player.currentCharacterID;
                            results = myHttpClient.makeGETRequest(URLString);
                            jsonElement = JsonParser.parseString(results);
                            jsonObject = jsonElement.getAsJsonObject();

                            // get current and temp values character data
                            gp.player.life = Integer.parseInt(String.valueOf(jsonObject.get("life").getAsInt()));
                            gp.player.mana = Integer.parseInt(String.valueOf(jsonObject.get("mana").getAsInt()));
                            gp.player.maxMana = Integer.parseInt(String.valueOf(jsonObject.get("maxMana").getAsInt()));
                            gp.player.strength = Integer.parseInt(String.valueOf(jsonObject.get("strength").getAsInt()));
                            gp.player.dexterity = Integer.parseInt(String.valueOf(jsonObject.get("dexterity").getAsInt()));

                            Entity setMe = new Entity(gp);

                            switch (String.valueOf(jsonObject.get("characterName"))){
                                case "Normal Sword" -> setMe = new OBJ_Sword_Normal(gp);
                                case "Pickaxe" -> setMe = new OBJ_Pickaxe(gp);
                                case "Woodcutter's Axe" -> setMe = new OBJ_Axe(gp);
                            }

                            gp.player.currentWeapon = setMe;


                            gp.player.currentShield = new OBJ_Shield_Wood(gp);

                            switch (Integer.parseInt(String.valueOf(jsonObject.get("currentProjectile").getAsInt()))){
                                case 1 -> setMe = new OBJ_Fireball(gp);
                                case 2 -> setMe = new OBJ_Rock(gp);
                            }


                            //gp.player.projectile = setMe;


                            gp.player.level = Integer.parseInt(String.valueOf(jsonObject.get("level").getAsInt()));
                            gp.player.nextLevelExp = Integer.parseInt(String.valueOf(jsonObject.get("nextLevelExp").getAsInt()));
                            gp.player.coin = Integer.parseInt(String.valueOf(jsonObject.get("coin").getAsInt()));
                            gp.player.name =  String.valueOf(jsonObject.get("characterName"));
                            // set to current character ID
                            gp.player.getCharacterImages(Integer.parseInt(String.valueOf(jsonObject.get("nextLevelExp").getAsInt())));

                            // make temp values
                            gp.player.tempLife = gp.player.life;
                            gp.player.tempMana = gp.player.mana;
                            gp.player.tempMaxMana = gp.player.maxMana;
                            gp.player.tempStrength = gp.player.strength;
                            gp.player.tempDexterity = gp.player.dexterity;
                            gp.player.tempCurrentWeapon = gp.player.currentWeapon;
                            gp.player.tempCurrentShield = gp.player.currentShield;
                            gp.player.tempCurrentProjectile = gp.player.projectile;
                            gp.player.tempLevel = gp.player.level;
                            gp.player.tempNextLevelExp = gp.player.nextLevelExp;
                            gp.player.tempCoin = gp.player.coin;
                            gp.player.tempName = gp.player.name;
                            gp.player.tempCharacterID = gp.player.currentCharacterID;


                        } else {
                           makeRandomCharacter();

                        }



                    } else {
                        // Show message that Login Did work try again
                        showError = true;
                        gp.keyH.enterPressed = false;
                    }


                }
            }

        }

        if(showError){
            g2.setColor(Color.RED);
            g2.setFont(g2.getFont().deriveFont(Font.BOLD, 44F));
            text = "Login Failed, Try Again!";
            x = getXforCenteredText(text);
            y += 55;
            g2.drawString(text, x, y);

            g2.setColor(Color.GRAY);
            g2.setFont(g2.getFont().deriveFont(Font.BOLD, 24F));
            text = "Enter to reset.";
            x = getXforCenteredText(text);
            y += 25;
            g2.drawString(text, x, y);

            if(gp.keyH.enterPressed){
                gp.userName = "";
                gp.password = "";
                showError = false;
                commandNum = 0;

            }
        }



        if(gp.keyH.enterPressed){
            gp.keyH.enterPressed = false;
        }



    }

    public void testScreen(){
        int x = 50;
        int y = 50;



        if(gp.keyH.backSpace){

            if(gp.userName.length() != 0){
                gp.userName = gp.userName.substring(0,gp.userName.length() - 1);
            }
            gp.keyH.backSpace = false;

        } else if(gp.getText) {

                gp.userName += gp.nextLetter;
                gp.getText = false;

        }
        g2.setColor(Color.WHITE);
        g2.setFont(g2.getFont().deriveFont(32f));

        g2.drawString("User Name", x, y);

        g2.drawString(gp.userName, x + 130, y);


    }

    public void characterSector(){

        g2.setFont(g2.getFont().deriveFont(Font.BOLD, 34F));
        String text = "Character Selector";
        int x = getXforCenteredText(text);
        int y = gp.tileSize * 1;

        // Shadow
        g2.setColor(Color.GRAY);
        g2.drawString(text, x + 5, y + 5);

        // Main Color
        g2.setColor(Color.WHITE);
        g2.drawString(text, x, y);

        g2.setFont(g2.getFont().deriveFont(Font.BOLD, 24F));
        text = "Strength: ";
        x = 100;
        y = gp.tileSize * 3;

        // Main Color
        g2.setColor(Color.WHITE);
        g2.drawString(text, x, y);
        g2.drawString(String.valueOf(gp.player.tempStrength), x + 90, y);


        g2.setFont(g2.getFont().deriveFont(Font.BOLD, 24F));
        text = "Dexterity: ";
        x = 97;
        y += 50;

        g2.drawString(text, x, y);
        g2.drawString(String.valueOf(gp.player.tempDexterity), x +95, y);


        g2.setFont(g2.getFont().deriveFont(Font.BOLD, 24F));
        text = "Life: ";
        x = 146;
        y += 50;

        g2.drawString(text, x, y);
        g2.drawString(String.valueOf(gp.player.tempLife), x + 49, y);

        g2.setFont(g2.getFont().deriveFont(Font.BOLD, 24F));
        text = "Mana: ";
        x = 131;
        y += 50;

        g2.drawString(text, x, y);
        g2.drawString(String.valueOf(gp.player.tempMana), x + 65, y);

        g2.setFont(g2.getFont().deriveFont(Font.BOLD, 24F));
        text = "Coins: ";
        x = 131;
        y += 50;

        g2.drawString(text, x, y);
        g2.drawString(String.valueOf(gp.player.tempCoin), x + 67, y);

        g2.setFont(g2.getFont().deriveFont(Font.BOLD, 24F));
        text = "Weapon: ";
        x = 110;
        y += 50;

        g2.drawString(text, x, y);
        g2.drawString(String.valueOf(gp.player.tempCurrentWeapon), x + 88, y);

        g2.setColor(Color.RED);
        g2.setFont(g2.getFont().deriveFont(Font.BOLD, 24F));
        text = "Kills: ";
        x = 747;
        y = gp.tileSize * 3;

        g2.drawString(text, x, y);
        g2.drawString(String.valueOf(gp.player.tempKills), x + 50, y);

        g2.setFont(g2.getFont().deriveFont(Font.BOLD, 24F));
        text = "Deaths: ";
        x = 725;
        y += 50;
        g2.setColor(Color.GRAY);
        g2.drawString(text, x, y);
        g2.drawString(String.valueOf(gp.player.tempDeaths), x + 73, y);

        g2.setFont(g2.getFont().deriveFont(Font.BOLD, 24F));
        text = "Level: ";
        x = 740;
        y += 50;
        g2.setColor(Color.WHITE);
        g2.drawString(text, x, y);
        g2.drawString(String.valueOf(gp.player.tempLevel), x + 59, y);

        g2.setFont(g2.getFont().deriveFont(Font.BOLD, 24F));
        text = "Next Level: ";
        x = 693;
        y += 50;

        g2.drawString(text, x, y);
        g2.drawString(String.valueOf(gp.player.tempNextLevelExp), x + 105, y);

        g2.setFont(g2.getFont().deriveFont(Font.BOLD, 24F));
        text = "Projectile: ";
        x = 701;
        y += 50;

        g2.drawString(text, x, y);
        g2.drawString(String.valueOf(gp.player.tempCurrentProjectile), x + 97, y);

        g2.setFont(g2.getFont().deriveFont(Font.BOLD, 24F));
        text = "Shield: ";
        x = 727;
        y += 50;

        g2.drawString(text, x, y);
        g2.drawString(String.valueOf(gp.player.tempCurrentShield), x + 69, y);


        // display current character
        count++;
        if(count > 10) {
            image++;
            if (image == 3) {
                image = 0;
                actionDone = true;
            }
            count = 0;
        }

        if(actionDone) {
            getAction = gp.ut.rnd.nextInt(100) + 1;
            getActionNumber = gp.ut.rnd.nextInt(16) + 1;
            image = 0;
            actionDone = false;
        }


        if(getAction < 85){
            tempPlaler = gp.player.down[image];
        } else {
            switch (getActionNumber){
                case 1 -> tempPlaler = gp.player.down[image];
                case 2 -> tempPlaler = gp.player.up[image];
                case 3 -> tempPlaler = gp.player.left[image];
                case 4 -> tempPlaler = gp.player.right[image];
                case 5 -> tempPlaler = gp.player.sword_Down[image];
                case 6 -> tempPlaler = gp.player.sword_Up[image];
                case 7 -> tempPlaler = gp.player.sword_Left[image];
                case 8 -> tempPlaler = gp.player.sword_Right[image];
                case 9 -> tempPlaler = gp.player.pickaxe_Down[image];
                case 10 -> tempPlaler = gp.player.pickaxe_Up[image];
                case 11 -> tempPlaler = gp.player.pickaxe_Left[image];
                case 12 -> tempPlaler = gp.player.pickaxe_Right[image];
                case 13 -> tempPlaler = gp.player.axe_Down[image];
                case 14 -> tempPlaler = gp.player.axe_Up[image];
                case 15 -> tempPlaler = gp.player.axe_Left[image];
                case 16 -> tempPlaler = gp.player.axe_Right[image];
            }

        }

        g2.drawImage(tempPlaler, 400, 200, gp.tileSize * 3, gp.tileSize * 3, null);

        g2.setFont(g2.getFont().deriveFont(Font.BOLD, 24F));
        text = "Name: ";
        x = 430;
        y = 360;
        g2.setColor(Color.MAGENTA);
        g2.drawString(text, x, y);
        g2.drawString(String.valueOf(gp.player.tempCurrentShield), x + 69, y);

        g2.setFont(g2.getFont().deriveFont(Font.BOLD, 38F));
        text = "Select Character";
        x = 400;
        y = 460;
        g2.setColor(Color.WHITE);
        g2.drawString(text, x, y);
        if (commandNum == 0) {
            g2.drawString(">", x - gp.tileSize, y);

            if(gp.keyH.enterPressed){
                // update user with new current character id
                MyHttpClient myHttpClient = new MyHttpClient();
                String URLString = "http://localhost:5001/user/" + gp.userName.toLowerCase();
                String requestBody = "{" + "userName" + ":" + gp.userName + ",";
               /* re



                        "memberId": "133e4214-c7a5-4235-8e55-4a781e026224",
                        "email":"mcbigboy@gmail.com",
                        "firstName":"Michael",
                        "lastName":"Collier",
                        "password":"password",
                        "dateOfBirth":"02/04/1964",
                        "characterListId":null,
                        "isActive":true,
                        "currentCharacter":"0"}"

                String results = myHttpClient.makePUTRequest(URLString, requestBody);*/

                // Make new character
                // update curren charcter data

                gp.gameState = gp.PLAY_STATE;

            }

        }

        text = "Next Character";
        x = 400;
        y += 50;
        g2.setColor(Color.WHITE);
        g2.drawString(text, x, y);
        if (commandNum == 1) {
            g2.drawString(">", x - gp.tileSize, y);
            if(gp.keyH.enterPressed){
                makeRandomCharacter();
            }
        }


    }

    public void drawSubWindow(int x, int y, int width, int height) {

        Color c = new Color(0, 0, 0, 210);
        g2.setColor(c);
        g2.fillRoundRect(x, y, width, height, 35, 35);

        c = new Color(255, 255, 255);
        g2.setColor(c);
        g2.setStroke(new BasicStroke(5));
        g2.drawRoundRect(x + 5, y + 5, width - 10, height - 10, 25, 25);

    }

    public int getXforCenteredText(String text) {
        int length = (int) g2.getFontMetrics().getStringBounds(text, g2).getWidth();
        return gp.screenWidth / 2 - length / 2;
    }

    public int getXforAlignToRight(String text, int tailX) {
        int length = (int) g2.getFontMetrics().getStringBounds(text, g2).getWidth();
        return tailX - length;
    }

    public String getText(String text){

        if(gp.keyH.backSpace){

            if(text.length() != 0){
                text =  text.substring(0,text.length() - 1);
            }
            gp.keyH.backSpace = false;

        } else if(gp.getText) {

            text += gp.nextLetter;
            gp.getText = false;

        }

        return text;

    }

    public void makeRandomCharacter(){

        // random character
        gp.player.tempCharacterID = gp.ut.rnd.nextInt(30) + 1;
        gp.player.getCharacterImages(gp.player.tempCharacterID);

        // base life = 10 + random 0 - 5;
        gp.player.tempLife = 10 + gp.ut.rnd.nextInt(6);

        // base mana = 4;
        gp.player.tempMana = 4;

        // base max mana = 8
        gp.player.tempMaxMana = 8;

        // base strenght = 5 + random 0 - 10
        gp.player.tempStrength = 5 + gp.ut.rnd.nextInt(11);

        // base dexterity 5 + random 0 - 10;
        gp.player.tempDexterity = 5 + gp.ut.rnd.nextInt(11);

        // base level = 0;
        gp.player.tempLevel = 0;

        // base next level = 10 + random 0 - 5
        gp.player.tempNextLevelExp = 10 + gp.ut.rnd.nextInt(6);

        // base coin = 0
        gp.player.tempCoin = 0;

        // base name
        gp.player.tempName = gp.ut.getName();

        // base weapon sword
        Entity setMe = new Entity(gp);
        int ramdomNum = gp.ut.rnd.nextInt(100);

        if(ramdomNum < 75){
            setMe = new OBJ_Sword_Normal(gp);
        } else if (ramdomNum > 75 && ramdomNum < 95) {
            setMe = new OBJ_Axe(gp);
        } else {
            setMe = new OBJ_Pickaxe(gp);
        }

        gp.player.tempCurrentWeapon = setMe;

        // base shield wooden
        gp.player.tempCurrentShield = new OBJ_Shield_Wood(gp);
        ramdomNum = gp.ut.rnd.nextInt(100);

        if(ramdomNum < 85) {
            setMe = new OBJ_Fireball(gp);
        } else {
            setMe = new OBJ_Rock(gp);
        }
        // base Projectile fire
        gp.player.tempCurrentProjectile = setMe;
    }
}
