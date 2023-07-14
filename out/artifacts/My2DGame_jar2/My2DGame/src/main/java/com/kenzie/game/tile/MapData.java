package com.kenzie.game.tile;

import java.util.ArrayList;

public class MapData {

    public int backGroundTileSheetID;
    public int backGroundTileID;
    public int foreGroundTileSheetID;
    public int foreGroundTileID;
    public boolean collision;

    // Future Fields need to do Tile Animation

    public boolean animation;
    public int animationTileSheetID;
    public int animationCounter;
    public int animatinoReset;
    public ArrayList<Integer> animationTileID;

}
