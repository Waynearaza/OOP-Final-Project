package entity;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;

import main.GamePanel;
import main.KeyHandler;
import main.UtilityTool;
import object.*;

import static javax.imageio.ImageIO.read;

public class Player extends Entity {

    KeyHandler keyH;
    public final int screenX;
    public final int screenY;
    private boolean moving = false;
    public boolean attackCanceled = false;
    public boolean lightUpdated = false;


    // Walking sprites
    BufferedImage up1, up2, up3, up4, up5, up6;
    public BufferedImage down1;
    BufferedImage down2, down3, down4, down5, down6;
    BufferedImage left1, left2, left3, left4, left5, left6;
    BufferedImage right1, right2, right3, right4, right5, right6;

    // Idle sprites
    BufferedImage upIdle1, upIdle2, upIdle3, upIdle4, upIdle5, upIdle6;
    BufferedImage downIdle1, downIdle2, downIdle3, downIdle4, downIdle5, downIdle6;
    BufferedImage leftIdle1, leftIdle2, leftIdle3, leftIdle4, leftIdle5, leftIdle6;
    BufferedImage rightIdle1, rightIdle2, rightIdle3, rightIdle4, rightIdle5, rightIdle6;

    // Attack sprites
    BufferedImage attackUp1, attackUp2, attackUp3, attackUp4;
    BufferedImage attackDown1, attackDown2, attackDown3, attackDown4;
    BufferedImage attackLeft1, attackLeft2, attackLeft3, attackLeft4;
    BufferedImage attackRight1, attackRight2, attackRight3, attackRight4;


    public Player(GamePanel gp, KeyHandler keyH) {
        super(gp);

        this.keyH = keyH;

        screenX = gp.screenWidth / 2 - gp.tileSize / 2;
        screenY = gp.screenHeight / 2 - gp.tileSize / 2;

        // Collision box
        solidArea = new Rectangle(17, 30, 24, 24);
        solidAreaDefaultX = solidArea.x;
        solidAreaDefaultY = solidArea.y;

        //Attack Area
        //attackArea.width = 36;
        //attackArea.height = 36;

        setDefaultValues();
        getPlayerImage();
        getPlayerAttackImage();
        getGuardImage(); // Turn Back on When the Sprite is now okay
        setItems();
        setDialogue();
        image = down1;
    }

    public void setDefaultValues(){
        // Spawn
        worldX = gp.tileSize * 23;
        worldY = gp.tileSize * 21;
        //worldX = gp.tileSize * 12;
        //worldY = gp.tileSize * 13;

        defaultSpeed = 4;
        speed = defaultSpeed;
        direction = "down";

        //PLAYER STATUS or ATTRIBUTES
        level =1;
        maxLife = 6;
        life = maxLife;
        maxMana = 4;
        mana = maxMana;
        ammo = 10;
        strength = 1; // More = More Damage Player Gives
        dexterity = 1; //More = Less Damage Player Receives
        exp = 0;
        nextLevelExp = 5;
        coin = 500; //We Broke Right Now
        currentWeapon = new OBJ_Sword_Normal(gp);
        currentShield = new OBJ_Shield_Wood(gp);
        projectile = new OBJ_Fireball(gp);
        //projectile = new OBJ_Rock(gp);
        attack =  getAttack(); //Total attack Value is decided by strength and weapon
        defense = getDefense(); //Total defense Value is decided by dexterity and shield
    }

    public void setDefaultPositions(){
        worldX = gp.tileSize*23;
        worldY = gp.tileSize*21;
        direction = "down";
    }

    public void setDialogue(){
        dialogues[0][0] = "You Are Now Level " + level + " Now!\n" + "You Feel More Blacker";


    }

    public void  restoreLifeAndMana(){
        life = maxLife;
        mana = maxMana;
        invincible = false;
        transparent = false;
    }

    public void setItems(){

        inventory.clear();
        inventory.add(currentWeapon);
        inventory.add(currentShield);
        inventory.add(new OBJ_Key(gp));
        inventory.add(new OBJ_Key(gp));

    }

    public int getAttack(){
        attackArea = currentWeapon.attackArea;
        motion1_Duration = currentWeapon.motion1_Duration;
        motion2_Duration = currentWeapon.motion2_Duration;
        return attack = strength * currentWeapon.attackValue;
    }

