package org.tilemanger.tables;

import org.tilemanger.Main;
import org.tilemanger.Reference;


import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;
import java.net.URISyntaxException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Objects;

public class GraphicsPackages implements Serializable{

    private int packageID;
    private String name;
    private ArrayList<Integer> tileSheetIDList = new ArrayList<>();

    private static ArrayList<GraphicsPackages> graphicsPackagesList = new ArrayList<>();

    private static final String[] COL_HEADERS = {"ID", "Name"};
    private static int nextGraphicsPackageID = 0;

    static String path = Reference.FILEPATH + "/" + Reference.GRAPHICS_PACKAGE_FILE_NAME;

    @Serial
    private static final long serialVersionUID = 6529685098267757690L;

    public GraphicsPackages(String name) {

        readDataFromDisk();

        this.packageID = nextGraphicsPackageID;
        this.name = name;
        nextGraphicsPackageID++;
        graphicsPackagesList.add(this);
        writeDataToDisk();

    }

    // Get the Last ID Added
    public static int getLastAddedID(){
        return nextGraphicsPackageID -1;
    }

    // Reset TileSheetData = null
    public static void resetTileSheetData(int packageID){
        readDataFromDisk();

        graphicsPackagesList.get(packageID).tileSheetIDList = null;

        writeDataToDisk();

    }

    // Get the Package Name
    public String getName(){
        return name;
    }

    // Get the Package
    public static GraphicsPackages getPackage(int id){

        readDataFromDisk();

        int index = findElementIndex(id);

        if(index == -1){
            return null;
        }

        return graphicsPackagesList.get(index);
    }

    // Update Package Name
    public static boolean updatePackageName(int id, String name){

        readDataFromDisk();

        int index = findElementIndex(id);

        if(index == -1){
            return false;
        }

        graphicsPackagesList.get(index).name = name;

        writeDataToDisk();

        return true;
    }

    // Add TileSheet Data
    public static boolean  addTileSheetData(int id, int tileSheetDataID){

        readDataFromDisk();

        // Check to see if tile sheet is in list
        if(!checkForTileSheetData(id, tileSheetDataID)){
            graphicsPackagesList.get(id).tileSheetIDList.add(tileSheetDataID);

            writeDataToDisk();
            return true;
        }

        return false;
    }

    // Find ID by name --- used to see if name is in list
    public static int findID(String name){

        readDataFromDisk();

        for(int i = 0; i < graphicsPackagesList.size(); i++){

            if(graphicsPackagesList.get(i).name.equals(name)){
                return i;
            }
        }

        return -1;
    }

    // Get Table of all the Graphics Packages
    public static DefaultTableModel getTableModel(){

        return new DefaultTableModel(makeTableRows(), COL_HEADERS){
            @Override
            public Class getColumnClass(int column) {
                switch (column) {
                    case 0:
                        return Integer.class;
                    default:
                        return String.class;
                }
            }
        };

    }

    // Get Table of all the TileSheetData for a given Graphics Package
    public static DefaultTableModel getTableModelforTileSheetData(int packageID) throws IOException {

        return new DefaultTableModel(makeTableRowsForTileSheetData(packageID), COL_HEADERS){
            @Override
            public Class getColumnClass(int column) {
                switch (column) {
                    case 0:
                        return Integer.class;
                    case 1:
                        return Icon.class;
                    default:
                        return String.class;
                }
            }
        };

    }

    // Get all Graphics Packages
    public static ArrayList<GraphicsPackages> getGraphicsPackagesList(){
        readDataFromDisk();
        return graphicsPackagesList;
    }

    // Read data from disk
    private static void readDataFromDisk(){


        File file = new File(path);

        if (!file.exists()){

            return;
        }

        try{
            FileInputStream readData = new FileInputStream(path);
            ObjectInputStream readStream = new ObjectInputStream(readData);

            graphicsPackagesList = (ArrayList<GraphicsPackages>) readStream.readObject();
            nextGraphicsPackageID = (Integer) readStream.readObject();
            readStream.close();

        } catch (Exception e){
            e.printStackTrace();
        }

    }

    // Write data to disk
    private static void writeDataToDisk(){


        try{
            FileOutputStream writeData = new FileOutputStream(path);
            ObjectOutputStream writeStream = new ObjectOutputStream(writeData);

            System.out.println("Write: " + graphicsPackagesList.size());

            writeStream.writeObject(graphicsPackagesList);
            writeStream.writeObject(nextGraphicsPackageID);
            writeStream.flush();
            writeStream.close();

        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    // Make the table row data
    private static Object[][] makeTableRows(){

        graphicsPackagesList.clear();

        readDataFromDisk();

        Object[][] rows = new Object[graphicsPackagesList.size()][COL_HEADERS.length];

        for(int i = 0; i < graphicsPackagesList.size(); i++){

            rows[i][0] = graphicsPackagesList.get(i).packageID;
            rows[i][1] = graphicsPackagesList.get(i).name;

        }

        return rows;
    }

    private static Object[][] makeTableRowsForTileSheetData(int packageID) {

        graphicsPackagesList.clear();

        readDataFromDisk();

        GraphicsPackages graphicsPackage = graphicsPackagesList.get(packageID);

        Object[][] rows = new Object[graphicsPackage.tileSheetIDList.size()][COL_HEADERS.length];

        for(int i = 0; i < graphicsPackage.tileSheetIDList.size(); i++){

            int index = graphicsPackage.tileSheetIDList.get(i);

            TileSheetData tileSheetData = TileSheetData.getById(index);

            File directory = new File("./resources/graphics_packages/Abandoned Mines/tilesets/SI_Abandoned_Mines_A5.png");

            rows[i][0] = tileSheetData.tileSheet_ID;
            rows[i][1] = new ImageIcon( directory.toString());
//
        }

        return rows;
    }

    // Find package by id
    private static int findElementIndex(int id){

        for(int i = 0; i < graphicsPackagesList.size(); i++){
            System.out.println(graphicsPackagesList.get(i).packageID);
            if(graphicsPackagesList.get(i).packageID == id){
                return i;
            }
        }

        return -1;
    }
//
//    private static String[] getFileNameParts(String fileName){
//
//        // Find path and file name
//        int startCut = fileName.lastIndexOf(Reference.ICON_PATH);
//
//
//    }

    private static boolean checkForTileSheetData(int gpaphicsPackageID,  int tileSheetDataID){

        for(int i = 0; i < graphicsPackagesList.get(gpaphicsPackageID).tileSheetIDList.size(); i++){
            if(graphicsPackagesList.get(gpaphicsPackageID).tileSheetIDList.get(i) == tileSheetDataID){
                return true;
            }
        }

        return false;
    }

    @Override
    public String toString() {
        return "GraphicsPackages{" +
                "packageID=" + packageID +
                ", name='" + name + '\'' +
                ", tileSheetIDList=" + tileSheetIDList +
                "}\n";
    }


}
