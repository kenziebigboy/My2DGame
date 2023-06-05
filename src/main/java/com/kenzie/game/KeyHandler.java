package com.kenzie.game;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class KeyHandler implements KeyListener {

    GamePanel gp;

    public boolean upPressed, downPressed, leftPressed, rightPressed, enterPressed;

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
        if(gp.gameState == gp.titleState){

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
                        case 0:
                            gp.gameState = gp.playState;
                            gp.playMusic(0);
                            //gp.ui.titleScreenState = 1;
                            break;
                        case 1:

                            break;
                        case 2:
                            System.exit(0);
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

        // Play State
        if(gp.gameState == gp.playState) {
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
                gp.gameState = gp.pauseState;
            }

            if (code == KeyEvent.VK_ENTER) {
                //gp.gameState = gp.pauseState;
                enterPressed = true;
            }

        } else if(gp.gameState == gp.pauseState){
            if (code == KeyEvent.VK_P) {

                gp.gameState = gp.playState;

            }
        } else if(gp.gameState == gp.dialoguState){
            if(code == KeyEvent.VK_ENTER){
                gp.gameState = gp.playState;
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
    }
}
