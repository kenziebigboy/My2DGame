package org.tilemanger.panels;

import org.tilemanger.Main;
import org.tilemanger.Reference;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.TitledBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class ImageProcessingPanel extends JPanel {

    public Font borderFont = Reference.borderFont;
    public Color borderColor = Reference.borderColor;
    public Font panelFont = Reference.panelFont;
    public Font smallFont = Reference.smallFont;
    public String statusText = "";
    public Image image;
    public Graphics2D g2;
    public BufferedImage bufferedImage;
    public int scale = 2;
    int screenX;
    int screenY;


    static boolean packageEdit = false;
    static int packageEdit_ID = -1;

    public ImageProcessingPanel(){
        bufferedImage = new BufferedImage(512, 512, BufferedImage.TYPE_INT_ARGB);
        g2 = (Graphics2D) bufferedImage.getGraphics();
    }

    public void displayImageProcessingPanel(int graphicePackageID, File filePath){


        int panelWidth = 800;
        int panelHeight = 650;
        int panelX = (getParent().getWidth() - panelWidth) / 2 ;
        int panelY = (getParent().getHeight() - panelHeight) / 2  ;

        System.out.println(getParent().getWidth() + " " + getParent().getHeight() );

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
        JTextArea processText_TXA = new JTextArea("Is this the right file\nYes / No");

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



        // ***************************************************************************************
        // Button Setup
        // ***************************************************************************************

        y += processText_TXA.getHeight() + heightSpace;
        x = x_buttonStart;

        yesGoodTileSheet_BTN.setBounds(x, y, buttonWidth, buttonHeight);
        yesGoodTileSheet_BTN.setFont(panelFont);

        x += buttonWidth + buttonSpace;

        noGoodTileSheet_BTN.setBounds(x, y, buttonWidth, buttonHeight);
        noGoodTileSheet_BTN.setFont(panelFont);


        add(displayIcon_LBL);
        add(iconFileName_LBL);

        add(yesGoodTileSheet_BTN);
        add(noGoodTileSheet_BTN);
        add(processStatus_LBL);
        add(processText_TXA);

        // ***************************************************************************************
        // Button Actions
        // ***************************************************************************************

        yesGoodTileSheet_BTN.addActionListener(new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {

                statusText = "Analyzing Image";
                processText_TXA.setText(statusText);
                int width = bufferedImage.getWidth(null);
                int height = bufferedImage.getHeight(null);

                statusText += "\nWidth: " + width;
                statusText += "\nHeight: " + height;

                processText_TXA.setText(statusText);

                statusText += "\n\nCutting Image Into";
                statusText += "\n" + width / 16 + " X " + height / 16 + "Tiles";

                processText_TXA.setText(statusText);

                repaint();




            }
        });

        noGoodTileSheet_BTN.addActionListener(new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {

                removeAll();
                setVisible(false);
                repaint();
                Main.packageManagerPanel.setVisible(true);
            }
        });




    }

    public void paintComponent(Graphics g){
        super.paintComponent(g);
        g2 = (Graphics2D) g;



//        BufferedImage scaledImage = new BufferedImage(512,512,bufferedImage.getType());
//        g2 = scaledImage.createGraphics();
//        g2.drawImage(bufferedImage,screenX,screenY,null);

        g2.drawImage(bufferedImage, screenX, screenY, 512,512,null);


        g2.setColor(Color.RED);
        g2.drawLine(screenX,screenY,100 + screenX, 100 + screenY);
    }
}
