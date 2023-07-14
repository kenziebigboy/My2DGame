package org.editor;

import org.editor.tables.GraphicsPackages;
import org.editor.tables.MapData;
import org.editor.tables.TileImage;
import org.editor.tables.WorldMap;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.awt.image.ImageObserver;
import java.util.HashMap;
import java.util.Map;

public class Main {

    double version = 1.01D;
    GUI gui = new GUI(this);
    public Map<Integer, TileImage> currentTileSet = new HashMap<>();

    int maxWorldCol;
    int maxWorldRow;
    int[][] mapTileNum;
    MapData[][] mapImageData;
    WorldMap worldMap;
    int selectedTileNum;
    int selectedForeGroundTileNum;
    int selectedBackGroundTileNum;
    boolean settingSolid = false;
    int selectedEditorPanel = 1;
    int selectedCol;
    int selectedRow;
    int topLeftCol = 0;
    int topLeftRow = 0;



    String tileImageDirectory;
    String tileSheetDirectory;
    String tileSheetName;
    String mapFileDirectory;
    String mapName;
    String tileDataFileDirectory;
    String tileDataFileName;
    BufferedImage worldImage;
    Graphics2D wg2;

    public static void main(String[] args) {
        Main main = new Main();
    }

    public Main() {

        GraphicsPackages.readDataFromDisk();


        this.gui.setupGUI();
        setMapSize(50);
        this.gui.getClass();
        this.gui.scale = 1.0D;
        loadConfig();
        this.gui.setEditorSize();

    }

    public void createNewProject() {

    }

    public BufferedImage getTileSheetCellImage(BufferedImage bufferedImage, int cellX, int cellY){

        return bufferedImage.getSubimage(cellX * 16, cellY * 16, 16, 16);

    }

    public ImageIcon makeImageIcon(BufferedImage bufferedImage){

        Image image = (bufferedImage.getScaledInstance(
                32, 32, 1));

        return new ImageIcon(image);
    }

    public void saveMap() {

    }

    public void saveMapAs() {

    }

    public void loadMap() {

    }

    public void showMapSizeNotification(int size) {

    }

    public void setMapSize(int size) {

        this.maxWorldCol = size;
        this.maxWorldRow = size;

        this.mapImageData = new MapData[size][size];

        int col = 0;
        int row = 0;
        while (col < size && row < size){
            this.mapImageData[col][row] = new MapData();

            col++;
            if(col == size){
                col = 0;
                row++;
            }
        }


        showSelectEditorPanel();

        this.topLeftCol = 0;
        this.topLeftRow = 0;

        this.gui.getClass();
        ((JLabel) this.gui.label.get(4)).setText("Map Size: " + size + " x " + size);
        this.gui.getClass();
        ((JLabel) this.gui.label.get(3)).setText("Col:0  Row:0");

        resetWorldImage();
    }

    private void resetWorldImage() {
        this.worldImage = new BufferedImage(16 * this.maxWorldCol, 16 * this.maxWorldRow, 2);
        this.wg2 = this.worldImage.createGraphics();

        refreshEditorImage();
        //refreshWorldImage();
    }

    public void showSelectEditorPanel() {

        this.gui.editorPanel.setVisible(true);

        this.gui.maxScreenCol = this.gui.defaultScreenCol;
        this.gui.maxScreenRow = this.gui.defaultScreenRow;

        refreshEditorImage();
    }

