package org.tilemanger;

import java.awt.*;

public class Reference {

    public static final String FILEPATH                    = "src/main/resources/tiles/";
    public static final String GRAPHICS_PACKAGE_FILE_NAME = "graphics_packages.dat";
    public static final String TILE_SHEET_DATA_FILE_NAME = "tile_sheet_data.dat";
    public static final String TILE_DATA_FILE_NAME = "tile_data.dat";
    public static final String ICON_PATH                   = FILEPATH + "/graphics_packages/";

    public static Font borderFont = new Font("SansSerif", Font.BOLD,20);
    public static Color borderColor = Color.BLACK;

    public static Font panelFont = new Font("SansSerif", Font.PLAIN, 14);
    public static Font smallFont = new Font("SansSerif", Font.PLAIN,10);

    // Calculates the total width a row of buttons will take
    public static int totalButtonWidth(int numberButtons, int buttonWidth, int buttonSpace){

        if (numberButtons < 2){
            return 0;
        }

        int totalWidth = 0;
        for(int i = 0; i < numberButtons; i++){
            if(i == 0){
                totalWidth = buttonWidth;
            } else {
                totalWidth += buttonSpace + buttonWidth;
            }
        }

        return totalWidth;

    }
}
