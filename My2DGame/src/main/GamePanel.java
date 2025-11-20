package main;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;

import javax.swing.JPanel;

import entity.Entity;
import entity.Player;
import tile.TileManager;


public class GamePanel extends JPanel implements Runnable {

    // ORIGINAL TILE SIZE in pixels
    final int originalTileSize = 20;

    // SCALE of tiles (16*3 = 48px)
    final int scale = 3;

    // TILE SIZE in pixels
    public final int tileSize = originalTileSize * scale;

    // Number of columns and rows on screen
    public final int maxScreenCol = 16;
    public final int maxScreenRow = 12;

    // Screen dimensions
    public final int screenWidth = tileSize * maxScreenCol;   // 48 * 16 = 768px
    public final int screenHeight = tileSize * maxScreenRow;  // 48 * 12 = 576px

    // WORLD SETTINGS
    public final int maxWorldCol = 50;
    public final int maxWorldRow = 50;
    public final int worldWidth = tileSize * maxWorldCol;
    public final int worldHeight = tileSize * maxWorldRow;

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

    //
    public EventHandler eHandler = new EventHandler(this);

    // Game loop thread
    Thread gameThread;

    //GAME STATES
    public int gameState;
    public final int titleState = 0;
    public final int playState =1;
    public final int pauseState = 2 ;
    public final int dialogueState = 3;


    //Entity and Object
    // Create player object
    public Player player = new Player(this, keyH);

    //Prepares 10 slot of objects
    public Entity obj[] = new Entity[10];
    ArrayList<Entity> entityList = new ArrayList<>();

    //Entity
    public Entity npc[] = new Entity[20];

    public Entity monster[] = new Entity[20];


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
        playMusic(0);
        //stopMusic();
        gameState = titleState;
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
                repaint();  // Redraw the screen
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
            for(int i = 0;i < npc.length; i++){
                if(npc[i]!= null){
                    npc[i].update();
                }
            }

            //Monster
            for(int i = 0; i < monster.length; i++){
                if(monster[i] != null){
                    monster[i].update();
                }
            }

        }
        if (gameState == pauseState){
            //Nothing
        }
    }

    // Draw the game
    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;

        // DEBUG
        long drawStart = 0;
        if(keyH.checkDrawTime == true) {
            drawStart = System.nanoTime();
        }

        //TITLE SCREEN
        if(gameState == titleState){
            ui.draw(g2);
        } else{
            //TILE
            tileM.draw(g2);    // Draw the tile map

            //ADD ENTITIES TO THE LIST
            entityList.add(player);

            for(int i = 0; i < npc.length; i++){
                if(npc[i] != null){
                    entityList.add(npc[i]);
                }
            }

            for(int i = 0; i < obj.length; i ++){
                if(obj[i] != null){
                    entityList.add(obj[i]);
                }
            }

            for(int i = 0; i < monster.length; i ++){
                if(monster[i] != null){
                    entityList.add(monster[i]);
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

        g2.dispose();      // Dispose graphics object to free memory
    }

    //Plays the Musics
    public void playMusic(int i){
        music.setFile(i);
        music.play();
        music.loop();
        music.setVolume(0.7f);
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
