package entity;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;

import main.GamePanel;
import main.KeyHandler;
import main.UtilityTool;

import static javax.imageio.ImageIO.read;

public class Player extends Entity {

    KeyHandler keyH;
    public final int screenX;
    public final int screenY;
    private boolean moving = false;

    // Walking sprites
    BufferedImage up1, up2, up3, up4, up5, up6;
    public BufferedImage down1;
    BufferedImage down2, down3, down4, down5, down6;
    BufferedImage left1, left2, left3, left4, left5, left6;
    BufferedImage right1, right2, right3, right4, right5, right6;

    // Idle sprites
    BufferedImage upIdle1, upIdle2, upIdle3, upIdle4, upIdle5, upIdle6;
    BufferedImage downIdle1, downIdle2, downIdle3, downIdle4, downIdle5, downIdle6;
    BufferedImage leftIdle1, leftIdle2, leftIdle3, leftIdle4, leftIdle5, leftIdle6;
    BufferedImage rightIdle1, rightIdle2, rightIdle3, rightIdle4, rightIdle5, rightIdle6;

    // Attack sprites
    BufferedImage attackUp1, attackUp2, attackUp3, attackUp4;
    BufferedImage attackDown1, attackDown2, attackDown3, attackDown4;
    BufferedImage attackLeft1, attackLeft2, attackLeft3, attackLeft4;
    BufferedImage attackRight1, attackRight2, attackRight3, attackRight4;

    public Player(GamePanel gp, KeyHandler keyH) {
        super(gp);

        this.keyH = keyH;

        screenX = gp.screenWidth / 2 - gp.tileSize / 2;
        screenY = gp.screenHeight / 2 - gp.tileSize / 2;

        // Collision box
        solidArea = new Rectangle(17, 30, 24, 24);
        solidAreaDefaultX = solidArea.x;
        solidAreaDefaultY = solidArea.y;

        //Attack Area
        attackArea.width = 36;
        attackArea.height = 36;

        // Spawn
        worldX = gp.tileSize * 23;
        worldY = gp.tileSize * 21;
        speed = 4;
        direction = "down";

        getPlayerImage();
        getPlayerAttackImage();
        image = down1;

        maxLife = 6;
        life = maxLife;
    }

