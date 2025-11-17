package tile;

import java.awt.Graphics2D;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import javax.imageio.ImageIO;

import main.GamePanel;
import main.UtilityTool;

public class TileManager {

    GamePanel gp;
    public Tile[] tile;
    public int[][] mapTileNum;

    public TileManager(GamePanel gp) {
        this.gp = gp;

        tile = new Tile[50];
        mapTileNum = new int[gp.maxWorldCol][gp.maxWorldRow]; 

        getTileImage();
        loadMap("/maps/map01.txt");
    }

    // Load tile images
    private void getTileImage() {

        //PlaceHolder To Prevent Null Pointer Exception Error
        setup(0, "grass00", false);
        setup(1, "grass00", false);
        setup(2, "grass00", false);
        setup(3, "grass00", false);
        setup(4, "grass00", false);
        setup(5, "grass00", false);
        setup(6, "grass00", false);
        setup(7, "grass00", false);
        setup(8, "grass00", false);
        setup(9, "grass00", false);


        //Actual Resources
        setup(10, "grass00", false);
        setup(11, "grass01", false);

        setup(12, "water00", true);
        setup(13, "water01", true);
        setup(14, "water02", true);
        setup(15, "water03", true);
        setup(16, "water04", true);
        setup(17, "water05", true);
        setup(18, "water06", true);
        setup(19, "water07", true);
        setup(20, "water08", true);
        setup(21, "water09", true);
        setup(22, "water10", true);
        setup(23, "water11", true);
        setup(24, "water12", true);
        setup(25, "water13", true);


        setup(26, "road00", false);
        setup(27, "road01", false);
        setup(28, "road02", false);
        setup(29, "road03", false);
        setup(30, "road04", false);
        setup(31, "road05", false);
        setup(32, "road06", false);
        setup(33, "road07", false);
        setup(34, "road08", false);
        setup(35, "road09", false);
        setup(36, "road10", false);
        setup(37, "road11", false);
        setup(38, "road12", false);

        setup(39, "earth", false);
        setup(40, "wall", true);
        setup(41, "tree", true);
    }

    public void setup(int index, String imageName, boolean collision) {

        UtilityTool uTool = new UtilityTool();

        try {
            InputStream is = getClass().getResourceAsStream("/tiles/" + imageName + ".png");
            if (is == null) {
                System.err.println("ERROR: Missing tile image - /tiles/" + imageName + ".png");
                return; // Skip this tile instead of crashing
            }
            tile[index] = new Tile();
            tile[index].image = ImageIO.read(getClass().getResourceAsStream("/tiles/" + imageName +".png"));
            tile[index].image = uTool.scaleImage(tile[index].image, gp.tileSize, gp.tileSize);
            tile[index].collision = collision;

        }catch(IOException e) {
            e.printStackTrace();
        }

    }

    // Load map from text file
    // File: My2DGame/src/tile/TileManager.java
    public void loadMap(String filePath) {
        try (InputStream is = getClass().getResourceAsStream(filePath)) {
            if (is == null) {
                throw new IOException("Map file not found: " + filePath);
            }

            try (BufferedReader br = new BufferedReader(new InputStreamReader(is))) {
                String line;
                int row = 0;

                while ((line = br.readLine()) != null && row < gp.maxWorldRow) {
                    String[] numbers = line.trim().split("\\s+");

                    for (int col = 0; col < gp.maxWorldCol && col < numbers.length; col++) {
                        try {
                            int num = Integer.parseInt(numbers[col]);
                            mapTileNum[col][row] = num;
                        } catch (NumberFormatException ex) {
                            System.err.println("Invalid tile number at row " + row + " col " + col);
                        }
                    }
                    row++;
                }
            }
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
                    g2.drawImage(tile[tileNum].image, screenX, screenY,null);
                }
            }
        }
    }
}
