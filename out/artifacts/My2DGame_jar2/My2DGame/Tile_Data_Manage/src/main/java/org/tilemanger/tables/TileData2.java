package org.tilemanger.tables;

import org.tilemanger.Reference;

import java.io.*;
import java.util.ArrayList;

public class TileData2 implements Serializable {

    public int tileSheet_ID;
    public int tile_ID;
    public int cellX;
    public int cellY;
    public int solidAll; // -1 = not solid, 0 = soild, 1 part soild
    public boolean solidTop;
    public boolean solidBottom;
    public boolean solidLeft;
    public boolean solidRight;

    private static ArrayList<TileData2> tileDataList = new ArrayList<>();

    public static String saveFilePath = Reference.TILE_DATA_FILE_NAME_TEMP;


    public TileData2(int tileSheet_ID, int tile_ID, int cellX, int cellY, int solidAll) {

        readDataFromDisk();

        this.tileSheet_ID = tileSheet_ID;
        this.tile_ID = tile_ID;
        this.cellX = cellX;
        this.cellY = cellY;
        this.solidAll = solidAll;

        tileDataList.add(this);

        writeDataToDisk();
    }

    // Get a all Tile Data
    public static ArrayList<TileData2> getTileDataList(){
        readDataFromDisk();
        return tileDataList;
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

            tileDataList = (ArrayList<TileData2>) readStream.readObject();

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

            writeStream.writeObject(tileDataList);

            writeStream.flush();
            writeStream.close();

        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    @Override
    public String toString() {
        return "TileData{" +
                "tileSheet_ID=" + tileSheet_ID +
                ", tile_ID=" + tile_ID +
                ", cellX=" + cellX +
                ", cellY=" + cellY +
                ", solidAll=" + solidAll +
                "}\n";
    }
}
