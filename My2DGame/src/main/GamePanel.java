package main;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;

import javax.swing.JPanel;

import ai.PathFinder;
import entity.Entity;
import entity.Player;
import environment.EnvironmentManager;
import tile.Map;
import tile.TileManager;
import tiles_interactive.InteractiveTile;


public class GamePanel extends JPanel implements Runnable {

    // ORIGINAL TILE SIZE in pixels
    final int originalTileSize = 20;

    // SCALE of tiles (16*3 = 48px)
    final int scale = 3;

    // TILE SIZE in pixels
    public final int tileSize = originalTileSize * scale;

    // Number of columns and rows on screen
    public final int maxScreenCol = 20;
    public final int maxScreenRow = 12;

    // Screen dimensions
    public final int screenWidth = tileSize * maxScreenCol;   // 960px
    public final int screenHeight = tileSize * maxScreenRow;  // 576px

    // WORLD SETTINGS
    public final int maxWorldCol = 50;
    public final int maxWorldRow = 50;
    public final int maxMap = 10;
    public int currentMap = 0;
    public final int worldWidth = tileSize * maxWorldCol;
    public final int worldHeight = tileSize * maxWorldRow;

    //FOR FULL SCREEN
    int screenWidth2 = screenWidth;
    int screenHeigh2 = screenHeight;
    BufferedImage tempScreen;
    Graphics2D g2;
    public boolean fullScreenOn = false;

    // Frames per second
    final int FPS = 60;

    //SYSTEM
    // Create tile manager
    public TileManager tileM = new TileManager(this);

    // Create keyboard handler
    public KeyHandler keyH = new KeyHandler(this);
    public int width;

    //Initiate the Sound
    Sound music = new Sound();
    Sound se = new Sound();

    //Initiate the Asset Setter Class
    public  AssetSetter aSetter = new AssetSetter(this);

    //Initiates the UI
    public UI ui = new UI(this);

    //Checks Collision
    public CollisionChecker cChecker = new CollisionChecker(this);

    //Initializes EventHandler
    public EventHandler eHandler = new EventHandler(this);

    //Initializes Config
    Config config = new Config(this);

    public PathFinder pFinder = new PathFinder(this);

    EnvironmentManager eManager = new EnvironmentManager(this);

    Map map = new Map(this);

    // Game loop thread
    Thread gameThread;

    //GAME STATES
    public int gameState;
    public final int titleState = 0;
    public final int playState = 1;
    public final int pauseState = 2 ;
    public final int dialogueState = 3;
    public final int characterState = 4;
    public final int optionState = 5;
    public final int gameOverState = 6;
    public final int transitionState = 7;
    public final int tradeState = 8;
    public final int sleepState = 9;
    public final int mapState = 10;


    //Entity and Object
    // Create player object
    public Player player = new Player(this, keyH);

    //Prepares 10 slot of objects
    public Entity obj[][] = new Entity[maxMap][20];



    //Entity
    public Entity npc[][] = new Entity[maxMap][20];

    public Entity monster[][] = new Entity[maxMap][20];
    public InteractiveTile iTile[][] = new InteractiveTile[maxMap][50];

    public Entity projectile[][] = new Entity[maxMap][20];

    //public ArrayList<Entity> projectileList = new ArrayList<>();
    public ArrayList<Entity> particleList = new ArrayList<>();
    ArrayList<Entity> entityList = new ArrayList<>();


    // Constructor
    public GamePanel() {
        // Set preferred size of the panel
        this.setPreferredSize(new Dimension(screenWidth, screenHeight));

        // Set background color
        this.setBackground(Color.black);

        // Enable double buffering for smooth graphics
        this.setDoubleBuffered(true);

        // Add key listener to detect key presses
        this.addKeyListener(keyH);

        // Make panel focusable so it can receive input
        this.setFocusable(true);

    }

    //Sets Up the Game objects and music or SFX
    public void setupGame(){
        aSetter.setObject();
        aSetter.setNPC();
        aSetter.setMonster();
        aSetter.setInteractiveTile();
        playMusic(0);
        //stopMusic();
        eManager.setup();
        gameState = titleState;

        tempScreen = new BufferedImage(screenWidth, screenHeight, BufferedImage.TYPE_INT_ARGB);
        g2 = (Graphics2D)tempScreen.getGraphics();

        if(fullScreenOn == true){
            setFullScreen();
        }

        // Request focus AFTER full screen
        this.requestFocusInWindow();
    }

    public void retry(){
        player.setDefaultPositions();
        player.restoreLifeAndMana();
        aSetter.setNPC();
        aSetter.setMonster();
    }

    public void restart(){
        player.setDefaultPositions();
        player.setDefaultValues();
        player.restoreLifeAndMana();
        player.setItems();
        aSetter.setObject();
        aSetter.setNPC();
        aSetter.setMonster();
        aSetter.setInteractiveTile();
    }

    public void setFullScreen(){
        //Get Local Screen Device
        GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
        GraphicsDevice gd = ge.getDefaultScreenDevice();
        gd.setFullScreenWindow(Main.window);

        //GET FULL SCREEN WIDTH AND HEIGHT
        screenHeigh2 = Main.window.getWidth();
        screenHeigh2 = Main.window.getHeight();

    }

    // Start the game loop
    public void startGameThread() {
        gameThread = new Thread(this);
        gameThread.start();
    }

