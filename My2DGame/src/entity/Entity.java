package entity;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;

import main.GamePanel;
import main.UtilityTool;

import javax.imageio.ImageIO;

public class Entity {
    public GamePanel gp;

    // Position in the world
    public int worldX, worldY;

    // Movement speed
    public int speed;

    // Sprite arrays (6 frames per direction)
    public BufferedImage up1, up2;
    public BufferedImage down1, down2;
    public BufferedImage left1, left2;
    public BufferedImage right1, right2;

    // Sprite attack arrays (4 frames per direction)
    public BufferedImage attackUp1, attackUp2, attackUp3, attackUp4;
    public BufferedImage attackDown1, attackDown2, attackDown3, attackDown4;
    public BufferedImage attackRight1, attackRight2, attackRight3, attackRight4;
    public BufferedImage attackLeft1, attackLeft2, attackLeft3, attackLeft4;


    public String direction = "down";

    // Collision
    public Rectangle solidArea = new Rectangle(0, 0, 48, 48);
    public  Rectangle attackArea = new Rectangle(0, 0,0,0);
    public int solidAreaDefaultX, solidAreaDefaultY;
    public boolean collisionOn = false;

    //actionLock
    public int actionLockCounter = 0;

    //Invincible Time
    public boolean invincible = false;
    public int invincibleCounter = 0;
    int dyingCounter = 0;
    int hpBarCounter = 0;

    boolean attacking = false;
    public boolean alive = true;
    public boolean dying = false;
    boolean hpBarOn = false;

    //NPC Dialogues
    String dialogues[] = new String[20];
    int dialogueIndex = 0;

    //From Super Object
    public BufferedImage image, image2, image3;
    public String name;
    public boolean collision = false;
    public int type; // 0 = Player, 1 = NPC, 2 = Monster

    //CHARACTER STATUS
    public int maxLife;
    public int life;




    // Animation
    public int spriteCounter = 0;
    public int spriteNum = 1; // 1 → 6


    //Entity
    public Entity(GamePanel gp) {
        this.gp = gp;
    }

    public void setAction(){}

    public void damageReaction(){}

    //Set the Behavior Of the NPC When Talk To
    public void speak(){
        //Restart the Dialogue of the npc when all of it is completed and prevent an error
        if(dialogues[dialogueIndex] == null){
            dialogueIndex =0;
        }
        gp.ui.currentDialogue = dialogues[dialogueIndex];
        dialogueIndex++;

        switch (gp.player.direction){
            case "up": direction = "down"; break;
            case "down": direction = "up"; break;
            case "left": direction = "right"; break;
            case "right": direction = "left"; break;

        }
    }

    public void update(){
        setAction();

        collisionOn = false;
        gp.cChecker.checkTile(this);
        gp.cChecker.checkObject(this, false); //Checks Object Collision
        gp.cChecker.checkEntity(this, gp.npc);
        gp.cChecker.checkEntity(this, gp.monster);
        gp.cChecker.checkPlayer(this); //Checks Player Collision
        boolean contactPlayer = gp.cChecker.checkPlayer(this);

        if(this.type == 2 && contactPlayer == true){
           if(gp.player.invincible == false) {
                //We Can Give Damage
               gp.playSE(6);
               gp.player.life -= 1;
               gp.player.invincible = true;
           }
        }

        if (collisionOn == false){
            switch (direction){
                case "up": worldY -=speed; break;
                case "down": worldY +=speed; break;
                case "left": worldX -=speed; break;
                case "right": worldX +=speed; break;
            }
        }

        spriteCounter++;
        if(spriteCounter > 12){
            if(spriteNum ==1){
                spriteNum =2;
            } else if (spriteNum== 2) {
                spriteNum = 1;
            }
            spriteCounter = 0;
        }

        if (invincible) {
            invincibleCounter++;
            if (invincibleCounter > 60) {
                invincible = false;
                invincibleCounter = 0;
            }
        }
    }


    public void draw(Graphics2D g2) {
        BufferedImage image = null;

        int screenX = worldX - gp.player.worldX + gp.player.screenX;
        int screenY = worldY - gp.player.worldY + gp.player.screenY;

        // Only draw tiles that are on the screen
        if (worldX + gp.tileSize > gp.player.worldX - gp.player.screenX &&
                worldX - gp.tileSize < gp.player.worldX + gp.player.screenX &&
                worldY + gp.tileSize > gp.player.worldY - gp.player.screenY &&
                worldY - gp.tileSize < gp.player.worldY + gp.player.screenY) {

            switch(direction) {
                case "up":
                    if(spriteNum == 1) image = up1;
                    else image = up2;
                    break;

                case "down":
                    if(spriteNum == 1) image = down1;
                    else image = down2;
                    break;

                case "left":
                    if(spriteNum == 1) image = left1;
                    else image = left2;
                    break;

                case "right":
                    if(spriteNum == 1) image = right1;
                    else image = right2;
                    break;
            }

            //Monster HP Bar
            if(type == 2 &&  hpBarOn == true){
                double oneScale = (double)gp.tileSize/maxLife;
                double hpBarValue = oneScale *life;


                g2.setColor(new Color(35, 35,35));
                g2.fillRect(screenX -1, screenY - 16, gp.tileSize+2, 12 );
                g2.setColor(new Color(255, 0, 30));
                g2.fillRect(screenX, screenY - 15, (int)hpBarValue, 10);

                hpBarCounter++;

                if(hpBarCounter >600){
                    hpBarCounter = 0;
                    hpBarOn = false;
                }
            }

            if (invincible == true) {
                hpBarOn = true;
                hpBarCounter = 0;
                changeAlpha(g2,0.4F);
            }

            if(dying == true){
                dyingAnimation(g2);
            }
            g2.drawImage(image, screenX, screenY, gp.tileSize, gp.tileSize, null);
            changeAlpha(g2,1F);
        }
    }

    public void dyingAnimation(Graphics2D g2){

        dyingCounter++;

        int i = 5;

        if(dyingCounter <= i){
            changeAlpha(g2, 0f);
        }
        if(dyingCounter > i*2 && dyingCounter <= i*3){
            changeAlpha(g2, 1f);
        }
        if(dyingCounter > i*3 && dyingCounter <= i*4){
            changeAlpha(g2, 0f);
        }
        if(dyingCounter > i*4 && dyingCounter <= i*5){
            changeAlpha(g2, 1f);
        }
        if(dyingCounter > i*5 && dyingCounter <= i*6){
            changeAlpha(g2, 0f);
        }
        if(dyingCounter > i*6 && dyingCounter <= i*7){
            changeAlpha(g2, 1f);
        }
        if(dyingCounter > i*7 && dyingCounter <= i*8){
            changeAlpha(g2, 0f);
        }
        if(dyingCounter > i*8 ){
            dying = false;
            alive = false;
        }
    }

    public void changeAlpha(Graphics2D g2, float alphaValue){
        g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, alphaValue));
    }
    public BufferedImage setup(String imagePath, int width, int height) {
        UtilityTool uTool = new UtilityTool();
        BufferedImage image = null;

        try {
            image = ImageIO.read(getClass().getResourceAsStream(imagePath + ".png"));
            image = uTool.scaleImage(image, width, height);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return image;
    }
}