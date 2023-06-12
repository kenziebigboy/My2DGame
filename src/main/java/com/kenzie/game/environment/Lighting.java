package com.kenzie.game.environment;

import com.kenzie.game.GamePanel;

import java.awt.*;
import java.awt.geom.Area;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;

public class Lighting {

    GamePanel gp;
    BufferedImage darknessFilter;

    public Lighting(GamePanel gp, int circleSize){

        // Create a buffered image
        darknessFilter = new BufferedImage(gp.screenWidth, gp.screenHeight, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2 = (Graphics2D) darknessFilter.getGraphics();

        // Create a screen-sized rectangle area
        Area screenArea = new Area(new Rectangle2D.Double(0, 0, gp.screenWidth, gp.screenHeight));

        // Get the center x & y of the light circle
        int centerX = gp.player.screenX + gp.tileSize / 2;
        int centerY = gp.player.screenY + gp.tileSize / 2;

        // Get the top left x & y of the light circle
        double x = centerX - circleSize / 2;
        double y = centerY - circleSize / 2;

        // Create a light circle shape
        Shape circleShape = new Ellipse2D.Double(x, y, circleSize, circleSize);

        // Create a light circle area
        Area lightArea = new Area(circleShape);

        // Subtract the light circle from the screen rectangle
        screenArea.subtract(lightArea);

        // Create a gradation effect within the light circle
        Color[] cclor = new Color[12];
        float[] fraction = new float[12];



        cclor[0] = new Color(0, 0, 0, 0.1f);
        cclor[1] = new Color(0, 0, 0, 0.42f);
        cclor[2] = new Color(0, 0, 0, 0.52f);
        cclor[3] = new Color(0, 0, 0, 0.61f);
        cclor[4] = new Color(0, 0, 0, 0.69f);
        cclor[5] = new Color(0, 0, 0, 0.76f);
        cclor[6] = new Color(0, 0, 0, 0.82f);
        cclor[7] = new Color(0, 0, 0, 0.87f);
        cclor[8] = new Color(0, 0, 0, 0.91f);
        cclor[9] = new Color(0, 0, 0, 0.94f);
        cclor[10] = new Color(0, 0, 0, 0.96f);
        cclor[11] = new Color(0, 0, 0, 0.98f);


        fraction[0] = 0f;
        fraction[1] = 0.4f;
        fraction[2] = 0.5f;
        fraction[3] = 0.6f;
        fraction[4] = 0.65f;
        fraction[5] = 0.7f;
        fraction[6] = 0.75f;
        fraction[7] = 0.8f;
        fraction[8] = 0.85f;
        fraction[9] = 0.9f;
        fraction[10] = 0.95f;
        fraction[11] = 1f;


        // Create a grandation paint setting for the light circle
        RadialGradientPaint gPaint = new RadialGradientPaint(centerX, centerY, circleSize / 2, fraction, cclor);

        // Set the gradient data on the g2
        g2.setPaint(gPaint);

        // Draw the light circle
        g2.fill(lightArea);

//        // Set color (black) to draw the rectangle
//        g2.setColor(new Color(0, 0, 0, 0.95f));

        // Draw the screen rectangle without the light circle area
        g2.fill(screenArea);

        g2.dispose();
    }

    public void draw(Graphics2D g2){

        g2.drawImage(darknessFilter, 0, 0, null);
    }
}