    // LOAD WALK + IDLE FRAMES
    public void getPlayerImage() {
        up1 = setup("/player/player-31.png", gp.tileSize, gp.tileSize);
        up2 = setup("/player/player-32.png", gp.tileSize, gp.tileSize);
        up3 = setup("/player/player-33.png", gp.tileSize, gp.tileSize);
        up4 = setup("/player/player-34.png", gp.tileSize, gp.tileSize);
        up5 = setup("/player/player-35.png", gp.tileSize, gp.tileSize);
        up6 = setup("/player/player-36.png", gp.tileSize, gp.tileSize);

        down1 = setup("/player/player-19.png", gp.tileSize, gp.tileSize);
        down2 = setup("/player/player-20.png", gp.tileSize, gp.tileSize);
        down3 = setup("/player/player-21.png", gp.tileSize, gp.tileSize);
        down4 = setup("/player/player-22.png", gp.tileSize, gp.tileSize);
        down5 = setup("/player/player-23.png", gp.tileSize, gp.tileSize);
        down6 = setup("/player/player-24.png", gp.tileSize, gp.tileSize);

        right1 = setup("/player/player-25.png", gp.tileSize, gp.tileSize);
        right2 = setup("/player/player-26.png", gp.tileSize, gp.tileSize);
        right3 = setup("/player/player-27.png", gp.tileSize, gp.tileSize);
        right4 = setup("/player/player-28.png", gp.tileSize, gp.tileSize);
        right5 = setup("/player/player-29.png", gp.tileSize, gp.tileSize);
        right6 = setup("/player/player-30.png", gp.tileSize, gp.tileSize);

        left1 = setup("/player/player-37.png", gp.tileSize, gp.tileSize);
        left2 = setup("/player/player-38.png", gp.tileSize, gp.tileSize);
        left3 = setup("/player/player-39.png", gp.tileSize, gp.tileSize);
        left4 = setup("/player/player-40.png", gp.tileSize, gp.tileSize);
        left5 = setup("/player/player-41.png", gp.tileSize, gp.tileSize);
        left6 = setup("/player/player-42.png", gp.tileSize, gp.tileSize);

        upIdle1 = setup("/player/player-13.png", gp.tileSize, gp.tileSize);
        upIdle2 = setup("/player/player-14.png", gp.tileSize, gp.tileSize);
        upIdle3 = setup("/player/player-15.png", gp.tileSize, gp.tileSize);
        upIdle4 = setup("/player/player-16.png", gp.tileSize, gp.tileSize);
        upIdle5 = setup("/player/player-17.png", gp.tileSize, gp.tileSize);
        upIdle6 = setup("/player/player-18.png", gp.tileSize, gp.tileSize);

        downIdle1 = setup("/player/player-1.png", gp.tileSize, gp.tileSize);
        downIdle2 = setup("/player/player-2.png", gp.tileSize, gp.tileSize);
        downIdle3 = setup("/player/player-3.png", gp.tileSize, gp.tileSize);
        downIdle4 = setup("/player/player-4.png", gp.tileSize, gp.tileSize);
        downIdle5 = setup("/player/player-5.png", gp.tileSize, gp.tileSize);
        downIdle6 = setup("/player/player-6.png", gp.tileSize, gp.tileSize);

        leftIdle1 = setup("/player/player-43.png", gp.tileSize, gp.tileSize);
        leftIdle2 = setup("/player/player-44.png", gp.tileSize, gp.tileSize);
        leftIdle3 = setup("/player/player-45.png", gp.tileSize, gp.tileSize);
        leftIdle4 = setup("/player/player-46.png", gp.tileSize, gp.tileSize);
        leftIdle5 = setup("/player/player-47.png", gp.tileSize, gp.tileSize);
        leftIdle6 = setup("/player/player-48.png", gp.tileSize, gp.tileSize);

        rightIdle1 = setup("/player/player-7.png", gp.tileSize, gp.tileSize);
        rightIdle2 = setup("/player/player-8.png", gp.tileSize, gp.tileSize);
        rightIdle3 = setup("/player/player-9.png", gp.tileSize, gp.tileSize);
        rightIdle4 = setup("/player/player-10.png", gp.tileSize, gp.tileSize);
        rightIdle5 = setup("/player/player-11.png", gp.tileSize, gp.tileSize);
        rightIdle6 = setup("/player/player-12.png", gp.tileSize, gp.tileSize);
    }

    // LOAD ATTACK FRAMES
    public void getPlayerAttackImage() {

        attackUp1 = setup("/player/player attack up-1.png", gp.tileSize*2, gp.tileSize );
        attackUp2 = setup("/player/player attack up-2.png", gp.tileSize*2, gp.tileSize );
        attackUp3 = setup("/player/player attack up-3.png", gp.tileSize*2, gp.tileSize );
        attackUp4 = setup("/player/player attack up-4.png", gp.tileSize*2, gp.tileSize );

        attackDown1 = setup("/player/player attack down-1.png", gp.tileSize*2, gp.tileSize);
        attackDown2 = setup("/player/player attack down-2.png", gp.tileSize*2, gp.tileSize );
        attackDown3 = setup("/player/player attack down-3.png", gp.tileSize*2, gp.tileSize );
        attackDown4 = setup("/player/player attack down-4.png", gp.tileSize*2, gp.tileSize );

        attackLeft1 = setup("/player/player attack left-1.png", gp.tileSize *2, gp.tileSize);
        attackLeft2 = setup("/player/player attack left-2.png", gp.tileSize *2, gp.tileSize);
        attackLeft3 = setup("/player/player attack left-3.png", gp.tileSize *2, gp.tileSize);
        attackLeft4 = setup("/player/player attack left-4.png", gp.tileSize *2, gp.tileSize);

        attackRight1 = setup("/player/player attack right-1.png", gp.tileSize *2, gp.tileSize);
        attackRight2 = setup("/player/player attack right-2.png", gp.tileSize *2, gp.tileSize);
        attackRight3 = setup("/player/player attack right-3.png", gp.tileSize *2, gp.tileSize);
        attackRight4 = setup("/player/player attack right-4.png", gp.tileSize *2, gp.tileSize);
    }