    public int getDefense(){
        return defense = dexterity * currentShield.defenseValue;
    }

    // LOAD WALK + IDLE FRAMES
    public void getPlayerImage() {
        up1 = setup("/player/player-31.png", gp.tileSize, gp.tileSize);
        up2 = setup("/player/player-32.png", gp.tileSize, gp.tileSize);
        up3 = setup("/player/player-33.png", gp.tileSize, gp.tileSize);
        up4 = setup("/player/player-34.png", gp.tileSize, gp.tileSize);
        up5 = setup("/player/player-35.png", gp.tileSize, gp.tileSize);
        up6 = setup("/player/player-36.png", gp.tileSize, gp.tileSize);

        down1 = setup("/player/player-19.png", gp.tileSize, gp.tileSize);
        down2 = setup("/player/player-20.png", gp.tileSize, gp.tileSize);
        down3 = setup("/player/player-21.png", gp.tileSize, gp.tileSize);
        down4 = setup("/player/player-22.png", gp.tileSize, gp.tileSize);
        down5 = setup("/player/player-23.png", gp.tileSize, gp.tileSize);
        down6 = setup("/player/player-24.png", gp.tileSize, gp.tileSize);

        right1 = setup("/player/player-25.png", gp.tileSize, gp.tileSize);
        right2 = setup("/player/player-26.png", gp.tileSize, gp.tileSize);
        right3 = setup("/player/player-27.png", gp.tileSize, gp.tileSize);
        right4 = setup("/player/player-28.png", gp.tileSize, gp.tileSize);
        right5 = setup("/player/player-29.png", gp.tileSize, gp.tileSize);
        right6 = setup("/player/player-30.png", gp.tileSize, gp.tileSize);

        left1 = setup("/player/player-37.png", gp.tileSize, gp.tileSize);
        left2 = setup("/player/player-38.png", gp.tileSize, gp.tileSize);
        left3 = setup("/player/player-39.png", gp.tileSize, gp.tileSize);
        left4 = setup("/player/player-40.png", gp.tileSize, gp.tileSize);
        left5 = setup("/player/player-41.png", gp.tileSize, gp.tileSize);
        left6 = setup("/player/player-42.png", gp.tileSize, gp.tileSize);

        upIdle1 = setup("/player/player-13.png", gp.tileSize, gp.tileSize);
        upIdle2 = setup("/player/player-14.png", gp.tileSize, gp.tileSize);
        upIdle3 = setup("/player/player-15.png", gp.tileSize, gp.tileSize);
        upIdle4 = setup("/player/player-16.png", gp.tileSize, gp.tileSize);
        upIdle5 = setup("/player/player-17.png", gp.tileSize, gp.tileSize);
        upIdle6 = setup("/player/player-18.png", gp.tileSize, gp.tileSize);

        downIdle1 = setup("/player/player-1.png", gp.tileSize, gp.tileSize);
        downIdle2 = setup("/player/player-2.png", gp.tileSize, gp.tileSize);
        downIdle3 = setup("/player/player-3.png", gp.tileSize, gp.tileSize);
        downIdle4 = setup("/player/player-4.png", gp.tileSize, gp.tileSize);
        downIdle5 = setup("/player/player-5.png", gp.tileSize, gp.tileSize);
        downIdle6 = setup("/player/player-6.png", gp.tileSize, gp.tileSize);

        leftIdle1 = setup("/player/player-43.png", gp.tileSize, gp.tileSize);
        leftIdle2 = setup("/player/player-44.png", gp.tileSize, gp.tileSize);
        leftIdle3 = setup("/player/player-45.png", gp.tileSize, gp.tileSize);
        leftIdle4 = setup("/player/player-46.png", gp.tileSize, gp.tileSize);
        leftIdle5 = setup("/player/player-47.png", gp.tileSize, gp.tileSize);
        leftIdle6 = setup("/player/player-48.png", gp.tileSize, gp.tileSize);

        rightIdle1 = setup("/player/player-7.png", gp.tileSize, gp.tileSize);
        rightIdle2 = setup("/player/player-8.png", gp.tileSize, gp.tileSize);
        rightIdle3 = setup("/player/player-9.png", gp.tileSize, gp.tileSize);
        rightIdle4 = setup("/player/player-10.png", gp.tileSize, gp.tileSize);
        rightIdle5 = setup("/player/player-11.png", gp.tileSize, gp.tileSize);
        rightIdle6 = setup("/player/player-12.png", gp.tileSize, gp.tileSize);
    }

