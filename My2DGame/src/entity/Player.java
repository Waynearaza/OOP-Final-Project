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
    public int hasKey = 0;
    private boolean moving = false;

    // 6-frame walking sprites
    BufferedImage up1, up2, up3, up4, up5, up6;
    BufferedImage down1, down2, down3, down4, down5, down6;
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

        solidArea = new Rectangle(4, 10, 20, 20);
        solidAreaDefaultX = solidArea.x;
        solidAreaDefaultY = solidArea.y;

        worldX = gp.tileSize * 23;
        worldY = gp.tileSize * 21;
        speed = 4;
        size = gp.tileSize;
        direction = "down";

        getPlayerImage();
        image = down1;
    }

    public void getPlayerImage() {
        up1 = setup("player-31.png"); up2 = setup("player-32.png"); up3 = setup("player-33.png");
        up4 = setup("player-34.png"); up5 = setup("player-35.png"); up6 = setup("player-36.png");

        down1 = setup("player-19.png"); down2 = setup("player-20.png"); down3 = setup("player-21.png");
        down4 = setup("player-22.png"); down5 = setup("player-23.png"); down6 = setup("player-24.png");

        left1 = setup("player-37.png"); left2 = setup("player-38.png"); left3 = setup("player-39.png");
        left4 = setup("player-40.png"); left5 = setup("player-41.png"); left6 = setup("player-42.png");

        right1 = setup("player-25.png"); right2 = setup("player-26.png"); right3 = setup("player-27.png");
        right4 = setup("player-28.png"); right5 = setup("player-29.png"); right6 = setup("player-30.png");

        // Idle frames
        upIdle1 = setup("player-13.png"); upIdle2 = setup("player-14.png"); upIdle3 = setup("player-15.png");
        upIdle4 = setup("player-16.png"); upIdle5 = setup("player-17.png"); upIdle6 = setup("player-18.png");

        downIdle1 = setup("player-1.png"); downIdle2 = setup("player-2.png"); downIdle3 = setup("player-3.png");
        downIdle4 = setup("player-4.png"); downIdle5 = setup("player-5.png"); downIdle6 = setup("player-6.png");

        leftIdle1 = setup("player-43.png"); leftIdle2 = setup("player-44.png"); leftIdle3 = setup("player-45.png");
        leftIdle4 = setup("player-46.png"); leftIdle5 = setup("player-47.png"); leftIdle6 = setup("player-48.png");

        rightIdle1 = setup("player-7.png"); rightIdle2 = setup("player-8.png"); rightIdle3 = setup("player-9.png");
        rightIdle4 = setup("player-10.png"); rightIdle5 = setup("player-11.png"); rightIdle6 = setup("player-12.png");
    }

    public BufferedImage setup(String imageName) {
        BufferedImage image = null;
        try {
            InputStream is = getClass().getResourceAsStream("/player/" + imageName + ".png");
            if (is == null) {
                System.out.println("ERROR: Cannot find image: /player/" + imageName + ".png");
                return null;
            }
            image = ImageIO.read(is);
            UtilityTool uTool = new UtilityTool();
            image = uTool.scaleImage(image, gp.tileSize, gp.tileSize);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return image;
    }

    @Override
    public void update() {
        moving = false;

        if (keyH.upPressed) { direction = "up"; moving = true; }
        if (keyH.downPressed) { direction = "down"; moving = true; }
        if (keyH.leftPressed) { direction = "left"; moving = true; }
        if (keyH.rightPressed) { direction = "right"; moving = true; }

        if (moving) {
            collisionOn = false;
            gp.cChecker.checkTile(this);
            int objIndex = gp.cChecker.checkObject(this, true);
            pickUpObject(objIndex);

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

    @Override
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
        if(i != 999){
            String objectName = gp.obj[i].name;

            switch(objectName){
                case "Key": //Pickups Key when player moves over it
                    gp.playSE(1);//Plays the SFX
                    hasKey++;
                    gp.obj[i] = null;
                    gp.ui.showMessage("You Got a Key!");
                    break;
                case "Door": //Checks if the player has key for door access
                    if(hasKey > 0){
                        gp.playSE(3);//Plays the SFX
                        gp.obj[i] = null;
                        gp.ui.showMessage("You Opened a Door!");
                        hasKey--;
                    }else {
                        gp.ui.showMessage("You Need A Key Nigga");
                    }
                    break;
                case "Boots": //Increase the speed of the player when the Boots is picked up
                    gp.playSE(2); //Plays the SFX
                    speed += 2;
                    gp.obj[i] = null;
                    gp.ui.showMessage("Speed Up");
                    break;
                case "Chest":
                    gp.ui.gameFinished = true;
                    gp.stopMusic();;
                    gp.playSE(4);
                    break;
            }
        }
    }
}
