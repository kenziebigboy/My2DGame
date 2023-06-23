package org.tilemanger;

import org.tilemanger.panels.ImageProcessingPanel;
import org.tilemanger.panels.PackageManagerPanel;
import org.tilemanger.panels.PackagesPanel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;

public class Main extends JFrame {




    // Packages Panel
    PackagesPanel packagesPanel;

    // PackageManagerPanel
    public static PackageManagerPanel packageManagerPanel;

    // Tile Sheet Image Pre View
    public static ImageProcessingPanel imagePerViewPanel;


    public Main() {

        setBackground(Color.LIGHT_GRAY);
        setLayout(null);
        MenuSystem();

        this.packagesPanel = new PackagesPanel();
        add(packagesPanel);

        this.packageManagerPanel = new PackageManagerPanel();
        add(packageManagerPanel);

        this.imagePerViewPanel = new ImageProcessingPanel();
        add(imagePerViewPanel);


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

    private void MenuSystem(){

        JMenuBar mainBar    = new JMenuBar();
        JMenu sheetData     = new JMenu("Sheet Data");

        // Adding menu to bar
        mainBar.add(sheetData);

        // Create menu items for sheet data
        JMenuItem packages  = new JMenuItem("Packages");

        JMenuItem viewTileSheets = new JMenuItem("View TileSheets");

        // Add menu items to sheet Data
        sheetData.add(packages);

        sheetData.add(viewTileSheets);

        setJMenuBar(mainBar);

        // Add Action Listeners for menus
        packages.addActionListener(new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {

                packagesPanel.displayPackagePanel();
            }
        });


        viewTileSheets.addActionListener(new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {

            }
        });


    }



}