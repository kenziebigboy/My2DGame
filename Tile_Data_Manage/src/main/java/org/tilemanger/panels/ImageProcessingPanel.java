package org.tilemanger.panels;

import org.tilemanger.Main;
import org.tilemanger.Reference;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.TitledBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.io.File;
import java.io.IOException;

public class ImageProcessingPanel extends JPanel {

    public Font borderFont = Reference.borderFont;
    public Color borderColor = Reference.borderColor;
    public Font panelFont = Reference.panelFont;
    public Font smallFont = Reference.smallFont;

    static boolean packageEdit = false;
    static int packageEdit_ID = -1;

    public void displayImageProcessingPanel(int graphicePackageID, File filePath){

        int panelX = 200;
        int panelY = 25;
        int panelWidth = 500;
        int panelHeight = 600;

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

        int x = 20;  int heightSpace   = 5;
        int y = 40;  int widthSpace    = 5;

        int col_1_componentsWidth = 100;      // how width the column one will be
        int col_2_componentsWidth = 160;      // how width the column two will be

        int col_1_x_start = x;                                                   // column 1 starting point
        int col_2_x_start = x + col_1_componentsWidth + widthSpace;;               // column 2 starting point
        int col_1_y_start = y;
        int col_2_y_start = y;

        int componentHeight = 20;
        int displayImageSize = 450;

        // Setup x & y for column 1
        x = col_1_x_start; y = col_1_y_start;

        int buttonWidth         = 100;
        int buttonHeight        = 30;
        int buttonSpace         = 10;

        int x_buttonStart       = (panelWidth - Reference.totalButtonWidth(3,buttonWidth,buttonSpace)) / 2;

        // ***************************************************************************************
        // Create components column 1
        // ***************************************************************************************

        ImageIcon imageIcon_II = new ImageIcon();
        JLabel displayIcon_LBL = new JLabel("");

        Border imageBorder = BorderFactory.createLineBorder(Color.gray,1);
        displayIcon_LBL.setBorder(imageBorder);

        JLabel iconFileName_LBL = new JLabel("");
        JLabel note_LBL = new JLabel("Is this the right file?");

        // ***************************************************************************************
        // Setup components column 1
        // ***************************************************************************************

        displayIcon_LBL.setBounds(x,y,displayImageSize, displayImageSize);
        displayIcon_LBL.setFont(panelFont);
        displayIcon_LBL.setHorizontalAlignment(JLabel.CENTER);

        y += displayImageSize + heightSpace;

        iconFileName_LBL.setBounds(x,y,400,20);
        iconFileName_LBL.setFont(borderFont);
        iconFileName_LBL.setHorizontalAlignment(JLabel.LEFT);

        y += 20 + heightSpace;

        note_LBL.setBounds(x + 5, y, 400, 25);
        note_LBL.setFont(borderFont);
        note_LBL.setForeground(Color.GREEN);
        note_LBL.setHorizontalAlignment(JLabel.CENTER);

        add(displayIcon_LBL);
        add(iconFileName_LBL);
        add(note_LBL);

        File openedFile = filePath;
        iconFileName_LBL.setText("File Name: " + openedFile.getName());
        try {

            Image image = ImageIO.read(openedFile.getAbsoluteFile());

            Image dimg = image.getScaledInstance(displayIcon_LBL.getWidth(), displayIcon_LBL.getHeight(),
                    Image.SCALE_SMOOTH);
            imageIcon_II.setImage(dimg);

            displayIcon_LBL.setIcon(imageIcon_II);
            repaint();

        } catch (IOException ioException) {
            ioException.printStackTrace();
        }

        // ***************************************************************************************
        // Create buttons
        // ***************************************************************************************

        JButton yesGoodTileSheet_BTN = new JButton("Yes");
        JButton noGoodTileSheet_BTN = new JButton("No");



        // ***************************************************************************************
        // Button Setup
        // ***************************************************************************************


        y = 550 + heightSpace;
        x = x_buttonStart;

        yesGoodTileSheet_BTN.setBounds(x, y, buttonWidth, buttonHeight);
        yesGoodTileSheet_BTN.setFont(panelFont);

        x += buttonWidth + buttonSpace;

        noGoodTileSheet_BTN.setBounds(x, y, buttonWidth, buttonHeight);
        noGoodTileSheet_BTN.setFont(panelFont);

        x += buttonWidth + buttonSpace;



        add(yesGoodTileSheet_BTN);
        add(noGoodTileSheet_BTN);

        // ***************************************************************************************
        // Button Actions
        // ***************************************************************************************

        yesGoodTileSheet_BTN.addActionListener(new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {

                removeAll();
                setVisible(false);
                repaint();
                Main.packageManagerPanel.setGoodTileSheet(true);
                Main.packageManagerPanel.setVisible(true);
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
}