    public void getSleepingImage(BufferedImage image){
        up1 = image;
        up2 = image;
        up3 = image;
        up4 = image;
        up5 = image;
        up6 = image;

        down1 = image;
        down2 = image;
        down3 = image;
        down4 = image;
        down5 = image;
        down6 = image;

        right1 = image;
        right2 = image;
        right3 = image;
        right4 = image;
        right5 = image;
        right6 = image;

        left1 = image;
        left2 = image;
        left3 = image;
        left4 = image;
        left5 = image;
        left6 = image;

        upIdle1 = image;
        upIdle2 = image;
        upIdle3 = image;
        upIdle4 = image;
        upIdle5 = image;
        upIdle6 = image;

        downIdle1 = image;
        downIdle2 = image;
        downIdle3 = image;
        downIdle4 = image;
        downIdle5 = image;
        downIdle6 = image;

        leftIdle1 = image;
        leftIdle2 = image;
        leftIdle3 = image;
        leftIdle4 = image;
        leftIdle5 = image;
        leftIdle6 = image;

        rightIdle1 = image;
        rightIdle2 = image;
        rightIdle3 = image;
        rightIdle4 = image;
        rightIdle5 = image;
        rightIdle6 = image;
    }

    // LOAD ATTACK FRAMES
    public void getPlayerAttackImage() {

        //LOADS SWORD ATTACK SPRITE IF PLAYER EQUIPPED AN SWORD
        if(currentWeapon.type == type_sword){
            attackUp1 = setup("/player/player attack up-1.png", gp.tileSize*2, gp.tileSize );
            attackUp2 = setup("/player/player attack up-2.png", gp.tileSize*2, gp.tileSize );
            attackUp3 = setup("/player/player attack up-3.png", gp.tileSize*2, gp.tileSize );
            attackUp4 = setup("/player/player attack up-4.png", gp.tileSize*2, gp.tileSize );

            attackDown1 = setup("/player/player attack down-1.png", gp.tileSize*2, gp.tileSize);
            attackDown2 = setup("/player/player attack down-2.png", gp.tileSize*2, gp.tileSize );
            attackDown3 = setup("/player/player attack down-3.png", gp.tileSize*2, gp.tileSize );
            attackDown4 = setup("/player/player attack down-4.png", gp.tileSize*2, gp.tileSize );

            attackLeft1 = setup("/player/player attack left-1.png", gp.tileSize *2, gp.tileSize);
            attackLeft2 = setup("/player/player attack left-2.png", gp.tileSize *2, gp.tileSize);
            attackLeft3 = setup("/player/player attack left-3.png", gp.tileSize *2, gp.tileSize);
            attackLeft4 = setup("/player/player attack left-4.png", gp.tileSize *2, gp.tileSize);

            attackRight1 = setup("/player/player attack right-1.png", gp.tileSize *2, gp.tileSize);
            attackRight2 = setup("/player/player attack right-2.png", gp.tileSize *2, gp.tileSize);
            attackRight3 = setup("/player/player attack right-3.png", gp.tileSize *2, gp.tileSize);
            attackRight4 = setup("/player/player attack right-4.png", gp.tileSize *2, gp.tileSize);
        }

        //LOADS AXE ATTACK SPRITE IF PLAYER EQUIPPED AN AXE
        if(currentWeapon.type == type_axe){
            attackUp1 = setup("/player/axe_swing/player axe up-1.png", gp.tileSize*2, gp.tileSize );
            attackUp2 = setup("/player/axe_swing/player axe up-2.png", gp.tileSize*2, gp.tileSize );
            attackUp3 = setup("/player/axe_swing/player axe up-3.png", gp.tileSize*2, gp.tileSize );

            attackDown1 = setup("/player/axe_swing/player axe down-1.png", gp.tileSize*2, gp.tileSize);
            attackDown2 = setup("/player/axe_swing/player axe down-2.png", gp.tileSize*2, gp.tileSize );
            attackDown3 = setup("/player/axe_swing/player axe down-3.png", gp.tileSize*2, gp.tileSize );

            attackLeft1 = setup("/player/axe_swing/player axe left-1.png", gp.tileSize *2, gp.tileSize);
            attackLeft2 = setup("/player/axe_swing/player axe left-2.png", gp.tileSize *2, gp.tileSize);
            attackLeft3 = setup("/player/axe_swing/player axe left-3.png", gp.tileSize *2, gp.tileSize);

            attackRight1 = setup("/player/axe_swing/player axe right-1.png", gp.tileSize *2, gp.tileSize);
            attackRight2= setup("/player/axe_swing/player axe right-2.png", gp.tileSize *2, gp.tileSize);
            attackRight3 = setup("/player/axe_swing/player axe right-3.png", gp.tileSize *2, gp.tileSize);
        }

    }

