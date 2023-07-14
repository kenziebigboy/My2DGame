package org.editor.tables;

import javax.swing.*;
import java.awt.image.BufferedImage;

public class TileImage {

    public boolean solid;
    public BufferedImage image;
    public ImageIcon icon;

    public TileImage(BufferedImage image) {

        this.image = image;
    }
}
