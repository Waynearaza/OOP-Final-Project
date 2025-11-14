package tile;

import java.awt.Graphics2D;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import javax.imageio.ImageIO;

import main.GamePanel;

public class TileManager {

    GamePanel gp;
    public Tile[] tile;
    public int[][] mapTileNum;

    public TileManager(GamePanel gp) {
        this.gp = gp;

        tile = new Tile[6]; // 0=grass, 1=wall, 2=water, 3=wall duplicate, 4=tree, 5=grass duplicate
        mapTileNum = new int[gp.maxWorldCol][gp.maxWorldRow]; 

        getTileImage();
        loadMap();
    }

    // Load tile images
    private void getTileImage() {
        try {
            tile[0] = new Tile();
            tile[0].image = ImageIO.read(getClass().getResourceAsStream("/tiles/grass00.png"));

            tile[1] = new Tile();
            tile[1].image = ImageIO.read(getClass().getResourceAsStream("/tiles/wall.png"));
            tile[1].collision = true;

            tile[2] = new Tile();
            tile[2].image = ImageIO.read(getClass().getResourceAsStream("/tiles/water01.png"));
            tile[2].collision = true;

            tile[3] = new Tile();
            tile[3].image = ImageIO.read(getClass().getResourceAsStream("/tiles/wall.png"));
            tile[3].collision = true;

            tile[4] = new Tile();
            tile[4].image = ImageIO.read(getClass().getResourceAsStream("/tiles/tree.png"));
            tile[4].collision = true;

            tile[5] = new Tile();
            tile[5].image = ImageIO.read(getClass().getResourceAsStream("/tiles/road00.png"));


        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Load map from text file
    private void loadMap() {
        try {
            InputStream is = getClass().getResourceAsStream("/maps/map.txt");
            BufferedReader br = new BufferedReader(new InputStreamReader(is));

            for (int row = 0; row < gp.maxWorldRow; row++) {
                String line = br.readLine();
                if (line == null) break; // Prevent null pointer if file is shorter than expected
                String[] numbers = line.split(" ");

                for (int col = 0; col < gp.maxWorldCol; col++) {
                    int num = Integer.parseInt(numbers[col]);
                    mapTileNum[col][row] = num; // Correct indexing: col then row
                }
            }

            br.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // Draw tiles
    public void draw(Graphics2D g2) {
        for (int worldRow = 0; worldRow < gp.maxWorldRow; worldRow++) {
            for (int worldCol = 0; worldCol < gp.maxWorldCol; worldCol++) {

                int tileNum = mapTileNum[worldCol][worldRow]; // Correct indexing

                int worldX = worldCol * gp.tileSize;
                int worldY = worldRow * gp.tileSize;

                int screenX = worldX - gp.player.worldX + gp.player.screenX;
                int screenY = worldY - gp.player.worldY + gp.player.screenY;

                // Only draw tiles that are on the screen
                if (screenX + gp.tileSize > 0 && screenX < gp.screenWidth &&
                    screenY + gp.tileSize > 0 && screenY < gp.screenHeight) {
                    g2.drawImage(tile[tileNum].image, screenX, screenY, gp.tileSize, gp.tileSize, null);
                }
            }
        }
    }
}
