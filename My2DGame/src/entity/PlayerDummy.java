package entity;

import main.GamePanel;

public class PlayerDummy extends Entity {
    public  static final String  npcName = "Dummy";

    public PlayerDummy(GamePanel gp){
        super(gp);

        name = npcName;
    }
    public void getImage() {
        up1 = setup("/player/player-31.png", gp.tileSize, gp.tileSize);
        up2 = setup("/player/player-32.png", gp.tileSize, gp.tileSize);

        down1 = setup("/player/player-19.png", gp.tileSize, gp.tileSize);
        down2 = setup("/player/player-20.png", gp.tileSize, gp.tileSize);


        right1 = setup("/player/player-25.png", gp.tileSize, gp.tileSize);
        right2 = setup("/player/player-26.png", gp.tileSize, gp.tileSize);


        left1 = setup("/player/player-37.png", gp.tileSize, gp.tileSize);
        left2 = setup("/player/player-38.png", gp.tileSize, gp.tileSize);

    }
}
