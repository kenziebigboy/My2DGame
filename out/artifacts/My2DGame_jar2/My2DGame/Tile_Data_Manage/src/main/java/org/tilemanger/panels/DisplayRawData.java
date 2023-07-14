package org.tilemanger.panels;

import org.tilemanger.Main;
import org.tilemanger.Reference;
import org.tilemanger.tables.GraphicsPackages;
import org.tilemanger.tables.TileData;
import org.tilemanger.tables.TileSheetData;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Arrays;

public class DisplayRawData extends JPanel {

    public Main main;
    public JTextArea displayData;
    public JLabel dataFileName;

    public DisplayRawData(Main main) {
        this.main = main;

    }

    public void makePanel(){
        int panelWidth = 800;
        int panelHeight = 650;
        int panelX = (getParent().getWidth() - panelWidth) / 2 ;
        int panelY = (getParent().getHeight() - panelHeight) / 2  ;


        setBounds(panelX, panelY, panelWidth, panelHeight);
        setLayout(null);
        setBackground(Color.LIGHT_GRAY);
        setVisible(true);

        TitledBorder titledBorder = BorderFactory.createTitledBorder
                (BorderFactory.createLineBorder(Color.gray, 1),
                        " Display Raw Data", TitledBorder.CENTER,
                        TitledBorder.DEFAULT_POSITION,
                        Reference.borderFont, Reference.borderColor);
        setBorder(titledBorder);

        int x = 25;  int heightSpace   = 5;
        int y = 50;  int widthSpace    = 5;

        int col_1_componentsWidth = panelWidth - 50;
        int componentHeight = 20;

        int buttonWidth         = 100;
        int buttonHeight        = 30;
        int buttonSpace         = 10;

        int x_buttonStart       = (panelWidth - buttonWidth) / 2;

        // ***************************************************************************************
        // Create components column 1
        // ***************************************************************************************

        dataFileName = new JLabel("File Name: ");
        displayData = new JTextArea();
        JScrollPane displayScrollData = new JScrollPane(displayData);

        // ***************************************************************************************
        // Setup components column 1
        // ***************************************************************************************

        displayScrollData.setBounds(x, y, col_1_componentsWidth,500);
        displayScrollData.setFont(Reference.panelFont);
        //displayScrollData.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
        displayScrollData.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);


        //dataFileName.setBounds(x, y, col_1_componentsWidth, 500 );
        dataFileName.setFont(Reference.borderFont);
        dataFileName.setForeground(Color.BLUE);
        dataFileName.setHorizontalAlignment(JLabel.LEFT);

        y += componentHeight + heightSpace;

        displayData.setBounds(x, y, col_1_componentsWidth, 500);
        displayData.setFont(Reference.panelFont);
        displayData.setBackground(Color.BLACK);
        displayData.setForeground(Color.WHITE);
        displayData.setAutoscrolls(true);
        //displayData.setLineWrap(true);
        displayData.setMargin(new Insets(5,10,5,10));

        // ***************************************************************************************
        // Create buttons
        // ***************************************************************************************

        JButton closePanel = new JButton("Close");

        // ***************************************************************************************
        // Button Setup
        // ***************************************************************************************

        x =  x_buttonStart;
        y += 500 + heightSpace * 2;
        closePanel.setBounds(x, y, buttonWidth, buttonHeight);
        closePanel.setFont(Reference.panelFont);

        // ***************************************************************************************
        // Add components to panel
        // ***************************************************************************************
        add(dataFileName);
        add(displayScrollData);
        add(closePanel);

        // ***************************************************************************************
        // Button Actions
        // ***************************************************************************************

        closePanel.addActionListener(e -> {
            removeAll();
            setVisible(false);
            repaint();
        });
    }

    public void displayRawTileSheetData(ArrayList<TileSheetData> tileSheetDataArrayList){
        makePanel();
        dataFileName.setText("Tile Sheet Data");
        displayData.setText(tileSheetDataArrayList.toString());
    }

    public void displayRawGraphicsPackages(ArrayList<GraphicsPackages> packagesArrayList){
        makePanel();
        dataFileName.setText("Graphics Packages");
        displayData.setText(packagesArrayList.toString());
    }

    public void displayRawTileData(ArrayList<TileData> tileDataArrayList){
        makePanel();
        dataFileName.setText("Tile Data");
        displayData.setText(tileDataArrayList.toString());
    }
}
