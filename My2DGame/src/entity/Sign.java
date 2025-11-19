package entity;

import main.GamePanel;
import java.awt.image.BufferedImage;
import java.awt.Graphics2D;

public class Sign extends Entity {

    public Sign(GamePanel gp) {
        super(gp);

        speed = 0;       // Static, doesn't move
        direction = "";  // No movement


        solidArea.width = gp.tileSize;
        solidArea.height = gp.tileSize;
        solidAreaDefaultX = solidArea.x;
        solidAreaDefaultY = solidArea.y;

        getImage();
        setDialogue();
    }

    // Single image for the sign
    public void getImage() {
        image = setup("/npc/sign-1"); // Generic sign image
    }

    // Example dialogues (can be customized per sign)
    public void setDialogue() {
        dialogues[0] = "This is Wayne's home.";
        dialogues[1] = "This is Wayne's home.";
        dialogues[2] = "This is Wayne's home.";

    }

    @Override
    public void update() {
        // Sign doesn't move, nothing to update
    }

    @Override
    public void draw(Graphics2D g2) {
        int screenX = worldX - gp.player.worldX + gp.player.screenX;
        int screenY = worldY - gp.player.worldY + gp.player.screenY;

        g2.drawImage(image, screenX, screenY, gp.tileSize, gp.tileSize, null);
    }

    @Override
    public void speak() {
        gp.gameState = gp.dialogueState;

        // Cycle through dialogues
        gp.ui.currentDialogue = dialogues[dialogueIndex];
        dialogueIndex++;
        if (dialogueIndex >= dialogues.length) {
            dialogueIndex = 0; // Loop back to first dialogue
        }
    }
}
