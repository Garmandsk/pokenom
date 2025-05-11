package ui;

import entity.Entity;
import font.FontManager;
import font.Fonts;
import main.GamePanel;
import object.HeartObject;
import object.ManaCrystalObject;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

public class UI {
    public GamePanel gameP;
    Graphics2D g2d;

    /* Font Setting */
    public FontManager fontM = new FontManager();
    Fonts[] fonts = new Fonts[10];
    public Font Open_Sans, Cinzel, Oswald;
    /* ===== */

//    BufferedImage keyImage;

    public boolean messageOn;
//    public String message = "";
    ArrayList<String> message = new ArrayList<>();
    ArrayList<Integer> messageCounter = new ArrayList<>();

    public boolean gameFinished;
//    public double playTime = 0;
//    DecimalFormat dFormat = new DecimalFormat("#0.00");

    /* Dialogue */
    public String currentDialogue = "";

    /* Menu */
    public int titleScreenState = 0;
    public int commandNum;

    /* Player Stats */
    BufferedImage heartBlank, heartFull, heartHalf;
    BufferedImage manaCrystalBlank, manaCrystalFull;

    /* Inventory */
    public int inventoryMaxCol = 5;
    public int inventoryMaxRow = 4;
    public int slotCol = 0, slotRow = 0;

    public UI(GamePanel gameP){
        this.gameP = gameP;
        fontM.setFont(fonts);

        Cinzel = fonts[0].font; // Untuk Title
        Open_Sans = fonts[1].font; // Untuk Dialogue
        Oswald = fonts[2].font; // Untuk Status

//        KeyObject key = new KeyObject(gameP);
//        keyImage = key.image;
        Entity heart = new HeartObject(gameP);
        heartBlank = heart.image;
        heartFull = heart.image2;
        heartHalf = heart.image3;

        Entity manaCrystal = new ManaCrystalObject(gameP);
        manaCrystalBlank = manaCrystal.image;
        manaCrystalFull = manaCrystal.image2;
    }

    public void addMessage(String text){
//        message = text;
//        messageOn = true;

        message.add(text);
        messageCounter.add(0);
    }

    public int getXForCenteredText(String text){
        int length = (int)g2d.getFontMetrics().getStringBounds(text, g2d).getWidth();
        return gameP.screenWidth/2 - length/2;
    }

    public int getXForAlignToRightText(String text, int tailX){
        int length = (int)g2d.getFontMetrics().getStringBounds(text, g2d).getWidth();
        return tailX - length;
    }

    public void drawSubWindow(int x, int y, int width, int height){
//        System.out.println("Width: " + x);
        Color c = new Color(0, 0, 0, 220);
        g2d.setColor(c);
        g2d.fillRoundRect(x, y, width, height, 35, 35);

        c = new Color(255, 255, 255);
        g2d.setColor(c);
        g2d.setStroke(new BasicStroke(5));
        g2d.drawRoundRect(x+5, y+5, width-10, height-10, 25, 25);
    }

    public void drawPlayerLife(){
//        gameP.player.life = 3;

        int x = gameP.tileSize/2;
        int y = gameP.tileSize/2;
        int i = 0;

        // Draw Max Lifes
        while (i < gameP.player.maxLife/2){
            g2d.drawImage(heartBlank, x, y, null);
            i++;
            x += gameP.tileSize;
        }

        // Reset
        x = gameP.tileSize/2;
        i = 0;

        while (i < gameP.player.life){
            g2d.drawImage(heartHalf, x, y, null);
            i++;
            if (i < gameP.player.life) g2d.drawImage(heartFull, x, y, null);
            i++;
            x += gameP.tileSize;
        }

        x = gameP.tileSize/2;
        y += gameP.tileSize*1;
        i = 0;

//        gameP.player.mana = 3;
        // Draw Max Mana
        while (i < gameP.player.maxMana){
            g2d.drawImage(manaCrystalBlank, x, y, null);
            i++;
            x += gameP.tileSize;
        }

        // Reset
        x = gameP.tileSize/2;
        i = 0;

        while (i < gameP.player.mana){
            g2d.drawImage(manaCrystalFull, x, y, null);
            i++;
            x += gameP.tileSize;
        }

    }