    public void getGuardImage(){
        // UPDATED: Changed width from gp.tileSize*2 to gp.tileSize
        guardUp = setup("/player/Shield/player_guard_up.png", gp.tileSize*2, gp.tileSize*2);
        guardDown = setup("/player/Shield/player_guard_down.png", gp.tileSize*2,gp.tileSize*2);
        guardLeft = setup("/player/Shield/player_guard_left.png",gp.tileSize*2, gp.tileSize*2);
        guardRight = setup("/player/Shield/player_guard_right.png", gp.tileSize*2, gp.tileSize*2);
    }

    public BufferedImage setup(String imagePath, int width, int height) {
        UtilityTool uTool = new UtilityTool();
        BufferedImage image = null;

        try {
            image = read(getClass().getResourceAsStream(imagePath));
            image = uTool.scaleImage(image, width, height);
        } catch (IOException e) {
            System.err.println("Error loading: " + imagePath);
        }

        return image;
    }

    // UPDATE PLAYER
    // UPDATE PLAYER
    public void update() {

        if(knockBack == true) {
            //Check Tile Collision
            gp.cChecker.checkObject(this, true);

            //Check NPC Collision
            gp.cChecker.checkEntity(this, gp.npc);

            //Check Monster Collision
            gp.cChecker.checkEntity(this, gp.monster);

            //Check Interactive Tile Collision
            gp.cChecker.checkEntity(this, gp.iTile);


            if (collisionOn == true) {
                knockBackCounter = 0;
                knockBack = false;
                speed = defaultSpeed;
            } else if (collisionOn == false) {
                switch (knockBackDirection) {
                    case "up":
                        worldY -= speed;
                        break;
                    case "down":
                        worldY += speed;
                        break;
                    case "left":
                        worldX -= speed;
                        break;
                    case "right":
                        worldX += speed;
                        break;
                }
            }

            knockBackCounter++;
            if (knockBackCounter == 10) {
                knockBackCounter = 0;
                knockBack = false;
                speed = defaultSpeed;
            }
        }

        if (attacking) {
            attacking();
            return;
        }

        // 1. CHECK GUARDING STATE FIRST
        if (keyH.spacePressed == true) {
            guarding = true;
            guardCounter++;
        } else {
            guarding = false;
            guardCounter = 0;
        }

        // 2. CHECK MOVEMENT (Only if NOT guarding)a
        if (guarding == true) {
            moving = false; // Player freezes while guarding
        }
        else {
            moving = false;

            if (keyH.upPressed) { direction = "up"; moving = true; }
            if (keyH.downPressed) { direction = "down"; moving = true; }
            if (keyH.leftPressed) { direction = "left"; moving = true; }
            if (keyH.rightPressed) { direction = "right"; moving = true; }

            // ENTER → INTERACT WITHOUT MOVING
            if (keyH.enterPressed) moving = true;

            if (moving) {

                collisionOn = false;
                gp.cChecker.checkTile(this);

                //Check Tile Collision
                int objIndex = gp.cChecker.checkObject(this, true);
                pickUpObject(objIndex);

                //Check NPC Collision
                int npcIndex = gp.cChecker.checkEntity(this, gp.npc);
                interactNPC(npcIndex);

                //Check Monster Collision
                int monsterIndex = gp.cChecker.checkEntity(this, gp.monster);
                contactMonster(monsterIndex);

                //Check Interactive Tile Collision
                int iTileIndex = gp.cChecker.checkEntity(this, gp.iTile);

                gp.eHandler.checkEvent();

                if (!collisionOn) {
                    if (keyH.upPressed) worldY -= speed;
                    if (keyH.downPressed) worldY += speed;
                    if (keyH.leftPressed) worldX -= speed;
                    if (keyH.rightPressed) worldX += speed;
                }

                if (keyH.enterPressed && !attackCanceled && gp.gameState == gp.playState) {
                    gp.playSE(7);
                    attacking = true;
                    spriteCounter = 0;
                }

                attackCanceled = false;
                gp.keyH.enterPressed = false;

                // --- REMOVED "guarding = false" HERE ---

                spriteCounter++;
                if (spriteCounter > 18) { // slower animation
                    spriteNum++;
                    if (spriteNum > 6) spriteNum = 1;
                    spriteCounter = 0;
                }
            }
            else {
                // IDLE ANIMATION
                spriteCounter++;
                if (spriteCounter > 30) { // slower idle animation
                    spriteNum++;
                    if (spriteNum > 6) spriteNum = 1;
                    spriteCounter = 0;
                }
            }
        }

        // 3. PROJECTILE LOGIC (Added "&& guarding == false" so you can't shoot while shielding)
        if (gp.keyH.shotKeyPressed == true && projectile.alive == false
                && shotAvailableCounter == 30 && projectile.havaResource(this) == true
                && guarding == false) {

            //Sets Default Coordinates, Direction, and User
            projectile.set(worldX, worldY, direction, true, this);

            //Subtract The Cost(Mana, Ammo, etc)
            projectile.subtractResource(this);

            for (int i = 0; i < gp.projectile[1].length; i++) {
                if (gp.projectile[gp.currentMap][i] == null) {
                    gp.projectile[gp.currentMap][i] = projectile;
                    break;
                }
            }
            shotAvailableCounter = 0;
            gp.playSE(10);
        }

        // 4. INVINCIBLE TIMER
        if (invincible == true) {
            invincibleCounter++;
            if (invincibleCounter > 60) {
                invincible = false;
                transparent = false;
                invincibleCounter = 0;
            }
        }

        if (shotAvailableCounter < 30) {
            shotAvailableCounter++;
        }

        // 5. STATUS LIMITS
        if (life > maxLife) {
            life = maxLife;
        }
        if (mana > maxMana) {
            mana = maxMana;
        }

        // 6. GAME OVER CHECK
        if (life <= 0) {
            gp.gameState = gp.gameOverState;
            gp.ui.commandNum = -1;
            gp.stopMusic();
            gp.playSE(12);
        }
    }



