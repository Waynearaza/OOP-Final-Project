package main;

import entity.Entity;
import object.OBJ_Heart;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

public class UI {
    GamePanel gp;
    Graphics2D g2;
    Font Pixel_Game;
    BufferedImage heart_full, heart_half, heart_blank; //Heart Images
    public boolean messageOn = false;
    //public String message = "";
    public String currentDialogue = "";
    //public int messageCounter = 0;
    public int gameFinished = 0;
    ArrayList<String> message = new ArrayList<>();
    ArrayList<Integer> messageCounter = new ArrayList<>();

    public int commandNum = 0;
    public int titleScreenState = 0; // 0 = main menu, 1-3 = intro pages

    // Transition system
    public int transitionAlpha = 0;
    public boolean doingTransition = false;
    public boolean fadeOut = false;
    public boolean fadeIn = false; // fade in before showing page

    public UI(GamePanel gp) {
        this.gp = gp;

        try {
            InputStream is = getClass().getResourceAsStream("/font/Pixel_Game.otf");
            Pixel_Game = Font.createFont(Font.TRUETYPE_FONT, is);
        } catch (FontFormatException | IOException e) {
            e.printStackTrace();
        }

        //CREATE HUD
        Entity heart = new OBJ_Heart(gp);
        heart_full = heart.image;
        heart_half = heart.image2;
        heart_blank = heart.image3;
    }

    public void addMessage(String text) {
        message.add(text);
        messageCounter.add(0);
    }

    public void draw(Graphics2D g2) {
        this.g2 = g2;
        g2.setFont(Pixel_Game);
        g2.setColor(Color.white);

        //TITLE STATE
        if (gp.gameState == gp.titleState) {
            drawTitleScreen();
        }
        //PLAY STATE
        else if (gp.gameState == gp.playState) {
            drawPlayerLife();
            drawMessage();
        }
        //PAUSE STATE
        else if (gp.gameState == gp.pauseState) {
            drawPauseScreen();
            drawPlayerLife();
        }
        //DIALOGUE STATE
        else if (gp.gameState == gp.dialogueState) {
            drawPlayerLife();
            drawDialogueScreen();// NPC dialogue uses box
        }
        //CHARACTER STATE
        else if (gp.gameState == gp.characterState) {
            drawCharacterScreen();
        }

        drawTransition();
    }

    public void drawPlayerLife(){
        //gp.player.life = 6;

        int x = gp.tileSize/2;
        int y = gp.tileSize/2;
        int i = 0;

        //Draw MAX LIFE
        while (i < gp.player.maxLife/2){
            g2.drawImage(heart_blank, x, y, null);
            i++;
            x += gp.tileSize;
        }

        //RESET
        x = gp.tileSize/2;
        y = gp.tileSize/2;
        i = 0;

        while (i < gp.player.life){
            g2.drawImage(heart_half, x, y, null);
            i++;
            if(i < gp.player.life){
                g2.drawImage(heart_full, x, y, null);
            }
            i++;
            x += gp.tileSize;
        }
    }

    public void drawMessage(){

        int messageX = gp.tileSize;
        int messageY = gp.tileSize*4;

        g2.setFont(g2.getFont().deriveFont(Font.BOLD, 32F));

        for(int i = 0; i < message.size(); i++){
            if (message.get(i) != null){
                g2.setColor(Color.black);
                g2.drawString(message.get(i), messageX+2, messageY+2);

                g2.setColor(Color.white);
                g2.drawString(message.get(i), messageX, messageY);

                int counter = messageCounter.get(i) + 1;
                messageCounter.set(i, counter);
                messageY += 50;

                if(messageCounter.get(i) > 180){
                    message.remove(i);
                    messageCounter.remove(i);
                }
            }
        }
    }

    // TITLE SCREEN / INTRO PAGES

