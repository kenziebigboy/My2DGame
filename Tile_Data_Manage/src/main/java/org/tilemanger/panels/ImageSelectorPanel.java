package org.tilemanger.panels;

import org.tilemanger.Main;
import org.tilemanger.tables.TileImage;
import org.tilemanger.tables.TileSheetData;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class ImageSelectorPanel extends JPanel {

    public Main main;
    public Graphics2D g2;
    public BufferedImage tileSheetImage;
    public int graphicsPackageID;
    public File filePath;
    public int scale = 2;
    int screenX;
    int screenY;
    public int maxCol = 16;
    public int maxRow = 16;
    public int imageWidth;
    public int imageHeight;

    public JPanel imagePanel;
    public JScrollPane imageScroll;

    public ImageSelectorPanel(Main main) {

        this.main = main;
        tileSheetImage = new BufferedImage(512, 512, BufferedImage.TYPE_INT_ARGB);
        g2 = (Graphics2D) tileSheetImage.getGraphics();

    }

    public void drawImageSelectorPanel(int graphicsPackagesID,int tileSheetID, File filePath){

        this.graphicsPackageID = graphicsPackagesID;
        this.filePath = filePath;
        TileSheetData tileSheetData = TileSheetData.getById(tileSheetID);


        int panelWidth = 650;
        int panelHeight = 650;
        int panelX = (getParent().getWidth() - panelWidth) / 2 ;
        int panelY = (getParent().getHeight() - panelHeight) / 2 + 30;

        setBounds(panelX, panelY, panelWidth, panelHeight);
        setLayout(null);
        setBackground(Color.LIGHT_GRAY);
        setVisible(true);

        imagePanel = new JPanel();
        imageScroll = new JScrollPane(imagePanel);
        imagePanel.setLayout(new GridLayout(16,16));
        //imagePanel.setBounds((panelWidth - 512) / 2, 60, 512, 512);


        imageScroll.setBounds((panelWidth - (512 + 128)) / 2, 60, 512 + 128, 256);
        imageScroll.setVisible(true);

        JButton[] imageButtons = new JButton[256];
        TileImage tileImage = null;

        ImageIcon blankIcon = null;

        boolean ones = true;

        System.out.println("Size: " + tileSheetData.tileDataList.size());

        try {

            tileSheetImage = ImageIO.read(filePath.getAbsoluteFile());


        } catch (IOException ioException) {
            ioException.printStackTrace();
        }

        int col = 0;
        int row = 0;
        int index = 0;
        int buttonIndex = 0;
        boolean isImage;
        int imageId = 0;

        while (col < maxCol && row < maxRow) {

            // test for blank or image

            if(index < tileSheetData.tileDataList.size() - 1) {
                isImage = tileSheetData.tileDataList.get(index).cellX == col && tileSheetData.tileDataList.get(index).cellY == row;
            } else {
                isImage = false;
            }

            if(isImage){
                // Make Image
                tileImage = new TileImage(getTileSheetCellImage(tileSheetImage, col, row));
                tileImage.icon = makeImageIcon(tileImage.image);
                tileImage.solid = tileSheetData.tileDataList.get(index).solid;
                main.currentTileSet.put(tileSheetData.tileDataList.get(index).tile_ID, tileImage);
                imageId = tileSheetData.tileDataList.get(index).tile_ID;

                index++;

            } else {
                // Make Blank
                if(!ones){

                    BufferedImage blankImage = getTileSheetCellImage(tileSheetImage, col, row);
                    blankIcon = makeImageIcon(blankImage);
                    ones = true;
                }
            }

            // Make Buttons
            imageButtons[buttonIndex] = new JButton();
            imageButtons[buttonIndex].setOpaque(false);
            imageButtons[buttonIndex].setBackground(Color.black);
            imageButtons[buttonIndex].setActionCommand(String.valueOf(tileSheetData.tileDataList.get(imageWidth).tile_ID));
            imageButtons[buttonIndex].setIcon(isImage ? tileImage.icon : blankIcon);
            imageButtons[buttonIndex].setMargin(new Insets(0,0,0,0));
            imageButtons[buttonIndex].setEnabled(isImage);
            int finalImageId = imageId;
            imageButtons[buttonIndex].addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    main.currentForeGroundTileID_LBL.setText(String.valueOf(finalImageId));
                }
            });


            imagePanel.add(imageButtons[buttonIndex]);

            buttonIndex++;

            col++;

            if (col == maxCol) {
                col = 0;
                row++;
            }
        }

        add(imageScroll, BorderLayout.CENTER);
        imageScroll.updateUI();
        repaint();

    }

    public void paintComponent(Graphics g){
        super.paintComponent(g);
        g2 = (Graphics2D) g;

//        imageWidth = bufferedImage.getWidth(null);
//        imageHeight = bufferedImage.getHeight(null);
//
//        g2.drawImage(bufferedImage, screenX, screenY, imageWidth * scale,imageHeight * scale,null);

    }

    public BufferedImage getTileSheetCellImage(BufferedImage bufferedImage, int cellX, int cellY){

        return bufferedImage.getSubimage(cellX * 16, cellY * 16, 16, 16);

    }

    public ImageIcon makeImageIcon(BufferedImage bufferedImage){

        Image image = (bufferedImage.getScaledInstance(
                32, 32, 1));

        return new ImageIcon(image);
    }

}
