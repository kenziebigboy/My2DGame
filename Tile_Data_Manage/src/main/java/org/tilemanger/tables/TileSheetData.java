package org.tilemanger.tables;

import org.tilemanger.Reference;
import javax.swing.table.DefaultTableModel;
import java.io.*;
import java.util.ArrayList;

public class TileSheetData implements Serializable{

    public int tileSheet_ID;
    public int packageID;
    public String tileSheetName;
    public String path;
    public int firstElement;
    public int lastElement;

    public boolean active;
    public ArrayList<TileData> tileDataList = new ArrayList<>();

    private static ArrayList<TileSheetData> tileSheetDataList = new ArrayList<>();
    private static final String[] COL_HEADERS = {"ID", "Active", "Image"};

    private static int nextTileSheetID = 0;
    private static int nextTileDataID = 0;

    public static String saveFilePath = Reference.TILE_SHEET_DATA_FILE_NAME;
    @Serial
    private static final long serialVersionUID = -2887215203747855321L;

    public TileSheetData(int packageId, String tileSheetName, String path, boolean active) {

        readDataFromDisk();

        this.tileSheet_ID = nextTileSheetID;
        this.tileSheetName = tileSheetName;
        this.packageID = packageId;
        this.path = path;
        this.active = active;

        tileSheetDataList.add(this);
        nextTileSheetID++;

        writeDataToDisk();

    }

    // Get the Last ID Added
    public static int getLastAddedID(){
        return nextTileSheetID -1;
    }

    public void add_TileData(ArrayList<Integer> tileDataID){

        //this.tileDataID = tileDataID;

        writeDataToDisk();

    }

    // Get the next TileDataId to start a new tile set
    public int getNextTileDataID(){
        return nextTileDataID;
    }

    // Update the first, last, and tileDataIDList tileData Elements
    public void updateTileDataElements(int tileSheet_ID, int firstElement, int lastElement, ArrayList<Integer> tileDataIDList){

        readDataFromDisk();

        tileSheetDataList.get(tileSheet_ID).firstElement = firstElement;
        tileSheetDataList.get(tileSheet_ID).lastElement = lastElement;
        //tileSheetDataList.get(tileSheet_ID).tileDataID = tileDataIDList;

        writeDataToDisk();

    }

    // Set the next TileDataId
    public static void setNextTileDataID(int setNextTileDataID){
        readDataFromDisk();

        nextTileDataID = setNextTileDataID;

        writeDataToDisk();
    }

    // Check to see if TileSheetData in list
    public static boolean checkInList(int graphicsPackageID, String tileSheetName){

        readDataFromDisk();

        if(findInList(graphicsPackageID, tileSheetName)){
            return true;
        }

        return false;
    }

    // Get Tile Sheet Data by id
    public static TileSheetData getById(int tileSheet_ID){
        readDataFromDisk();
        return tileSheetDataList.get(tileSheet_ID);
    }

    // Get all Tile Sheet Data
    public static ArrayList<TileSheetData> getTileSheetDataList(){
        readDataFromDisk();

        return tileSheetDataList;
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


        File file = new File(saveFilePath);

        if (!file.exists()){
            return;
        }

        try{
            FileInputStream readData = new FileInputStream(saveFilePath);
            ObjectInputStream readStream = new ObjectInputStream(readData);

            tileSheetDataList = (ArrayList<TileSheetData>) readStream.readObject();
            nextTileSheetID = (Integer) readStream.readObject();
            nextTileDataID = (Integer) readStream.readObject();
            readStream.close();

        } catch (Exception e){
            e.printStackTrace();
        }

    }

    // Write data to disk
    private static void writeDataToDisk(){

        try{
            FileOutputStream writeData = new FileOutputStream(saveFilePath);
            ObjectOutputStream writeStream = new ObjectOutputStream(writeData);

            writeStream.writeObject(tileSheetDataList);
            writeStream.writeObject(nextTileSheetID);
            writeStream.writeObject(nextTileDataID);
            writeStream.flush();
            writeStream.close();

        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    // Make the table row data
    private static Object[][] makeTableRows(){

        tileSheetDataList.clear();

        readDataFromDisk();

        Object[][] rows = new Object[tileSheetDataList.size()][COL_HEADERS.length];

        for(int i = 0; i < tileSheetDataList.size(); i++){

            rows[i][0] = tileSheetDataList.get(i).tileSheet_ID;
            //rows[i][1] = tileSheetDataList.get(i).;

        }

        return rows;
    }

    // Check to see if in list
    private static boolean findInList(int packageID, String tileSheetName){

        for(int i = 0; i < tileSheetDataList.size(); i++){
            if(tileSheetDataList.get(i).packageID == packageID && tileSheetDataList.get(i).tileSheetName.equals(tileSheetName)){
                return true;
            }
        }

        return false;
    }

    @Override
    public String toString() {
        return "TileSheetData{" +
                "tileSheet_ID: " + tileSheet_ID +
                ", packageID: " + packageID +
                ", tileSheetName: '" + tileSheetName + '\'' + "\n" +
                ", path: '" + path + '\'' +
                ", firstElement: " + firstElement +
                ", lastElement: " + lastElement +
                ", active: " + active +  "\n" +
                ", tileDataList: " + tileDataList +
                '}';
    }
}
