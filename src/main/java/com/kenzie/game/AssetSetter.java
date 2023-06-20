package com.kenzie.game;

import com.kenzie.game.data.Progress;

import com.kenzie.game.tile_interactive.IT_DestructibleWall;
import com.kenzie.game.tile_interactive.IT_DryTree;
import com.kenzie.game.tile_interactive.IT_MetalPlate;

public class AssetSetter {

    GamePanel gp;

    public AssetSetter(GamePanel gp) {
        this.gp = gp;
    }

    public void setObject(){


        addObjects("Bronze Coin", 0, 25, 23, null);
        addObjects("Lantern", 0, 18, 20, null);
        addObjects("Tent", 0, 19, 20, null);
        addObjects("Woodcutter's Axe", 0, 26, 21, null);
        addObjects("Blue Shield", 0, 35, 21, null);

        for(int i = 9; i < 13; i++) {
            addObjects("Red Potion", 1, 10, 1, null);
        }

        addObjects("Door", 0, 14, 28, null);
        addObjects("Chest", 0, 14, 21, "Tent");
        addObjects("Chest", 2, 40, 41, "Pickaxe");
        addObjects("Chest", 2, 13, 16, "Red Potion");
        addObjects("Chest", 2, 26, 34, "Red Potion");
        addObjects("Chest", 2, 27, 15, "Red Potion");
        addObjects("Iron Door", 2, 18, 23, null);
        addObjects("Iron Door", 3, 25, 15, null);
        addObjects("Blue Heart", 3, 25, 8, null);

    }

    public void setNPC(){

        addNPC("Old Man", 0, 25, 26);
        //addNPC("Old Man", 0, 17, 21);

        addNPC("Merchant", 1, 12, 7);
        addNPC("Old Man", 1, 10, 11);

        addNPC("Big Rock", 2, 20, 25);
        addNPC("Big Rock", 2, 11, 18);
        addNPC("Big Rock", 2, 11, 10);

    }

    public void setMonster(){

        int x = 0;
        int y = 0;

        // Random put them in screen each time you play
        for(int i = 0; i < 3; i++){

            x = gp.ut.getRandomBetween(20, 25);
            y = gp.ut.getRandomBetween(36, 42);
            addMonster("Green Slime",0, x, y);

        }

        x = gp.ut.getRandomBetween(10, 15);
        y = gp.ut.getRandomBetween(29, 34);
        addMonster("Orc",0, x, y);

        addMonster("Bat",2, 34, 39);
        addMonster("Bat",2, 34, 25);
        addMonster("Bat",2, 39, 26);
        addMonster("Bat",2, 28, 28);
        addMonster("Bat",2, 28, 11);
        addMonster("Bat",2, 10, 19);


        if(!Progress.skeletionLordDefated) {
            addMonster("Skeleton Lord",3, 23, 16);
        }
    }

