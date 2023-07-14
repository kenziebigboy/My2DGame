package com.kenzie.game.tile;

import javax.swing.*;
import java.awt.image.BufferedImage;

public class TileImage {

    public boolean solid;
    public BufferedImage image;
    public ImageIcon icon;

    public TileImage(boolean solid, BufferedImage image) {
        this.solid = solid;
        this.image = image;
    }
}
