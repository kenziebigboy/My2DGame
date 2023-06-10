package com.kenzie.game;

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

        int i = 0;
        gp.obj[i] = new OBJ_Coin_Bronze(gp);
        gp.obj[i].worldX = gp.tileSize * 25;
        gp.obj[i].worldY = gp.tileSize * 23;

        i++;
        gp.obj[i] = new OBJ_Coin_Bronze(gp);
        gp.obj[i].worldX = gp.tileSize * 21;
        gp.obj[i].worldY = gp.tileSize * 19;

        i++;
        gp.obj[i] = new OBJ_Axe(gp);
        gp.obj[i].worldX = gp.tileSize * 26;
        gp.obj[i].worldY = gp.tileSize * 21;

        i++;
        gp.obj[i] = new OBJ_Shield_Blue(gp);
        gp.obj[i].worldX = gp.tileSize * 35;
        gp.obj[i].worldY = gp.tileSize * 21;

        i++;
        gp.obj[i] = new OBJ_Potion_Red(gp);
        gp.obj[i].worldX = gp.tileSize * 36;
        gp.obj[i].worldY = gp.tileSize * 21;

        i++;
        gp.obj[i] = new OBJ_Heart(gp);
        gp.obj[i].worldX = gp.tileSize * 22;
        gp.obj[i].worldY = gp.tileSize * 29;

        i++;
        gp.obj[i] = new OBJ_ManaCrystal(gp);
        gp.obj[i].worldX = gp.tileSize * 22;
        gp.obj[i].worldY = gp.tileSize * 31;
    }

    public void setNPC(){

        gp.npc[0] = new NPC_OldMan(gp);
        gp.npc[0].worldX = gp.tileSize * 9;
        gp.npc[0].worldY = gp.tileSize * 10;

        gp.npc[1] = new NPC_OldMan(gp);
        gp.npc[1].worldX = gp.tileSize * 11;
        gp.npc[1].worldY = gp.tileSize * 21;

        gp.npc[2] = new NPC_OldMan(gp);
        gp.npc[2].worldX = gp.tileSize * 31;
        gp.npc[2].worldY = gp.tileSize * 21;

    }

    public void setMonster(){

        gp.monster[0] = new MON_GreenSlime(gp);
        gp.monster[0].worldX = gp.tileSize * 28;
        gp.monster[0].worldY = gp.tileSize * 21;

        gp.monster[1] = new MON_GreenSlime(gp);
        gp.monster[1].worldX = gp.tileSize * 23;
        gp.monster[1].worldY = gp.tileSize * 37;

        gp.monster[2] = new MON_GreenSlime(gp);
        gp.monster[2].worldX = gp.tileSize * 23;
        gp.monster[2].worldY = gp.tileSize * 42;

        gp.monster[3] = new MON_GreenSlime(gp);
        gp.monster[3].worldX = gp.tileSize * 26;
        gp.monster[3].worldY = gp.tileSize * 37;

    }

    public void setInteractiveTile(){

        int i = 0;
        gp.iTile[i] = new IT_DryTree(gp, 27, 12); i++;
        gp.iTile[i] = new IT_DryTree(gp, 28, 12); i++;
        gp.iTile[i] = new IT_DryTree(gp, 29, 12); i++;
        gp.iTile[i] = new IT_DryTree(gp, 30, 12); i++;
        gp.iTile[i] = new IT_DryTree(gp, 31, 12); i++;
        gp.iTile[i] = new IT_DryTree(gp, 32, 12); i++;
        gp.iTile[i] = new IT_DryTree(gp,  33, 12); i++;

        gp.iTile[i] = new IT_DryTree(gp,  30, 20); i++;
        gp.iTile[i] = new IT_DryTree(gp,  30, 21); i++;
        gp.iTile[i] = new IT_DryTree(gp,  30, 22); i++;
        gp.iTile[i] = new IT_DryTree(gp,  20, 23); i++;
        gp.iTile[i] = new IT_DryTree(gp,  20, 24); i++;
        gp.iTile[i] = new IT_DryTree(gp,  22, 25); i++;
        gp.iTile[i] = new IT_DryTree(gp,  23, 26); i++;


    }
}
