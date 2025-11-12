package entity;

import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.IOException;
import javax.imageio.ImageIO;
import main.GamePanel;
import main.KeyHandler;

public class Player extends Entity {
    KeyHandler keyH;
    public final int screenX;
    public final int screenY;

    public Player(GamePanel gp, KeyHandler keyH) {
        super(gp); // call Entity constructor
        this.keyH = keyH;

        // Screen center coordinates
        screenX = gp.screenWidth / 2 - gp.tileSize / 2;
        screenY = gp.screenHeight / 2 - gp.tileSize / 2;
        
        
        // only the inside of the character is solid
        solidArea = new Rectangle();
        solidArea.x = 8;
        solidArea.y = 16;
        solidArea.width = 32;
        solidArea.height = 32;
        

        // Default position in the world
        this.worldX = gp.tileSize * 23;
        this.worldY = gp.tileSize * 21;
        this.speed = 4;
        this.size = gp.tileSize;

        direction = "down"; // default direction
        getPlayerImage();   // load sprites
        image = down1;      // initial sprite
    }

    // Load player sprites
    public void getPlayerImage() {
        try {
            up1 = ImageIO.read(getClass().getResourceAsStream("/player/boy_up_1.png"));
            up2 = ImageIO.read(getClass().getResourceAsStream("/player/boy_up_2.png"));
            down1 = ImageIO.read(getClass().getResourceAsStream("/player/boy_down_1.png"));
            down2 = ImageIO.read(getClass().getResourceAsStream("/player/boy_down_2.png"));
            left1 = ImageIO.read(getClass().getResourceAsStream("/player/boy_left_1.png"));
            left2 = ImageIO.read(getClass().getResourceAsStream("/player/boy_left_2.png"));
            right1 = ImageIO.read(getClass().getResourceAsStream("/player/boy_right_1.png"));
            right2 = ImageIO.read(getClass().getResourceAsStream("/player/boy_right_2.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Update player every frame
    @Override
    public void update() {
        boolean moving = false;

        if (keyH.upPressed) {
            direction = "up";
            moving = true;
        }
        if (keyH.downPressed) {
            direction = "down";            
            moving = true;
        }
        if (keyH.leftPressed) {
            direction = "left";            
            moving = true;
        }
        if (keyH.rightPressed) {
            direction = "right";            
            moving = true;
        }

        if (moving) { // ✅ only move when a key is pressed
            collisionOn = false;
            gp.cChecker.checkTile(this);

            if (!collisionOn) {
                switch(direction) {
                    case "up": worldY -= speed; break;
                    case "down": worldY += speed; break;
                    case "left": worldX -= speed; break;
                    case "right": worldX += speed; break;
                }
            }

            // Animate sprite when moving
            spriteCounter++;
            if (spriteCounter > 12) {
                spriteNum = (spriteNum == 1) ? 2 : 1;
                spriteCounter = 0;
            }
        } else {
            // optional: reset sprite when idle
            spriteNum = 1;
            spriteCounter = 0;
        }

        // Prevent leaving map boundaries (keep inside world)
        if (worldX < 0) worldX = 0;
        if (worldY < 0) worldY = 0;
        if (worldX > gp.tileSize * (gp.maxWorldCol - 1)) worldX = gp.tileSize * (gp.maxWorldCol - 1);
        if (worldY > gp.tileSize * (gp.maxWorldRow - 1)) worldY = gp.tileSize * (gp.maxWorldRow - 1);
    

        // Animate sprite when moving
        if (moving) {
            spriteCounter++;
            if (spriteCounter > 12) {
                spriteNum = (spriteNum == 1) ? 2 : 1;
                spriteCounter = 0;
            }
        }
    }

    // Draw player at the center of the screen
    @Override
    public void draw(Graphics2D g2) {
        BufferedImage img = null;

        switch(direction) {
            case "up":    img = (spriteNum == 1) ? up1 : up2; break;
            case "down":  img = (spriteNum == 1) ? down1 : down2; break;
            case "left":  img = (spriteNum == 1) ? left1 : left2; break;
            case "right": img = (spriteNum == 1) ? right1 : right2; break;
        }

        g2.drawImage(img, screenX, screenY, gp.tileSize, gp.tileSize, null);
    }
}
