package com.kenzie.game;

import com.kenzie.game.ai.PathFinder;
import com.kenzie.game.data.SaveLoad;
import com.kenzie.game.entity.Entity;
import com.kenzie.game.entity.Player;
import com.kenzie.game.environment.EnvironmentManager;
import com.kenzie.game.tile.Map;
import com.kenzie.game.tile.TileManger;
import com.kenzie.game.tile_interactive.InteractiveTile;

import javax.swing.*;
import java.awt.*;

import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class GamePanel extends JPanel implements Runnable{

    // screen settings
    final int originalTileSize = 16; // 16x16 tile
    final int  scale = 3;

    public final int tileSize = originalTileSize * scale; // 48 x 48 tile
    public final int maxScreenCol = 20;
    public final int maxScreenRow = 12;
    public final int screenWidth = tileSize * maxScreenCol;  // 760 pixels
    public final int screenHeight = tileSize * maxScreenRow; // 576 pixels

    // world settings
    public int maxWorldCol;
    public int maxWorldRow ;
    public final int maxMap = 10;
    public int currentMap = 0;

    // For Full Screen
    int screenWidth2 = screenWidth;
    int screenHeight2 = screenHeight;
    BufferedImage tempScreen;
    Graphics2D g2;
    public boolean fullScreenOn = false;


    // FPS
    int FPS = 60;

    // System
    public TileManger tileM = new TileManger(this);
    public KeyHandler keyH = new KeyHandler(this);
    Sound music = new Sound();
    Sound se = new Sound();

    public CollisionChecker cChecker = new CollisionChecker(this);
    public AssetSetter aSetter = new AssetSetter(this);
    public UI ui = new UI(this);
    public EventHandler eHandler = new EventHandler(this);
    Config config = new Config(this);
    public PathFinder pFinder = new PathFinder(this);
    EnvironmentManager eManager = new EnvironmentManager(this);
    Map map = new Map(this);
    SaveLoad saveLoad = new SaveLoad(this);
    public EntityGenerator eGenerator = new EntityGenerator(this);
    public CutsceneManager csManager = new CutsceneManager(this);
    public UtilityTool ut = new UtilityTool();
    Thread gameThread;

    // Entity & Objects
    public Player player = new Player(this, keyH);
    public Entity[][] obj =  new Entity[maxMap][20];
    public static int obj_List_Counter = 0;
    public Entity[][] npc = new Entity[maxMap][10];
    public static int npc_List_Counter = 0;
    public Entity[][] monster = new Entity[maxMap][20];
    public static int monster_List_Counter = 0;
    public InteractiveTile[][] iTile = new InteractiveTile[maxMap][50];
    public Entity[][] projectile = new Entity[maxMap][20];
    //public ArrayList<Entity> projectileList = new ArrayList<>();
    public ArrayList<Entity> particleList = new ArrayList<>();
    ArrayList<Entity> entityList = new ArrayList<>();

    // Game State
    public int gameState;
    public final int TITLE_STATE = 0;
    public final int PLAY_STATE = 1;
    public final int PAUSE_STATE = 2;
    public final int DIALOGUE_STATE = 3;
    public final int CHARACTER_STATE = 4;
    public final int OPTIONS_STATE = 5;
    public final int GAME_OVER_STATE = 6;
    public final int TRANSITION_STATE = 7;
    public final int TRADE_STATE = 8;
    public final int SLEEP_STATE = 9;
    public final int MAP_STATE = 10;
    public final int CUT_SCENE_STATE = 11;

    // Others
    public  boolean bossBattleOn = false;

    // Area
    public int currentArea;
    public int nextArea;
    public final int OUTSIDE = 50;
    public final int INDOOR = 51;
    public final int DUNGEON = 52;

    public GamePanel(){

        this.setPreferredSize(new Dimension(screenWidth,screenHeight));
        this.setBackground(Color.BLACK);
        this.setDoubleBuffered(true);
        this.addKeyListener(keyH);
        this.setFocusable(true);

    }

    public void setupGame(){

        aSetter.setObject();
        aSetter.setNPC();
        aSetter.setMonster();
        aSetter.setInteractiveTile();
        eManager.setup();

        playMusic(0);
        stopMusic();
        gameState = TITLE_STATE;
        currentArea = OUTSIDE;

        //tempScreen = new BufferedImage(screenWidth, screenHeight, BufferedImage.TYPE_INT_ARGB);
        //g2 = (Graphics2D) tempScreen.getGraphics();

//        if(fullScreenOn) {
//            setFullScreen();
//        }
    }

    public void resetGame(boolean restart){

        stopMusic();
        currentArea = OUTSIDE;
        removeTempEntity();
        bossBattleOn = false;
        player.setDefaultPositions();
        player.restoreStatus();
        player.resetCounter();
        aSetter.setNPC();
        aSetter.setMonster();

        obj_List_Counter = 0;
        npc_List_Counter = 0;
        monster_List_Counter = 0;

        if(restart) {
            player.setDefaultValues();
            aSetter.setObject();
            aSetter.setInteractiveTile();
            eManager.lighting.resetDay();
        }

    }

    public void setFullScreen(){

        // Get Local Screen Device
        GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
        GraphicsDevice gd = ge.getDefaultScreenDevice();
        gd.setFullScreenWindow(Main.window);

        // Get Full Screen Width & Height
        screenWidth2 = Main.window.getWidth();
        screenHeight2 = Main.window.getHeight();
    }

    public void startGameThread(){
        gameThread = new Thread(this);
        gameThread.start();
    }

    @Override
    public void  run(){
        double drawInterval = 1000000000/FPS;
        double delta = 0;
        long lastTime = System.nanoTime();
        long currentTime;
        long timer = 0;
        int drawCount = 0;

        while (gameThread != null) {

            currentTime = System.nanoTime();
            delta += (currentTime - lastTime) / drawInterval;
            timer += (currentTime - lastTime);
            lastTime = currentTime;

            if(delta >= 1){
                update();

                repaint();
                //drawToTempScreen(); // draw everything to buffered image
                //drawToScreen(); // draw the buffered image to the screen
                delta--;
                drawCount++;
            }

            if(timer >= 1000000000){
                System.out.println("FPS: " + drawCount);
                drawCount = 0;
                timer = 0;
            }


        }

    }

    public void update(){

        if(gameState == PLAY_STATE) {
            player.update();

            for(int i = 0; i < npc[1].length; i++){
                if(npc[currentMap][i] != null){
                    npc[currentMap][i].update();
                }
            }

            for(int i = 0; i < monster[1].length; i++){
                if(monster[currentMap][i] != null){
                    if(monster[currentMap][i].alive && !monster[currentMap][i].dying ){
                        monster[currentMap][i].update();
                    }
                    if(!monster[currentMap][i].alive){
                        monster[currentMap][i].checkDrop();
                        monster[currentMap][i] = null;
                    }

                }
            }

            for(int i = 0; i < projectile[1].length; i++){
                if(projectile[currentMap][i] != null){
                    if(projectile[currentMap][i].alive){
                        projectile[currentMap][i].update();
                    }
                    if(!projectile[currentMap][i].alive){
                        projectile[currentMap][i] = null;
                    }

                }
            }

            for(int i = 0; i < particleList.size(); i++){
                if(particleList.get(i) != null){
                    if(particleList.get(i).alive){
                        particleList.get(i).update();
                    }
                    if(!particleList.get(i).alive){
                        particleList.remove(i);
                    }

                }
            }

            for(int i = 0; i < iTile[1].length; i++){
                if(iTile[currentMap][i] != null){
                    iTile[currentMap][i].update();
                }
            }
        }

        eManager.update();

        if (gameState == PAUSE_STATE){
            // nother right now
        }
    }

    public void drawToTempScreen(){

        // Title Screen
        if(gameState == TITLE_STATE){

            ui.draw(g2);

        } else { // Others

            // Tile
            tileM.draw(g2);

            // Interactive Tile
            for(int i = 0; i < iTile[1].length; i++){
                if(iTile[currentMap][i] != null){
                    iTile[currentMap][i].draw(g2);
                }
            }

            entityList.add(player);

            for(int i = 0; i < npc[1].length; i++){
                if(npc[currentMap][i] != null){
                    entityList.add(npc[currentMap][i]);
                }
            }

            for(int i = 0; i < obj[1].length; i++){
                if(obj[currentMap][i] != null){
                    entityList.add(obj[currentMap][i]);
                }
            }

            for(int i = 0; i < monster[1].length; i++){
                if(monster[currentMap][i] != null){
                    entityList.add(monster[currentMap][i]);
                }
            }

            for(int i = 0; i < projectile[1].length; i++){
                if(projectile[currentMap][i] != null){
                    entityList.add(projectile[currentMap][i]);
                }
            }

            for(int i = 0; i < particleList.size(); i++){
                if(particleList.get(i) != null){
                    entityList.add(particleList.get(i));
                }
            }

            Collections.sort(entityList, new Comparator<Entity>() {
                @Override
                public int compare(Entity e1, Entity e2) {
                    int result = Integer.compare(e1.worldY,e2.worldY);
                    return result;

                }
            });

            for(int i = 0; i < entityList.size(); i++){

                entityList.get(i).draw(g2);
            }

            entityList.clear();



            // UI
            ui.draw(g2);
        }
    }

    public void drawToScreen(){

        Graphics g = getGraphics();
        g.drawImage(tempScreen, 0, 0, screenWidth2, screenHeight2, null);
        g.dispose();

    }

    public void playMusic(int i){
        music.setFile(i);
        music.play();
        music.loop();
    }

    public void stopMusic(){
        music.stop();
    }

    public void playSE(int i){
        se.setFile(i);
        se.play();
    }

    public void changeArea(){

        if(nextArea != currentArea){
            stopMusic();
            if(nextArea == OUTSIDE){
                playMusic(0);
            }
            if(nextArea == INDOOR){
                playMusic(18);
            }
            if(nextArea == DUNGEON){
                playMusic(19);
            }
            npc_List_Counter = 0;
            aSetter.setNPC();
        }
        currentArea = nextArea;
        aSetter.setMonster();
    }

    public void paintComponent(Graphics g){
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D)  g;

        long drawStart = System.nanoTime();

        // Title Screen
        if(gameState == TITLE_STATE) {

            ui.draw(g2);
        } else if (gameState == MAP_STATE){
            map.drawFullMapScreen(g2);
        } else { // Others

            // Tile
            tileM.draw(g2);

            // Interactive Tile
            for(int i = 0; i < iTile[1].length; i++){
                if(iTile[currentMap][i] != null){
                    iTile[currentMap][i].draw(g2);
                }
            }

            entityList.add(player);

            for(int i = 0; i < npc[1].length; i++){
                if(npc[currentMap][i] != null){
                    entityList.add(npc[currentMap][i]);
                }
            }

            for(int i = 0; i < obj[1].length; i++){
                if(obj[currentMap][i] != null){
                    entityList.add(obj[currentMap][i]);
                }
            }

            for(int i = 0; i < monster[1].length; i++){
                if(monster[currentMap][i] != null){
                    entityList.add(monster[currentMap][i]);
                }
            }

            for(int i = 0; i < projectile[1].length; i++){
                if(projectile[currentMap][i] != null){
                    entityList.add(projectile[currentMap][i]);
                }
            }

            for(int i = 0; i < particleList.size(); i++){
                if(particleList.get(i) != null){
                    entityList.add(particleList.get(i));
                }
            }

            Collections.sort(entityList, new Comparator<Entity>() {
                @Override
                public int compare(Entity e1, Entity e2) {
                    int result = Integer.compare(e1.worldY,e2.worldY);
                    return result;

                }
            });

            for(int i = 0; i < entityList.size(); i++){

                entityList.get(i).draw(g2);
            }

            entityList.clear();

            // Environment
            eManager.draw(g2);

            // Mini Map
            map.drawMiniMap(g2);

            // UI
            ui.draw(g2);

            // Cutscene
            csManager.draw(g2);

            // Debug
            if(keyH.showDebugText){
                long drawEnd = System.nanoTime();
                long passed = drawEnd - drawStart;

                g2.setFont(new Font("Arial", Font.PLAIN, 20));
                g2.setColor(Color.WHITE);

                int x = 10;
                int y = 400;
                int lineHeight = 20;

                g2.drawString("WorldX: " + player.worldX, x, y); y += lineHeight;
                g2.drawString("WorldY: " + player.worldY, x, y); y += lineHeight;
                g2.drawString("Col: " + (player.worldX + player.solidArea.x) / tileSize, x, y); y+= lineHeight;
                g2.drawString("Row: " + (player.worldY + player.solidArea.y) / tileSize, x, y); y+= lineHeight;
                g2.drawString("Draw Time: " + passed, x, y); y+= lineHeight;
                g2.drawString("God Mode: " + (keyH.godModeOn ? "Yes" : "No"), x , y);

            }
        }
        g2.dispose();

    }

    public void removeTempEntity(){

        for(int mapNum = 0; mapNum < maxMap; mapNum++){

            for(int i = 0; i < obj[1].length; i++){
                if(obj[mapNum][i] != null && obj[mapNum][i].temp){
                    obj[mapNum][i] = null;
                }
            }
        }
    }
}
