package entity;
import main.GamePanel;

public class NPC_OldMan extends Entity {

    // Movement pattern
    String[] pattern = {"left", "left", "left", "up", "right", "right", "right", "down"};
    int patternIndex = 0;
    int patternDuration = 60;   // how long each direction lasts (60 frames = 1 sec)
    int patternCounter = 0;

    public NPC_OldMan (GamePanel gamePanel){
        super(gamePanel);

        type = 1;
        direction = "down";
        speed = 1;

        getImage();
        setDialogue();
    }

    public void getImage(){
        up1 = setup("/npc/oldman_down_1", gp.tileSize, gp.tileSize);
        up2 = setup("/npc/oldman_up_2", gp.tileSize, gp.tileSize);

        down1 = setup("/npc/oldman_down_1", gp.tileSize, gp.tileSize);
        down2 = setup("/npc/oldman_down_2", gp.tileSize, gp.tileSize);

        left1 = setup("/npc/oldman_left_1", gp.tileSize, gp.tileSize);
        left2 = setup("/npc/oldman_left_2", gp.tileSize, gp.tileSize);

        right1 = setup("/npc/oldman_right_1", gp.tileSize, gp.tileSize);
        right2 = setup("/npc/oldman_right_2", gp.tileSize, gp.tileSize);
    }

    public void setDialogue(){
        dialogues[0] ="Hello, My Nigga!";
        dialogues[1] ="So You've Come To This Island To \nFarm Cotton?";
        dialogues[2] ="You are now my Slave As of Today";
        dialogues[3] ="Now Go Work Nigga Ahh Bitch!";
    }

    @Override
    public void setAction() {

        // Count frames
        patternCounter++;

        // Move to next direction after duration
        if (patternCounter >= patternDuration) {
            patternIndex++;
            if (patternIndex >= pattern.length) {
                patternIndex = 0;  // loop pattern
            }

            direction = pattern[patternIndex];
            patternCounter = 0;
        }
    }

    @Override
    public void speak(){
        super.speak();
    }
}
