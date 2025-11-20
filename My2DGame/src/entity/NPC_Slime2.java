package entity;

import main.GamePanel;
import java.awt.image.BufferedImage;
import java.awt.Graphics2D;

public class NPC_Slime2 extends Entity {

    // 6-frame idle animation
    public BufferedImage idle1, idle2, idle3, idle4, idle5, idle6;

    public NPC_Slime2(GamePanel gp) {
        super(gp);

        speed = 0; // stationary

        getImage();
        setDialogue();
    }

    public void getImage() {
        idle1 = setup("/npc/slime-7", gp.tileSize, gp.tileSize);
        idle2 = setup("/npc/slime-8", gp.tileSize, gp.tileSize);
        idle3 = setup("/npc/slime-9", gp.tileSize, gp.tileSize);
        idle4 = setup("/npc/slime-10", gp.tileSize, gp.tileSize);
        idle5 = setup("/npc/slime-11", gp.tileSize, gp.tileSize);
        idle6 = setup("/npc/slime-12", gp.tileSize, gp.tileSize);
    }

    public void setDialogue() {
        dialogues[0] = "The hell you lookin' at";
        dialogues[1] = "Let me tell you something";
        dialogues[2] = "I hate jews";
    }

    @Override
    public void update() {
        // Cycle through idle animation frames
        spriteCounter++;
        if (spriteCounter > 12) { // animation speed
            spriteNum++;
            if (spriteNum > 6) spriteNum = 1;
            spriteCounter = 0;
        }
    }

    @Override
    public void draw(Graphics2D g2) {
        BufferedImage image = null;

        switch (spriteNum) {
            case 1: image = idle1; break;
            case 2: image = idle2; break;
            case 3: image = idle3; break;
            case 4: image = idle4; break;
            case 5: image = idle5; break;
            case 6: image = idle6; break;
        }

        int screenX = worldX - gp.player.worldX + gp.player.screenX;
        int screenY = worldY - gp.player.worldY + gp.player.screenY;

        g2.drawImage(image, screenX, screenY, gp.tileSize, gp.tileSize, null);
    }
}
