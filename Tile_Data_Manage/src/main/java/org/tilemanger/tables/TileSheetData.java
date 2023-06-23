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
    public ArrayList<TileData> tileData = new ArrayList<>();

    private static ArrayList<TileSheetData> tileSheetDataList = new ArrayList<>();
    private static final String[] COL_HEADERS = {"ID", "Active", "Image"};

    private static int nextTileSheetID = 0;
    private static int nextTileDataID = 0;

    public static String saveFilePath = Reference.FILEPATH + "/" + Reference.TILE_SHEET_DATA_FILE_NAME;

    public TileSheetData(int packageId, String tileSheetName, String path, boolean active) {

        readDataFromDisk();

        this.tileSheet_ID = nextTileSheetID;
        this.tileSheetName = tileSheetName;
        this.packageID = packageId;
        this.path = path;
        this.active = active;
        nextTileSheetID++;

        writeDataToDisk();

    }

    public void create_TileData(){

        // make tile data

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
}