    public void refreshEditorImage() {
        if (this.topLeftCol < 0) {
            this.topLeftCol = 0;
        }
        if (this.topLeftCol > this.maxWorldCol - this.gui.maxScreenCol) {
            this.topLeftCol = this.maxWorldCol - this.gui.maxScreenCol;
        }
        if (this.topLeftRow < 0) {
            this.topLeftRow = 0;
        }
        if (this.topLeftRow > this.maxWorldRow - this.gui.maxScreenRow) {
            this.topLeftRow = this.maxWorldRow - this.gui.maxScreenRow;
        }

        BufferedImage editorImage = new BufferedImage(
                this.gui.defaultScreenCol * this.gui.tileSize, this.gui.defaultScreenRow * this.gui.tileSize, 2);
        Graphics2D g2 = editorImage.createGraphics();

        int blockSize = 48;

        int x = 0;
        int y = 0;
        int colStart = this.topLeftCol;
        int rowStart = this.topLeftRow;
        int colEnd = colStart + this.gui.maxScreenCol;
        int rowEnd = rowStart + this.gui.maxScreenRow;
        int col = colStart;
        int row = rowStart;

        while (col < colEnd && row < rowEnd) {

            // Background image
            int tileBackNum = this.mapImageData[col][row].backGroundTileID;
            if(tileBackNum != -1 && currentTileSet.containsKey(tileBackNum)) {
                g2.drawImage(currentTileSet.get(tileBackNum).image   , x, y, blockSize, blockSize, null);
            }

            // Foreground image
            int tileForeNum = this.mapImageData[col][row].foreGroundTileID;
            if(tileForeNum != -1 && currentTileSet.containsKey(tileForeNum)) {
                System.out.println(tileForeNum);
                g2.drawImage(currentTileSet.get(tileForeNum).image   , x, y, blockSize, blockSize, null);
            }

            col++;
            x += blockSize;

            if (col == colEnd) {

                col = colStart;
                x = 0;

                row++;
                y += blockSize;
            }
        }

        this.gui.editorImagePanel.drawGra(editorImage, 0, 0, editorImage.getWidth(), editorImage.getHeight());


        drawGreenFrame();

        this.gui.window.requestFocus();
    }

    public void drawOnEditor() {
        int worldCol = this.topLeftCol + this.selectedCol;
        int worldRow = this.topLeftRow + this.selectedRow;

//        if (this.selectedTileNum <= this.tiles.size()) {
//            this.mapTileNum[worldCol][worldRow] = this.selectedTileNum;
//        }

        if(currentTileSet.containsKey(selectedBackGroundTileNum)){
            this.mapImageData[worldCol][worldRow].backGroundTileID = selectedBackGroundTileNum;
        }

        if(currentTileSet.containsKey(selectedForeGroundTileNum)){
            this.mapImageData[worldCol][worldRow].foreGroundTileID = selectedForeGroundTileNum;
        }

        refreshEditorImage();
        drawOnWorldMap(worldCol, worldRow);
    }

    public void drawOnWorldMap(int worldCol, int worldRow) {
        int tileForeNum = this.mapImageData[worldCol][worldRow].foreGroundTileID;
        int tileBackNum = this.mapImageData[worldCol][worldRow].backGroundTileID;

        System.out.println("TileForeNum: " + tileForeNum + " TileBackNum: " + tileBackNum);
        if (this.currentTileSet.size() > 0) {

            if(tileBackNum != -1) {
                this.wg2.drawImage(currentTileSet.get(tileBackNum).image, worldCol * 16, worldRow * 16, (ImageObserver) null);
            }

            if(tileForeNum != -1) {
                this.wg2.drawImage(currentTileSet.get(tileForeNum).image, worldCol * 16, worldRow * 16, (ImageObserver) null);
            }

            this.gui.worldMapPanel.drawGra(this.worldImage, 0, 0, this.gui.worldMPsize, this.gui.worldMPsize);
            this.gui.window.requestFocus();
        }
    }

    public void loadConfig() {

    }

    public void drawGreenFrame() {
        double wmpTileSize = this.gui.worldMPsize / this.maxWorldCol;
        int x2 = (int) (this.topLeftCol * wmpTileSize);
        int y2 = (int) (this.topLeftRow * wmpTileSize);
        int width = (int) (this.gui.maxScreenCol * wmpTileSize);
        int height = (int) (this.gui.maxScreenRow * wmpTileSize);
        this.gui.greenFrame.setBounds(x2, y2, width, height);
    }

//    public void zoomIn() {
//        if (this.selectedEditorPanel > 1) {
//            this.selectedEditorPanel--;
//            showSelectEditorPanel();
//        }
//    }
//
//    public void zoomOut() {
//        if (this.selectedEditorPanel < this.gui.editorPanel.length - 1) {
//            this.selectedEditorPanel++;
//            if (getMaxScreenCol() > this.maxWorldCol) {
//                this.selectedEditorPanel--;
//            }
//        }
//        showSelectEditorPanel();
//    }

    public void moveUp() {
        this.topLeftRow -= this.gui.maxScreenRow / 8;

        if (this.topLeftRow < 0) {
            this.topLeftRow = 0;
        }
        refreshEditorImage();
    }

