package main;

import entity.Entity;

public class CollisionChecker {
	
	GamePanel gp;
	
	public CollisionChecker(GamePanel gp) {
		this.gp = gp;
		
	}
	
	public void checkTile(Entity entity) {
	    int entityLeftWorldX = entity.worldX + entity.solidArea.x;
	    int entityRightWorldX = entity.worldX + entity.solidArea.x + entity.solidArea.width;
	    int entityTopWorldY = entity.worldY + entity.solidArea.y;
	    int entityBottomWorldY = entity.worldY + entity.solidArea.y + entity.solidArea.height;

	    int entityLeftCol = entityLeftWorldX / gp.tileSize;
	    int entityRightCol = entityRightWorldX / gp.tileSize;
	    int entityTopRow = entityTopWorldY / gp.tileSize;
	    int entityBottomRow = entityBottomWorldY / gp.tileSize;

	    int tileNum1, tileNum2;

	    // Prevent out-of-bounds access
	    if (entityLeftCol < 0 || entityRightCol >= gp.maxWorldCol ||
	        entityTopRow < 0 || entityBottomRow >= gp.maxWorldRow) {
	        entity.collisionOn = true; // treat outside map as collidable
	        return;
	    }

	    switch(entity.direction) {
	        case "up":
	            entityTopRow = (entityTopWorldY - entity.speed) / gp.tileSize;
	            if (entityTopRow >= 0) {
	                tileNum1 = gp.tileM.mapTileNum[entityLeftCol][entityTopRow];
	                tileNum2 = gp.tileM.mapTileNum[entityRightCol][entityTopRow];
	                if (gp.tileM.tile[tileNum1].collision || gp.tileM.tile[tileNum2].collision) {
	                    entity.collisionOn = true;
	                }
	            }
	            break;

	        case "down":
	            entityBottomRow = (entityBottomWorldY + entity.speed) / gp.tileSize;
	            if (entityBottomRow < gp.maxWorldRow) {
	                tileNum1 = gp.tileM.mapTileNum[entityLeftCol][entityBottomRow];
	                tileNum2 = gp.tileM.mapTileNum[entityRightCol][entityBottomRow];
	                if (gp.tileM.tile[tileNum1].collision || gp.tileM.tile[tileNum2].collision) {
	                    entity.collisionOn = true;
	                }
	            }
	            break;

	        case "left":
	            entityLeftCol = (entityLeftWorldX - entity.speed) / gp.tileSize;
	            if (entityLeftCol >= 0) {
	                tileNum1 = gp.tileM.mapTileNum[entityLeftCol][entityTopRow];
	                tileNum2 = gp.tileM.mapTileNum[entityLeftCol][entityBottomRow];
	                if (gp.tileM.tile[tileNum1].collision || gp.tileM.tile[tileNum2].collision) {
	                    entity.collisionOn = true;
	                }
	            }
	            break;

	        case "right":
	            entityRightCol = (entityRightWorldX + entity.speed) / gp.tileSize;
	            if (entityRightCol < gp.maxWorldCol) {
	                tileNum1 = gp.tileM.mapTileNum[entityRightCol][entityTopRow];
	                tileNum2 = gp.tileM.mapTileNum[entityRightCol][entityBottomRow];
	                if (gp.tileM.tile[tileNum1].collision || gp.tileM.tile[tileNum2].collision) {
	                    entity.collisionOn = true;
	                }
	            }
	            break;
	    }
	}
}

