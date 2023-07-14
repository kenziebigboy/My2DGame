package org.editor;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.awt.image.ImageObserver;
import java.io.*;
import java.util.ArrayList;

public class Main {
    double version = 1.01D;
    GUI gui = new GUI(this);
    ArrayList<Tile> tiles = new ArrayList<>();

    int maxWorldCol;
    int maxWorldRow;
    int[][] mapTileNum;
    int selectedTileNum;
    boolean settingSolid = false;
    int selectedEditorPanel = 1;
    int selectedCol;
    int selectedRow;
    int topLeftCol = 0;
    int topLeftRow = 0;

    boolean gridOn = true;


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
        this.gui.setupGUI();
        setMapSize(50);
        this.gui.getClass();
        this.gui.scale = 1.0D;
        loadConfig();
        this.gui.setEditorSize(this.gui.scale);

    }

    public void saveConfig() {
        try {
            BufferedWriter bw = new BufferedWriter(new FileWriter("system.txt"));
            bw.write((int) this.gui.scale);

            bw.close();
        } catch (Exception e) {
            System.out.println("Save Config Exception!");
        }
    }

    public void loadConfig() {
        try {
            BufferedReader br = new BufferedReader(new FileReader("system.txt"));
            this.gui.scale = Double.parseDouble(br.readLine());

            br.close();
        } catch (Exception e) {
            System.out.println("Load Config Exception!");
        }
    }

    public void createNewProject() {
        UIManager.put("OptionPane.yesButtonText", "YES");
        UIManager.put("OptionPane.noButtonText", "Oops, no");

        int answer = JOptionPane.showConfirmDialog(
                null, "Start a new project? \nThe unsaved map will be lost.\n ", "New Project", 0);

        if (answer == 0) {

            resetMapTileNum();
            resetTileSelectionImages();
            resetWorldImage();
            this.mapFileDirectory = null;
            this.mapName = null;
        }
    }

    public void selectDirectory(String type) {
        if (type.equals("tile")) {
            FileDialog fd = new FileDialog(this.gui.window, "Select a tile", 0);
            fd.setVisible(true);

            if (fd.getFile() != null) {

                this.tileImageDirectory = fd.getDirectory();
                importTile();
            }
        }
        if (type.equals("sheet")) {
            FileDialog fd = new FileDialog(this.gui.window, "Select a tile sheet", 0);
            fd.setVisible(true);

            if (fd.getFile() != null) {
                this.tileSheetName = fd.getFile();
                this.tileSheetDirectory = fd.getDirectory();
                importTileSheet();
            }

        } else if (type.equals("savedata")) {
            FileDialog fd = new FileDialog(this.gui.window, "Save Tile Data", 1);
            fd.setVisible(true);

            if (fd.getFile() != null) {
                this.tileDataFileName = fd.getFile();
                this.tileDataFileDirectory = fd.getDirectory();
                saveTileData();
            }

        } else if (type.equals("loaddata")) {
            FileDialog fd = new FileDialog(this.gui.window, "Load Tile Data", 0);
            fd.setVisible(true);

            if (fd.getFile() != null) {
                this.tileDataFileName = fd.getFile();
                this.tileDataFileDirectory = fd.getDirectory();
                loadTileData();
            }
        }
    }

    private void importTile() {
        resetTileSelectionImages();

        File path = new File(this.tileImageDirectory);
        File[] files = path.listFiles();

        ArrayList<File> filess = new ArrayList<>();

        int i;
        for (i = 0; i < files.length; i++) {
            if (files[i].getName().contains("png")) {
                filess.add(files[i]);
            }
            if (files[i].getName().contains("jpg")) {
                filess.add(files[i]);
            }
        }

        this.tiles.clear();

        for (i = 0; i < filess.size(); i++) {


            try {
                this.tiles.add(new Tile());
                ((Tile) this.tiles.get(i)).name = ((File) filess.get(i)).getName();
                ((Tile) this.tiles.get(i)).image = ImageIO.read(filess.get(i));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        scaleImage();
        setIcons();
        refreshEditorImage();
        refreshWorldImage();
    }

    private void importTileSheet() {
        File file = new File(String.valueOf(this.tileSheetDirectory) + this.tileSheetName);

        try {
            BufferedImage tileSheet = ImageIO.read(file);
            int sheetCol = tileSheet.getWidth() / 16;
            int sheetRow = tileSheet.getHeight() / 16;

            int i = 0;
            int col = 0;
            int row = 0;

            while (col < sheetCol && row < sheetRow) {

                this.tiles.add(new Tile());
                ((Tile) this.tiles.get(i)).image = tileSheet.getSubimage(col * 16, row * 16, 16, 16);
                col++;
                i++;

                if (col == sheetCol) {
                    col = 0;
                    row++;
                }
            }
        } catch (IOException e) {
            System.out.println("tile sheet exception");
        }

        scaleImage();
        setIcons();
        refreshEditorImage();
        refreshWorldImage();
    }

    public void scaleImage() {
        for (int i = 0; i < this.tiles.size(); i++) {
            Image image48 = ((Tile) this.tiles.get(i)).image.getScaledInstance(
                    (int) (48.0D * this.gui.scale), (int) (48.0D * this.gui.scale), 1);
            if (i < this.tiles.size()) {
                ((Tile) this.tiles.get(i)).icon = new ImageIcon(image48);
            }
        }
    }

    public void setIcons() {
        for (int i = 0; i < this.tiles.size(); i++) {
            this.gui.tileButton[i].setIcon(((Tile) this.tiles.get(i)).icon);
        }
    }

    private void saveTileData() {
        try {
            BufferedWriter bw = new BufferedWriter(new FileWriter(String.valueOf(this.tileDataFileDirectory) + this.tileDataFileName));

            for (int i = 0; i < this.tiles.size(); i++) {
                bw.write(((Tile) this.tiles.get(i)).name);
                bw.newLine();
                bw.write(String.valueOf(((Tile) this.tiles.get(i)).collision));
                bw.newLine();
            }
            bw.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void loadTileData() {
        try {
            BufferedReader br = new BufferedReader(new FileReader(String.valueOf(this.tileDataFileDirectory) + this.tileDataFileName));


            for (int i = 0; i < this.tiles.size(); i++) {
                String line;
                if ((line = br.readLine()) != null) {

                    ((Tile) this.tiles.get(i)).name = line;

                    if (br.readLine().equals("true")) {
                        ((Tile) this.tiles.get(i)).collision = true;
                    } else {

                        ((Tile) this.tiles.get(i)).collision = false;
                    }
                }
            }
            br.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        setCollisionRedFrame();
    }

    private void setCollisionRedFrame() {
        for (int i = 0; i < this.tiles.size(); i++) {
            if (((Tile) this.tiles.get(i)).collision) {
                this.gui.tileButton[i].setBorder(BorderFactory.createLineBorder(Color.red, 1));
            }
        }
    }

    public void saveMap() {
        if (this.mapName == null) {
            saveMapAs();
        } else {

            savingMap();
        }
    }

    public void saveMapAs() {
        FileDialog fd = new FileDialog(this.gui.window, "Save", 1);
        fd.setVisible(true);

        if (fd.getFile() != null) {
            this.mapName = fd.getFile();
            this.mapFileDirectory = fd.getDirectory();
            setTitle(this.mapName);
            savingMap();
        }
    }

    private void savingMap() {
        try {
            BufferedWriter bw = new BufferedWriter(new FileWriter(String.valueOf(this.mapFileDirectory) + this.mapName));

            int col = 0;
            int row = 0;

            while (col < this.maxWorldCol && row < this.maxWorldRow) {

                bw.write(String.valueOf(this.mapTileNum[col][row]) + " ");
                col++;

                if (col == this.maxWorldCol) {
                    col = 0;
                    row++;
                    bw.newLine();
                }
            }
            bw.close();
        } catch (Exception e) {
            System.out.println("Exception!");
        }
    }

    public void loadMap() {
        FileDialog fd = new FileDialog(this.gui.window, "Open", 0);
        fd.setVisible(true);

        if (fd.getFile() != null) {
            this.mapName = fd.getFile();
            this.mapFileDirectory = fd.getDirectory();
        }


        try {
            BufferedReader br0 = new BufferedReader(new FileReader(String.valueOf(this.mapFileDirectory) + this.mapName));


            String line0 = br0.readLine();
            String[] splitLine0 = line0.split(" ");
            int mapSize = splitLine0.length;
            if (mapSize > this.maxWorldCol) {
                setMapSize(mapSize);
            }
            br0.close();


            BufferedReader br = new BufferedReader(new FileReader(String.valueOf(this.mapFileDirectory) + this.mapName));
            int col = 0;
            int row = 0;

            while (col < mapSize && row < mapSize) {

                String line = br.readLine();

                while (col < mapSize) {

                    String[] splitLine = line.split(" ");
                    int tileNum = Integer.parseInt(splitLine[col]);
                    this.mapTileNum[col][row] = tileNum;
                    col++;
                }

                if (col == mapSize) {
                    col = 0;
                    row++;
                }
            }
            br.close();
            setTitle(this.mapName);
        } catch (Exception e) {
            System.out.println("Load Map Exception!");
        }

        resetWorldImage();
    }

    public void setTitle(String s) {
        this.gui.window.setTitle("Simple 2D Tile Editor version  " + this.version + " - " + s);
    }

    public void showMapSizeNotification(int size) {
        UIManager.put("OptionPane.yesButtonText", "YES");
        UIManager.put("OptionPane.noButtonText", "Oops, no");

        int result = JOptionPane.showConfirmDialog(
                null, "Changing the map size will reset the current map. \nAre you sure to change it?", "Set Map Size\n ",
                0, 3);

        if (result == 0) {
            setMapSize(size);
        }
    }

    public void setMapSize(int size) {
        this.maxWorldCol = size;
        this.maxWorldRow = size;

        this.mapTileNum = new int[size][size];
        this.selectedEditorPanel = 1;
        showSelectEditorPanel();

        this.topLeftCol = 0;
        this.topLeftRow = 0;

        this.gui.getClass();
        ((JLabel) this.gui.label.get(4)).setText("Map Size: " + size + " x " + size);
        this.gui.getClass();
        ((JLabel) this.gui.label.get(3)).setText("Col:0  Row:0");

        resetWorldImage();
    }

    private void resetMapTileNum() {
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
    }

    private void resetTileSelectionImages() {
        for (int i = 0; i < this.tiles.size(); i++) {
            this.gui.tileButton[i].setIcon((Icon) null);
            this.gui.tileButton[i].setBorder(BorderFactory.createLineBorder(Color.gray, 1));
            ((Tile) this.tiles.get(i)).icon = null;
        }
        this.tiles.clear();

        this.gui.getClass();
        ((JLabel) this.gui.label.get(1)).setIcon((Icon) null);
        this.gui.getClass();
        ((JLabel) this.gui.label.get(2)).setText((String) null);
    }

    private void resetWorldImage() {
        this.worldImage = new BufferedImage(16 * this.maxWorldCol, 16 * this.maxWorldRow, 2);
        this.wg2 = this.worldImage.createGraphics();

        refreshEditorImage();
        refreshWorldImage();
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

        int blockSize = 0;

        switch (this.selectedEditorPanel) {
            case 1:
                blockSize = (int) (48.0D * this.gui.scale);
                break;
            case 2:
                blockSize = (int) (24.0D * this.gui.scale);
                break;
            case 3:
                blockSize = (int) (12.0D * this.gui.scale);
                break;
            case 4:
                blockSize = (int) (6.0D * this.gui.scale);
                break;
        }

        int x = 0;
        int y = 0;
        int colStart = this.topLeftCol;
        int rowStart = this.topLeftRow;
        int colEnd = colStart + this.gui.maxScreenCol;
        int rowEnd = rowStart + this.gui.maxScreenRow;
        int col = colStart;
        int row = rowStart;

        while (col < colEnd && row < rowEnd) {

            int tileNum = this.mapTileNum[col][row];


            if (this.tiles.size() != 0 && tileNum >= 0) {
                g2.drawImage(((Tile) this.tiles.get(tileNum)).image, x, y, blockSize, blockSize, null);
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

    public void drawGreenFrame() {
        double wmpTileSize = this.gui.worldMPsize / this.maxWorldCol;
        int x2 = (int) (this.topLeftCol * wmpTileSize);
        int y2 = (int) (this.topLeftRow * wmpTileSize);
        int width = (int) (this.gui.maxScreenCol * wmpTileSize);
        int height = (int) (this.gui.maxScreenRow * wmpTileSize);
        this.gui.greenFrame.setBounds(x2, y2, width, height);
    }

    public void refreshWorldImage() {
        int tileSize = 16;
        int x = 0;
        int y = 0;
        int col = 0;
        int row = 0;

        while (col < this.maxWorldCol && row < this.maxWorldRow) {

            int tileNum = this.mapTileNum[col][row];

            if (this.tiles.size() != 0 && tileNum >= 0) {
                this.wg2.drawImage(((Tile) this.tiles.get(tileNum)).image, x, y, (ImageObserver) null);
            }

            col++;
            x += tileSize;
            if (col == this.maxWorldCol) {
                col = 0;
                x = 0;
                row++;
                y += tileSize;
            }
        }

        this.gui.worldMapPanel.drawGra(this.worldImage, 0, 0, this.gui.worldMPsize, this.gui.worldMPsize);
        this.gui.window.requestFocus();
    }

    public void drawOnEditor() {
        int worldCol = this.topLeftCol + this.selectedCol;
        int worldRow = this.topLeftRow + this.selectedRow;

        if (this.selectedTileNum <= this.tiles.size()) {
            this.mapTileNum[worldCol][worldRow] = this.selectedTileNum;
        }
        refreshEditorImage();
        drawOnWorldMap(worldCol, worldRow);
    }

    public void showSelectEditorPanel() {
        for (int i = 1; i < this.gui.editorPanel.length; i++) {
            this.gui.editorPanel[i].setVisible(false);
        }

        this.gui.editorPanel[this.selectedEditorPanel].setVisible(true);

        this.gui.maxScreenCol = getMaxScreenCol();
        this.gui.maxScreenRow = getMaxScreenRow();

        refreshEditorImage();
    }

    public int getMaxScreenCol() {
        switch (this.selectedEditorPanel) {
            case 1:
                this.gui.maxScreenCol = this.gui.defaultScreenCol;
                break;
            case 2:
                this.gui.maxScreenCol = this.gui.defaultScreenCol * 2;
                break;
            case 3:
                this.gui.maxScreenCol = this.gui.defaultScreenCol * 4;
                break;
            case 4:
                this.gui.maxScreenCol = this.gui.defaultScreenCol * 8;
                break;
        }

        return this.gui.maxScreenCol;
    }

    public int getMaxScreenRow() {
        switch (this.selectedEditorPanel) {
            case 1:
                this.gui.maxScreenRow = this.gui.defaultScreenRow;
                break;
            case 2:
                this.gui.maxScreenRow = this.gui.defaultScreenRow * 2;
                break;
            case 3:
                this.gui.maxScreenRow = this.gui.defaultScreenRow * 4;
                break;
            case 4:
                this.gui.maxScreenRow = this.gui.defaultScreenRow * 8;
                break;
        }

        return this.gui.maxScreenRow;
    }

    public void drawOnWorldMap(int worldCol, int worldRow) {
        int tileNum = this.mapTileNum[worldCol][worldRow];

        if (this.tiles.size() > 0) {
            this.wg2.drawImage(((Tile) this.tiles.get(tileNum)).image, worldCol * 16, worldRow * 16, (ImageObserver) null);
            this.gui.worldMapPanel.drawGra(this.worldImage, 0, 0, this.gui.worldMPsize, this.gui.worldMPsize);
            this.gui.window.requestFocus();
        }
    }

    public void zoomIn() {
        if (this.selectedEditorPanel > 1) {
            this.selectedEditorPanel--;
            showSelectEditorPanel();
        }
    }

    public void zoomOut() {
        if (this.selectedEditorPanel < this.gui.editorPanel.length - 1) {
            this.selectedEditorPanel++;
            if (getMaxScreenCol() > this.maxWorldCol) {
                this.selectedEditorPanel--;
            }
        }
        showSelectEditorPanel();
    }

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
        refreshWorldImage();
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
        refreshWorldImage();
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
        refreshWorldImage();
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
        refreshWorldImage();
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
        refreshWorldImage();
    }

    public void gridOnOff() {
        int col = 0;
        int row = 0;
        while (col < 16 && row < 9) {

            if (this.gridOn) {
                this.gui.editBlocks16[col][row].setBorderPainted(false);
            }
            if (!this.gridOn) {
                this.gui.editBlocks16[col][row].setBorderPainted(true);
            }
            col++;
            if (col == 16) {
                col = 0;
                row++;
            }
        }

        col = 0;
        row = 0;
        while (col < 32 && row < 18) {

            if (this.gridOn) {
                this.gui.editBlocks32[col][row].setBorderPainted(false);
            }
            if (!this.gridOn) {
                this.gui.editBlocks32[col][row].setBorderPainted(true);
            }
            col++;
            if (col == 32) {
                col = 0;
                row++;
            }
        }

        col = 0;
        row = 0;
        while (col < 64 && row < 36) {

            if (this.gridOn) {
                this.gui.editBlocks64[col][row].setBorderPainted(false);
            }
            if (!this.gridOn) {
                this.gui.editBlocks64[col][row].setBorderPainted(true);
            }
            col++;
            if (col == 64) {
                col = 0;
                row++;
            }
        }

        if (this.gridOn) {
            this.gridOn = false;
        } else {
            this.gridOn = true;
        }

    }

}


/* Location:              C:\Users\mcbig\Downloads\Simple2DTileEditor1.01(1)\!\com\simpleeditor\main\Main.class
 * Java compiler version: 12 (56.0)
 * JD-Core Version:       1.1.3
 */