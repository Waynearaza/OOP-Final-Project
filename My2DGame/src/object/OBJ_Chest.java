package object;

import entity.Entity;
import main.GamePanel;

import javax.imageio.ImageIO;
import java.io.IOException;

public class OBJ_Chest extends Entity {
    GamePanel gp;
    Entity loot;
    boolean opened = false;

    public  OBJ_Chest(GamePanel gp, Entity loot) {
        super(gp);
        this.gp = gp;
        this.loot = loot;

        type = type_obstacle;
        name = "Chest";

        image = setup("/objects/chest", gp.tileSize, gp.tileSize);
        image2 = setup("/objects/chest_opened", gp.tileSize, gp.tileSize);
        down1 = image;
        collision = true;

        solidArea.x = 4;
        solidArea.y =  16;
        solidArea.width = 40;
        solidArea.height = 32;
        solidAreaDefaultX = solidArea.x;
        solidAreaDefaultY = solidArea.y;
        setDialogue();
    }

    public void setLoot(Entity loot){
        this.loot = loot;

        setDialogue();
    }

    public void setDialogue(){
        dialogues[0][0] = "You opened this chest and found a " + loot.name +"!\nBut You're Inventory is Full";
        dialogues[1][0] = "You opened this chest and found a " + loot.name +"\nYou Obtained the " + loot.name +"!";
        dialogues[2][0] = "It's Empty";

    }

    public void interact(){
        if(opened == false){
            gp.playSE(3);

            if(gp.player.canObtainItem(loot) == false){
                startDialogue(this, 0);
            }
            else {
                startDialogue(this, 1);
                down1 = image2;
                opened = true;
            }
        }
        else {
            startDialogue(this, 2);
        }
    }
}
