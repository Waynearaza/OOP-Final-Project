package entity;
import main.GamePanel;

import java.util.Random;

public class NPC_OldMan extends Entity {
     public NPC_OldMan (GamePanel gamePanel){
         super(gamePanel);

         type = 1;
         direction = "down";
         speed = 1;

         getImage();
         setDialogue();
     }

     // Gets image of Old Man NPC on resources
     public void getImage(){
         up1 = setup("/npc/oldman_down_1");
         up2 = setup("/npc/oldman_up_2");

         down1 = setup("/npc/oldman_down_1");
         down2 = setup("/npc/oldman_down_2");


         left1 = setup("/npc/oldman_left_1");
         left2 = setup("/npc/oldman_left_2");

         right1 = setup("/npc/oldman_right_1");
         right2 = setup("/npc/oldman_right_2");
     }

     public void setDialogue(){
         dialogues[0] ="Hello, My Nigga!";
         dialogues[1] ="So You've Come To This Island To \nFarm Cotton?";
         dialogues[2] ="You are now my Slave As of Today";
         dialogues[3] ="Now Go Work Nigga Ahh Bitch!";
     }

     //Sets the action of the Old Man
     public void setAction(){

         actionLockCounter ++;

         if(actionLockCounter == 120){
             Random random = new Random();
             int i = random.nextInt(100)+1; //Pickup a number from 1 to 100

             if(i <= 25){
                 direction = "up";
             }
             if (i > 25 && i <=50){
                 direction = "down";
             }
             if (i > 50 && i <=75){
                 direction = "left";
             }
             if (i > 75 && i <=100){
                 direction = "right";
             }
             actionLockCounter = 0;

         }
     }

     public void speak(){
         //Do THis Character Specific Stuff and Shi
        super.speak();
     }
}
