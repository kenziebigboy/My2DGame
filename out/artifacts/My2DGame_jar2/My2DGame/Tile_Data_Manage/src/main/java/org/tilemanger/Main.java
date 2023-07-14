package org.tilemanger;

import org.tilemanger.panels.DisplayRawData;
import org.tilemanger.panels.ImageProcessingPanel;
import org.tilemanger.panels.PackageManagerPanel;
import org.tilemanger.panels.PackagesPanel;
import org.tilemanger.tables.*;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class Main extends JFrame {

    public int selectedCol;
    public int selectedRow;

    // Packages Panel
    PackagesPanel packagesPanel;

    // PackageManagerPanel
    public static PackageManagerPanel packageManagerPanel;

    // Tile Sheet Image Pre View
    public static ImageProcessingPanel imagePerViewPanel;

    public DisplayRawData displayRawData = new DisplayRawData(this);


    public Main() {

        setBackground(Color.LIGHT_GRAY);
        setLayout(null);
        MenuSystem();

        this.packagesPanel = new PackagesPanel(this);
        add(packagesPanel);

        this.packageManagerPanel = new PackageManagerPanel();
        add(packageManagerPanel);

        this.imagePerViewPanel = new ImageProcessingPanel(this);
        add(imagePerViewPanel);

        add(displayRawData);

    }

    public static void main(String[] args) {

        Main main = new Main();
        main.setVisible(true);
        main.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        main.setTitle("Game TileSheet Manger");
        main.setBounds(25,25,1000, 800);
        main.setLocationRelativeTo(null);
        Dimension mainScreenDim = new Dimension(1000,800);
        main.setPreferredSize(mainScreenDim);

    }

    public int getStartX(int panelWidth){
        return (getWidth() - panelWidth) / 2;
    }

    public int getStartY(int panelHeight){
        return (getHeight() - panelHeight) / 2;
    }

    private void MenuSystem(){

        JMenuBar mainBar    = new JMenuBar();
        JMenu sheetData     = new JMenu("Sheet Data");
        JMenu rawdata       = new JMenu("Raw Data");

        // Adding menu to bar
        mainBar.add(sheetData);
        mainBar.add(rawdata);

        // Create menu items for sheet data
        JMenuItem packages  = new JMenuItem("Packages");
        JMenuItem viewTileSheets = new JMenuItem("View TileSheets");

        // Add menu items to sheet Data
        sheetData.add(packages);
        sheetData.add(viewTileSheets);

        // Create Menu Items for Raw Data
        JMenuItem displayRawGraphicsPackageData = new JMenuItem("Graphics Package");
        JMenuItem displayRawTileSheetData = new JMenuItem("Tile Sheet Data");

        // Add menu Items to Raw Data
        rawdata.add(displayRawGraphicsPackageData);
        rawdata.add(displayRawTileSheetData);

        setJMenuBar(mainBar);

        // Add Action Listeners for menus
        packages.addActionListener(e -> packagesPanel.displayPackagePanel());

        viewTileSheets.addActionListener(e -> {

        });

        displayRawGraphicsPackageData.addActionListener(e -> {
            displayRawData.displayRawGraphicsPackages(GraphicsPackages.getGraphicsPackagesList());
        });

        displayRawTileSheetData.addActionListener(e -> {
            displayRawData.displayRawTileSheetData(TileSheetData.getTileSheetDataList());
        });

    }



}