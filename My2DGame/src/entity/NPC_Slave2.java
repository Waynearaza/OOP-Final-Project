package entity;

import main.GamePanel;
import java.awt.image.BufferedImage;
import java.awt.Graphics2D;

public class NPC_Slave2 extends Entity {


    public NPC_Slave2(GamePanel gp) {
        super(gp);

        speed = 0; // stationary

        getImage();
        setDialogue();
    }

    public void getImage() {
        // ASSIGNING TO INHERITED FIELDS idle1 and idle2 FOR CORRECT DRAWING
        // The images are scaled here to 2x the tileSize
        idle1 = setup("/npc/slave13", gp.tileSize * 2, gp.tileSize * 2);
        idle2 = setup("/npc/slave14", gp.tileSize * 2, gp.tileSize * 2);
    }

    public void setDialogue() {
        dialogues[0][0] = "Master, I'm so tired...";
        dialogues[0][1] = "Nice weather today.";
        dialogues[0][2] = "Stay safe on your journey.";
    }

    @Override
    public void update() {
        // Cycle through idle animation frames
        spriteCounter++;
        if (spriteCounter > 12) { // animation speed
            spriteNum++;
            if (spriteNum > 2) spriteNum = 1;
            spriteCounter = 0;
        }
    }

    @Override
    public void draw(Graphics2D g2) {
        BufferedImage image = null;

        switch (spriteNum) {
            case 1:
                image = idle1;
                break;
            case 2:
                image = idle2;
                break;
        }

        // --- FIX: Adjust screen coordinates for centering ---
        // The image is 2x tileSize, so it's 1 tileSize too wide/tall.
        // Shift left and up by half of the extra tileSize.
        int drawOffset = gp.tileSize / 6;

        int screenX = worldX - gp.player.worldX + gp.player.screenX - drawOffset;
        int screenY = worldY - gp.player.worldY + gp.player.screenY - drawOffset;
        // ----------------------------------------------------

        // The image is drawn at 2x the size (gp.tileSize * 2)
        g2.drawImage(image, screenX, screenY, gp.tileSize * 2, gp.tileSize * 2, null);
    }

    @Override
    public void speak() {

        facePlayer();

        // When -1, force it to 0
        if (dialogueSet == -1) {
            dialogueSet = 0;
        }

        startDialogue(this, dialogueSet);

        dialogueSet++;

        // Prevent out of bounds
        if (dialogueSet >= dialogues.length || dialogues[dialogueSet][0] == null) {
            dialogueSet = 0; // loop
        }
    }
}