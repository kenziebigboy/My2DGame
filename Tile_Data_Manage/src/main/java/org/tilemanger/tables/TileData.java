package org.tilemanger.tables;

import org.tilemanger.Reference;

import java.io.*;
import java.util.ArrayList;

public class TileData implements Serializable {

    public int tileSheet_ID;
    public int tile_ID;
    public int cellX;
    public int cellY;
    public int solidAll; // -1 = not solid, 0 = soild, 1 part soild
    public boolean solidTop;
    public boolean solidBottom;
    public boolean solidLeft;
    public boolean solidRight;

    private static ArrayList<TileData> tileDataList = new ArrayList<>();

    public static String saveFilePath = Reference.FILEPATH + "/" + Reference.TILE_DATA_FILE_NAME;


    public TileData(int tileSheet_ID, int tile_ID, int cellX, int cellY, int solidAll) {

        this.tileSheet_ID = tileSheet_ID;
        this.tile_ID = tile_ID;
        this.cellX = cellX;
        this.cellY = cellY;
        this.solidAll = solidAll;
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

            tileDataList = (ArrayList<TileData>) readStream.readObject();

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
}