    public void draw(Graphics2D g2) {
        BufferedImage img = null;
        int drawX = screenX;
        int drawY = screenY;

        if (attacking) {
            switch (direction) {
                case "up":
                    img = getSprite(attackUp1, attackUp2, attackUp3, attackUp4);
                    drawX = screenX - gp.tileSize / 2; // center wide sprite horizontally
                    drawY = screenY; // move it up visually
                    break;
                case "down":
                    img = getSprite(attackDown1, attackDown2, attackDown3, attackDown4);
                    drawX = screenX - gp.tileSize / 2; // center wide sprite horizontally
                    drawY = screenY; // keep at player position vertically
                    break;
                case "left":
                    img = getSprite(attackLeft1, attackLeft2, attackLeft3, attackLeft4);
                    drawX = screenX - gp.tileSize / 2; // move left to center
                    drawY = screenY;
                    break;
                case "right":
                    img = getSprite(attackRight1, attackRight2, attackRight3, attackRight4);
                    drawX = screenX - gp.tileSize / 2; // already aligned
                    drawY = screenY;
                    break;
            }
        }
        else if (guarding == true) {
            switch (direction) {
                case "up":    img = guardUp;
                    drawX = screenX - gp.tileSize / 2; // center wide sprite horizontally
                    drawY = screenY - gp.tileSize / 2;
                    break;
                case "down":  img = guardDown;
                    drawX = screenX - gp.tileSize / 2; // center wide sprite horizontally
                    drawY = screenY - gp.tileSize / 2;
                    break;
                case "left":  img = guardLeft;
                    drawX = screenX - gp.tileSize / 2; // center wide sprite horizontally
                    drawY = screenY - gp.tileSize / 2;
                    break;

                case "right": img = guardRight;
                    drawX = screenX - gp.tileSize / 2; // center wide sprite horizontally
                    drawY = screenY - gp.tileSize / 2;
                    break;
            }
        }

        // WALKING
        else if (moving) {
            switch (direction) {
                case "up":    img = getSprite(up1, up2, up3, up4, up5, up6); break;
                case "down":  img = getSprite(down1, down2, down3, down4, down5, down6); break;
                case "left":  img = getSprite(left1, left2, left3, left4, left5, left6); break;
                case "right": img = getSprite(right1, right2, right3, right4, right5, right6); break;
            }
        }
        // IDLE
        else {
            switch (direction) {
                case "up":    img = getSprite(upIdle1, upIdle2, upIdle3, upIdle4, upIdle5, upIdle6); break;
                case "down":  img = getSprite(downIdle1, downIdle2, downIdle3, downIdle4, downIdle5, downIdle6); break;
                case "left":  img = getSprite(leftIdle1, leftIdle2, leftIdle3, leftIdle4, leftIdle5, leftIdle6); break;
                case "right": img = getSprite(rightIdle1, rightIdle2, rightIdle3, rightIdle4, rightIdle5, rightIdle6); break;
            }
        }

        if (transparent == true) {
            g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.4f));
        }
        g2.drawImage(img, drawX, drawY, null);

        g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1f));
    }


    private BufferedImage getSprite(BufferedImage s1, BufferedImage s2, BufferedImage s3,
                                    BufferedImage s4, BufferedImage... extra) {

        switch (spriteNum) {
            case 1: return s1;
            case 2: return s2;
            case 3: return s3;
            case 4: return s4;
            case 5: return extra.length > 0 ? extra[0] : s1;
            case 6: return extra.length > 1 ? extra[1] : s1;
        }
        return s1;
    }

    public void pickUpObject(int i) {
        if (i != 999){
            //Debug
            //System.out.println("Object Collision Detected! Type: " + gp.obj[gp.currentMap][i].type);

            //Pickup Only Items
            if(gp.obj[gp.currentMap][i].type == type_pickupOnly) {
                gp.obj[gp.currentMap][i].use(this);
                gp.obj[gp.currentMap][i] = null;
            }


            //OBSTACLE
            else if (gp.obj[gp.currentMap][i].type == type_obstacle) {
                if(keyH.enterPressed == true){
                    attackCanceled = true;
                    gp.obj[gp.currentMap][i].interact();
                }
            }


            //Inventory Items
            else {
                String text;

                if(canObtainItem(gp.obj[gp.currentMap][i]) == true ){
                    gp.playSE(1);
                    text = "Got a " + gp.obj[gp.currentMap][i].name + "!";
                }
                else {
                    text = "Your Inventory is Full Nigger";
                }
                gp.ui.addMessage(text);
                gp.obj[gp.currentMap][i] = null;
            }
        }
    }


    // ENTER → INTERACT
    public void interactNPC(int i){
        if(gp.keyH.enterPressed){
            if(i != 999){
                attackCanceled = true;
                gp.npc[gp.currentMap][i].speak();
            }
        }
    }

    public void contactMonster(int i){
        if(i != 999){
            if(invincible == false &&gp.monster[gp.currentMap][i].dying == false){
                gp.playSE(6);
                int damage = gp.monster[gp.currentMap][i].attack - defense;
                if(damage < 1){
                    damage =1;
                }
                life -= damage;
                invincible = true;
                transparent = true;
            }
        }
    }

    public void damageMonster(int i, Entity attacker, int attack, int knockBackPower){
        if(i != 999){
           if(gp.monster[gp.currentMap][i].invincible == false){
                gp.playSE(5);

                if(knockBackPower > 0){
                    setKnockBack(gp.monster[gp.currentMap][i],attacker, knockBackPower);
                }

                if(gp.monster[gp.currentMap][i].offBalance == true){
                        attack *= 5;
                }

                int damage = attack - gp.monster[gp.currentMap][i].defense;
                if(damage < 0){
                    damage =0;
                }
                gp.monster[gp.currentMap][i].life -= damage;
                gp.ui.addMessage(damage + " damage!");
                gp.monster[gp.currentMap][i].invincible = true;
                gp.monster[gp.currentMap][i].damageReaction();
                if(gp.monster[gp.currentMap][i].life <=0){
                    gp.monster[gp.currentMap][i].dying = true;
                    gp.ui.addMessage("killed the " + gp.monster[gp.currentMap][i].name + "!");
                    gp.ui.addMessage("Exp + " + gp.monster[gp.currentMap][i].exp);
                    exp += gp.monster[gp.currentMap][i].exp;
                    checkLevelUp();
                }
           }
        }
    }



    //Add Level Up Conditions and Rewards
    public void checkLevelUp(){

        if(exp >= nextLevelExp){
            level++;
            nextLevelExp = nextLevelExp*2;
            maxLife += 2;
            strength++;
            dexterity++;
            attack = getAttack();
            defense = getDefense();

            gp.playSE(8);
            gp.gameState = gp.dialogueState;
            startDialogue(this, 0);

        }
    }



    public void damageInteractiveTile(int i){
        if(i != 999 && gp.iTile[gp.currentMap][i].destructible == true && gp.iTile[gp.currentMap][i].isCorrectItem(this) == true
        && gp.iTile[gp.currentMap][i].invincible == false){
            gp.iTile[gp.currentMap][i].playSE();
            gp.iTile[gp.currentMap][i].life--;
            gp.iTile[gp.currentMap][i].invincible = true;
            generateParticle(gp.iTile[gp.currentMap][i], gp.iTile[gp.currentMap][i]);
            if(gp.iTile[gp.currentMap][i].life == 0){
                gp.iTile[gp.currentMap][i] = gp.iTile[gp.currentMap][i].getDestroyedForm();
            }

        }
    }

    public void damamageProjectile(int i){
        if(i != 999){
            Entity projectile = gp.projectile[gp.currentMap][i];
            projectile.alive = false;
            generateParticle(projectile, projectile);
        }
    }

    //Equip Items in Inventory
    public void selectItem(){
        int itemIndex = gp.ui.getItemIndexOnSlot(gp.ui.playerCol, gp.ui.playerSlotRow);

        if(itemIndex < inventory.size()){
            Entity selectedItem = inventory.get(itemIndex);

            //CHECKS THE PLAYER CURRENT SELECTED WEAPON
            if(selectedItem.type == type_sword || selectedItem.type == type_axe){
                currentWeapon = selectedItem;
                attack = getAttack();
                getPlayerAttackImage();
            }
            if(selectedItem.type == type_shield){
                currentShield = selectedItem;
                defense = getDefense();
            }
            if(selectedItem.type == type_light){
               if(currentLight == selectedItem){
                   currentLight = null;
               }
               else {
                   currentLight = selectedItem;
               }
               lightUpdated = true;
            }

            if(selectedItem.type == type_consumable){
                if(selectedItem.use(this) == true){
                    if(selectedItem.amount > 1){
                        selectedItem.amount--;
                    }else {
                        inventory.remove(itemIndex);
                    }
                }
            }
        }

    }

    public int searchItemInInventory(String itemName){
        int itemIndex = 999;

        for(int i = 0; i < inventory.size(); i++){
            if(inventory.get(i).name.equals(itemName)){
                itemIndex = i;
                break;
            }
        }

    return itemIndex;
    }

    public boolean canObtainItem(Entity item){
        boolean canObtain = false;

        //Check if Item is Stackable
        if(item.stackable == true){
            int index = searchItemInInventory(item.name);

            if(index != 999){
                inventory.get(index).amount++;
                canObtain = true;
            }
            else {//New Item Need To Check Vacancy
                if(inventory.size() != maxinventorySize){
                    inventory.add(item);
                    canObtain = true;
                }

            }
        }
        else {// Not Stackable Check Vacancy
            if(inventory.size() != maxinventorySize){
                inventory.add(item);
                canObtain = true;
            }
        }

        return canObtain;
    }
}
