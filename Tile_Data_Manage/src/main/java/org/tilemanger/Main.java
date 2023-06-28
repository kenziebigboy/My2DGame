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

        //ArrayList<TileData> td = TileData.getTileDataList();
        //ArrayList<TileData2> td2 = TileData2.getTileDataList();
        //System.out.println(td.size());

     /* ArrayList<TileSheetData2> tsd2 = TileSheetData2.getTileSheetDataList();

      for(int i = 0; i < tsd2.size(); i++){

          ArrayList<TileData> tileDataNewList = new ArrayList<>();

          // Make TileData Objests to all to list
          for(int j = 0; j < tsd2.get(i).tileDataID.size(); j++){
              int index = tsd2.get(i).tileDataID.get(j);

              TileData td = new TileData(td2.get(index).tile_ID,
                                         td2.get(index).cellX,
                                         td2.get(index).cellY,
                                          true);

              tileDataNewList.add(td);

          }

          // Now we can make new TileSheetData

          TileSheetData tsd = new TileSheetData(tsd2.get(i).tileSheet_ID,
                                                tsd2.get(i).packageID,
                                                tsd2.get(i).tileSheetName,
                                                tsd2.get(i).path,
                                                true,
                                                tileDataNewList);

          if(i % 50 == 0){
              System.out.println("Index: " + i);
          }

      }


        System.exit(0);*/

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
        JMenuItem displayRawTileData = new JMenuItem("Tile Data");

        // Add menu Items to Raw Data
        rawdata.add(displayRawGraphicsPackageData);
        rawdata.add(displayRawTileSheetData);
        rawdata.add(displayRawTileData);

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

        displayRawTileData.addActionListener(e -> {
            //displayRawData.displayRawTileData(TileData.getTileDataList());
        });


    }



}