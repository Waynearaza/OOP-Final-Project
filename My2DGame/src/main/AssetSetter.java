package main;

import entity.NPC_Merchant;
import entity.NPC_OldMan;
import entity.NPC_Slime;
import entity.NPC_Slime2;
import monster.MON_GreenSlime;
import object.*;
import tiles_interactive.IT_DryTree;

public class AssetSetter {

    GamePanel gp;

    public AssetSetter(GamePanel gamePanel){
        this.gp = gamePanel;
    }

    //Sets the Object on the Map
    public void setObject(){
        int mapNum = 0;
        int i = 0;
        //gp.obj[0] = new OBJ_Door(gp);
        //gp.obj[0].worldX = gp.tileSize*21;
        //gp.obj[0].worldY = gp.tileSize*22;

        //gp.obj[1] = new OBJ_Door(gp);
        //gp.obj[1].worldX = gp.tileSize*23;
        //gp.obj[1].worldY = gp.tileSize*25;

        gp.obj[mapNum][i] = new OBJ_Bridge1(gp);
        gp.obj[mapNum][i].worldX = gp.tileSize*41;
        gp.obj[mapNum][i].worldY = gp.tileSize*8;
        i++;

        gp.obj[mapNum][i] = new OBJ_Bridge2(gp);
        gp.obj[mapNum][i].worldX = gp.tileSize*41;
        gp.obj[mapNum][i].worldY = gp.tileSize*9;
        i++;

        gp.obj[mapNum][i] = new OBJ_Bridge3(gp);
        gp.obj[mapNum][i].worldX = gp.tileSize*41;
        gp.obj[mapNum][i].worldY = gp.tileSize*10;
        i++;

        gp.obj[mapNum][i] = new OBJ_Bridge4(gp);
        gp.obj[mapNum][i].worldX = gp.tileSize*42;
        gp.obj[mapNum][i].worldY = gp.tileSize*8;
        i++;

        gp.obj[mapNum][i] = new OBJ_Coin_Bronze(gp);
        gp.obj[mapNum][i].worldX = gp.tileSize*23;
        gp.obj[mapNum][i].worldY = gp.tileSize*19;
        i++;

        gp.obj[mapNum][i] = new OBJ_Axe(gp);
        gp.obj[mapNum][i].worldX = gp.tileSize*33;
        gp.obj[mapNum][i].worldY = gp.tileSize*21;
        i++;

        gp.obj[mapNum][i] = new OBJ_Shield_Blue(gp);
        gp.obj[mapNum][i].worldX = gp.tileSize*35;
        gp.obj[mapNum][i].worldY = gp.tileSize*21;
        i++;

        gp.obj[mapNum][i] = new OBJ_Potion_Red(gp);
        gp.obj[mapNum][i].worldX = gp.tileSize*22;
        gp.obj[mapNum][i].worldY = gp.tileSize*27;
        i++;

        gp.obj[mapNum][i] = new OBJ_Potion_Red(gp);
        gp.obj[mapNum][i].worldX = gp.tileSize*23;
        gp.obj[mapNum][i].worldY = gp.tileSize*27;
        i++;


        gp.obj[mapNum][i] = new OBJ_Potion_Red(gp);
        gp.obj[mapNum][i].worldX = gp.tileSize*23;
        gp.obj[mapNum][i].worldY = gp.tileSize*28;
        i++;


        gp.obj[mapNum][i] = new OBJ_Heart(gp);
        gp.obj[mapNum][i].worldX = gp.tileSize*22;
        gp.obj[mapNum][i].worldY = gp.tileSize*29;
        i++;

        gp.obj[mapNum][i] = new OBJ_ManaCrystal(gp);
        gp.obj[mapNum][i].worldX = gp.tileSize*22;
        gp.obj[mapNum][i].worldY = gp.tileSize*31;
        i++;

        gp.obj[mapNum][i] = new OBJ_Door(gp);
        gp.obj[mapNum][i].worldX = gp.tileSize*14;
        gp.obj[mapNum][i].worldY = gp.tileSize*28;
        i++;

        gp.obj[mapNum][i] = new OBJ_Door(gp);
        gp.obj[mapNum][i].worldX = gp.tileSize*10;
        gp.obj[mapNum][i].worldY = gp.tileSize*12;
        i++;

        gp.obj[mapNum][i] = new OBJ_Chest(gp, new OBJ_Key(gp));
        gp.obj[mapNum][i].worldX = gp.tileSize*23;
        gp.obj[mapNum][i].worldY = gp.tileSize*29;
        i++;
    }

    public void setNPC(){
        int mapNum = 0;
        int i =0;
        gp.npc[mapNum][i] = new NPC_OldMan(gp);
        gp.npc[mapNum][i].worldX = gp.tileSize*21;
        gp.npc[mapNum][i].worldY = gp.tileSize*21;
        i++;

        gp.npc[mapNum][i] = new NPC_Slime(gp);
        gp.npc[mapNum][i].worldX = gp.tileSize*28;
        gp.npc[mapNum][i].worldY = gp.tileSize*19;
        i++;

        gp.npc[mapNum][i] = new NPC_Slime2(gp);
        gp.npc[mapNum][i].worldX = gp.tileSize*31;
        gp.npc[mapNum][i].worldY = gp.tileSize*27;
        i++;

        mapNum = 1;
        i = 0;
        gp.npc[mapNum][i] = new NPC_Merchant(gp);
        gp.npc[mapNum][i].worldX = gp.tileSize*12;
        gp.npc[mapNum][i].worldY = gp.tileSize*7;
        i++;


    }

    public void setMonster(){
        int mapNum = 0;
        int i = 0;
        gp.monster[mapNum][i] = new MON_GreenSlime(gp);
        gp.monster[mapNum][i].worldX = gp.tileSize*23;
        gp.monster[mapNum][i].worldY = gp.tileSize*36;
        i++;

        gp.monster[mapNum][i] = new MON_GreenSlime(gp);
        gp.monster[mapNum][i].worldX = gp.tileSize*23;
        gp.monster[mapNum][i].worldY = gp.tileSize*37;
        i++;


        gp.monster[mapNum][i] = new MON_GreenSlime(gp);
        gp.monster[mapNum][i].worldX = gp.tileSize*24;
        gp.monster[mapNum][i].worldY = gp.tileSize*37;
        i++;


        gp.monster[mapNum][i] = new MON_GreenSlime(gp);
        gp.monster[mapNum][i].worldX = gp.tileSize*24;
        gp.monster[mapNum][i].worldY = gp.tileSize*42;
        i++;


        gp.monster[mapNum][i] = new MON_GreenSlime(gp);
        gp.monster[mapNum][i].worldX = gp.tileSize*38;
        gp.monster[mapNum][i].worldY = gp.tileSize*42;
        i++;

        //Sets Mobs On New Map
        //mapNum = 1;
        //gp.monster[mapNum][i] = new MON_GreenSlime(gp);
        // gp.monster[mapNum][i].worldX = gp.tileSize*23;
        //gp.monster[mapNum][i].worldY = gp.tileSize*36;
        i++;
    }

    public void setInteractiveTile(){
        int mapNum = 0;
        int i = 0;
        gp.iTile[mapNum][i] = new IT_DryTree(gp, 25, 14);i++;




        gp.iTile[mapNum][i] = new IT_DryTree(gp, 30, 20);i++;
        gp.iTile[mapNum][i] = new IT_DryTree(gp, 30, 22);i++;

        gp.iTile[mapNum][i] = new IT_DryTree(gp, 20, 20);i++;

        gp.iTile[mapNum][i] = new IT_DryTree(gp, 20, 22);i++;
    }

}
