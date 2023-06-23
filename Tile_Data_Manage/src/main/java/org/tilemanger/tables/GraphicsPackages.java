package org.tilemanger.tables;

import org.tilemanger.Reference;


import javax.swing.table.DefaultTableModel;
import java.io.*;
import java.util.ArrayList;

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
    public static boolean  addTileSheetData(int id, String fileName){

        readDataFromDisk();

        // Check to see if tile sheet is in list


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
        System.out.println(graphicsPackagesList.size());
        Object[][] rows = new Object[graphicsPackagesList.size()][COL_HEADERS.length];

        for(int i = 0; i < graphicsPackagesList.size(); i++){

            rows[i][0] = graphicsPackagesList.get(i).packageID;
            rows[i][1] = graphicsPackagesList.get(i).name;

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
}