    public void drawTitleScreen() {
        if (titleScreenState == 0) {
            // Main menu
            g2.setColor(new Color(100, 156, 245));
            g2.fillRect(0, 0, gp.screenWidth, gp.screenHeight);

            g2.setFont(g2.getFont().deriveFont(Font.BOLD, 150F));
            String text = "NIGGA QUEST";
            int x = getXforCenteredText(text);
            int y = gp.tileSize * 3;

            // Shadow
            g2.setColor(new Color(0, 0, 0, 120));
            g2.drawString(text, x + 6, y + 6);

            // Main
            g2.setColor(Color.white);
            g2.drawString(text, x, y);

            // Player image
            x = gp.screenWidth / 2 - gp.tileSize;
            y += gp.tileSize * 2;
            g2.drawImage(gp.player.down1, x, y, gp.tileSize * 2, gp.tileSize * 2, null);

            // Menu
            g2.setFont(g2.getFont().deriveFont(Font.BOLD, 70F));
            text = "NEW GAME";
            x = getXforCenteredText(text);
            y += gp.tileSize * 4;
            drawMenuText(text, x, y, 0);

            text = "LOAD GAME";
            x = getXforCenteredText(text);
            y += gp.tileSize;
            drawMenuText(text, x, y, 1);

            text = "QUIT";
            x = getXforCenteredText(text);
            y += gp.tileSize;
            drawMenuText(text, x, y, 2);
        }


        // INTRO PAGES (1-3) CENTERED TEXT, NO BOX //increase to add another page

        else if (titleScreenState >= 1 && titleScreenState <= 5) {
            g2.setColor(Color.BLACK);
            g2.fillRect(0, 0, gp.screenWidth, gp.screenHeight);

            g2.setFont(g2.getFont().deriveFont(48F));
            g2.setColor(Color.WHITE);

            // Set text per page //increase to add another page
            switch (titleScreenState) {
                case 1:
                    currentDialogue = "Greetings, my Nigga!" +
                            "\n Welcome to Nigga Quest.";
                    break;
                case 2:
                    currentDialogue = "We hate Gays, we hate Nigga's," +
                            "\n but more importantly, we hate the Jews";
                    break;
                case 3:
                    currentDialogue = "W - UP" +
                            "\n A - LEFT" +
                            "\n S - DOWN" +
                            "\n D - RIGHT" +
                            "\n P - Pause" +
                            "\n ENTER - to interact" +
                            "\n you retard.";
                    break;
                case 4:
                    currentDialogue = "okay";

                    break;
                case 5:
                    currentDialogue = "alright";
                   break;
            }

            drawCenteredDialogue();

            // Instruction
            String instr = "Press [Enter] to continue";
            int x = getXforCenteredText(instr);
            int y = gp.screenHeight - gp.tileSize;
            g2.drawString(instr, x, y);
        }
    }

    public void drawMenuText(String text, int x, int y, int index) {
        g2.setColor(new Color(0, 0, 0, 120));
        g2.drawString(text, x + 6, y + 6);
        g2.setColor(Color.white);
        g2.drawString(text, x, y);
        if (commandNum == index) {
            g2.drawString(">", x - gp.tileSize, y);
        }
    }


    // TRANSITION SYSTEM (FADE IN / FADE OUT)
    public void drawTransition() {
        if (!doingTransition) return;

        g2.setColor(new Color(0, 0, 0, transitionAlpha));
        g2.fillRect(0, 0, gp.screenWidth, gp.screenHeight);

        // Fade out to next page
        if (fadeOut) {
            transitionAlpha += 5;
            if (transitionAlpha >= 255) {
                transitionAlpha = 255;
                doingTransition = false;
                fadeOut = false;

                // Move to next page //increase to add another page
                if (titleScreenState == 0) titleScreenState = 1;
                else if (titleScreenState >= 1 && titleScreenState < 5) titleScreenState++;
                else if (titleScreenState == 5) {
                    gp.gameState = gp.playState;
                    titleScreenState = 0;
                }

                // Start fade in
                doingTransition = true;
                fadeIn = true;
            }
        }

        // Fade in before showing page
        else if (fadeIn) {
            transitionAlpha -= 5;
            if (transitionAlpha <= 0) {
                transitionAlpha = 0;
                doingTransition = false;
                fadeIn = false;
            }
        }
    }


    // PAUSE SCREEN
    public void drawPauseScreen() {
        g2.setFont(g2.getFont().deriveFont(80F));
        String text = "Paused";
        int x = getXforCenteredText(text);
        int y = gp.screenHeight / 2;

        g2.setColor(new Color(0, 0, 0, 120));
        g2.drawString(text, x + 4, y + 4);

        g2.setColor(Color.white);
        g2.drawString(text, x, y);
    }


    // NPC DIALOGUE (WITH BOX)

    public void drawDialogueScreen() {
        int x = gp.tileSize * 2;
        int y = gp.tileSize / 2;
        int width = gp.screenWidth - gp.tileSize * 4;
        int height = gp.tileSize * 4;

        drawSubWindow(x, y, width, height);

        g2.setFont(g2.getFont().deriveFont(40F));
        x += gp.tileSize;
        y += gp.tileSize;

        for (String line : currentDialogue.split("\n")) {
            g2.drawString(line, x, y);
            y += 40;
        }
    }

