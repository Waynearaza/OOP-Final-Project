package main;

import entity.*;
import object.OBJ_Boots;
import object.OBJ_Chest;
import object.OBJ_Door;
import object.OBJ_Key;

public class AssetSetter {

    GamePanel gp;

    public AssetSetter(GamePanel gamePanel){
        this.gp = gamePanel;
    }

    //Sets the Object on the Map
    public void setObject(){

    }

    public void setNPC(){
        gp.npc[0] = new NPC_OldMan(gp);
        gp.npc[0].worldX = gp.tileSize*20;
        gp.npc[0].worldY = gp.tileSize*21;

        gp.npc[1] = new NPC_Slime(gp);
        gp.npc[1].worldX = gp.tileSize*28;
        gp.npc[1].worldY = gp.tileSize*19;

        gp.npc[2] = new NPC_Slime2(gp);
        gp.npc[2].worldX = gp.tileSize*31;
        gp.npc[2].worldY = gp.tileSize*27;

        gp.npc[3] = new Sign(gp);
        gp.npc[3].worldX = gp.tileSize*21;
        gp.npc[3].worldY = gp.tileSize*19;

        gp.npc[4] = new Sign2(gp);
        gp.npc[4].worldX = gp.tileSize*25;
        gp.npc[4].worldY = gp.tileSize*19;

        gp.npc[5] = new Door(gp);
        gp.npc[5].worldX = gp.tileSize*27;
        gp.npc[5].worldY = gp.tileSize*18;

    }
}