    public BufferedImage setup(String imagePath, int width, int height) {
        UtilityTool uTool = new UtilityTool();
        BufferedImage image = null;

        try {
            image = read(getClass().getResourceAsStream(imagePath));
            image = uTool.scaleImage(image, width, height);
        } catch (IOException e) {
            System.err.println("Error loading: " + imagePath);
        }

        return image;
    }

    // UPDATE PLAYER
    public void update() {

        if (attacking) {
            attacking();
            return;
        }

        moving = false;

        // MOVEMENT
        if (keyH.upPressed) { direction = "up"; moving = true; }
        if (keyH.downPressed) { direction = "down"; moving = true; }
        if (keyH.leftPressed) { direction = "left"; moving = true; }
        if (keyH.rightPressed) { direction = "right"; moving = true; }

        // ENTER → INTERACT WITHOUT MOVING
        if (keyH.enterPressed) moving = true;

        if (moving) {

            collisionOn = false;
            gp.cChecker.checkTile(this);

            int objIndex = gp.cChecker.checkObject(this, true);
            pickUpObject(objIndex);

            int npcIndex = gp.cChecker.checkEntity(this, gp.npc);
            interactNPC(npcIndex);

            int monsterIndex = gp.cChecker.checkEntity(this, gp.monster);
            contactMonster(monsterIndex);

            gp.eHandler.checkEvent();

            if (!collisionOn) {
                if (keyH.upPressed) worldY -= speed;
                if (keyH.downPressed) worldY += speed;
                if (keyH.leftPressed) worldX -= speed;
                if (keyH.rightPressed) worldX += speed;
            }

            gp.keyH.enterPressed = false;

            spriteCounter++;
            if (spriteCounter > 18) { // slower animation
                spriteNum++;
                if (spriteNum > 6) spriteNum = 1;
                spriteCounter = 0;
            }
        }
        else {
            spriteCounter++;
            if (spriteCounter > 30) { // slower idle animation
                spriteNum++;
                if (spriteNum > 6) spriteNum = 1;
                spriteCounter = 0;
            }
        }

        if (invincible) {
            invincibleCounter++;
            if (invincibleCounter > 60) {
                invincible = false;
                invincibleCounter = 0;
            }
        }
    }

    // 4 FRAME ATTACKING
    public void attacking() {

        spriteCounter++;

        if (spriteCounter <= 10) {spriteNum = 1;}
        else if (spriteCounter <= 20) {
            spriteNum = 2;

            //Save the Current worldX, WorldY, Solid Area Width, Solid Area Height
            int currentWorldX = worldX;
            int currentWorldY = worldY;
            int solidAreaWidth = solidArea.width;
            int soldAreaHeight = solidArea.height;

            switch (direction){
                case "up": worldY -= attackArea.height; break;
                case "down": worldY += attackArea.height; break;
                case "left": worldX -= attackArea.width; break;
                case "right": worldX += attackArea.width; break;
            }

            //Attack Becomes Solid Area
            solidArea.width = attackArea.width;
            solidArea.height = attackArea.height;

            //Check Monster Collision Based with the Updated world X, World Y, and Solid Area
            int monsterIndex = gp.cChecker.checkEntity(this, gp.monster);
            damageMonster(monsterIndex);

            //After Checking Collision, Restores Original Size
            worldX = currentWorldX;
            worldY = currentWorldY;
            solidArea.width = solidAreaWidth;
            solidArea.height = soldAreaHeight;

        }
        else if (spriteCounter <= 30) {spriteNum = 3;}
        else if (spriteCounter <= 40) {spriteNum = 4;}

        if (spriteCounter > 40) {
            spriteCounter = 0;
            spriteNum = 1;
            attacking = false;
        }
    }

