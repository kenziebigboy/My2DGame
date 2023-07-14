package com.kenzie.game.tile;

import com.kenzie.game.GamePanel;
import com.kenzie.game.UtilityTool;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class TileManger {

    GamePanel gp;
    public Tile[] tile;
    public int mapTileNum[][][];
    boolean drawPath = false;
    ArrayList<String> fileNames = new ArrayList<>();
    ArrayList<String> collisionStatus = new ArrayList<>();
    Map<Integer, TileImage> currentTileSet = new HashMap<>();
    ArrayList<BufferedImage> usedTileSheet;

    public TileManger(GamePanel gp) {
        this.gp = gp;

        // Read Tile Data File
        InputStream is = getClass().getResourceAsStream("/maps/tiledata.txt");
        BufferedReader br = new BufferedReader(new InputStreamReader(is));

        // Getting tile names & collision info from the file
        String line;


        try {

            while (((line = br.readLine()) != null)) {

                fileNames.add(line);
                collisionStatus.add(br.readLine());
            }

            br.close();

        } catch (IOException e) {
            throw new RuntimeException(e);
        }


        tile = new Tile[fileNames.size()];
        getTileImage();

        // Get the maxWorld Col & Row
        is = getClass().getResourceAsStream(("/maps/worldmap.txt"));
        br = new BufferedReader(new InputStreamReader(is));

        try {
            String line2 = br.readLine();
            String maxTile[] = line2.split(" ");

            gp.maxWorldCol = maxTile.length;
            gp.maxWorldRow = maxTile.length;
            mapTileNum = new int[gp.maxMap][gp.maxWorldCol][gp.maxWorldRow];

            br.close();

        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        loadMap("/maps/worldmap.txt", 0);
        loadMap("/maps/indoor01.txt", 1);
        loadMap("/maps/dungeon01.txt", 2);
        loadMap("/maps/dungeon02.txt", 3);


//        loadMap("/maps/worldV3.txt", 0);
//        loadMap("/maps/interior01.txt", 1);
    }

    public void getTileImage() {

        for(int i = 0; i < fileNames.size(); i++){

            String fileName;
            boolean collision;

            // Get file name
            fileName = fileNames.get(i);

            // Get a collision status
            collision = collisionStatus.get(i).equals("true");

            setup(i, fileName, collision);
        }
    }

    public void createCurrentTileSet(int mapId){

        // Current WorldMap
        WorldMap wm = WorldMap.getWorldMapById(mapId);

        // set Map Size
        gp.maxWorldCol = wm.maxCol;
        gp.maxWorldRow = wm.maxRow;

        usedTileSheet = new ArrayList<>();
        // Get all the Tile Sheet used to make up this CurrentTileSet
        for(int i = 0; i < wm.tileDataSheetsID.size(); i++) {
            // Get TileSheetData
            TileSheetData tsd = TileSheetData.getById(wm.tileDataSheetsID.get(i));

            usedTileSheet.add(loadTileSheet(i, new File (tsd.path + tsd.tileSheetName) ));
        }

        // Loop over all the Columns and Rows from WorldMap
        int col = 0;
        int row = 0;

        while (col < gp.maxWorldCol && row < gp.maxWorldRow) {

            // Check to see if the ground tile is in the Map
            if(!currentTileSet.containsKey(wm.mapData[col][row].backGroundTileID)){
                TileImage ti = new TileImage(wm.mapData[col][row].collision,cutTileFromSheet(wm.mapData[col][row].backGroundTileSheetID, col, row));
                currentTileSet.put(wm.mapData[col][row].backGroundTileID, ti);

            }

            if(!currentTileSet.containsKey(wm.mapData[col][row].foreGroundTileID)){
                TileImage ti = new TileImage(wm.mapData[col][row].collision,cutTileFromSheet(wm.mapData[col][row].foreGroundTileSheetID, col, row));
                currentTileSet.put(wm.mapData[col][row].foreGroundTileID, ti);

            }

            if (col == gp.maxWorldCol) {
                col = 0;
                row++;
            }

        }

    }

    public BufferedImage cutTileFromSheet(int tileSheetID, int col, int row){

        return usedTileSheet.get(tileSheetID).getSubimage(col * 16, row * 16, 16, 16);
    }

    public BufferedImage loadTileSheet(int tileSheetId, File filePath){

        BufferedImage bufferedImage = null;

        try {

            bufferedImage = ImageIO.read(filePath.getAbsoluteFile());

        } catch (IOException ioException) {
            ioException.printStackTrace();
        }

        return bufferedImage;
    }

    public void setup(int index, String imageName, boolean collision) {

        UtilityTool uTool = new UtilityTool();

        try {

            tile[index] = new Tile();
            tile[index].image = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/tiles/" + imageName)));
            tile[index].image = uTool.scaleImage(tile[index].image, gp.tileSize, gp.tileSize);
            tile[index].collision = collision;

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void loadMap(String filePath, int map) {

        try {
            InputStream is = getClass().getResourceAsStream(filePath);
            BufferedReader br = new BufferedReader(new InputStreamReader(is));

            int col = 0;
            int row = 0;

            while (col < gp.maxWorldCol && row < gp.maxWorldRow) {
                String line = br.readLine();

                while (col < gp.maxWorldCol) {
                    String numbers[] = line.split(" ");
                    int num = Integer.parseInt(numbers[col]);
                    mapTileNum[map][col][row] = num;
                    col++;
                }

                if (col == gp.maxWorldCol) {
                    col = 0;
                    row++;
                }
            }

            br.close();

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public void draw(Graphics2D g2) {

        int worldCol = 0;
        int worldRow = 0;

        while (worldCol < gp.maxWorldCol && worldRow < gp.maxWorldRow) {

            int tileNum = mapTileNum[gp.currentMap][worldCol][worldRow];

            int worldX = worldCol * gp.tileSize;
            int worldY = worldRow * gp.tileSize;
            int screenX = worldX - gp.player.worldX + gp.player.screenX;
            int screenY = worldY - gp.player.worldY + gp.player.screenY;

            if (worldX + gp.tileSize > gp.player.worldX - gp.player.screenX &&
                    worldX - gp.tileSize < gp.player.worldX + gp.player.screenX &&
                    worldY + gp.tileSize > gp.player.worldY - gp.player.screenY &&
                    worldY - gp.tileSize < gp.player.worldY + gp.player.screenY) {

                g2.drawImage(tile[tileNum].image, screenX, screenY, null);
            }

            worldCol++;

            if (worldCol == gp.maxWorldCol) {
                worldCol = 0;

                worldRow++;

            }
        }

        if (drawPath) {

            g2.setColor(new Color(255, 0, 0, 70));

            for (int i = 0; i < gp.pFinder.pathList.size(); i++) {

                int worldX = gp.pFinder.pathList.get(i).col * gp.tileSize;
                int worldY = gp.pFinder.pathList.get(i).row * gp.tileSize;
                int screenX = worldX - gp.player.worldX + gp.player.screenX;
                int screenY = worldY - gp.player.worldY + gp.player.screenY;

                g2.fillRect(screenX, screenY, gp.tileSize, gp.tileSize);
            }
        }
    }
}