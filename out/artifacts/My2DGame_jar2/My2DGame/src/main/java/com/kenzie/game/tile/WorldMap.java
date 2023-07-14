package com.kenzie.game.tile;

import java.io.*;
import java.util.ArrayList;

public class WorldMap {

    public int mapID;
    public String name;
    public int maxCol;
    public int maxRow;
    public ArrayList<Integer> tileDataSheetsID;
    public MapData[][] mapData;

    private static ArrayList<WorldMap> worldMapArrayList = new ArrayList<>();
    public static String filePath = "./resources/data_files/world_map.dat";

    private static int nextWorldMapID = 0;


    @Serial
    private static final long serialVersionUID = -2887215203747855321L;


    // Get WorldMap by id
    public static WorldMap getWorldMapById(int mapID){
        readDataFromDisk();

        return worldMapArrayList.get(mapID);
    }

    // Read data from disk
    private static void readDataFromDisk(){


        File file = new File(filePath);

        if (!file.exists()){
            return;
        }

        try{
            FileInputStream readData = new FileInputStream(filePath);
            ObjectInputStream readStream = new ObjectInputStream(readData);

            worldMapArrayList = (ArrayList<WorldMap>) readStream.readObject();
            nextWorldMapID = (Integer) readStream.readObject();

            readStream.close();

        } catch (Exception e){
            e.printStackTrace();
        }

    }

    // Write data to disk
    private static void writeDataToDisk(){

        try{
            FileOutputStream writeData = new FileOutputStream(filePath);
            ObjectOutputStream writeStream = new ObjectOutputStream(writeData);

            writeStream.writeObject(worldMapArrayList);
            writeStream.writeObject(nextWorldMapID);

            writeStream.flush();
            writeStream.close();

        } catch (IOException e) {
            e.printStackTrace();
        }

    }





}
