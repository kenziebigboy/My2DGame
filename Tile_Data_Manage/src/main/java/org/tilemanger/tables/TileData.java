package org.tilemanger.tables;

import org.tilemanger.Reference;

import java.io.*;
import java.util.ArrayList;

public class TileData implements Serializable {


    public int tile_ID;
    public int cellX;
    public int cellY;
    public boolean solid;

    public TileData(int tile_ID, int cellX, int cellY, boolean solid) {

        this.tile_ID = tile_ID;
        this.cellX = cellX;
        this.cellY = cellY;
        this.solid = solid;
    }

    @Override
    public String toString() {
        return "TileData{" +
                "tile_ID: " + tile_ID +
                ", cellX: " + cellX +
                ", cellY: " + cellY +
                ", solid: " + solid +
                '}';
    }
}
