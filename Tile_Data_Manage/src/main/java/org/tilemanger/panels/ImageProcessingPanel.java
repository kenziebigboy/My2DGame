package org.tilemanger.panels;

import org.tilemanger.Main;
import org.tilemanger.Reference;
import org.tilemanger.tables.GraphicsPackages;
import org.tilemanger.tables.TileData;
import org.tilemanger.tables.TileSheetData;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.TitledBorder;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.regex.Pattern;

public class ImageProcessingPanel extends JPanel {

    public Main main;
    public Font borderFont = Reference.borderFont;
    public Color borderColor = Reference.borderColor;
    public Font panelFont = Reference.panelFont;
    public Font smallFont = Reference.smallFont;
    public Font superSmallFont = Reference.supperSmallFont;
    public String statusText = "";
    public Image image;
    public Graphics2D g2;
    public BufferedImage bufferedImage;
    public int scale = 2;
    int screenX;
    int screenY;
    public int maxCol = 16;
    public int maxRow = 16;
    public int imageWidth;
    public int imageHeight;
    public JPanel buttonPanel;
    public JButton[][] tileButtons = new JButton[16][16];
    public boolean[][] addTile = new boolean[16][16];

    public JLabel colAndRow_LBL;
    public JTextArea processText_TXA;

    public int graphicsPackageID;
    public File filePath;

    static boolean packageEdit = false;
    static int packageEdit_ID = -1;

    public ImageProcessingPanel(Main main){

        this.main = main;
        bufferedImage = new BufferedImage(512, 512, BufferedImage.TYPE_INT_ARGB);
        g2 = (Graphics2D) bufferedImage.getGraphics();



    }

