package entity;

import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;

import javax.imageio.ImageIO;
import main.GamePanel;
import main.KeyHandler;
import main.UtilityTool;

public class Player extends Entity {

    KeyHandler keyH;
    public final int screenX;
    public final int screenY;
    private boolean moving = false;

    // 6-frame walking sprites
    BufferedImage up1, up2, up3, up4, up5, up6;
    public BufferedImage down1;
    BufferedImage down2;
    BufferedImage down3;
    BufferedImage down4;
    BufferedImage down5;
    BufferedImage down6;
    BufferedImage left1, left2, left3, left4, left5, left6;
    BufferedImage right1, right2, right3, right4, right5, right6;

    // 6-frame idle sprites
    BufferedImage upIdle1, upIdle2, upIdle3, upIdle4, upIdle5, upIdle6;
    BufferedImage downIdle1, downIdle2, downIdle3, downIdle4, downIdle5, downIdle6;
    BufferedImage leftIdle1, leftIdle2, leftIdle3, leftIdle4, leftIdle5, leftIdle6;
    BufferedImage rightIdle1, rightIdle2, rightIdle3, rightIdle4, rightIdle5, rightIdle6;

    public Player(GamePanel gp, KeyHandler keyH) {
        super(gp);

        this.keyH = keyH;

        screenX = gp.screenWidth / 2 - gp.tileSize / 2;
        screenY = gp.screenHeight / 2 - gp.tileSize / 2;

        // Sets the Player Collision Size
        solidArea = new Rectangle(17, 30, 24, 24);
        solidAreaDefaultX = solidArea.x;
        solidAreaDefaultY = solidArea.y;

        //spawn location
        worldX = gp.tileSize * 23;
        worldY = gp.tileSize * 21;
        speed = 4;
        direction = "down";

        getPlayerImage();
        image = down1;

        //PLAYER STATUS
        maxLife =6;
        life =maxLife;
    }


    public void getPlayerImage() {
        up1 = setup("/player/player-31.png");
        up2 = setup("/player/player-32.png");
        up3 = setup("/player/player-33.png");
        up4 = setup("/player/player-34.png");
        up5 = setup("/player/player-35.png");
        up6 = setup("/player/player-36.png");

        down1 = setup("/player/player-19.png");
        down2 = setup("/player/player-20.png");
        down3 = setup("/player/player-21.png");
        down4 = setup("/player/player-22.png");
        down5 = setup("/player/player-23.png");
        down6 = setup("/player/player-24.png");

        right1 = setup("/player/player-25.png");
        right2 = setup("/player/player-26.png");
        right3 = setup("/player/player-27.png");
        right4 = setup("/player/player-28.png");
        right5 = setup("/player/player-29.png");
        right6 = setup("/player/player-30.png");

        left1 = setup("/player/player-37.png");
        left2 = setup("/player/player-38.png");
        left3 = setup("/player/player-39.png");
        left4 = setup("/player/player-40.png");
        left5 = setup("/player/player-41.png");
        left6 = setup("/player/player-42.png");




        // Idle frames
        upIdle1 = setup("/player/player-13.png"); upIdle2 = setup("/player/player-14.png"); upIdle3 = setup("/player/player-15.png");
        upIdle4 = setup("/player/player-16.png"); upIdle5 = setup("/player/player-17.png"); upIdle6 = setup("/player/player-18.png");

        downIdle1 = setup("/player/player-1.png"); downIdle2 = setup("/player/player-2.png"); downIdle3 = setup("/player/player-3.png");
        downIdle4 = setup("/player/player-4.png"); downIdle5 = setup("/player/player-5.png"); downIdle6 = setup("/player/player-6.png");

        leftIdle1 = setup("/player/player-43.png"); leftIdle2 = setup("/player/player-44.png"); leftIdle3 = setup("/player/player-45.png");
        leftIdle4 = setup("/player/player-46.png"); leftIdle5 = setup("/player/player-47.png"); leftIdle6 = setup("/player/player-48.png");

        rightIdle1 = setup("/player/player-7.png"); rightIdle2 = setup("/player/player-8.png"); rightIdle3 = setup("/player/player-9.png");
        rightIdle4 = setup("/player/player-10.png"); rightIdle5 = setup("/player/player-11.png"); rightIdle6 = setup("/player/player-12.png");
    }

