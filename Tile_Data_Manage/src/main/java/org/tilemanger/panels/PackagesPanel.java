package org.tilemanger.panels;


import org.tilemanger.Main;
import org.tilemanger.Reference;
import org.tilemanger.tables.GraphicsPackages;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumnModel;
import java.awt.*;
import java.awt.event.ActionEvent;

public class PackagesPanel extends JPanel{


    public static JTable packagesTable;
    
    public Font borderFont = Reference.borderFont;
    public Color borderColor = Reference.borderColor;
    public Font panelFont = Reference.panelFont;
    public Font smallFont = Reference.smallFont;

    static boolean packageEdit = false;
    static int packageEdit_ID = -1;

   //PackageManagerPanel packageManagerPanel = new PackageManagerPanel();


    public void displayPackagePanel() {

        int panelX = 200;
        int panelY = 25;
        int panelWidth = 360;
        int panelHeight = 325;

        setBounds(panelX, panelY, panelWidth, panelHeight);
        setLayout(null);
        setBackground(Color.LIGHT_GRAY);
        setVisible(true);

        TitledBorder titledBorder = BorderFactory.createTitledBorder
                (BorderFactory.createLineBorder(Color.gray, 1),
                        " Graphics Packages ", TitledBorder.CENTER,
                        TitledBorder.DEFAULT_POSITION,
                         borderFont, borderColor);
        setBorder(titledBorder);

        int x = 20;
        int heightSpace = 5;
        int y = 40;
        int widthSpace = 5;

        int col_1_componentsWidth = 100;      // how width the column one will be
        int col_2_componentsWidth = 160;      // how width the column two will be

        int col_1_x_start = x;                                                   // column 1 starting point
        int col_2_x_start = x + col_1_componentsWidth + widthSpace;
        ;               // column 2 starting point
        int col_1_y_start = y;
        int col_2_y_start = y;

        int componentHeight = 20;

        // Setup x & y for column 1
        x = col_1_x_start;
        y = col_1_y_start;

        int buttonWidth = 100;
        int buttonHeight = 30;
        int buttonSpace = 10;

        int x_buttonStart = (panelWidth - Reference.totalButtonWidth(3, buttonWidth, buttonSpace)) / 2;

        // ***************************************************************************************
        // Create components column 1
        // ***************************************************************************************

        // Create labels
        JLabel name_LBL = new JLabel("Name: ");
        JLabel packages_LBL = new JLabel("Packages: ");

        // ***************************************************************************************
        // Setup components column 1
        // ***************************************************************************************

        name_LBL.setBounds(x, y, col_1_componentsWidth, componentHeight);
        name_LBL.setFont(panelFont);
        name_LBL.setHorizontalAlignment(JLabel.RIGHT);

        y += componentHeight + heightSpace;

        packages_LBL.setBounds(x, y, col_1_componentsWidth, componentHeight);
        packages_LBL.setFont(panelFont);
        packages_LBL.setHorizontalAlignment(JLabel.RIGHT);


        // ***************************************************************************************
        // Create components column 2
        // ***************************************************************************************

        // Create text field
        JTextField packages_TXT = new JTextField();

        // Create JTable
        DefaultTableModel graphicsPackagesDataTableModel = GraphicsPackages.getTableModel();

        packagesTable = new JTable(graphicsPackagesDataTableModel) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        packagesTable.setAutoCreateRowSorter(true);
        packagesTable.getTableHeader().setReorderingAllowed(false);
        packagesTable.getTableHeader().setFont(new Font("SansSerif", Font.BOLD, 14));

        int[] columnWidths = {60, 100};

        TableColumnModel packagesColumnModel = packagesTable.getColumnModel();
        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment(JLabel.CENTER);

        for (int i = 0; i < columnWidths.length; i++) {
            packagesColumnModel.getColumn(i).setResizable(false);
            packagesColumnModel.getColumn(i).setPreferredWidth(columnWidths[i]);
            packagesTable.getColumnModel().getColumn(i).setCellRenderer(centerRenderer);
        }

        JScrollPane packagesTableScroll = new JScrollPane(packagesTable);

        // ***************************************************************************************
        // Setup components column 2
        // ***************************************************************************************

        x = col_2_x_start;
        y = col_2_y_start;

        packages_TXT.setBounds(x, y, col_2_componentsWidth, componentHeight);
        packages_TXT.setFont(panelFont);

        y += componentHeight + heightSpace;

        packagesTableScroll.setBounds(x, y, 160, 200);
        packagesTableScroll.setFont(panelFont);
        packagesTableScroll.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        packagesTableScroll.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);

        // ***************************************************************************************
        // Create buttons
        // ***************************************************************************************

