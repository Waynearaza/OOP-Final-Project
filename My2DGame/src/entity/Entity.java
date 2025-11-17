package entity;

import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import main.GamePanel;

public class Entity {
    GamePanel gp;

    // Position in the world
    public int worldX, worldY;

    // Movement speed
    public int speed;

    // Sprite arrays (6 frames per direction)
    public BufferedImage[] upSprites;
    public BufferedImage[] downSprites;
    public BufferedImage[] leftSprites;
    public BufferedImage[] rightSprites;

    // Current image to draw
    public BufferedImage image;

    // Size of the entity
    public int size;

    // Direction: "up", "down", "left", "right"
    public String direction = "down";

    // Collision
    public Rectangle solidArea;
    public int solidAreaDefaultX, solidAreaDefaultY;
    public boolean collisionOn = false;

    // Animation
    public int spriteCounter = 0;
    public int spriteNum = 1; // 1 → 6

    public Entity(GamePanel gp) {
        this.gp = gp;

        // Allocate sprite arrays (6 frames per direction)
        upSprites = new BufferedImage[6];
        downSprites = new BufferedImage[6];
        leftSprites = new BufferedImage[6];
        rightSprites = new BufferedImage[6];
    }

    // Update entity logic (to be overridden by subclasses)
    public void update() {
        // Default: do nothing
    }

    // Draw entity on screen
    public void draw(Graphics2D g2) {
        if (image != null) {
            int drawX = worldX - gp.player.worldX + gp.player.screenX;
            int drawY = worldY - gp.player.worldY + gp.player.screenY;

            g2.drawImage(image, drawX, drawY, size, size, null);
        }
    }

    // Animate sprite
    public void updateSprite() {
        spriteCounter++;
        if (spriteCounter > 8) { // adjust speed of animation
            spriteNum++;
            if (spriteNum > 6) spriteNum = 1; // cycle frames 1 → 6
            spriteCounter = 0;
        }

        // Update current image based on direction
        switch (direction) {
            case "up": image = upSprites[spriteNum - 1]; break;
            case "down": image = downSprites[spriteNum - 1]; break;
            case "left": image = leftSprites[spriteNum - 1]; break;
            case "right": image = rightSprites[spriteNum - 1]; break;
        }
    }
}
