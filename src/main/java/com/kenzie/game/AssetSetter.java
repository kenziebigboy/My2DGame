package com.kenzie.game;

import com.kenzie.game.entity.NPC_Merchant;
import com.kenzie.game.entity.NPC_OldMan;
import com.kenzie.game.monster.MON_GreenSlime;
import com.kenzie.game.object.*;
import com.kenzie.game.tile_interactive.IT_DryTree;

public class AssetSetter {

    GamePanel gp;

    public AssetSetter(GamePanel gp) {
        this.gp = gp;
    }

    public void setObject(){

        int mapNum = 0;
        int i = 0;
        gp.obj[mapNum][i] = new OBJ_Coin_Bronze(gp);
        gp.obj[mapNum][i].worldX = gp.tileSize * 25;
        gp.obj[mapNum][i].worldY = gp.tileSize * 23;

        i++;
        gp.obj[mapNum][i] = new OBJ_Coin_Bronze(gp);
        gp.obj[mapNum][i].worldX = gp.tileSize * 21;
        gp.obj[mapNum][i].worldY = gp.tileSize * 19;

        i++;
        gp.obj[mapNum][i] = new OBJ_Axe(gp);
        gp.obj[mapNum][i].worldX = gp.tileSize * 26;
        gp.obj[mapNum][i].worldY = gp.tileSize * 21;

        i++;
        gp.obj[mapNum][i] = new OBJ_Shield_Blue(gp);
        gp.obj[mapNum][i].worldX = gp.tileSize * 35;
        gp.obj[mapNum][i].worldY = gp.tileSize * 21;

        i++;
        gp.obj[mapNum][i] = new OBJ_Potion_Red(gp);
        gp.obj[mapNum][i].worldX = gp.tileSize * 36;
        gp.obj[mapNum][i].worldY = gp.tileSize * 21;

        i++;
        gp.obj[mapNum][i] = new OBJ_Door(gp);
        gp.obj[mapNum][i].worldX = gp.tileSize * 14;
        gp.obj[mapNum][i].worldY = gp.tileSize * 28;

        i++;
        gp.obj[mapNum][i] = new OBJ_Door(gp);
        gp.obj[mapNum][i].worldX = gp.tileSize * 12;
        gp.obj[mapNum][i].worldY = gp.tileSize * 12;

        i++;
        gp.obj[mapNum][i] = new OBJ_Chest(gp, new OBJ_Key(gp));
        gp.obj[mapNum][i].worldX = gp.tileSize * 12;
        gp.obj[mapNum][i].worldY = gp.tileSize * 8;
    }

    public void setNPC(){

        int mapNum = 0;
        int i = 0;

        gp.npc[mapNum][i] = new NPC_OldMan(gp);
        gp.npc[mapNum][i].worldX = gp.tileSize * 25;
        gp.npc[mapNum][i].worldY = gp.tileSize * 10;

        i++;
        gp.npc[mapNum][i] = new NPC_OldMan(gp);
        gp.npc[mapNum][i].worldX = gp.tileSize * 17;
        gp.npc[mapNum][i].worldY = gp.tileSize * 21;

        mapNum = 1;
        i = 0;
        gp.npc[mapNum][i] = new NPC_Merchant(gp);
        gp.npc[mapNum][i].worldX = gp.tileSize * 12;
        gp.npc[mapNum][i].worldY = gp.tileSize * 7;

    }

    public void setMonster(){

        int mapNum = 0;

        gp.monster[mapNum][0] = new MON_GreenSlime(gp);
        gp.monster[mapNum][0].worldX = gp.tileSize * 28;
        gp.monster[mapNum][0].worldY = gp.tileSize * 21;

        gp.monster[mapNum][1] = new MON_GreenSlime(gp);
        gp.monster[mapNum][1].worldX = gp.tileSize * 23;
        gp.monster[mapNum][1].worldY = gp.tileSize * 37;

        gp.monster[mapNum][2] = new MON_GreenSlime(gp);
        gp.monster[mapNum][2].worldX = gp.tileSize * 23;
        gp.monster[mapNum][2].worldY = gp.tileSize * 42;

        gp.monster[mapNum][3] = new MON_GreenSlime(gp);
        gp.monster[mapNum][3].worldX = gp.tileSize * 26;
        gp.monster[mapNum][3].worldY = gp.tileSize * 37;

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



    }
}