        JButton savePackage_BTN = new JButton("Add");
        JButton managePackage_BTN = new JButton("Manage Package");
        JButton closePackagePanel_BTN = new JButton("Close");
        JButton clearPackage_BTN = new JButton("Clear");

        // ***************************************************************************************
        // Button Setup
        // ***************************************************************************************

        y += 210 + heightSpace;
        x = x_buttonStart;

        savePackage_BTN.setBounds(x, y, buttonWidth, buttonHeight);
        savePackage_BTN.setFont(panelFont);

        x += buttonWidth + buttonSpace;

        managePackage_BTN.setBounds(x,y,buttonWidth,buttonHeight);
        managePackage_BTN.setFont(panelFont);
        managePackage_BTN.setEnabled(false);

        x += buttonWidth + buttonSpace;


        closePackagePanel_BTN.setBounds(x, y, buttonWidth, buttonHeight);
        closePackagePanel_BTN.setFont(panelFont);

        clearPackage_BTN.setBounds(290, 43, 60, 14);
        clearPackage_BTN.setFont(smallFont);

        // ***************************************************************************************
        // Adding components to TV Channel Panel
        // ***************************************************************************************

        add(name_LBL);
        add(packages_TXT);
        add(packages_LBL);
        add(packagesTableScroll);

        add(savePackage_BTN);
        add(managePackage_BTN);
        add(closePackagePanel_BTN);
        add(clearPackage_BTN);

        //add(packagesPanel);

        
        repaint();

        packages_TXT.requestFocus();

        // ***************************************************************************************
        // Button Actions
        // ***************************************************************************************

        savePackage_BTN.addActionListener(new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {

                // Add name to graphics packages

                // Test to make sure name is not empty
                if(packages_TXT.getText().length() == 0){
                    JOptionPane.showMessageDialog(null,"Package can not " +
                                    " be empty! Try again.","Error Adding Package",
                            JOptionPane.WARNING_MESSAGE);

                    packages_TXT.setBackground(Color.YELLOW);
                    packages_TXT.requestFocus();
                    return;
                } else {
                    packages_TXT.setBackground(Color.WHITE);
                }

                // Test to make sure name is not in list
                if(GraphicsPackages.findID(packages_TXT.getText()) != -1){
                    JOptionPane.showMessageDialog(null,"Graphics Package -  " +
                                    packages_TXT.getText() + " is in list! Can not be Added!","Error Adding Category",
                            JOptionPane.WARNING_MESSAGE);

                    packages_TXT.setBackground(Color.YELLOW);
                    packages_TXT.requestFocus();
                    return;
                } else {
                    packages_TXT.setBackground(Color.WHITE);
                }

                DefaultTableModel model = (DefaultTableModel) packagesTable.getModel();

                if(packageEdit){

                    // Update element
                    if(!GraphicsPackages.updatePackageName(packageEdit_ID, packages_TXT.getText())){
                        JOptionPane.showMessageDialog(null,"Error -  " +
                                        packages_TXT.getText() + " was not able to be Added!",
                                "Error Adding Category",
                                JOptionPane.WARNING_MESSAGE);
                    }

                    // Find table row to update
                    for(int i = 0; i < model.getRowCount(); i++){
                        if(model.getValueAt(i,0).equals(packageEdit_ID)){
                            model.setValueAt(packages_TXT.getText(), i, 1);
                        }
                    }

                    packageEdit_ID = -1;
                    packageEdit = false;


                } else {

                    // Add to Package
                    new GraphicsPackages(packages_TXT.getText());

                    //DefaultTableModel model = (DefaultTableModel) packagesTable.getModel();
                    model.addRow(new Object[]{GraphicsPackages.getLastAddedID(), packages_TXT.getText()});
                    packages_TXT.setText("");
                    packages_TXT.requestFocus();
                }

            }
        });

        managePackage_BTN.addActionListener(new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                removeAll();
                setVisible(false);
                repaint();

               Main.packageManagerPanel.displayPackageManager(packageEdit_ID, null, false);

            }
        });

        closePackagePanel_BTN.addActionListener(new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {

                removeAll();
                setVisible(false);
                repaint();
            }
        });

        ListSelectionModel selectionModel = packagesTable.getSelectionModel();
        selectionModel.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        selectionModel.addListSelectionListener(e -> {

            if(packagesTable.getSelectedRow() != -1 ){

                // Get Package ID
                packageEdit_ID = (Integer) packagesTable.getValueAt(packagesTable.getSelectedRow(), 0);
                System.out.println("Get Record: " +  packageEdit_ID);
                GraphicsPackages record = GraphicsPackages.getPackage(packageEdit_ID);
                packages_TXT.setText(record.getName());
                packages_TXT.setBackground(Color.WHITE);
                packages_TXT.requestFocus();

                packageEdit = true;
                managePackage_BTN.setEnabled(true);

            }
        });

    }



}