    public void moveDown() {
        this.topLeftRow += this.gui.maxScreenRow / 8;

        if (this.topLeftRow > this.maxWorldRow - this.gui.maxScreenRow) {
            this.topLeftRow = this.maxWorldRow - this.gui.maxScreenRow;
        }
        refreshEditorImage();
    }

    public void moveLeft() {
        this.topLeftCol -= this.gui.maxScreenCol / 8;

        if (this.topLeftCol < 0) {
            this.topLeftCol = 0;
        }
        refreshEditorImage();
    }

    public void moveRight() {
        this.topLeftCol += this.gui.maxScreenCol / 8;

        if (this.topLeftCol > this.maxWorldCol - this.gui.maxScreenCol) {
            this.topLeftCol = this.maxWorldCol - this.gui.maxScreenCol;
        }
        refreshEditorImage();
    }

    public void moveAll(String direction) {
        int[][] newMapTileNum = new int[this.maxWorldCol][this.maxWorldRow];

        int col = 0;
        int row = 0;

        while (col < this.maxWorldCol && row < this.maxWorldRow) {
            String str;
            switch ((str = direction).hashCode()) {
                case 3739:
                    if (!str.equals("up"))
                        break;
                    if (row == this.maxWorldRow - 1) {
                        newMapTileNum[col][row] = 0;
                        break;
                    }
                    newMapTileNum[col][row] = this.mapTileNum[col][row + 1];
                    break;
                case 3089570:
                    if (!str.equals("down"))
                        break;
                    if (row == 0) {
                        newMapTileNum[col][row] = 0;
                        break;
                    }
                    newMapTileNum[col][row] = this.mapTileNum[col][row - 1];
                    break;
                case 3317767:
                    if (!str.equals("left"))
                        break;
                    if (col == this.maxWorldCol - 1) {
                        newMapTileNum[col][row] = 0;
                        break;
                    }
                    newMapTileNum[col][row] = this.mapTileNum[col + 1][row];
                    break;
                case 108511772:
                    if (!str.equals("right"))
                        break;
                    if (col == 0) {
                        newMapTileNum[col][row] = 0;
                        break;
                    }
                    newMapTileNum[col][row] = this.mapTileNum[col - 1][row];
                    break;
            }


            col++;
            if (col == this.maxWorldCol) {
                col = 0;
                row++;
            }
        }


        col = 0;
        row = 0;
        while (col < this.maxWorldCol && row < this.maxWorldRow) {
            this.mapTileNum[col][row] = newMapTileNum[col][row];
            col++;
            if (col == this.maxWorldCol) {
                col = 0;
                row++;
            }
        }

        refreshEditorImage();
        //refreshWorldImage();
    }

    public void splash() {
        int col = this.topLeftCol;
        int row = this.topLeftRow;
        int endCol = col + this.gui.maxScreenCol;
        int endRow = row + this.gui.maxScreenRow;

        while (col < endCol && row < endRow) {

            this.mapTileNum[col][row] = this.selectedTileNum;
            col++;
            if (col == endCol) {
                col = this.topLeftCol;
                row++;
            }
        }
        refreshEditorImage();
        //refreshWorldImage();
    }

    public void splashAll() {
        int col = 0;
        int row = 0;
        while (col < this.maxWorldCol && row < this.maxWorldRow) {

            this.mapTileNum[col][row] = this.selectedTileNum;
            col++;
            if (col == this.maxWorldCol) {
                col = 0;
                row++;
            }
        }
        refreshEditorImage();
        //refreshWorldImage();
    }

    public void erase() {
        int col = this.topLeftCol;
        int row = this.topLeftRow;
        int endCol = col + this.gui.maxScreenCol;
        int endRow = row + this.gui.maxScreenRow;

        while (col < endCol && row < endRow) {

            this.mapTileNum[col][row] = 0;
            col++;
            if (col == endCol) {
                col = this.topLeftCol;
                row++;
            }
        }
        refreshEditorImage();
        //refreshWorldImage();
    }

    public void eraseAll() {
        int col = 0;
        int row = 0;
        while (col < this.maxWorldCol && row < this.maxWorldRow) {

            this.mapTileNum[col][row] = 0;
            col++;
            if (col == this.maxWorldCol) {
                col = 0;
                row++;
            }
        }
        refreshEditorImage();
        //refreshWorldImage();
    }

}