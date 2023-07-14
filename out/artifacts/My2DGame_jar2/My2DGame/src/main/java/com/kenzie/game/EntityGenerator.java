package com.kenzie.game;

import com.kenzie.game.entity.Entity;
import com.kenzie.game.object.*;
import com.kenzie.game.entity.*;
import com.kenzie.game.monster.*;

public class EntityGenerator {

    GamePanel gp;

    public EntityGenerator(GamePanel gp) {
        this.gp = gp;
    }

    public Entity getObject(String itemName){

        Entity obj = null;

        switch (itemName){
            case OBJ_Axe.OBJ_NAME -> obj = new OBJ_Axe(gp);
            case OBJ_Boots.OBJ_NAME -> obj = new OBJ_Boots(gp);
            case OBJ_Chest.OBJ_NAME -> obj = new OBJ_Chest(gp);
            case OBJ_Coin_Bronze.OBJ_NAME -> obj = new OBJ_Coin_Bronze(gp);
            case OBJ_Door_Iron.OBJ_NAME -> obj = new OBJ_Door_Iron(gp);
            case OBJ_Door.OBJ_NAME -> obj = new OBJ_Door(gp);
            case OBJ_Fireball.OBJ_NAME -> obj = new OBJ_Fireball(gp);
            case OBJ_Heart.OBJ_NAME -> obj = new OBJ_Heart(gp);
            case OBJ_Key.OBJ_NAME -> obj = new OBJ_Key(gp);
            case OBJ_Lantern.OBJ_NAME -> obj = new OBJ_Lantern(gp);
            case OBJ_ManaCrystal.OBJ_NAME -> obj = new OBJ_ManaCrystal(gp);
            case OBJ_Pickaxe.OBJ_NAME -> obj = new OBJ_Pickaxe(gp);
            case OBJ_Potion_Red.OBJ_NAME -> obj = new OBJ_Potion_Red(gp);
            case OBJ_Rock.OBJ_NAME -> obj = new OBJ_Rock(gp);
            case OBJ_Shield_Blue.OBJ_NAME -> obj = new OBJ_Shield_Blue(gp);
            case OBJ_Shield_Wood.OBJ_NAME -> obj = new OBJ_Shield_Wood(gp);
            case OBJ_Sword_Normal.OBJ_NAME -> obj = new OBJ_Sword_Normal(gp);
            case OBJ_Tent.OBJ_NAME -> obj = new OBJ_Tent(gp);
            case OBJ_BlueHeart.OBJ_NAME -> obj = new OBJ_BlueHeart(gp);



        }
        return  obj;
    }

    public Entity getNPC(String npc_Name){

        Entity npc = null;

        switch (npc_Name){
            case NPC_BigRock.NPC_NAME -> npc = new NPC_BigRock(gp);
            case NPC_OldMan.NPC_NAME -> npc = new NPC_OldMan(gp);
            case NPC_Merchant.NPC_NAME -> npc = new NPC_Merchant(gp);

        }

        return npc;
    }

    public Entity getMonster(String monster_name){

        Entity mon = null;

        switch (monster_name){
            case MON_Bat.MONSTER_NAME -> mon = new MON_Bat(gp);
            case MON_GreenSlime.MONSTER_NAME -> mon = new MON_GreenSlime(gp);
            case MON_Orc.MONSTER_NAME -> mon = new MON_Orc(gp);
            case MON_SkeletonLord.MONSTER_NAME -> mon = new MON_SkeletonLord(gp);
        }

        return mon;
    }
}
