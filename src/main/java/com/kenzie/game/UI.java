package com.kenzie.game;

import com.kenzie.game.entity.Entity;
import com.kenzie.game.object.OBJ_Heart;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

public class UI {

    GamePanel gp;
    Graphics2D g2;
    Font maruMonica, purisaB;
    BufferedImage heart_full, heart_half, heart_blank;

    public boolean messaageOn = false;
    // public String message = "";
    // int messageCounter = 0;

    ArrayList<String> message = new ArrayList<>();
    ArrayList<Integer> messageCounter = new ArrayList<>();

    public boolean gameFinished = false;
    public String currentDialouge = "";
    public int commandNum = 0;
    public int titleScreenState = 0;



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

    }

    public void addMessage(String text){


        message.add(text);
        messageCounter.add(0);
    }
    public void draw(Graphics2D g2){
        this.g2 = g2;

        g2.setFont(maruMonica);
        g2.setColor(Color.WHITE);

        // Title State
        if(gp.gameState == gp.titleState){
            drawTitleScreen();
        }


        // Play State
        if(gp.gameState == gp.playState){
            drawPlayerLife();
            drawMessage();
        }

        // Pause State
        if(gp.gameState == gp.pauseState){
            drawPlayerLife();
            drawPauseScreen();
        }

        // Dialogue State
        if(gp.gameState == gp.dialogueState){
            drawPlayerLife();
            drawDialogueScreen();
        }

        // Character State
        if(gp.gameState == gp.characterState){
            drawCharacterScreen();
        }
    }

    public void drawPlayerLife(){

        int x = gp.tileSize / 2;
        int y = gp.tileSize / 2;
        int i  = 0;

        // Draw Blank Heart
        while (i < gp.player.maxLife / 2){

            g2.drawImage(heart_blank,x,y,null);
            i++;
            x += gp.tileSize;
        }

        // Reset
        x = gp.tileSize / 2;
        //y = gp.tileSize / 2;
        i  = 0;

        // Draw Current Life
        while (i < gp.player.life){

            g2.drawImage(heart_half,x,y,null);
            i++;
            if(i < gp.player.life){
                g2.drawImage(heart_full,x,y,null);
            }
            i++;
            x += gp.tileSize;
        }

    }

    public void drawMessage(){

        int messageX = gp.tileSize;
        int messageY = gp.tileSize * 4;
        g2.setFont(g2.getFont().deriveFont(Font.BOLD, 32F));

        for(int i = 0; i < message.size(); i++){
            if(message.get(i) != null) {

                g2.setColor(Color.BLACK);
                g2.drawString(message.get(i), messageX + 2, messageY + 2);

                g2.setColor(Color.WHITE);
                g2.drawString(message.get(i), messageX, messageY);

                int counter = messageCounter.get(i) + 1;
                messageCounter.set(i, counter);
                messageY += 50;

                if(messageCounter.get(i) > 180){
                    message.remove(i);
                    messageCounter.remove(i);
                }
            }
        }
    }

    public void drawTitleScreen(){


        if(titleScreenState == 0) {
            g2.setFont(g2.getFont().deriveFont(Font.BOLD, 96F));
            String text = "Blue Boy Adventure";
            int x = getXforCenteredText(text);
            int y = gp.tileSize * 3;

            // Shadow
            g2.setColor(Color.GRAY);
            g2.drawString(text, x + 5, y + 5);

            // Main Color
            g2.setColor(Color.WHITE);
            g2.drawString(text, x, y);

            // Blue Boy Image
            x = gp.screenWidth / 2 - (gp.tileSize * 2) / 2;
            y += gp.tileSize * 2;
            g2.drawImage(gp.player.down1, x, y, gp.tileSize * 2, gp.tileSize * 2, null);

            // Menu
            g2.setFont(g2.getFont().deriveFont(Font.BOLD, 48F));

            text = "NEW GAME";
            x = getXforCenteredText(text);
            y += gp.tileSize * 3.5;
            g2.drawString(text, x, y);
            if (commandNum == 0) {
                g2.drawString(">", x - gp.tileSize, y);
            }

            text = "LOAD GAME";
            x = getXforCenteredText(text);
            y += gp.tileSize;
            g2.drawString(text, x, y);
            if (commandNum == 1) {
                g2.drawString(">", x - gp.tileSize, y);
            }

            text = "QUIT";
            x = getXforCenteredText(text);
            y += gp.tileSize;
            g2.drawString(text, x, y);
            if (commandNum == 2) {
                g2.drawString(">", x - gp.tileSize, y);
            }
        } else if (titleScreenState == 1) {

            // Class Selection Screen
            g2.setColor(Color.WHITE);
            g2.setFont(g2.getFont().deriveFont(42F));

            String text = "Select your class!";
            int x = getXforCenteredText(text);
            int y = gp.tileSize * 3;
            g2.drawString(text,x,y);

            text = "Fighter";
            x = getXforCenteredText(text);
            y += gp.tileSize * 3;
            g2.drawString(text,x,y);
            if (commandNum == 0) {
                g2.drawString(">", x - gp.tileSize, y);
            }

            text = "Thief";
            x = getXforCenteredText(text);
            y += gp.tileSize;
            g2.drawString(text,x,y);
            if (commandNum == 1) {
                g2.drawString(">", x - gp.tileSize, y);
            }

            text = "Sorcerer";
            x = getXforCenteredText(text);
            y += gp.tileSize;
            g2.drawString(text,x,y);
            if (commandNum == 2) {
                g2.drawString(">", x - gp.tileSize, y);
            }

            text = "Back";
            x = getXforCenteredText(text);
            y += gp.tileSize;
            g2.drawString(text,x,y);
            if (commandNum == 3) {
                g2.drawString(">", x - gp.tileSize, y);
            }
        }


    }


    public void drawPauseScreen(){

        String text = "PAUSED";
        int x = getXforCenteredText(text);
        int y = gp.screenHeight / 2;

        g2.drawString(text,x,y);
    }

    public void drawDialogueScreen(){

        // Window
        int x = gp.tileSize * 2;
        int y = gp.tileSize / 2;
        int width = gp.screenWidth - (gp.tileSize * 4);
        int height = gp.tileSize * 4;

        drawSubWindow(x,y,width,height);

        g2.setFont(g2.getFont().deriveFont(Font.PLAIN,32F));
        x += gp.tileSize;
        y += gp.tileSize;

        for(String line : currentDialouge.split("\n")){
            g2.drawString(line,x,y);
            y += 40;
        }


    }

    public void drawCharacterScreen(){

        // Create a frame
        final int frameX = gp.tileSize;
        final int frameY = gp.tileSize;
        final int frameWidth = gp.tileSize * 5;
        final int frameHeight = gp.tileSize * 10;
        drawSubWindow(frameX,frameY,frameWidth,frameHeight);

        // Text
        g2.setColor(Color.WHITE);
        g2.setFont(g2.getFont().deriveFont(32f));

        int textX = frameX + 20;
        int textY = frameY + gp.tileSize;
        final int lineHeight = 35;

        // Names
        g2.drawString("Level", textX,textY);
        textY += lineHeight;
        g2.drawString("Life", textX,textY);
        textY += lineHeight;
        g2.drawString("Strength", textX,textY);
        textY += lineHeight;
        g2.drawString("Dexterity", textX,textY);
        textY += lineHeight;
        g2.drawString("Attack", textX,textY);
        textY += lineHeight;
        g2.drawString("Defense", textX,textY);
        textY += lineHeight;
        g2.drawString("Exp", textX,textY);
        textY += lineHeight;
        g2.drawString("Next Level", textX,textY);
        textY += lineHeight;
        g2.drawString("Coin", textX,textY);
        textY += lineHeight + 20;
        g2.drawString("Weapon", textX,textY);
        textY += lineHeight + 15;
        g2.drawString("Shield", textX,textY);

        // Values
        int tailX = (frameX + frameWidth) - 30;
        // Reset textY
        textY = frameY + gp.tileSize;
        String value;

        value = String.valueOf(gp.player.level);
        textX = getXforAlignToRight(value,tailX);
        g2.drawString(value,textX,textY);

        textY += lineHeight;
        value = String.valueOf(gp.player.life + "/" + gp.player.maxLife);
        textX = getXforAlignToRight(value,tailX);
        g2.drawString(value,textX,textY);

        textY += lineHeight;
        value = String.valueOf(gp.player.strength);
        textX = getXforAlignToRight(value,tailX);
        g2.drawString(value,textX,textY);

        textY += lineHeight;
        value = String.valueOf(gp.player.dexterity);
        textX = getXforAlignToRight(value,tailX);
        g2.drawString(value,textX,textY);

        textY += lineHeight;
        value = String.valueOf(gp.player.attack);
        textX = getXforAlignToRight(value,tailX);
        g2.drawString(value,textX,textY);

        textY += lineHeight;
        value = String.valueOf(gp.player.defense);
        textX = getXforAlignToRight(value,tailX);
        g2.drawString(value,textX,textY);

        textY += lineHeight;
        value = String.valueOf(gp.player.exp);
        textX = getXforAlignToRight(value,tailX);
        g2.drawString(value,textX,textY);

        textY += lineHeight;
        value = String.valueOf(gp.player.nextLevelExp);
        textX = getXforAlignToRight(value,tailX);
        g2.drawString(value,textX,textY);

        textY += lineHeight;
        value = String.valueOf(gp.player.coin);
        textX = getXforAlignToRight(value,tailX);
        g2.drawString(value,textX,textY);

        textY += lineHeight;
        g2.drawImage(gp.player.currentWeapon.down1, tailX - gp.tileSize, textY - 14, null );
        textY += gp.tileSize;
        g2.drawImage(gp.player.currentShield.down1, tailX - gp.tileSize, textY - 14, null );


    }

    public  void drawSubWindow(int x, int y, int width, int height){

        Color c = new Color(0,0,0, 210);
        g2.setColor(c);
        g2.fillRoundRect(x,y,width,height,35,35);

        c = new Color(255,255,255);
        g2.setColor(c);
        g2.setStroke(new BasicStroke(5));
        g2.drawRoundRect(x + 5, y +5, width - 10, height - 10,25,25);

    }

    public int getXforCenteredText(String text){
        int length = (int) g2.getFontMetrics().getStringBounds(text,g2).getWidth();
        return gp.screenWidth / 2 - length / 2;
    }

    public int getXforAlignToRight(String text, int tailX){
        int length = (int) g2.getFontMetrics().getStringBounds(text,g2).getWidth();
        return tailX - length;
    }
}
