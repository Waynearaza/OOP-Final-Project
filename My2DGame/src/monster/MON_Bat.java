package monster;

import entity.Entity;
import main.GamePanel;
import object.OBJ_Coin_Bronze;
import object.OBJ_Heart;
import object.OBJ_ManaCrystal;
import object.OBJ_Rock;

import java.util.Random;

public class MON_Bat extends Entity {
    GamePanel gp;

    public MON_Bat(GamePanel gp) {
        super(gp);

        this.gp = gp;


        type = type_monster;
        name = "Bat";
        defaultSpeed = 4;
        speed = defaultSpeed;

        maxLife = 4;
        life = maxLife;

        //MONSTERS ATTRIBUTES
        attack = 7;
        defense = 0;
        exp = 7;
        projectile = new OBJ_Rock(gp);


        solidArea.x = 3;
        solidArea.y = 15;
        solidArea.width = 42;
        solidArea.height = 21;
        solidAreaDefaultX = solidArea.x;
        solidAreaDefaultY = solidArea.y;
        getImage();
    }

    public void getImage(){
        up1 = setup("/monsters/Bat/bat_down_1", gp.tileSize, gp.tileSize);
        up2 = setup("/monsters/Bat/bat_down_2", gp.tileSize, gp.tileSize);
        down1 = setup("/monsters/Bat/bat_down_1", gp.tileSize, gp.tileSize);
        down2 = setup("/monsters/Bat/bat_down_2", gp.tileSize, gp.tileSize);
        left1 = setup("/monsters/Bat/bat_down_1", gp.tileSize, gp.tileSize);
        left2 = setup("/monsters/Bat/bat_down_2", gp.tileSize, gp.tileSize);
        right1 = setup("/monsters/Bat/bat_down_1", gp.tileSize, gp.tileSize);
        right2 = setup("/monsters/Bat/bat_down_2", gp.tileSize, gp.tileSize);
    }


    public void setAction(){

        if(onPath == true){




        }
        else {

            //Get A random Direction
            getRandomDirection(10);
        }
    }

    //Makes the Monster Runaway from the player for a slight moment
    public void damageReaction(){
        actionLockCounter = 0;
        //direction = gp.player.direction;

    }

    public void checkDrop(){

        //CAST A DIE
        int i = new Random().nextInt(100)+1;

        //SETS THE MONSTER DROP
        if(i < 50){
            dropItem(new OBJ_Coin_Bronze(gp));
        }

        if(i >=50 && i <75){
            dropItem(new OBJ_Heart(gp));
        }

        if(i >=75 && i <100){
            dropItem(new OBJ_ManaCrystal(gp));
        }

    }
}