    public void setInteractiveTile(){
        int mapNum = 0;
        int i = 0;
        gp.iTile[mapNum][i] = new IT_DryTree(gp, 27, 12); i++;
        gp.iTile[mapNum][i] = new IT_DryTree(gp, 28, 12); i++;
        gp.iTile[mapNum][i] = new IT_DryTree(gp, 29, 12); i++;
        gp.iTile[mapNum][i] = new IT_DryTree(gp, 30, 12); i++;
        gp.iTile[mapNum][i] = new IT_DryTree(gp, 31, 12); i++;
        gp.iTile[mapNum][i] = new IT_DryTree(gp, 32, 12); i++;
        gp.iTile[mapNum][i] = new IT_DryTree(gp,  33, 12); i++;

        gp.iTile[mapNum][i] = new IT_DryTree(gp,  31, 21); i++;
        gp.iTile[mapNum][i] = new IT_DryTree(gp,  18, 40); i++;
        gp.iTile[mapNum][i] = new IT_DryTree(gp,  17, 40); i++;
        gp.iTile[mapNum][i] = new IT_DryTree(gp,  16, 40); i++;
        gp.iTile[mapNum][i] = new IT_DryTree(gp,  15, 40); i++;
        gp.iTile[mapNum][i] = new IT_DryTree(gp,  14, 40); i++;
        gp.iTile[mapNum][i] = new IT_DryTree(gp,  13, 40); i++;
        gp.iTile[mapNum][i] = new IT_DryTree(gp,  13, 41); i++;
        gp.iTile[mapNum][i] = new IT_DryTree(gp,  12, 41); i++;
        gp.iTile[mapNum][i] = new IT_DryTree(gp,  11, 41); i++;
        gp.iTile[mapNum][i] = new IT_DryTree(gp,  10, 41); i++;
        gp.iTile[mapNum][i] = new IT_DryTree(gp,  10, 40); i++;

        mapNum = 2;
        i = 0;

        gp.iTile[mapNum][i] = new IT_DestructibleWall(gp,  18, 30); i++;
        gp.iTile[mapNum][i] = new IT_DestructibleWall(gp,  17, 31); i++;
        gp.iTile[mapNum][i] = new IT_DestructibleWall(gp,  17, 32); i++;
        gp.iTile[mapNum][i] = new IT_DestructibleWall(gp,  17, 34); i++;
        gp.iTile[mapNum][i] = new IT_DestructibleWall(gp,  18, 34); i++;
        gp.iTile[mapNum][i] = new IT_DestructibleWall(gp,  18, 33); i++;
        gp.iTile[mapNum][i] = new IT_DestructibleWall(gp,  10, 22); i++;
        gp.iTile[mapNum][i] = new IT_DestructibleWall(gp,  10, 24); i++;
        gp.iTile[mapNum][i] = new IT_DestructibleWall(gp,  38, 18); i++;
        gp.iTile[mapNum][i] = new IT_DestructibleWall(gp,  38, 19); i++;
        gp.iTile[mapNum][i] = new IT_DestructibleWall(gp,  38, 20); i++;
        gp.iTile[mapNum][i] = new IT_DestructibleWall(gp,  38, 21); i++;
        gp.iTile[mapNum][i] = new IT_DestructibleWall(gp,  18, 13); i++;
        gp.iTile[mapNum][i] = new IT_DestructibleWall(gp,  18, 14); i++;
        gp.iTile[mapNum][i] = new IT_DestructibleWall(gp,  22, 28); i++;
        gp.iTile[mapNum][i] = new IT_DestructibleWall(gp,  30, 28); i++;
        gp.iTile[mapNum][i] = new IT_DestructibleWall(gp,  32, 28); i++;

        gp.iTile[mapNum][i] = new IT_MetalPlate(gp,  20, 22); i++;
        gp.iTile[mapNum][i] = new IT_MetalPlate(gp,  8, 17); i++;
        gp.iTile[mapNum][i] = new IT_MetalPlate(gp,  39, 31); i++;


    }

    public void addObjects(String name, int mapNum, int x, int y, String  loot){

        System.out.println(name);
        int i = GamePanel.obj_List_Counter;
        gp.obj[mapNum][i] = gp.eGenerator.getObject(name);
        gp.obj[mapNum][i].worldX = gp.tileSize * x;
        gp.obj[mapNum][i].worldY = gp.tileSize * y;

        if(loot != null){
            gp.obj[mapNum][i].setLoot(gp.eGenerator.getObject(loot));
        }
        GamePanel.obj_List_Counter++;

    }

    public void addNPC(String name, int mapNum, int x, int y){

        int i = GamePanel.npc_List_Counter;
        gp.npc[mapNum][i] = gp.eGenerator.getNPC(name);
        gp.npc[mapNum][i].worldX = gp.tileSize * x;
        gp.npc[mapNum][i].worldY = gp.tileSize * y;

        GamePanel.npc_List_Counter++;
    }

    public void addMonster(String name, int mapNum, int x, int y){

        int i = GamePanel.monster_List_Counter;
        gp.monster[mapNum][i] = gp.eGenerator.getMonster(name);
        gp.monster[mapNum][i].worldX = gp.tileSize * x;
        gp.monster[mapNum][i].worldY = gp.tileSize * y;

        GamePanel.monster_List_Counter++;
    }
}
