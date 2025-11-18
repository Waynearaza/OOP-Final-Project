package main;

import object.OBJ_Key;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.text.DecimalFormat;

public class UI {
    GamePanel gp;
    Graphics2D g2;
    Font  Pixel_Game, arial_40, arial_80B;
    public  boolean messageOn = false;
    public String message = "";
    int messageCounter = 0;
    public boolean gameFinished = false;
    public String currentDialogue = "";
    public int commandNum = 0;
    public int titleScreenState = 0;


    public UI(GamePanel gp){
        this.gp = gp;

        //Initiates the Fonts and Gets the Font From Resources
        try{
            InputStream is = getClass().getResourceAsStream("/font/Pixel_Game.otf");
            //You Can Add More Fonts Here
            Pixel_Game = Font.createFont(Font.TRUETYPE_FONT, is);
        } catch (FontFormatException e){
            e.printStackTrace();
        } catch (IOException e){
            e.printStackTrace();
        }
    }

    public void showMessage(String text){
        message = text;
        messageOn = true;
    }

    public void draw(Graphics2D g2){
        this.g2 = g2;

        g2.setFont(Pixel_Game);
        g2.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON );
        g2.setColor(Color.white);

        //TITLE STAE
        if(gp.gameState == gp.titleState){
            drawTitleScreen();
        }
        //PLAY STATE
        if(gp.gameState == gp.playState){
            //Do Something Player
        }
        //PAUSE STATE
        if(gp.gameState == gp.pauseState){
            drawPauseScreen();
        }
        //DIALOGUE STATE
        if (gp.gameState == gp.dialogueState){
            drawDialogueScreen();
        }
    }

    //TITLE SCREEN PARAMETERS
    public void drawTitleScreen(){

        //SETS BACKGROUND COLOR
        g2.setColor(new Color(100, 156, 245));
        g2.fillRect(0, 0, gp.screenWidth, gp.screenHeight);

        //TITLE NAME
        g2.setFont(g2.getFont().deriveFont(Font.BOLD, 150F));
        String text = "NIGGA QUEST";
        int x = getXforCenteredText(text);
        int y = gp.tileSize*3;

        //Shadow
        g2.setColor(new Color(0, 0, 0, 120)); // translucent black shadow
        g2.drawString(text, x + 6, y + 6);

        //Main Text
        g2.setColor(Color.WHITE);
        g2.drawString(text, x, y);

        x = gp.screenWidth/2 - (gp.tileSize*2)/2;
        y += gp.tileSize*2;

        g2.drawImage(gp.player.down1, x, y, gp.tileSize*2, gp.tileSize*2, null);

        //MENU
        g2.setFont(g2.getFont().deriveFont(Font.BOLD, 70F));
        text = "NEW GAME";
        x = getXforCenteredText(text);
        y += gp.tileSize*4;
        //Shadow
        g2.setColor(new Color(0, 0, 0, 120)); // translucent black shadow
        g2.drawString(text, x + 6, y + 6);
        //Main Text
        g2.setColor(Color.WHITE);
        g2.drawString(text, x, y);
        if(commandNum == 0){
            g2.drawString(">", x-gp.tileSize, y);
        }

        text = "LOAD GAME";
        x = getXforCenteredText(text);
        y += gp.tileSize;
        //Shadow
        g2.setColor(new Color(0, 0, 0, 120)); // translucent black shadow
        g2.drawString(text, x + 6, y + 6);

        //Main Text
        g2.setColor(Color.WHITE);
        g2.drawString(text, x, y);
        if(commandNum == 1){
            g2.drawString(">", x-gp.tileSize, y);
        }

        text = "QUIT";
        x = getXforCenteredText(text);
        y += gp.tileSize;
        //Shadow
        g2.setColor(new Color(0, 0, 0, 120)); // translucent black shadow
        g2.drawString(text, x + 6, y + 6);

        //Main Text
        g2.setColor(Color.WHITE);
        g2.drawString(text, x, y);
        if(commandNum == 2){
            g2.drawString(">", x-gp.tileSize, y);
        }


    }

    //Shows the Pause when Paused
    public void drawPauseScreen(){
        g2.setFont(g2.getFont().deriveFont(Font.PLAIN, 80F));

        String text = "Paused";
        int x = getXforCenteredText(text);

        int y = gp.screenHeight/2;

        //Shadows Behind The Text
        g2.setColor(new Color(0, 0, 0, 120)); // translucent black shadow
        g2.drawString(text, x + 4, y + 4);  //Sets The Offset of the Shadow

        //Main TExt
        g2.setColor(Color.white);
        g2.drawString(text, x, y);
    }

    //Dialogue Screen
    public void drawDialogueScreen(){
        //WINDOW
        int x = gp.tileSize*2;
        int y = gp.tileSize/2;
        int width = gp.screenWidth - (gp.tileSize*4);
        int height= gp.tileSize*4;

        drawSubWindow(x, y, width, height);

        g2.setFont(g2.getFont().deriveFont(Font.PLAIN, 40F));
        x += gp.tileSize;
        y += gp.tileSize;

        for(String line : currentDialogue.split("\n")){
            g2.drawString(line, x, y);
            y += 40;
        }


    }

    //Sub Window of Dialogue the White Border Outside
    public void drawSubWindow(int x, int y, int width, int height){
        Color c = new Color (0, 0, 0, 210);

        g2.setColor(c);
        g2.fillRoundRect(x, y, width, height,35, 35);

        c = new Color(255, 255, 255);
        g2.setColor(c);
        g2.setStroke(new BasicStroke(5));
        g2.drawRoundRect(x+5, y+5, width-10, height-10, 25, 25 );
    }

    public int getXforCenteredText(String text){
        int length = (int)g2.getFontMetrics().getStringBounds(text, g2).getWidth();
        int x = gp.screenWidth/2 - length/2;
        return x;
    }
}

