package org.tilemanger.panels;

import org.tilemanger.Main;
import org.tilemanger.Reference;
import org.tilemanger.tables.GraphicsPackages;
import org.tilemanger.tables.TileSheetData;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumnModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.io.File;

public class PackageManagerPanel extends JPanel {

    public static JTable tileSheetDataTable;

    public Font borderFont = Reference.borderFont;
    public Color borderColor = Reference.borderColor;
    public Font panelFont = Reference.panelFont;
    public Font smallFont = Reference.smallFont;
    public boolean good = false;

    public void displayPackageManager(int id, File openfile, boolean process ){

        GraphicsPackages currentPackage = GraphicsPackages.getPackage(id);

        int panelX = 200;
        int panelY = 25;
        int panelWidth = 500;
        int panelHeight = 700;

        setBounds(panelX, panelY, panelWidth, panelHeight);
        setLayout(null);
        setBackground(Color.LIGHT_GRAY);
        setVisible(true);

        TitledBorder titledBorder = BorderFactory.createTitledBorder
                (BorderFactory.createLineBorder(Color.gray, 1),
                        " Graphics Packages Manager ", TitledBorder.CENTER,
                        TitledBorder.DEFAULT_POSITION,
                        borderFont, borderColor);
        setBorder(titledBorder);


        // Display a text area with notes about adding tilesheet to the Manager
        JTextArea notes = new JTextArea();
        notes.setFont(getFont().deriveFont(16f));
        notes.setMargin(new Insets(5,5,5,5));
        notes.setBounds(50,50, 400, 100 );
        notes.setVisible(true);
        notes.setText("You can add tile sheets to the Manager. You will not be \nable to remove or delete them, but you " +
                "can deactivate \nthem. When adding sheet the images is cut into 16 x 16 \ntiles. Each tile is give an index " +
                "that can not be remove.");
        notes.setLineWrap(true);
        add(notes);

        // Display the Graphics Package working with
        JLabel graphicsPackage_LBL = new JLabel("Package: " +  currentPackage.getName());
        graphicsPackage_LBL.setFont(borderFont);
        graphicsPackage_LBL.setBounds(50, 150, 200,50);
        graphicsPackage_LBL.setVisible(true);
        add(graphicsPackage_LBL);

        // ***************************************************************************************
        // Create buttons
        // ***************************************************************************************

        JButton addTileSheet_BTN = new JButton("Add");
        JButton switchActive_BTN = new JButton("De/activate");
        JButton closePackagePanel_BTN = new JButton("Close");

        int buttonWidth = 100;
        int buttonHeight = 30;
        int buttonSpace = 10;

        int x_buttonStart = (panelWidth - Reference.totalButtonWidth(3, buttonWidth, buttonSpace)) / 2;

        // ***************************************************************************************
        // Button Setup
        // ***************************************************************************************

        int heightSpace = 5;
        int y = 200 + heightSpace;
        int x = x_buttonStart;

        addTileSheet_BTN.setBounds(x, y, buttonWidth, buttonHeight);
        addTileSheet_BTN.setFont(panelFont);

        x += buttonWidth + buttonSpace;

        closePackagePanel_BTN.setBounds(x, y, buttonWidth, buttonHeight);
        closePackagePanel_BTN.setFont(panelFont);

        x += buttonWidth + buttonSpace;

        switchActive_BTN.setBounds(x, y, buttonWidth, buttonHeight);
        switchActive_BTN.setFont(smallFont);


        add(addTileSheet_BTN);
        add(switchActive_BTN);
        add(closePackagePanel_BTN);

        // ***************************************************************************************
        // Setup Table
        // ***************************************************************************************

        DefaultTableModel tileSheetDataTableModel = TileSheetData.getTableModel();

        tileSheetDataTable = new JTable(tileSheetDataTableModel){
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        tileSheetDataTable.setRowHeight(450);
        tileSheetDataTable.setAutoCreateRowSorter(false);
        tileSheetDataTable.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);

        int[] columnWidth = {50,450};

        TableColumnModel tileSheetDataColumnModel = tileSheetDataTable.getColumnModel();

        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment(JLabel.CENTER);

        JScrollPane tableScroll = new JScrollPane(tileSheetDataTable);

        tableScroll.setBounds(20, y + 20 + buttonHeight, panelWidth - 40, 430 );
        tableScroll.setFont(panelFont);
        tableScroll.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
        tableScroll.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);

        add(tableScroll);

        // ***************************************************************************************
        // Button Actions
        // ***************************************************************************************

        addTileSheet_BTN.addActionListener(new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {

                JFileChooser jFileChooser = new JFileChooser();
                jFileChooser.setAcceptAllFileFilterUsed(false);
                FileNameExtensionFilter fileNameExtensionFilter = new FileNameExtensionFilter("Image file", "jpg", "jpeg", "PNG");
                jFileChooser.addChoosableFileFilter(fileNameExtensionFilter);
                jFileChooser.setCurrentDirectory(new File(Reference.ICON_PATH));

                int checkInput = jFileChooser.showOpenDialog(null);

                if(checkInput == JFileChooser.APPROVE_OPTION){
                    System.out.println(jFileChooser.getSelectedFile());
                    setVisible(false);
                    // Try to add tile sheet to Graphic Package
                   Main.imagePerViewPanel.displayImageProcessingPanel(id, jFileChooser.getSelectedFile());

                    System.out.println(good);

                }
            }
        });
    }

    public void setGoodTileSheet(boolean good){
        this.good = good;
    }
}
