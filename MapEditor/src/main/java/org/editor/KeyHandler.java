package org.editor;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class KeyHandler
        implements KeyListener {
    Main main;

    public KeyHandler(Main main) {
        this.main = main;
    }


    public void keyTyped(KeyEvent e) {
    }


    public void keyPressed(KeyEvent e) {
        int code = e.getKeyCode();

        if (e.isShiftDown() && e.getKeyCode() == 87) {
            this.main.moveAll("up");
        } else if (e.isShiftDown() && e.getKeyCode() == 83) {
            this.main.moveAll("down");
        } else if (e.isShiftDown() && e.getKeyCode() == 65) {
            this.main.moveAll("left");
        } else if (e.isShiftDown() && e.getKeyCode() == 68) {
            this.main.moveAll("right");
        }
        if (e.isControlDown() && e.getKeyCode() == 83) {
            this.main.saveMap();
        } else if (code == 87) {
            this.main.moveUp();
        } else if (code == 83) {
            this.main.moveDown();
        } else if (code == 65) {
            this.main.moveLeft();
        } else if (code == 68) {
            this.main.moveRight();
        } else if (code == 71) {
            this.main.gridOnOff();
        } else if (e.getKeyCode() == 81) {
            this.main.zoomIn();
        } else if (e.getKeyCode() == 69) {
            this.main.zoomOut();
        }
    }

    public void keyReleased(KeyEvent e) {
    }
}


/* Location:              C:\Users\mcbig\Downloads\Simple2DTileEditor1.01(1)\!\com\simpleeditor\main\KeyHandler.class
 * Java compiler version: 12 (56.0)
 * JD-Core Version:       1.1.3
 */