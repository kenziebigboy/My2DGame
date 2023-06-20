package com.kenzie.game;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.Random;

public class UtilityTool {

    public Random rnd = new Random();

    public BufferedImage scaleImage(BufferedImage original, int width, int height){

        BufferedImage scaledImage = new BufferedImage(width,height,original.getType());
        Graphics2D g2 = scaledImage.createGraphics();
        g2.drawImage(original,0,0,width,height,null);
        g2.dispose();

        return scaledImage;
    }

    public int getRandomBetween(int min, int max){

        int rndNum = (max - min) + 1;
        return rnd.nextInt(rndNum) + min;

    }
}
