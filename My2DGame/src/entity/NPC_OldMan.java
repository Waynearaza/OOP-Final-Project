package entity;

import main.GamePanel;

public class NPC_OldMan extends Entity {

    // Pattern array for movement directions
    private String[] pattern = {"left", "left", "left", "up", "right", "right", "right", "down"};
    private int patternIndex = 0;

    public NPC_OldMan(GamePanel gp) {
        super(gp);
        direction = pattern[patternIndex]; // Start with first direction
        speed = 1;

        getImage();
        setDialogue();
    }

    public void getImage() {
        up1 = setup("/npc/oldman_up_1");
        up2 = setup("/npc/oldman_up_2");
        down1 = setup("/npc/oldman_down_1");
        down2 = setup("/npc/oldman_down_2");
        left1 = setup("/npc/oldman_left_1");
        left2 = setup("/npc/oldman_left_2");
        right1 = setup("/npc/oldman_right_1");
        right2 = setup("/npc/oldman_right_2");
    }

    public void setDialogue() {
        dialogues[0] = "Hello there!";
        dialogues[1] = "Welcome to the island.";
        dialogues[2] = "Take care of yourself!";
        dialogues[3] = "Enjoy your adventure!";
    }


    // Move according to the fixed pattern
    public void setAction() {
        actionLockCounter++;

        if (actionLockCounter >= 60) { // Change direction every 60 frames
            // Move to next direction in pattern
            patternIndex++;
            if (patternIndex >= pattern.length) patternIndex = 0;

            direction = pattern[patternIndex];
            actionLockCounter = 0;
        }
    }

    @Override
    public void speak() {
        super.speak();
    }
}
