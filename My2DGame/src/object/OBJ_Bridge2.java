package object;

import entity.Entity;
import main.GamePanel;
import tile.Tile;

import javax.imageio.ImageIO;
import java.io.IOException;

public class OBJ_Bridge2 extends Entity {

    public OBJ_Bridge2(GamePanel gp) {
        super(gp);

        name = "Bridge2";
        down1 = setup("/objects/bridge9", gp.tileSize, gp.tileSize);

        collision = true;

        solidArea.x = 0;
        solidArea.y =  16;
        solidArea.width = 48;
        solidArea.height = 32;
        solidAreaDefaultX = solidArea.x;
        solidAreaDefaultY = solidArea.y;



    }
}
