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

        tile = new Tile[200];
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

        setup(42, "maki's house-1", true);
        setup(43, "maki's house-2", true);
        setup(44, "maki's house-3", true);
        setup(45, "maki's house-4", true);
        setup(46, "maki's house-5", true);
        setup(47, "maki's house-6", true);
        setup(48, "maki's house-7", true);
        setup(49, "maki's house-8", true);
        setup(50, "maki's house-9", true);
        setup(51, "maki's house-10", true);
        setup(52, "maki's house-11", true);
        setup(53, "maki's house-12", true);
        setup(54, "maki's house-13", true);
        setup(55, "maki's house-14", true);
        setup(56, "maki's house-15", true);

        setup(57, "post1", true);
        setup(58, "post2", true);
        setup(59, "post3", true);
        setup(60, "post4", true);

        setup(61, "sign-1", true);


        setup(62, "farm-1", true);
        setup(63, "farm-2", true);
        setup(64, "farm-3", true);
        setup(65, "farm-4", true);
        setup(66, "farm-5", true);
        setup(67, "farm-6", true);
        setup(68, "farm-7", true);
        setup(69, "farm-8", true);
        setup(70, "farm-9", true);
        setup(71, "farm-10", true);
        setup(72, "farm-11", true);
        setup(73, "farm-12", true);
        setup(74, "farm-13", true);
        setup(75, "farm-14", true);
        setup(76, "farm-15", true);
        setup(77, "farm-16", true);
        setup(78, "farm-17", true);
        setup(79, "farm-18", true);
        setup(80, "farm-19", true);
        setup(81, "farm-20", true);
        setup(82, "farm-21", true);
        setup(83, "farm-22", true);
        setup(84, "farm-23", true);

        setup(85, "wayne house1", true);
        setup(86, "wayne house2", true);
        setup(87, "wayne house3", true);
        setup(88, "wayne house4", true);
        setup(89, "wayne house5", true);
        setup(90, "wayne house6", true);
        setup(91, "wayne house7", true);
        setup(92, "wayne house8", true);
        setup(93, "wayne house9", true);
        setup(94, "wayne house10", true);
        setup(95, "wayne house11", true);
        setup(96, "wayne house12", true);
        setup(97, "wayne house13", true);
        setup(98, "wayne house14", true);
        setup(99, "wayne house15", true);

        setup(100, "well", true);
        setup(101, "well1", true);
        setup(102, "well2", true);
        setup(103, "well3", true);
        setup(104, "well4", true);

        setup(105, "fence r", true);
        setup(106, "fence lr", true);
        setup(107, "fence l", true);
        setup(108, "fence du", true);
        setup(109, "fence lu", true);
        setup(110, "fence dr", true);

        setup(111, "houseb1", true);
        setup(112, "houseb2", true);
        setup(113, "houseb3", true);
        setup(114, "houseb4", true);
        setup(115, "houseb5", true);
        setup(116, "houseb6", true);
        setup(117, "houseb7", true);
        setup(118, "houseb8", true);
        setup(119, "houseb9", true);
        setup(120, "houseb10", true);
        setup(121, "houseb11", true);
        setup(122, "houseb12", true);
        setup(123, "houseb13", true);
        setup(124, "houseb14", true);
        setup(125, "houseb15", true);


        setup(126, "bridge1", true);
        setup(127, "bridge2", true);
        setup(128, "bridge3", true);
        setup(129, "bridge4", true);
        setup(130, "bridge5", true);
        setup(131, "bridge6", true);
        setup(132, "bridge7", true);
        setup(133, "bridge8", true);
        setup(134, "bridge9", true);
        setup(135, "bridge10", true);
        setup(136, "bridge11", true);
        setup(137, "bridge12", true);
        setup(138, "bridge13", true);
        setup(139, "bridge14", true);
        setup(140, "bridge15", true);
        setup(141, "bridge16", true);
        setup(142, "bridge17", true);
        setup(143, "bridge18", true);
        setup(144, "bridge19", true);
        setup(145, "bridge20", true);
        setup(146, "bridge21", true);
        setup(147, "bridge22", true);
        setup(148, "bridge23", true);
        setup(149, "bridge24", true);











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
