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

        dialogueSet = -1;

        getImage();
        setDialogue();
    }

    public void getImage(){
        up1 = setup("/npc/oldman_up_1", gp.tileSize, gp.tileSize);
        up2 = setup("/npc/oldman_up_2", gp.tileSize, gp.tileSize);

        down1 = setup("/npc/oldman_down_1", gp.tileSize, gp.tileSize);
        down2 = setup("/npc/oldman_down_2", gp.tileSize, gp.tileSize);

        left1 = setup("/npc/oldman_left_1", gp.tileSize, gp.tileSize);
        left2 = setup("/npc/oldman_left_2", gp.tileSize, gp.tileSize);

        right1 = setup("/npc/oldman_right_1", gp.tileSize, gp.tileSize);
        right2 = setup("/npc/oldman_right_2", gp.tileSize, gp.tileSize);
    }

    public void setDialogue(){
        dialogues[0][0] ="Greetings, Traveler!";
        dialogues[0][1] ="We are in grave danger and we need your help!";
        dialogues[0][2] ="The possessed slimes are invading our village \n" +
                "and we need your help to stop them.";
        dialogues[0][3] ="Defeat the Giant Skeleton for our village to \n" +
                "be free of their tyranny.";


        dialogues[1][0] ="1Good luck, traveler!";
        dialogues[1][1] ="2Good luck, traveler!";
        dialogues[1][2] ="3Good luck, traveler!";

        dialogues[2][0] ="4Good luck, traveler!";

    }

    @Override
    public void setAction() {


        if(onPath == true){

            //int goalCol = 11;
           //int goalRow = 9;'
            int goalCol = (gp.player.worldX + gp.player.solidArea.x)/gp.tileSize;
            int goalRow = (gp.player.worldY + gp.player.solidArea.y)/gp.tileSize;



            searchPath(goalCol, goalRow);
        }
        else {
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
    }

    @Override
    public void speak(){
        facePlayer();
        startDialogue(this, dialogueSet);

        dialogueSet++;

        if(dialogues[dialogueSet][0] == null){
            dialogueSet--;
        }


        //onPath = true;

    }
}