    // Game loop
    @Override
    public void run() {
        double drawInterval = 1000000000.0 / FPS; // Time per frame in nanoseconds
        double delta = 0;
        long lastTime = System.nanoTime();
        long currentTime;

        while (gameThread != null) {
            // Get current time
            currentTime = System.nanoTime();

            // Add the elapsed time to delta
            delta += (currentTime - lastTime) / drawInterval;

            // Update lastTime
            lastTime = currentTime;

            // If it's time to update
            if (delta >= 1) {
                update();   // Update game logic
                drawToTempScreen(); //Draw Everything to the Buffered Image
                repaint();// Draw the Buffered Image to the Screen
                delta--;
            }
        }
    }

    // Update game logic
    public void update() {
        if(gameState == playState){
            //Player
            player.update();

            //NPC
            for(int i = 0;i < npc[1].length; i++){
                if(npc[currentMap][i]!= null){
                    npc[currentMap][i].update();
                }
            }

            //Monster
            for(int i = 0; i < monster[1].length; i++){
                if(monster[currentMap][i] != null){
                    if(monster[currentMap][i].alive == true && monster[currentMap][i].dying == false){
                        monster[currentMap][i].update();
                    }
                    if(monster[currentMap][i].alive == false){
                        monster[currentMap][i].checkDrop();
                        monster[currentMap][i] = null;
                    }
                }
            }

            for(int i = 0; i < projectile[1].length; i++){
                if(projectile[currentMap][i] != null){
                    if(projectile[currentMap][i] .alive == true){
                        projectile[currentMap][i].update();
                    }
                    if(projectile[currentMap][i] .alive == false){
                        projectile[currentMap][i] = null;
                    }
                }
            }

            for(int i = 0; i < particleList.size(); i++){
                if(particleList.get(i) != null){
                    if(particleList.get(i).alive == true){
                        particleList.get(i).update();
                    }
                    if(particleList.get(i).alive == false){
                        particleList.remove(i);
                    }
                }
            }


            for(int i = 0; i < iTile[1].length; i++){
                if(iTile[currentMap][i] != null){
                    iTile[currentMap][i].update();
                }
            }
            eManager.update();
        }
        if (gameState == pauseState){
            //Nothing
        }
    }

    //Draws the Game
    public void drawToTempScreen() {
        // DEBUG
        long drawStart = 0;
        if(keyH.checkDrawTime == true) {
            drawStart = System.nanoTime();
        }

        //TITLE SCREEN
        if(gameState == titleState){
            ui.draw(g2);
        }
        //Map State
        else if (gameState == mapState) {
            map.drawFullMapScreen(g2);

        } else{
            //TILE
            tileM.draw(g2);    // Draw the tile map

            //INTERACTIVE TILE
            for(int i = 0; i <iTile[1].length; i++){
                if(iTile[currentMap][i] != null){
                    iTile[currentMap][i].draw(g2);
                }
            }

            //ADD ENTITIES TO THE LIST
            entityList.add(player);

            for(int i = 0; i < npc[1].length; i++){
                if(npc[currentMap][i] != null){
                    entityList.add(npc[currentMap][i]);
                }
            }

            for(int i = 0; i < obj[1].length; i ++){
                if(obj[currentMap][i] != null){
                    entityList.add(obj[currentMap][i]);
                }
            }

            for(int i = 0; i < monster[1].length; i ++){
                if(monster[currentMap][i] != null){
                    entityList.add(monster[currentMap][i]);
                }
            }

            for(int i = 0; i < projectile[1].length; i ++){
                if(projectile[currentMap][i] != null){
                    entityList.add(projectile[currentMap][i]);
                }
            }

            for(int i = 0; i < particleList.size(); i ++){
                if(particleList.get(i) != null){
                    entityList.add(particleList.get(i));
                }
            }

            //SORT
            Collections.sort(entityList, new Comparator<Entity>() {
                @Override
                public int compare(Entity e1, Entity e2) {
                    int result = Integer.compare(e1.worldY, e2.worldY);
                    return result;
                }
            });

            //DRAW ENTITIES
            for (int i = 0; i < entityList.size(); i++){
                entityList.get(i).draw(g2);
            }

            //EMPTY ENTITY LIST
            entityList.clear();

            //Environment
            eManager.draw(g2);

            //MINI MAP
            map.drawMiniMap(g2);

            //UI
            ui.draw(g2); //Draw the UI
        }


        // DEBUG
        if(keyH.checkDrawTime == true) {
            long drawEnd = System.nanoTime();
            long passed = drawEnd - drawStart;
            g2.setColor(Color.white);
            g2.drawString("Draw Time: " + passed, 10, 400);
            System.out.println("Draw Time: "+passed);
        }
    }


    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        int w = getWidth();
        int h = getHeight();

        double scaleX = (double) w / screenWidth;
        double scaleY = (double) h / screenHeight;
        double scale = Math.min(scaleX, scaleY); // Keep aspect ratio

        int newW = (int)(screenWidth * scale);
        int newH = (int)(screenHeight * scale);

        int x = (w - newW) / 2;
        int y = (h - newH) / 2;

        g.drawImage(tempScreen, x, y, newW, newH, null);
    }

    //Plays the Musics
    public void playMusic(int i){
        music.setFile(i);
        music.play();
        music.loop();
    }

    //Sound the Music
    public void stopMusic(){
        music.stop();
    }

    //Plays the SFX
    public void playSE(int i){
        se.setFile(i);
        se.play();
    }
}