    public void drawSubWindow(int x, int y, int width, int height) {
        g2.setColor(new Color(0, 0, 0, 210));
        g2.fillRoundRect(x, y, width, height, 35, 35);

        g2.setColor(Color.white);
        g2.setStroke(new BasicStroke(5));
        g2.drawRoundRect(x + 5, y + 5, width - 10, height - 10, 25, 25);
    }

    public void drawCharacterScreen(){

        //FRAME
        final int frameX = gp.tileSize;
        final int frameY = gp.tileSize ;
        final int frameWidth = gp.tileSize*5;
        final int frameHeight = gp.tileSize*10;
        drawSubWindow(frameX, frameY, frameWidth, frameHeight);

        //TEXT
        g2.setColor(Color.white);
        g2.setFont(g2.getFont().deriveFont(45F));

        int textX = frameX + 20;
        int textY = frameY +gp.tileSize;
        final int lineHeight = 45;

        //NAMES
        g2.drawString("Level", textX, textY);
        textY += lineHeight;
        g2.drawString("Life", textX, textY);
        textY += lineHeight;
        g2.drawString("Strength", textX, textY);
        textY += lineHeight;
        g2.drawString("Dexterity", textX, textY);
        textY += lineHeight;
        g2.drawString("Attack", textX, textY);
        textY += lineHeight;
        g2.drawString("Defense", textX, textY);
        textY += lineHeight;
        g2.drawString("Exp", textX, textY);
        textY += lineHeight;
        g2.drawString("Next Level", textX, textY);
        textY += lineHeight;
        g2.drawString("Coin", textX, textY);
        textY += lineHeight+20;
        g2.drawString("Weapon", textX, textY);
        textY += lineHeight + 15;
        g2.drawString("Shield", textX, textY);
        textY += lineHeight;


        //VALUES
        int tailX = (frameX + frameWidth) - 30;

        //RESET TEXT Y
        textY = frameY + gp.tileSize;
        String value;

        value = String.valueOf(gp.player.level);
        textX = getXforAlignToRightText(value, tailX);
        g2.drawString(value, textX, textY);
        textY += lineHeight;

        value = String.valueOf(gp.player.life + "/" + gp.player.maxLife);
        textX = getXforAlignToRightText(value, tailX);
        g2.drawString(value, textX, textY);
        textY += lineHeight;

        value = String.valueOf(gp.player.strength);
        textX = getXforAlignToRightText(value, tailX);
        g2.drawString(value, textX, textY);
        textY += lineHeight;

        value = String.valueOf(gp.player.dexterity);
        textX = getXforAlignToRightText(value, tailX);
        g2.drawString(value, textX, textY);
        textY += lineHeight;

        value = String.valueOf(gp.player.attack);
        textX = getXforAlignToRightText(value, tailX);
        g2.drawString(value, textX, textY);
        textY += lineHeight;

        value = String.valueOf(gp.player.defense);
        textX = getXforAlignToRightText(value, tailX);
        g2.drawString(value, textX, textY);
        textY += lineHeight;

        value = String.valueOf(gp.player.exp);
        textX = getXforAlignToRightText(value, tailX);
        g2.drawString(value, textX, textY);
        textY += lineHeight;

        value = String.valueOf(gp.player.nextLevelExp);
        textX = getXforAlignToRightText(value, tailX);
        g2.drawString(value, textX, textY);
        textY += lineHeight;

        value = String.valueOf(gp.player.coin);
        textX = getXforAlignToRightText(value, tailX);
        g2.drawString(value, textX, textY);
        textY += lineHeight;

        g2.drawImage(gp.player.currentWeapon.down1, tailX - gp.tileSize, textY- 28, null);
        textY += gp.tileSize;
        g2.drawImage(gp.player.currentShield.down1, tailX - gp.tileSize, textY- 28, null);


    }


    // CENTERED DIALOGUE FOR INTRO PAGES

    public void drawCenteredDialogue() {
        String[] lines = currentDialogue.split("\n");
        g2.setFont(g2.getFont().deriveFont(48F));
        int totalHeight = lines.length * 50; // line spacing
        int startY = gp.screenHeight / 2 - totalHeight / 2;

        for (String line : lines) {
            int x = getXforCenteredText(line);
            g2.drawString(line, x, startY);
            startY += 50;
        }
    }

    public int getXforCenteredText(String text) {
        int length = (int) g2.getFontMetrics().getStringBounds(text, g2).getWidth();
        return gp.screenWidth / 2 - length / 2;
    }

    public int getXforAlignToRightText(String text, int tailX) {
        int length = (int) g2.getFontMetrics().getStringBounds(text, g2).getWidth();
        int x = tailX - length;
        return tailX - length;
    }
}
