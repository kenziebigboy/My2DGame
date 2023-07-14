package org.tilemanger.panels;

import org.tilemanger.Main;
import org.tilemanger.Reference;
import org.tilemanger.tables.GraphicsPackages;
import org.tilemanger.tables.TileSheetData;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.border.TitledBorder;
import java.awt.*;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import java.util.Objects;
import javax.swing.border.Border;

public class TileSheetSelectorPanel extends JPanel {

    public Main main;


    public Font borderFont = Reference.borderFont;
    public Color borderColor = Reference.borderColor;
    public Font panelFont = Reference.panelFont;
    public Font smallFont = Reference.smallFont;

    public JButton[] imageButtons;
    public JComboBox<ComboItem> graphicsPackage_CMB;
    public JPanel imagePanel;
    public JScrollPane jScrollPane;
    public boolean firstTime = true;

    public int panelWidth = 570;
    public int panelHeight = 700;


    public TileSheetSelectorPanel(Main main){

        this.main = main;

    }

    public void displayTileSheetSelectorPanel(){

        ArrayList<GraphicsPackages> graphicsPackagesList = GraphicsPackages.getGraphicsPackagesList();

        // Setup panel size and center on screen
        panelWidth = 570;
        panelHeight = 700;
        int panelX = (getParent().getWidth() - panelWidth) / 2 ;
        int panelY = (getParent().getHeight() - panelHeight) / 2;

        setBounds(panelX, panelY, panelWidth, panelHeight);
        setLayout(null);
        setBackground(Color.LIGHT_GRAY);
        setVisible(true);

        TitledBorder titledBorder = BorderFactory.createTitledBorder
                (BorderFactory.createLineBorder(Color.gray, 1),
                        " Tile Sheet Selector Panel ", TitledBorder.CENTER,
                        TitledBorder.DEFAULT_POSITION,
                        borderFont, borderColor);
        setBorder(titledBorder);

        // Setup how the ComboBox will display text
        DefaultListCellRenderer listRenderer = new DefaultListCellRenderer();
        listRenderer.setHorizontalAlignment(DefaultListCellRenderer.CENTER); // center-aligned items

        // Setup Label
        JLabel graphicsPackage_LBL = new JLabel("Graphics Package");
        graphicsPackage_LBL.setBounds((panelWidth - 200) / 2,35,200,20);
        graphicsPackage_LBL.setFont(panelFont);
        graphicsPackage_LBL.setHorizontalAlignment(JLabel.CENTER);
        graphicsPackage_LBL.setVisible(true);

        // Setup ComboBox
        graphicsPackage_CMB = new JComboBox<>();
        graphicsPackage_CMB.addItem(new ComboItem(-1,"Pick Graphics Package"));
        for(GraphicsPackages record : graphicsPackagesList){
            graphicsPackage_CMB.addItem(new ComboItem(record.getPackageID(), record.getName()));
        }

        graphicsPackage_CMB.setBounds((panelWidth - 300) / 2, 60, 300,20);
        graphicsPackage_CMB.setFont(panelFont);
        graphicsPackage_CMB.setRenderer(listRenderer);

        add(graphicsPackage_LBL);
        add(graphicsPackage_CMB);

        imagePanel = new JPanel();
        jScrollPane = new JScrollPane(imagePanel);
        imagePanel.setLayout(new GridLayout(0,2));
        //imagePanel.setBounds((panelWidth - 512) / 2, 85, 512, 600);
        imagePanel.setBackground(Color.WHITE);

        graphicsPackage_CMB.addActionListener(e -> {

            ComboItem imageId = (ComboItem) graphicsPackage_CMB.getSelectedItem();

                displayTileSheets(imageId.getID());
                repaint();

        });

    }

    public void displayTileSheets(int tileSheetIDs){

        // Make Image Pane
        imagePanel.removeAll();

        ArrayList<TileSheetData> gpTileSheetData = TileSheetData.getTileSheetsForGP
                (Objects.requireNonNull(GraphicsPackages.getTileSheetDataArrayList(tileSheetIDs)));

        System.out.println("Size: " +  gpTileSheetData.size());
        imageButtons = new JButton[gpTileSheetData.size()];
        int index = 0;
        for(TileSheetData tileSheetImage : gpTileSheetData){

            File directory = new File("./resources/graphics_packages/" + tileSheetImage.path + tileSheetImage.tileSheetName );

            try {
                BufferedImage bufferedImage = ImageIO.read(new File(directory.toString()));
                System.out.println("Index: " + index);
                // Make buttons and to button panel
                imageButtons[index] = new JButton();
                imageButtons[index].setOpaque(false);
                imageButtons[index].setBackground(Color.black);
                //imageButtons[index].setActionCommand(String.valueOf(tileSheetImage.tileSheet_ID));
                imageButtons[index].setIcon(new ImageIcon(bufferedImage));
                imageButtons[index].setMargin(new Insets(0,0,0,0));
                imageButtons[index].addActionListener(e -> {

                    main.imageSelectorPanel.drawImageSelectorPanel(tileSheetImage.packageID,
                            tileSheetImage.tileSheet_ID, directory);

                    removeAll();
                    setVisible(false);
                    repaint();

                });

                imagePanel.add(imageButtons[index]);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }

            index++;

        }

        jScrollPane.setBounds((panelWidth - 542) / 2, 85, 542, 600);

        add(jScrollPane, BorderLayout.CENTER);

        jScrollPane.updateUI();
        imagePanel.repaint();
        repaint();
    }
}
