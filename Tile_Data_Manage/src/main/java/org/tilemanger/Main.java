package org.tilemanger;

import org.tilemanger.panels.*;
import org.tilemanger.tables.GraphicsPackages;
import org.tilemanger.tables.TileImage;
import org.tilemanger.tables.TileSheetData;

import javax.swing.*;
import java.awt.*;
import java.util.HashMap;
import java.util.Map;

public class Main extends JFrame {

    public int selectedCol;
    public int selectedRow;
    public Map<Integer, TileImage> currentTileSet = new HashMap<>();

    // Packages Panel
    PackagesPanel packagesPanel;

    // PackageManagerPanel
    public static PackageManagerPanel packageManagerPanel;

    // Tile Sheet Image Pre View
    public static ImageProcessingPanel imagePerViewPanel;

    public DisplayRawData displayRawData = new DisplayRawData(this);
    public TileSheetSelectorPanel tileSheetSelectorPanel = new TileSheetSelectorPanel(this);
    public ImageSelectorPanel imageSelectorPanel = new ImageSelectorPanel(this);

    public JLabel currentBackGroundTileID_LBL;
    public JLabel currentForeGroundTileID_LBL;

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
        add(tileSheetSelectorPanel);
        add(imageSelectorPanel);

        displayCurrentTileSelection();

    }

    public static void main(String[] args) {

        TileSheetData.writeDataToDisk();

        Main main = new Main();
        main.setVisible(true);
        main.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        main.setTitle("Game TileSheet Manger");
        main.setBounds(25,25,1400, 950);
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
            tileSheetSelectorPanel.displayTileSheetSelectorPanel();
        });

        displayRawGraphicsPackageData.addActionListener(e -> {
            displayRawData.displayRawGraphicsPackages(GraphicsPackages.getGraphicsPackagesList());
        });

        displayRawTileSheetData.addActionListener(e -> {
            displayRawData.displayRawTileSheetData(TileSheetData.getTileSheetDataList());
        });

    }

    private void displayCurrentTileSelection(){

        JLabel lableCrrentBackGroundTileID_LBL = new JLabel("Current Back Tile Id: ");
        lableCrrentBackGroundTileID_LBL.setBounds(5,20,200,20);
        lableCrrentBackGroundTileID_LBL.setVisible(true);
        lableCrrentBackGroundTileID_LBL.setHorizontalAlignment(JLabel.RIGHT);

        JLabel labelCurrentForeGroundTileID_LBL = new JLabel("Current Fore Tile Id: ");
        labelCurrentForeGroundTileID_LBL.setBounds(5, 40, 200,20);
        labelCurrentForeGroundTileID_LBL.setVisible(true);
        labelCurrentForeGroundTileID_LBL.setHorizontalAlignment(JLabel.RIGHT);


        currentBackGroundTileID_LBL = new JLabel("0000");
        currentBackGroundTileID_LBL.setBounds(215,20,100,20);
        currentBackGroundTileID_LBL.setVisible(true);
        currentBackGroundTileID_LBL.setHorizontalAlignment(JLabel.LEFT);

        currentForeGroundTileID_LBL = new JLabel("0000");
        currentForeGroundTileID_LBL.setBounds(215,40,100,20);
        currentBackGroundTileID_LBL.setVisible(true);
        currentForeGroundTileID_LBL.setHorizontalAlignment(JLabel.LEFT);

        add(lableCrrentBackGroundTileID_LBL);
        add(currentBackGroundTileID_LBL);

        add((labelCurrentForeGroundTileID_LBL));
        add(currentForeGroundTileID_LBL);


    }



}