    public void draw(Graphics2D g2) {
        BufferedImage img = null;
        int drawX = screenX;
        int drawY = screenY;

        if (attacking) {
            switch (direction) {
                case "up":
                    img = getSprite(attackUp1, attackUp2, attackUp3, attackUp4);
                    drawX = screenX - gp.tileSize / 2; // center wide sprite horizontally
                    drawY = screenY; // move it up visually
                    break;
                case "down":
                    img = getSprite(attackDown1, attackDown2, attackDown3, attackDown4);
                    drawX = screenX - gp.tileSize / 2; // center wide sprite horizontally
                    drawY = screenY; // keep at player position vertically
                    break;
                case "left":
                    img = getSprite(attackLeft1, attackLeft2, attackLeft3, attackLeft4);
                    drawX = screenX - gp.tileSize / 2; // move left to center
                    drawY = screenY;
                    break;
                case "right":
                    img = getSprite(attackRight1, attackRight2, attackRight3, attackRight4);
                    drawX = screenX - gp.tileSize / 2; // already aligned
                    drawY = screenY;
                    break;
            }
        }
        // WALKING
        else if (moving) {
            switch (direction) {
                case "up":    img = getSprite(up1, up2, up3, up4, up5, up6); break;
                case "down":  img = getSprite(down1, down2, down3, down4, down5, down6); break;
                case "left":  img = getSprite(left1, left2, left3, left4, left5, left6); break;
                case "right": img = getSprite(right1, right2, right3, right4, right5, right6); break;
            }
        }
        // IDLE
        else {
            switch (direction) {
                case "up":    img = getSprite(upIdle1, upIdle2, upIdle3, upIdle4, upIdle5, upIdle6); break;
                case "down":  img = getSprite(downIdle1, downIdle2, downIdle3, downIdle4, downIdle5, downIdle6); break;
                case "left":  img = getSprite(leftIdle1, leftIdle2, leftIdle3, leftIdle4, leftIdle5, leftIdle6); break;
                case "right": img = getSprite(rightIdle1, rightIdle2, rightIdle3, rightIdle4, rightIdle5, rightIdle6); break;
            }
        }

        if (invincible == true) {
            g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.4f));
        }
        g2.drawImage(img, drawX, drawY, null);

        g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1f));
    }


    private BufferedImage getSprite(BufferedImage s1, BufferedImage s2, BufferedImage s3,
                                    BufferedImage s4, BufferedImage... extra) {

        switch (spriteNum) {
            case 1: return s1;
            case 2: return s2;
            case 3: return s3;
            case 4: return s4;
            case 5: return extra.length > 0 ? extra[0] : s1;
            case 6: return extra.length > 1 ? extra[1] : s1;
        }
        return s1;
    }

    public void pickUpObject(int i) {}

    // ENTER → INTERACT
    public void interactNPC(int i){
        if(gp.keyH.enterPressed){
            if(i != 999){
                gp.gameState = gp.dialogueState;
                gp.npc[i].speak();
            } else {
                attacking = true;
            }
        }
    }

    public void contactMonster(int i){
        if(i != 999){
            if(!invincible){
                life -= 1;
                invincible = true;
            }
        }
    }

    public void damageMonster(int i){
        if(i != 999){
           if(gp.monster[i].invincible == false){
                gp.monster[i].life -=1;
                gp.monster[i].invincible = true;
                if(gp.monster[i].life <=0){
                    gp.monster[i] = null;
                }
           }
        }
    }
}
