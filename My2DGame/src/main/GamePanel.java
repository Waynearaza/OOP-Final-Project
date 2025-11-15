package main;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;

import javax.swing.JPanel;

import entity.Player;
import object.SuperObject;
import tile.TileManager;

public class GamePanel extends JPanel implements Runnable {

    // ORIGINAL TILE SIZE in pixels
    final int originalTileSize = 16;

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
    KeyHandler keyH = new KeyHandler();

    //Initiate the Sound
    Sound music = new Sound();
    Sound se = new Sound();

    //Initiate the Asset Setter Class
    public  AssetSetter aSetter = new AssetSetter(this);

    //Initiates the UI
    public UI ui = new UI(this);

    //Checks Collision
    public CollisionChecker cChecker = new CollisionChecker(this);

    // Game loop thread
    Thread gameThread;



    //Entity and Object
    // Create player object
    public Player player = new Player(this, keyH);

    //Prepares 10 slot of objects
    public SuperObject obj[] = new SuperObject[10];



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

        playMusic(0);
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
        player.update(); // Update player position
    }

    // Draw the game
    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;

        tileM.draw(g2);    // Draw the tile map

        //Object
        for (int i = 0; i < obj.length; i++) {
            if (obj[i] != null) {
                obj[i].draw(g2, this);
            }
        }
        //Player
        player.draw(g2);   // Draw the player

        ui.draw(g2); //Draw the UI

        g2.dispose();      // Dispose graphics object to free memory
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
