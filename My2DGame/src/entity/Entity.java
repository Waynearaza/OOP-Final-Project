package entity;

import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import main.GamePanel;

public class Entity {
    GamePanel gp;

    // Position in world
    public int worldX, worldY;

    // Speed of movement
    public int speed;

    // Sprite images
    public BufferedImage up1, up2, down1, down2, left1, left2, right1, right2;

    // Current image to draw
    public BufferedImage image;

    // Size of the entity
    public int size;

    // Direction ("up", "down", "left", "right")
    public String direction = "down";
    
    public Rectangle solidArea;

    public  int solidAreaDefaultX, solidAreaDefaultY;

    public boolean collisionOn = false;

    // Sprite animation control
    public int spriteCounter = 0;
    public int spriteNum = 1; // 1 or 2

    public Entity(GamePanel gp) {
        this.gp = gp;
    }
    
   

    // Update entity (to be overridden by subclasses)
    public void update() {
        // Default: do nothing
    }

    // Draw entity on screen
    public void draw(Graphics2D g2) {
        if (image != null) {
        	int drawX = worldX + 2; // center horizontally
        	int drawY = worldY + 2; // center vertically
        	g2.drawImage(image, drawX, drawY, size, size, null);

        }
    }

    // Animate sprite (switch between 1 and 2)
    public void updateSprite() {
        spriteCounter++;
        if (spriteCounter > 8) { // change every 12 frames
            if (spriteNum == 1) spriteNum = 2;
            else spriteNum = 1;
            spriteCounter = 0;
        }

        // Update current image based on direction
        switch(direction) {
            case "up":
                image = (spriteNum == 1) ? up1 : up2;
                break;
            case "down":
                image = (spriteNum == 1) ? down1 : down2;
                break;
            case "left":
                image = (spriteNum == 1) ? left1 : left2;
                break;
            case "right":
                image = (spriteNum == 1) ? right1 : right2;
                break;
        }
    }
}