    public void drawMessage(){
        int messageX = gameP.tileSize;
        int messageY = gameP.tileSize * 4;
        g2d.setFont(Oswald.deriveFont(Font.BOLD, 32f));

        for (int i = 0; i < message.size(); i++){

            if (message.get(i) != null){
                g2d.setColor(Color.black);
                g2d.drawString(message.get(i), messageX+2, messageY+2);
                g2d.setColor(Color.white);
                g2d.drawString(message.get(i), messageX, messageY);

                int counter = messageCounter.get(i) + 1;
                messageCounter.set(i, counter);
                messageY += gameP.tileSize;

                if (messageCounter.get(i) >= 180){
                    message.remove(i);
                    messageCounter.remove(i);
                }
            }
        }
    }

    public void drawPauseScreen(){
        g2d.setFont(g2d.getFont().deriveFont(Font.PLAIN, 80f));

        String text = "PAUSED";
        int x = getXForCenteredText(text);
        int y = gameP.screenHeight/2;

        g2d.drawString(text, x, y);
    }

    public void drawDialogueScreen(){
        g2d.setFont(Open_Sans);
        g2d.setFont(g2d.getFont().deriveFont(Font.PLAIN,32f));
        int x = gameP.tileSize*2;
        int y = gameP.tileSize/2;
        int width = gameP.tileSize*12;
        int height = gameP.tileSize * 4;
        drawSubWindow(x, y, width, height);

        x += gameP.tileSize;
        y += gameP.tileSize;
        for (String line : currentDialogue.split("\n")){
            g2d.drawString(line, x, y);
            y += gameP.tileSize/2;
        }
    }

    public void drawTitleScreen(){

        // Background
        g2d.setColor(Color.GRAY);
        g2d.fillRect(0, 0, gameP.screenWidth, gameP.screenHeight);
        if (titleScreenState == 0){
            // Text
            g2d.setFont(Cinzel);
            g2d.setFont(g2d.getFont().deriveFont(Font.PLAIN,90f));
            String text = "Pokénom";
            int x = getXForCenteredText(text);
            int y = gameP.tileSize*3;

            // Shadow
            g2d.setColor(new Color(61, 125, 202));
            g2d.drawString(text, x+5, y+5);

            // Text Color
            g2d.setColor(new Color(255, 203, 5));
            g2d.drawString(text, x, y);

//        // Image
//        x = gameP.screenWidth/2 - (gameP.tileSize*2)/2;
//        y += gameP.tileSize;
//        g2d.drawImage(gameP.player.down1, x, y, gameP.tileSize, gameP.tileSize, null);

            /* Menu */
            g2d.setFont(g2d.getFont().deriveFont(Font.BOLD, 40f));

            text = "NEW GAME";
            x = getXForCenteredText(text);
            y += gameP.tileSize*4;
            g2d.drawString(text, x, y);
            if (commandNum ==  0) g2d.drawString(">", x - gameP.tileSize, y);

            text = "CONTINUE";
            x = getXForCenteredText(text);
            y += gameP.tileSize*1 ;
            g2d.drawString(text, x, y);
            if (commandNum ==  1) g2d.drawString(">", x - gameP.tileSize, y);

            text = "EXIT";
            x = getXForCenteredText(text);
            y += gameP.tileSize*1;
            g2d.drawString(text, x, y);
            if (commandNum ==  2) g2d.drawString(">", x - gameP.tileSize, y);
        } else if (titleScreenState == 1) {
            g2d.setFont(Open_Sans);
            g2d.setFont(g2d.getFont().deriveFont(Font.BOLD, 40f));

            g2d.setColor(new Color(255, 203, 5));
            String text = "Select Your Signature Element!";
            int x = getXForCenteredText(text);
            int y = gameP.tileSize * 3;
            g2d.drawString(text, x, y);

            g2d.setColor(new Color(0, 161, 203));
            text = "Water";
            x = getXForCenteredText(text);
            y += gameP.tileSize*2;
            g2d.drawString(text, x, y);
            if (commandNum == 0) g2d.drawString(">", x - gameP.tileSize, y);

            g2d.setColor(new Color(153, 46, 28));
            text = "Fire";
            x = getXForCenteredText(text);
            y += gameP.tileSize*1;
            g2d.drawString(text, x, y);
            if (commandNum == 1) g2d.drawString(">", x - gameP.tileSize, y);

            g2d.setColor(new Color(3, 195, 12));
            text = "Earth";
            x = getXForCenteredText(text);
            y += gameP.tileSize*1;
            g2d.drawString(text, x, y);
            if (commandNum == 2) g2d.drawString(">", x - gameP.tileSize, y);

            g2d.setColor(new Color(245, 166, 35));
            text = "Thunder";
            x = getXForCenteredText(text);
            y += gameP.tileSize*1;
            g2d.drawString(text, x, y);
            if (commandNum == 3) g2d.drawString(">", x - gameP.tileSize, y);

            g2d.setColor(new Color(255, 203, 5));
            text = "Back";
            x = getXForCenteredText(text);
            y += gameP.tileSize*2;
            g2d.drawString(text, x, y);
            if (commandNum == 4) g2d.drawString(">", x - gameP.tileSize, y);

        }

    }

