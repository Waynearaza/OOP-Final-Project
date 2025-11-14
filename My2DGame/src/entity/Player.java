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
    int hasKey = 0; //key number or invertory of player has

    public Player(GamePanel gp, KeyHandler keyH) {
        super(gp); // call Entity constructor
        this.keyH = keyH;

        // Screen center coordinates
        screenX = gp.screenWidth / 2 - gp.tileSize / 2;
        screenY = gp.screenHeight / 2 - gp.tileSize / 2;
        
        
        // only the inside of the character is solid
        solidArea = new Rectangle();
        solidArea.x = 4;
        solidArea.y = 10;
        solidAreaDefaultX = solidArea.x;
        solidAreaDefaultY = solidArea.y;
        solidArea.width = 20;
        solidArea.height = 20;
        

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

        if (moving) { // only move when a key is pressed
            collisionOn = false;
            gp.cChecker.checkTile(this);

       //Check object Collision
        int objIndex = gp.cChecker.checkObject(this, true);
        pickUpObject(objIndex);

       //If Collision is False, Player can Move
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
        }
        else {
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

    //Sets the Behavior of the object when an object is picked up by the player
    public void pickUpObject(int i) {
        if(i != 999){
            String objectName = gp.obj[i].name;

            switch(objectName){
                case "Key": //Pickups Key when player moves over it
                    gp.playSE(1);//Plays the SFX
                    hasKey++;
                    gp.obj[i] = null;
                    System.out.println("Key:"+hasKey);
                    break;
                case "Door": //Checks if the player has key for door access
                    if(hasKey > 0){
                        gp.playSE(3);//Plays the SFX
                        gp.obj[i] = null;
                        hasKey--;
                    }
                    System.out.println("Key:"+hasKey);
                    break;
                case "Boots": //Increase the speed of the player when the Boots is picked up
                    gp.playSE(2); //Plays the SFX
                    speed += 2;
                    gp.obj[i] = null;
                    break;
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