    public BufferedImage setup(String imagePath) {
        UtilityTool uTool = new UtilityTool();
        BufferedImage image = null;

        try {
            image = ImageIO.read(getClass().getResourceAsStream(imagePath + ".png"));

            if (image == null) {
                System.err.println("ERROR: Image not found: " + imagePath + ".png");
                return null;
            }

            image = uTool.scaleImage(image, gp.tileSize, gp.tileSize);

        } catch (IOException | IllegalArgumentException e) {
            System.err.println("ERROR loading image: " + imagePath + ".png");
            e.printStackTrace();
            return null;
        }

        return image;
    }




    public void update() {
        moving = false;

        if (keyH.upPressed) { direction = "up"; moving = true; }
        if (keyH.downPressed) { direction = "down"; moving = true; }
        if (keyH.leftPressed) { direction = "left"; moving = true; }
        if (keyH.rightPressed) { direction = "right"; moving = true; }

        if (moving) {
            collisionOn = false;

            //Check the Collision
            gp.cChecker.checkTile(this);

            //CheckObject Collision
            int objIndex = gp.cChecker.checkObject(this, true);
            pickUpObject(objIndex);

            //Check NPC collision
            int npcIndex = gp.cChecker.checkEntity(this, gp.npc);
            interactNPC(npcIndex);

            //CHECK EVENT
            gp.eHandler.checkEvent();
            gp.keyH.enterPressed = false;

            if (!collisionOn) {
                switch(direction) {
                    case "up": worldY -= speed; break;
                    case "down": worldY += speed; break;
                    case "left": worldX -= speed; break;
                    case "right": worldX += speed; break;
                }
            }

            // Animate walking (6 frames)
            spriteCounter++;
            if (spriteCounter > 12) {
                spriteNum++;
                if (spriteNum > 6) spriteNum = 1;
                spriteCounter = 0;
            }
        } else {
            // Animate idle (6 frames)
            spriteCounter++;
            if (spriteCounter > 20) {
                spriteNum++;
                if (spriteNum > 6) spriteNum = 1;
                spriteCounter = 0;
            }
        }

        // Keep player inside map
        if (worldX < 0) worldX = 0;
        if (worldY < 0) worldY = 0;
        if (worldX > gp.tileSize * (gp.maxWorldCol - 1)) worldX = gp.tileSize * (gp.maxWorldCol - 1);
        if (worldY > gp.tileSize * (gp.maxWorldRow - 1)) worldY = gp.tileSize * (gp.maxWorldRow - 1);
    }


    public void draw(Graphics2D g2) {
        BufferedImage img = null;

        if (moving) {
            switch(direction) {
                case "up":    img = getSprite(up1, up2, up3, up4, up5, up6); break;
                case "down":  img = getSprite(down1, down2, down3, down4, down5, down6); break;
                case "left":  img = getSprite(left1, left2, left3, left4, left5, left6); break;
                case "right": img = getSprite(right1, right2, right3, right4, right5, right6); break;
            }
        } else {
            switch(direction) {
                case "up":    img = getSprite(upIdle1, upIdle2, upIdle3, upIdle4, upIdle5, upIdle6); break;
                case "down":  img = getSprite(downIdle1, downIdle2, downIdle3, downIdle4, downIdle5, downIdle6); break;
                case "left":  img = getSprite(leftIdle1, leftIdle2, leftIdle3, leftIdle4, leftIdle5, leftIdle6); break;
                case "right": img = getSprite(rightIdle1, rightIdle2, rightIdle3, rightIdle4, rightIdle5, rightIdle6); break;
            }
        }

        g2.drawImage(img, screenX, screenY, gp.tileSize, gp.tileSize, null);
    }

    // Generic sprite picker
    private BufferedImage getSprite(BufferedImage s1, BufferedImage s2, BufferedImage s3,
                                    BufferedImage s4, BufferedImage s5, BufferedImage s6) {
        switch(spriteNum) {
            case 1: return s1;
            case 2: return s2;
            case 3: return s3;
            case 4: return s4;
            case 5: return s5;
            case 6: return s6;
            default: return s1;
        }
    }

    //Sets the Behavior of the object when an object is picked up by the player
    public void pickUpObject(int i) {
        if(i != 999) {

        }
    }

    //Sets Behavior of NPC When You Collide With It
    public void interactNPC(int i){
        if(i != 999) {
            if(gp.keyH.enterPressed == true){
                gp.gameState = gp.dialogueState;
                gp.npc[i].speak();
            }
        }
    }

}