    public void displayImageProcessingPanel(int graphicsPackageID, File filePath){

        this.graphicsPackageID = graphicsPackageID;
        this.filePath = filePath;

        int panelWidth = 800;
        int panelHeight = 650;
        int panelX = (getParent().getWidth() - panelWidth) / 2 ;
        int panelY = (getParent().getHeight() - panelHeight) / 2;

        setBounds(panelX, panelY, panelWidth, panelHeight);
        setLayout(null);
        setBackground(Color.LIGHT_GRAY);
        setVisible(true);

        TitledBorder titledBorder = BorderFactory.createTitledBorder
                (BorderFactory.createLineBorder(Color.gray, 1),
                        " Tile Sheet Image Processing", TitledBorder.CENTER,
                        TitledBorder.DEFAULT_POSITION,
                        borderFont, borderColor);
        setBorder(titledBorder);

        int x = 25;  int heightSpace   = 5;
        int y = 50;  int widthSpace    = 5;

        int col_1_componentsWidth = 512;      // how width the column one will be
        int col_2_componentsWidth = 210;      // how width the column two will be

        int col_1_x_start = x;                                                   // column 1 starting point
        int col_2_x_start = x + col_1_componentsWidth + widthSpace * 3;               // column 2 starting point
        int col_1_y_start = y;
        int col_2_y_start = y;

        int componentHeight = 20;
        int displayImageSize = 512;

        // Setup x & y for column 1
        x = col_1_x_start; y = col_1_y_start;

        int buttonWidth         = 100;
        int buttonHeight        = 30;
        int buttonSpace         = 10;

        int x_buttonStart       = col_2_x_start + widthSpace +
                (col_2_componentsWidth - Reference.totalButtonWidth(2,buttonWidth,buttonSpace)) / 2;

        // ***************************************************************************************
        // Create components column 1
        // ***************************************************************************************

        JLabel iconFileName_LBL = new JLabel("");
        JLabel displayIcon_LBL = new JLabel("");
        colAndRow_LBL = new JLabel("Col: " + main.selectedCol + " Row: " + main.selectedRow);

        Border imageBorder = BorderFactory.createLineBorder(Color.gray,1);
        displayIcon_LBL.setBorder(imageBorder);

        // ***************************************************************************************
        // Setup components column 1
        // ***************************************************************************************

        iconFileName_LBL.setBounds(x,y,col_1_componentsWidth,componentHeight);
        iconFileName_LBL.setFont(borderFont);
        iconFileName_LBL.setHorizontalAlignment(JLabel.LEFT);

        y += componentHeight + heightSpace * 2;

        screenX = x;
        screenY = y;

        displayIcon_LBL.setBounds(x,y,displayImageSize, displayImageSize);
        displayIcon_LBL.setFont(panelFont);
        displayIcon_LBL.setHorizontalAlignment(JLabel.CENTER);

        y += displayImageSize + heightSpace * 2;

        colAndRow_LBL.setBounds(x, y, col_1_componentsWidth, componentHeight);
        colAndRow_LBL.setFont(borderFont);
        colAndRow_LBL.setForeground(Color.WHITE);
        colAndRow_LBL.setHorizontalAlignment(JLabel.LEFT);

        File openedFile = filePath;
        iconFileName_LBL.setText("File Name: " + openedFile.getName());
        try {

            bufferedImage = ImageIO.read(openedFile.getAbsoluteFile());

            repaint();

        } catch (IOException ioException) {
            ioException.printStackTrace();
        }

        // ***************************************************************************************
        // Create components column 2
        // ***************************************************************************************

        JLabel processStatus_LBL = new JLabel("Status");
        processText_TXA = new JTextArea("Is this the right file\nYes / No");

        x = col_2_x_start; y = col_2_y_start;

        processStatus_LBL.setBounds(x + 5, y + 5, col_2_componentsWidth, heightSpace + 10);
        processStatus_LBL.setFont(borderFont);
        processStatus_LBL.setHorizontalAlignment(JLabel.CENTER);

        y += heightSpace * 6;

        processText_TXA.setBounds(x, y, col_2_componentsWidth + 10, 475);
        processText_TXA.setFont(panelFont);
        processText_TXA.setBackground(Color.BLACK);
        processText_TXA.setForeground(Color.WHITE);
        processText_TXA.setMargin(new Insets(5,5,5,5));

        // ***************************************************************************************
        // Create buttons
        // ***************************************************************************************

        JButton yesGoodTileSheet_BTN = new JButton("Yes");
        JButton noGoodTileSheet_BTN = new JButton("No");
        JButton startProcessingTile_BTN = new JButton("Start");
        JButton closePanel_BTN = new JButton("Close");

        // ***************************************************************************************
        // Button Setup
        // ***************************************************************************************

        y += processText_TXA.getHeight() + heightSpace;
        x = x_buttonStart;

        startProcessingTile_BTN.setBounds(x + (col_2_componentsWidth - buttonWidth) / 2, y, buttonWidth, buttonHeight);
        startProcessingTile_BTN.setVisible(false);
        startProcessingTile_BTN.setFont(panelFont);

        yesGoodTileSheet_BTN.setBounds(x, y, buttonWidth, buttonHeight);
        yesGoodTileSheet_BTN.setFont(panelFont);

        x += buttonWidth + buttonSpace;

        noGoodTileSheet_BTN.setBounds(x, y, buttonWidth, buttonHeight);
        noGoodTileSheet_BTN.setFont(panelFont);

        closePanel_BTN.setBounds((panelWidth - buttonWidth )/ 2, 610, buttonWidth,buttonHeight);
        closePanel_BTN.setFont(panelFont);


        // ***************************************************************************************
        // Adding components Panel
        // ***************************************************************************************

        add(displayIcon_LBL);
        add(iconFileName_LBL);

        add(yesGoodTileSheet_BTN);
        add(noGoodTileSheet_BTN);
        add(startProcessingTile_BTN);
        add(colAndRow_LBL);
        add(processStatus_LBL);
        add(processText_TXA);
        add(closePanel_BTN);

        // ***************************************************************************************
        // Button Actions
        // ***************************************************************************************

        yesGoodTileSheet_BTN.addActionListener(e -> {

            statusText = "Analyzing Image";
            updateStatus(statusText);

            statusText += "\nWidth: " + imageWidth;
            statusText += "\nHeight: " + imageHeight;

            updateStatus(statusText);

            statusText += "\n\nCutting Image Into";

            maxCol = imageWidth / 16;
            maxRow = imageHeight / 16;
            statusText += "\n" + maxCol + " X " + maxRow + "Tiles";

            updateStatus(statusText);

            createButtonPanel(tileButtons, screenX, screenY, maxCol, maxRow);
            setAddTile(maxCol,maxRow);
            repaint();

            statusText += "\n\nPlease select any tile\nYOU DO NOT WANT TO \nADDED TO Tile Set\nClick Start To Add Tiles";

            updateStatus(statusText);

            noGoodTileSheet_BTN.setVisible(false);
            yesGoodTileSheet_BTN.setVisible(false);

            startProcessingTile_BTN.setVisible(true);

        });

        noGoodTileSheet_BTN.addActionListener(e -> {

            removeAll();
            setVisible(false);
            repaint();
            Main.packageManagerPanel.setVisible(true);
        });

        startProcessingTile_BTN.addActionListener(e -> {

            System.out.println("GraphicsPackageID: " + graphicsPackageID);
            System.out.println("FileName: " + filePath.getName());
            System.out.println("Path: " + getPath(filePath.getParent()));

            startProcessingTile_BTN.setVisible(false);

            statusText += "\n\nCheck TileSheetData In List?";
            updateStatus(statusText);

            // Step 1: Check to make sure the TileSheetData if not in list
            if(!TileSheetData.checkInList(graphicsPackageID,filePath.getName())){
                statusText += "\n TileSheetData not find.\n Creating TileSheetData";
                updateStatus(statusText);
                // Step 2: Make TileSheetData
                TileSheetData tileSheetData = new TileSheetData(graphicsPackageID, filePath.getName(),
                        getPath(filePath.getParent()),true);


                // Step 3: update GraphicsPackage with TileSheetDataID
                if(!GraphicsPackages.addTileSheetData(graphicsPackageID, tileSheetData.tileSheet_ID)){
                    JOptionPane.showMessageDialog(null,"TileSheet " + filePath.getName() +
                                    " can not be add, found in list","Error Adding TileSheetData",
                            JOptionPane.WARNING_MESSAGE);
                    return;
                }

                statusText += "\nGraphics Package Updated!\nMaking TileData Now!";
                updateStatus(statusText);


                // Step 4: Make TileData for TileSheetData
                ArrayList<Integer> tileDataList =   makeTileData( tileSheetData, 16, 16);

                // Step 5: Add TileData to TileSheetData
                tileSheetData.add_TileData(tileDataList);

                statusText += "\nTile Data Files have been made!\nYou can close this panel.";
                updateStatus(statusText);

            }

        });

        closePanel_BTN.addActionListener(e -> {
            removeAll();
            setVisible(false);
            repaint();
        });

    }

