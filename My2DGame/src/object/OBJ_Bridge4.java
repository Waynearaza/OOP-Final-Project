package object;

import entity.Entity;
import main.GamePanel;
import tile.Tile;

import javax.imageio.ImageIO;
import java.io.IOException;

public class OBJ_Bridge4 extends Entity {

    public OBJ_Bridge4(GamePanel gp) {
        super(gp);

        name = "Bridge4";
        down1 = setup("/objects/bridge2", gp.tileSize, gp.tileSize);

        collision = true;
        pickUpAble = false;

        solidArea.x = 0;
        solidArea.y =  16;
        solidArea.width = 48;
        solidArea.height = 32;
        solidAreaDefaultX = solidArea.x;
        solidAreaDefaultY = solidArea.y;



    }
}
