package com.kenzie.game.object;

import com.kenzie.game.GamePanel;
import com.kenzie.game.entity.Entity;

public class OBJ_BlueHeart extends Entity {

    GamePanel gp;
    public static final String OBJ_NAME = "Blue Heart";

    public OBJ_BlueHeart(GamePanel gp) {
        super(gp);
        this.gp = gp;

        name = OBJ_NAME;
        type = TYPE_PICKUP_ONLY;
        down1 = setup("/objects/blueheart", gp.tileSize, gp.tileSize);
        setDialogues();

    }

    public void setDialogues(){

        dialogues[0][0] = "You pick up a beautiful blue gem.";
        dialogues[0][1] = "You find the Blue Heart, the legendary treasure!";
    }

    public boolean use(Entity entity){

        gp.gameState = gp.CUT_SCENE_STATE;
        gp.csManager.sceneNum = gp.csManager.ENDING;
        return true;
    }
}
