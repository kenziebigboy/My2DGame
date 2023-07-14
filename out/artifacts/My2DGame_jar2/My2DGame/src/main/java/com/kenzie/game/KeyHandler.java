package com.kenzie.game;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class KeyHandler implements KeyListener {

    GamePanel gp;
    public boolean upPressed, downPressed, leftPressed, rightPressed, enterPressed, shotKeyPressed, spacePressed;

    // Debug
    public boolean showDebugText = false;
    public boolean godModeOn = false;

    public KeyHandler(GamePanel gp) {
        this.gp = gp;
    }

    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {

        int code = e.getKeyCode();

        // Title State
        if(gp.gameState == gp.TITLE_STATE){
            titleState(code);
        } else if(gp.gameState == gp.PLAY_STATE) { // Play State
            playState(code);
        } else if(gp.gameState == gp.PAUSE_STATE){
            pauseState(code);
        } else if(gp.gameState == gp.DIALOGUE_STATE || gp.gameState ==
                gp.CUT_SCENE_STATE){
            dialogueState(code);
        } else if (gp.gameState == gp.CHARACTER_STATE) {
            characterState(code);
        } else if(gp.gameState == gp.OPTIONS_STATE){
            optionsState(code);
        } else if(gp.gameState == gp.GAME_OVER_STATE){
            gameOverState(code);
        } else if(gp.gameState == gp.TRADE_STATE){
            tradeState(code);
        } else if(gp.gameState == gp.MAP_STATE){
            mapState(code);
        }


    }

    public void titleState(int code){
        if(gp.ui.titleScreenState == 0) {

            if (code == KeyEvent.VK_W || code == KeyEvent.VK_UP) {
                gp.ui.commandNum--;
                if (gp.ui.commandNum < 0) {
                    gp.ui.commandNum = 2;
                }
            }

            if (code == KeyEvent.VK_S || code == KeyEvent.VK_DOWN) {
                gp.ui.commandNum++;
                if (gp.ui.commandNum > 2) {
                    gp.ui.commandNum = 0;
                }
            }

            if (code == KeyEvent.VK_ENTER) {

                switch (gp.ui.commandNum) {
                    case 0 -> {
                        gp.gameState = gp.PLAY_STATE;
                        gp.playMusic(0);
                    }
                    //gp.ui.titleScreenState = 1;
                    case 1 -> {
                        gp.saveLoad.load();
                        gp.gameState = gp.PLAY_STATE;
                        gp.playMusic(0);
                    }
                    case 2 -> System.exit(0);
                }
            }
        } else if(gp.ui.titleScreenState == 1){

            if (code == KeyEvent.VK_W || code == KeyEvent.VK_UP) {
                gp.ui.commandNum--;
                if (gp.ui.commandNum < 0) {
                    gp.ui.commandNum = 3;
                }
            }

            if (code == KeyEvent.VK_S || code == KeyEvent.VK_DOWN) {
                gp.ui.commandNum++;
                if (gp.ui.commandNum > 3) {
                    gp.ui.commandNum = 0;
                }
            }

            if (code == KeyEvent.VK_ENTER) {

                switch (gp.ui.commandNum) {
                    case 0:
                        System.out.println("Do some fighter specific stuff");
                        break;
                    case 1:
                        System.out.println("Do some thief specific stuff");
                        break;
                    case 2:
                        System.out.println("Do some sorcerer specific stuff");
                        break;
                    case 3:
                        gp.ui.titleScreenState = 0;

                }
            }
        }
    }

    public void playState(int code){
        if (code == KeyEvent.VK_W || code == KeyEvent.VK_UP) {
            upPressed = true;
        }

        if (code == KeyEvent.VK_S || code == KeyEvent.VK_DOWN) {
            downPressed = true;
        }

        if (code == KeyEvent.VK_A || code == KeyEvent.VK_LEFT) {
            leftPressed = true;
        }

        if (code == KeyEvent.VK_D || code == KeyEvent.VK_RIGHT) {
            rightPressed = true;
        }

        if (code == KeyEvent.VK_P) {
            gp.gameState = gp.PAUSE_STATE;
        }

        if(code == KeyEvent.VK_C){
            gp.gameState = gp.CHARACTER_STATE;
        }

        if (code == KeyEvent.VK_ENTER) {
            //gp.gameState = gp.pauseState;
            enterPressed = true;
        }

        if(code == KeyEvent.VK_F){
            shotKeyPressed = true;
        }

        if(code == KeyEvent.VK_ESCAPE){
            gp.gameState = gp.OPTIONS_STATE;
        }

        if(code == KeyEvent.VK_M){
            gp.gameState = gp.MAP_STATE;
        }

        if(code == KeyEvent.VK_X){
            gp.map.miniMapOn = !gp.map.miniMapOn;
        }

        if(code == KeyEvent.VK_SPACE) {
            spacePressed = true;
        }

        if(code == KeyEvent.VK_G){
            godModeOn = !godModeOn;
        }

        if(code == KeyEvent.VK_T){
            showDebugText = !showDebugText;
        }
    }

    public void pauseState(int code){
        if (code == KeyEvent.VK_P) {

            gp.gameState = gp.PLAY_STATE;

        }
    }

    public void dialogueState(int code){
        if(code == KeyEvent.VK_ENTER){
            enterPressed = true;
        }
    }

    public void characterState(int code){
        if(code == KeyEvent.VK_C){
            gp.gameState = gp.PLAY_STATE;
        }

        if(code == KeyEvent.VK_ENTER){
            gp.player.selectItem();
        }

        playerInventory(code);
    }

    public void optionsState(int code){

        if(code == KeyEvent.VK_ESCAPE){
            gp.gameState = gp.PLAY_STATE;
        }

        if(code == KeyEvent.VK_ENTER){
            enterPressed = true;
        }

        int maxCommandNum = 0;
        switch (gp.ui.subState){
            case 0: maxCommandNum = 5; break;
            case 3: maxCommandNum = 1;
        }

        if(code == KeyEvent.VK_W){
            gp.ui.commandNum--;
            gp.playSE(9);
            if(gp.ui.commandNum < 0){
                gp.ui.commandNum = maxCommandNum;
            }
        }

        if(code == KeyEvent.VK_S){
            gp.ui.commandNum++;
            gp.playSE(9);
            if(gp.ui.commandNum > maxCommandNum){
                gp.ui.commandNum = 0;
            }
        }

        if(code == KeyEvent.VK_A){
            if(gp.ui.subState == 0) {
                if (gp.ui.commandNum == 1 && gp.music.volumeScale > 0) {
                    gp.music.volumeScale--;
                    gp.music.checkVolume();
                    gp.playSE(9);
                }

                if (gp.ui.commandNum == 2 && gp.se.volumeScale > 0) {
                    gp.se.volumeScale--;

                    gp.playSE(9);
                }
            }
        }

        if(code == KeyEvent.VK_D){
            if(gp.ui.subState == 0) {
                if (gp.ui.commandNum == 1 && gp.music.volumeScale < 5) {
                    gp.music.volumeScale++;
                    gp.music.checkVolume();
                    gp.playSE(9);
                }

                if (gp.ui.commandNum == 2 && gp.se.volumeScale < 5) {
                    gp.se.volumeScale++;

                    gp.playSE(9);
                }
            }
        }

    }

    public void gameOverState(int code){

        if(code == KeyEvent.VK_W){
            gp.ui.commandNum--;
            gp.playSE(9);
            if(gp.ui.commandNum < 0){
                gp.ui.commandNum = 1;
            }
        }

        if(code == KeyEvent.VK_S){
            gp.ui.commandNum++;
            gp.playSE(9);
            if(gp.ui.commandNum > 1){
                gp.ui.commandNum = 0;
            }
        }

        if(code == KeyEvent.VK_ENTER){
            if(gp.ui.commandNum == 0){
                gp.gameState = gp.PLAY_STATE;
                gp.resetGame(false);
                gp.playSE(0);
            } else {
                gp.gameState = gp.TITLE_STATE;
                gp.resetGame(true);
            }
        }
    }

    public void tradeState(int code){

        if(code == KeyEvent.VK_ENTER){
            enterPressed = true;

        }

        if(gp.ui.subState == 0){

            if(code  == KeyEvent.VK_W){
                gp.ui.commandNum--;
                gp.playSE(9);
                if(gp.ui.commandNum < 0){
                    gp.ui.commandNum = 2;
                }
            }

            if(code == KeyEvent.VK_S){
                gp.ui.commandNum++;
                gp.playSE(9);
                if(gp.ui.commandNum > 2){
                    gp.ui.commandNum = 0;
                }
            }
        }

        if(gp.ui.subState == 1){
            npcInventory(code);
            if(code == KeyEvent.VK_ESCAPE){
                gp.ui.subState = 0;
            }
        }

        if(gp.ui.subState == 2){
            playerInventory(code);
            if(code == KeyEvent.VK_ESCAPE){
                gp.ui.subState = 0;
            }
        }

    }

    public void mapState(int code){

        if(code == KeyEvent.VK_M){
            gp.gameState = gp.PLAY_STATE;
        }
    }

    public void playerInventory(int code){

        if(code == KeyEvent.VK_W){
            if(gp.ui.playerSlotRow != 0) {
                gp.ui.playerSlotRow--;
                gp.playSE(9);
            }
        }

        if(code == KeyEvent.VK_A){
            if(gp.ui.playerSlotCol != 0) {
                gp.ui.playerSlotCol--;
                gp.playSE(9);
            }
        }

        if(code == KeyEvent.VK_S){
            if(gp.ui.playerSlotRow != 3) {
                gp.ui.playerSlotRow++;
                gp.playSE(9);
            }
        }

        if(code == KeyEvent.VK_D){
            if(gp.ui.playerSlotCol != 4) {
                gp.ui.playerSlotCol++;
                gp.playSE(9);
            }
        }

    }

    public void npcInventory(int code){

        if(code == KeyEvent.VK_W){
            if(gp.ui.npcSlotRow != 0) {
                gp.ui.npcSlotRow--;
                gp.playSE(9);
            }
        }

        if(code == KeyEvent.VK_A){
            if(gp.ui.npcSlotCol != 0) {
                gp.ui.npcSlotCol--;
                gp.playSE(9);
            }
        }

        if(code == KeyEvent.VK_S){
            if(gp.ui.npcSlotRow != 3) {
                gp.ui.npcSlotRow++;
                gp.playSE(9);
            }
        }

        if(code == KeyEvent.VK_D){
            if(gp.ui.npcSlotCol != 4) {
                gp.ui.npcSlotCol++;
                gp.playSE(9);
            }
        }

    }

    @Override
    public void keyReleased(KeyEvent e) {

        int code = e.getKeyCode();

        if(code == KeyEvent.VK_W || code == KeyEvent.VK_UP){
            upPressed = false;
        }

        if(code == KeyEvent.VK_S || code == KeyEvent.VK_DOWN){
            downPressed = false;
        }

        if(code == KeyEvent.VK_A || code == KeyEvent.VK_LEFT) {
            leftPressed = false;
        }

        if(code == KeyEvent.VK_D || code == KeyEvent.VK_RIGHT){
            rightPressed = false;
        }

        if(code == KeyEvent.VK_F){
            shotKeyPressed = false;
        }

        if(code == KeyEvent.VK_ENTER){
            enterPressed = false;
        }

        if(code == KeyEvent.VK_SPACE){
            spacePressed = false;
        }

    }
}