    public void drawCharacterScreen(){
        final int panelX = gameP.tileSize*2;
        final int panelY = gameP.tileSize*1;
        final int panelWidth = gameP.tileSize*5;
        final int panelHeight = gameP.tileSize*10 + 16;
        drawSubWindow(panelX, panelY, panelWidth, panelHeight);

        /* Text */
        g2d.setColor(Color.white);
        g2d.setFont(Oswald.deriveFont(32f));

        int textX = panelX + gameP.tileSize/2;
        int textY = panelY + gameP.tileSize*1;
        final int lineHeight = gameP.tileSize/2 + 15;

        // Names
        g2d.drawString("Level", textX, textY);
        textY += lineHeight;

        g2d.drawString("Life", textX, textY);
        textY += lineHeight;

        g2d.drawString("Mana", textX, textY);
        textY += lineHeight;

        g2d.drawString("Strength", textX, textY);
        textY += lineHeight;

        g2d.drawString("Dexterity", textX, textY);
        textY += lineHeight;

        g2d.drawString("Attack", textX, textY);
        textY += lineHeight;

        g2d.drawString("Defense", textX, textY);
        textY += lineHeight;

        g2d.drawString("Exp", textX, textY);
        textY += lineHeight;

        g2d.drawString("Next Level", textX, textY);
        textY += lineHeight;

        g2d.drawString("Coin", textX, textY);
        textY += gameP.tileSize;

        g2d.drawString("Weapon", textX, textY);
        textY += gameP.tileSize;

        g2d.drawString("Shield", textX, textY);

        // Values
        String value;
        int tailX = (panelX + panelWidth) - 30;

        value = String.valueOf(gameP.player.level);
        textX = getXForAlignToRightText(value, tailX);
        textY = panelY + gameP.tileSize*1;
        g2d.drawString(value, textX, textY);

        value = String.valueOf(gameP.player.life) + "/" + gameP.player.maxLife;
        textX = getXForAlignToRightText(value, tailX);
        textY += lineHeight;
        g2d.drawString(value, textX, textY);

        value = String.valueOf(gameP.player.mana + "/" + gameP.player.maxMana);
        textX = getXForAlignToRightText(value, tailX);
        textY += lineHeight;
        g2d.drawString(value, textX, textY);

        value = String.valueOf(gameP.player.strength);
        textX = getXForAlignToRightText(value, tailX);
        textY += lineHeight;
        g2d.drawString(value, textX, textY);

        value = String.valueOf(gameP.player.dexterity);
        textX = getXForAlignToRightText(value, tailX);
        textY += lineHeight;
        g2d.drawString(value, textX, textY);

        value = String.valueOf(gameP.player.attackPower);
        textX = getXForAlignToRightText(value, tailX);
        textY += lineHeight;
        g2d.drawString(value, textX, textY);

        value = String.valueOf(gameP.player.defensePower);
        textX = getXForAlignToRightText(value, tailX);
        textY += lineHeight;
        g2d.drawString(value, textX, textY);

        value = String.valueOf(gameP.player.exp);
        textX = getXForAlignToRightText(value, tailX);
        textY += lineHeight;
        g2d.drawString(value, textX, textY);

        value = String.valueOf(gameP.player.nextLevelExp);
        textX = getXForAlignToRightText(value, tailX);
        textY += lineHeight;
        g2d.drawString(value, textX, textY);

        value = String.valueOf(gameP.player.coin);
        textX = getXForAlignToRightText(value, tailX);
        textY += lineHeight;
        g2d.drawString(value, textX, textY);

        textY += gameP.tileSize*1;
        g2d.drawImage(gameP.player.currentWeapon.down1, tailX - gameP.tileSize, textY-44, null);
        textY += gameP.tileSize*1;
        g2d.drawImage(gameP.player.currentShield.down1, tailX - gameP.tileSize, textY-44, null);
    }

