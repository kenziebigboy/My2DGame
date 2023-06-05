package com.kenzie.game;

import com.kenzie.game.entity.NPC_OldMan;
import com.kenzie.game.monster.MON_GreenSlime;
import com.kenzie.game.object.OBJ_Boots;
import com.kenzie.game.object.OBJ_Chest;
import com.kenzie.game.object.OBJ_Door;
import com.kenzie.game.object.OBJ_Key;

public class AssetSetter {

    GamePanel gp;

    public AssetSetter(GamePanel gp) {
        this.gp = gp;
    }

    public void setObject(){

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
        gp.monster[0].worldX = gp.tileSize * 11;
        gp.monster[0].worldY = gp.tileSize * 10;

        gp.monster[1] = new MON_GreenSlime(gp);
        gp.monster[1].worldX = gp.tileSize * 11;
        gp.monster[1].worldY = gp.tileSize * 11;

    }
}
