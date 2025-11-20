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

    boolean attacking = false;

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

            if (invincible == true) {
                g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.4f));
            }
            g2.drawImage(image, screenX, screenY, gp.tileSize, gp.tileSize, null);
        }
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