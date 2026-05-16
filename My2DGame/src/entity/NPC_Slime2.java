package entity;

import main.GamePanel;
import java.awt.image.BufferedImage;
import java.awt.Graphics2D;

public class NPC_Slime2 extends Entity {

    // 6-frame idle animation variables
    public BufferedImage idle1, idle2, idle3, idle4, idle5, idle6;

    public NPC_Slime2(GamePanel gp) {
        super(gp);

        direction = "down";
        speed = 0;

        // IMPORTANT: Set type to 1 (NPC) so the collision checker knows it's interactable
        type = 1;

        // MATCHING OLD MAN LOGIC:
        // Your working Old Man class starts this at -1.
        // We must match that behavior to ensure the dialogue cycle works correctly.
        dialogueSet = -1;

        // HITBOX (CRITICAL):
        // Without this, the interaction raycast passes through the empty space.
        solidArea.x = 8;
        solidArea.y = 16;
        solidArea.width = 32;
        solidArea.height = 32;
        solidAreaDefaultX = solidArea.x;
        solidAreaDefaultY = solidArea.y;

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
        dialogues[0][0] = "Traveler, you need to help us.";
        dialogues[0][1] = "Our village has been invaded by the possessed slimes.";
        dialogues[0][2] = "Be careful now, you're gonna need it.";
    }

    // Interaction method called by Player when pressing Enter
    @Override
    public void speak() {
        // 1. Face the player
        facePlayer();

        // 2. Start the dialogue using the Entity helper
        startDialogue(this, dialogueSet);

        // 3. Increment dialogueSet (Logic matching your Old Man class)
        dialogueSet++;

        // 4. Safety check: if the next set is empty, loop back or stay on current
        if (dialogues[dialogueSet][0] == null) {
            dialogueSet--;
            // Note: If you want it to loop back to the start, use: dialogueSet = 0;
        }
    }

    @Override
    public void update() {
        // Custom animation loop for the slime (6 frames)
        spriteCounter++;
        if (spriteCounter > 12) { // Animation speed (higher = slower)
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

        // Optimization: Only draw if on screen
        if (worldX + gp.tileSize > gp.player.worldX - gp.player.screenX &&
                worldX - gp.tileSize < gp.player.worldX + gp.player.screenX &&
                worldY + gp.tileSize > gp.player.worldY - gp.player.screenY &&
                worldY - gp.tileSize < gp.player.worldY + gp.player.screenY) {

            g2.drawImage(image, screenX, screenY, gp.tileSize, gp.tileSize, null);
        }

    }



}