package com.kenzie.game.data;

import java.io.Serializable;
import java.util.ArrayList;

public class DataStorage implements Serializable {

    int level;
    int maxLife;
    int live;
    int maxMana;
    int mana;
    int strenght;
    int dexterity;
    int exp;
    int nextLevelExp;
    int coin;

    // Player Inventory
    ArrayList<String> itemNames = new ArrayList<>();
    ArrayList<Integer> itemAmounts = new ArrayList<>();
    int currentWeaponSlot;
    int currentShieldSlot;

    // Object On Map
    String[][] mapObjectNames;
    int[][] mapObjectWorldX;
    int[][] mapObjectWorldY;
    String[][] mapObjectLootName;
    boolean[][] mapObjectOpened;

}