    public void paintComponent(Graphics g){
        super.paintComponent(g);
        g2 = (Graphics2D) g;

        imageWidth = bufferedImage.getWidth(null);
        imageHeight = bufferedImage.getHeight(null);

        g2.drawImage(bufferedImage, screenX, screenY, imageWidth * scale,imageHeight * scale,null);

    }

    public void createButtonPanel(JButton[][] tileButtons, int startX, int startY, int maxCol, int maxRow){

        buttonPanel = new JPanel();
        buttonPanel.setLayout(new GridLayout(maxRow, maxCol));
        buttonPanel.setBounds(startX,startY,imageWidth * 2,imageHeight * 2);
        buttonPanel.setOpaque(false);
        buttonPanel.setVisible(true);
        add(this.buttonPanel);

        int col = 0;
        int row = 0;
        while (col < maxCol && row < maxRow) {

            tileButtons[col][row] = new JButton();
            tileButtons[col][row].setOpaque(false);

            tileButtons[col][row].setBackground(Color.black);
            tileButtons[col][row].setActionCommand(String.valueOf(col) + " " + row);
            tileButtons[col][row].addActionListener(e -> {

                String colAndRow = e.getActionCommand();
                String[] s = colAndRow.split(" ");
                main.selectedCol = Integer.parseInt(s[0]);
                main.selectedRow = Integer.parseInt(s[1]);
                colAndRow_LBL.setText("Col: " + main.selectedCol + " Row: " + main.selectedRow);
                changeAddTile(main.selectedCol, main.selectedRow);
            });

            buttonPanel.add(tileButtons[col][row]);

            col++;
            if (col == maxCol) {
                col = 0;
                row++;
            }

        }
    }

    public void changeAddTile(int col, int row){
        addTile[col][row] = !addTile[col][row];

        if(!addTile[col][row]){
            tileButtons[col][row].setBorder(BorderFactory.createLineBorder(Color.RED,2));

        } else {
            tileButtons[col][row].setBorder(BorderFactory.createLineBorder(Color.gray,1));
        }
    }

    public void setAddTile(int maxCol, int maxRow){

        int col = 0;
        int row = 0;
        while (col < maxCol && row < maxRow) {
            addTile[col][row] = true;
            col++;
            if (col == maxCol) {
                col = 0;
                row++;
            }
        }
    }

    public String getPath(String path){
        String makePath = "/tiles/";
        boolean start = false;
        String[] parts = path.split(Pattern.quote(File.separator));

        for(String name : parts){
            if(name.equals("graphics_packages")){ start = true; }
            if(start){
                makePath += name + "/";
            }
        }

        return makePath;

    }

    public ArrayList<Integer> makeTileData(TileSheetData tileSheetData, int maxCol, int maxRow){

        int tileNum = tileSheetData.getNextTileDataID();
        ArrayList<Integer> tileDataIDs = new ArrayList<>();

        int col = 0;
        int row = 0;
        while (col < maxCol && row < maxRow) {
            if(addTile[col][row]){
                System.out.println("Making TileData: " + tileNum);
                TileData tileData = new TileData(tileSheetData.tileSheet_ID, tileNum, col, row, 1);
                tileNum++;
                tileDataIDs.add(tileData.tile_ID);
            }

            col++;
            if (col == maxCol) {
                col = 0;
                row++;
            }
        }

        tileSheetData.updateTileDataElements(tileSheetData.tileSheet_ID, tileSheetData.getNextTileDataID(), tileNum--, tileDataIDs);
        TileSheetData.setNextTileDataID(tileNum);

        return tileDataIDs;


    }

    public void updateStatus(String text){
        processText_TXA.setText(text);
    }
}
