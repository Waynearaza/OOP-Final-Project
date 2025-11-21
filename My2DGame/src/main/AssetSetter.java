package main;

import entity.NPC_OldMan;
import entity.NPC_Slime;
import entity.NPC_Slime2;
import monster.MON_GreenSlime;
import object.*;

public class AssetSetter {

    GamePanel gp;

    public AssetSetter(GamePanel gamePanel){
        this.gp = gamePanel;
    }

    //Sets the Object on the Map
    public void setObject(){
        //gp.obj[0] = new OBJ_Door(gp);
        //gp.obj[0].worldX = gp.tileSize*21;
        //gp.obj[0].worldY = gp.tileSize*22;

        //gp.obj[1] = new OBJ_Door(gp);
        //gp.obj[1].worldX = gp.tileSize*23;
        //gp.obj[1].worldY = gp.tileSize*25;

        gp.obj[0] = new OBJ_Bridge1(gp);
        gp.obj[0].worldX = gp.tileSize*41;
        gp.obj[0].worldY = gp.tileSize*8;

        gp.obj[1] = new OBJ_Bridge2(gp);
        gp.obj[1].worldX = gp.tileSize*41;
        gp.obj[1].worldY = gp.tileSize*9;

        gp.obj[2] = new OBJ_Bridge3(gp);
        gp.obj[2].worldX = gp.tileSize*41;
        gp.obj[2].worldY = gp.tileSize*10;

        gp.obj[3] = new OBJ_Bridge4(gp);
        gp.obj[3].worldX = gp.tileSize*42;
        gp.obj[3].worldY = gp.tileSize*8;
    }

    public void setNPC(){
        gp.npc[0] = new NPC_OldMan(gp);
        gp.npc[0].worldX = gp.tileSize*21;
        gp.npc[0].worldY = gp.tileSize*21;

        gp.npc[1] = new NPC_Slime(gp);
        gp.npc[1].worldX = gp.tileSize*28;
        gp.npc[1].worldY = gp.tileSize*19;

        gp.npc[2] = new NPC_Slime2(gp);
        gp.npc[2].worldX = gp.tileSize*31;
        gp.npc[2].worldY = gp.tileSize*27;

    }

    public void setMonster(){

        int i = 0;
        gp.monster[i] = new MON_GreenSlime(gp);
        gp.monster[i].worldX = gp.tileSize*23;
        gp.monster[i].worldY = gp.tileSize*36;
        i++;

        gp.monster[i] = new MON_GreenSlime(gp);
        gp.monster[i].worldX = gp.tileSize*23;
        gp.monster[i].worldY = gp.tileSize*37;
        i++;


        gp.monster[i] = new MON_GreenSlime(gp);
        gp.monster[i].worldX = gp.tileSize*24;
        gp.monster[i].worldY = gp.tileSize*37;
        i++;


        gp.monster[i] = new MON_GreenSlime(gp);
        gp.monster[i].worldX = gp.tileSize*24;
        gp.monster[i].worldY = gp.tileSize*42;
        i++;


        gp.monster[i] = new MON_GreenSlime(gp);
        gp.monster[i].worldX = gp.tileSize*38;
        gp.monster[i].worldY = gp.tileSize*42;
        i++;


    }
}