    public void drawInventory(){

        /* Item Panel */
        final int panelX = gameP.tileSize*9;
        final int panelY = gameP.tileSize*1;
        final int panelWidth = gameP.tileSize * (inventoryMaxCol + 1);
        final int panelHeight = gameP.tileSize * (inventoryMaxRow + 1);
        drawSubWindow(panelX, panelY, panelWidth, panelHeight);

        // Slot
        final int slotXStart = panelX + gameP.tileSize/2;
        final int slotYStart = panelY + gameP.tileSize/2;
        int slotX = slotXStart;
        int slotY = slotYStart;
        int slotSize = gameP.tileSize+4;

        for (int i = 0; i < gameP.player.inventory.size(); i++){

            if (gameP.player.inventory.get(i) == gameP.player.currentWeapon || gameP.player.inventory.get(i) == gameP.player.currentShield){
                g2d.setColor(new Color(240, 190, 90));
                g2d.fillRoundRect(slotX, slotY, gameP.tileSize, gameP.tileSize, 10, 10);
            }

            g2d.drawImage(gameP.player.inventory.get(i).down1, slotX, slotY, null);
            slotX += slotSize;

            if (i == 4 || i == 9 || i == 14){
                slotX = slotXStart;
                slotY += slotSize;
            }
        }

        // Cursor
        int cursorX = slotXStart + (slotSize * slotCol);
        int cursorY = slotYStart + (slotSize * slotRow);
        int cursorWidth = gameP.tileSize;
        int cursorHeight = gameP.tileSize;

        g2d.setColor(Color.white);
        g2d.setStroke(new BasicStroke(3));
        g2d.drawRoundRect(cursorX, cursorY, cursorWidth, cursorHeight, 10, 10);

        /* ===== */

        /* Description Panel */
        int dPanelX = panelX;
        int dPanelY = panelY + panelHeight;
        int dPanelWidth = panelWidth;
        int dPanelHeight = gameP.tileSize*3;

        // Text
        int textX = dPanelX + gameP.tileSize/2;
        int textY = dPanelY + gameP.tileSize/2 + 16;
        g2d.setFont(Oswald.deriveFont(28f));

        int itemIndex = getItemIndexOnSlot();

        if (itemIndex < gameP.player.inventory.size()){
            drawSubWindow(dPanelX, dPanelY, dPanelWidth, dPanelHeight);
            for (String line : gameP.player.inventory.get(itemIndex).itemDescription.split("\n")){
                g2d.drawString(line, textX, textY);
                textY += gameP.tileSize/2+16;
            }
        }
        /* ===== */
    }

    public int getItemIndexOnSlot(){
//        System.out.println("Slot COl: " + slotCol);
//        System.out.println("Slot Row: " + slotRow);

        return slotCol + (slotRow * inventoryMaxCol);
    }

    public void draw(Graphics2D g2d){
        this.g2d = g2d;
//        System.out.println(fontM.fonts[1].font);
        g2d.setFont(Cinzel);
        g2d.setColor(Color.white);

        if (gameP.gameState == gameP.playState){
            drawPlayerLife();
            drawMessage();
        }

        if (gameP.gameState == gameP.pauseState){
            drawPlayerLife();
            drawPauseScreen();
        }

        if (gameP.gameState == gameP.dialogueState){
            drawPlayerLife();
            drawDialogueScreen();
        }

        if (gameP.gameState == gameP.titleState){
            drawTitleScreen();
        }

        if (gameP.gameState ==  gameP.characterState){
            drawCharacterScreen();
            drawInventory();
        }
    }
}
