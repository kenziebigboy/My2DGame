package com.kenzie.game.tile;

import java.awt.image.BufferedImage;

public class TileImage {

    public boolean solid;
    public BufferedImage image;

    public TileImage(boolean solid, BufferedImage image) {
        this.solid = solid;
        this.image = image;
    }
}
