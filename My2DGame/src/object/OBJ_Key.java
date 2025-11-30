package object;

import entity.Entity;
import main.GamePanel;

import javax.imageio.ImageIO;

import java.io.IOException;

public class OBJ_Key extends Entity {
    public OBJ_Key(GamePanel gp) {
        super(gp);

        type = type_consumable;
        name = "Key";
        down1 = setup("/objects/key", gp.tileSize, gp.tileSize);
        //pickUpAble = true;
        description ="[" + name + "]\nOpens A Door";
        price = 100;
        stackable = true;
        setDialogue();

    }

    public void setDialogue(){
        dialogues[0][0] ="You used the " + name + " to open this door";

        dialogues[1][0] = "Are Dumb there is no use for this RN Nigger";
    }

    public boolean use(Entity entity){
        gp.gameState = gp.dialogueState;

        int objIndex = getDetected(entity, gp.obj, "Door");

        if(objIndex != 999){
            startDialogue(this, 0);
            gp.playSE(3);
            gp.obj[gp.currentMap][objIndex] = null;
            return true;
        }
        else {
            startDialogue(this, 1);
            return false;
        }

    }
}

