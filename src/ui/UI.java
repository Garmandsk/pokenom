package ui;

import entity.Entity;
import font.FontManager;
import font.Fonts;
import main.GamePanel;
import object.CoinBronzeObject;
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
    public int counter = 0;
//    public double playTime = 0;
//    DecimalFormat dFormat = new DecimalFormat("#0.00");

    /* Dialogue */
    public String currentDialogue = "";

    /* State */
    public int titleScreenState = 0;
    public int optionScreenState = 0;
    public int tradeScreenState = 0;
    public int commandNum;

    /* Player Stats */
    BufferedImage heartBlank, heartFull, heartHalf;
    BufferedImage manaCrystalBlank, manaCrystalFull;
    BufferedImage coin;

    /* Inventory */
    public int inventoryMaxCol = 5;
    public int inventoryMaxRow = 4;
    public int playerSlotCol = 0, playerSlotRow = 0;
    public int npcSlotCol = 0, npcSlotRow = 0;

    public Entity npc;

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

        Entity bronzeCoin = new CoinBronzeObject(gameP);
        coin = bronzeCoin.down1;
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

    public int getItemIndexOnSlot(int slotCol, int slotRow){
//        System.out.println("Slot COl: " + slotCol);
//        System.out.println("Slot Row: " + slotRow);

        return slotCol + (slotRow * inventoryMaxCol);
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
        int messageX = gameP.tileSize/2;
        int messageY = gameP.tileSize * 4;
        g2d.setFont(Oswald.deriveFont(Font.BOLD, 28f));

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

    public void drawTransition(){
        counter++;
        g2d.setColor(new Color(0, 0, 0, counter*5));
        g2d.fillRect(0, 0, gameP.screenWidth, gameP.screenHeight);
        if (counter >= 50) {
            counter = 0;
            gameP.gameState = gameP.playState;
            gameP.currentMap = gameP.eventH.tempMap;
            gameP.player.worldX = gameP.tileSize * gameP.eventH.tempCol;
            gameP.player.worldY = gameP.tileSize * gameP.eventH.tempRow;
            gameP.eventH.previousEventX = gameP.player.worldX;
            gameP.eventH.previousEventY = gameP.player.worldY;
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
        int x = gameP.tileSize*5;
        int y = gameP.tileSize/2;
        int width = gameP.tileSize*10;
        int height = gameP.tileSize * 4;
        drawSubWindow(x, y, width, height);

        x += gameP.tileSize/2;
        y += gameP.tileSize;
        for (String line : currentDialogue.split("\n")){
            g2d.drawString(line, x, y);
            y += gameP.tileSize;
        }
    }

    public void drawTitleScreen(){
        // Background
        g2d.setColor(Color.GRAY);
        g2d.fillRect(0, 0, gameP.screenWidth, gameP.screenHeight);

        switch (titleScreenState) {
            case 0:
                title_mainMenu();
                break;
            case 1:
                title_selectElement();
                break;
        }

        gameP.keyH.enterPressed = false;
    }

    public void title_mainMenu(){
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
    }

    public void title_selectElement(){
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

        g2d.setColor(new Color(65, 50, 30));
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

    public void drawCharacterScreen(){
        final int windowX = gameP.tileSize*2;
        final int windowY = gameP.tileSize*1;
        final int windowWidth = gameP.tileSize*5;
        final int windowHeight = gameP.tileSize*10 + 36;
        drawSubWindow(windowX, windowY, windowWidth, windowHeight);

        /* Text */
        g2d.setColor(Color.white);
        g2d.setFont(Oswald.deriveFont(32f));

        int textX = windowX + gameP.tileSize/2;
        int textY = windowY + gameP.tileSize*1;
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
        int tailX = (windowX + windowWidth) - 20;

        value = String.valueOf(gameP.player.level);
        textX = getXForAlignToRightText(value, tailX);
        textY = windowY + gameP.tileSize*1;
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

        textY += gameP.tileSize*1 + 8;
        g2d.drawImage(gameP.player.currentWeapon.down1, tailX - gameP.tileSize + 10, textY-44, null);
        textY += gameP.tileSize*1;
        g2d.drawImage(gameP.player.currentShield.down1, tailX - gameP.tileSize + 10, textY-44, null);
    }

    public void drawInventory(Entity entity, boolean cursor){

        int windowX = gameP.tileSize * (gameP.maxScreenCol*3/5);
        int windowY = gameP.tileSize*1;
        int windowWidth = gameP.tileSize * (inventoryMaxCol + 1) + (gameP.tileSize/4);
        int windowHeight = gameP.tileSize * (inventoryMaxRow + 1);
        int slotCol = 0, slotRow = 0;

        if (entity == gameP.player){
            windowX = gameP.tileSize * (gameP.maxScreenCol*3/5);
            windowY = gameP.tileSize*1;
            windowWidth = gameP.tileSize * (inventoryMaxCol + 1) + (gameP.tileSize/4);
            windowHeight = gameP.tileSize * (inventoryMaxRow + 1);
            slotCol = playerSlotCol;
            slotRow = playerSlotRow;
        } else {
            windowX = gameP.tileSize * (gameP.maxScreenCol/10);
            windowY = gameP.tileSize*1;
            windowWidth = gameP.tileSize * (inventoryMaxCol + 1) + (gameP.tileSize/4);
            windowHeight = gameP.tileSize * (inventoryMaxRow + 1);
            slotCol = npcSlotCol;
            slotRow = npcSlotRow;
        }
        /* Item window */
        drawSubWindow(windowX, windowY, windowWidth, windowHeight);

        // Slot
        final int slotXStart = windowX + gameP.tileSize/2;
        final int slotYStart = windowY + gameP.tileSize/2;
        int slotX = slotXStart;
        int slotY = slotYStart;
        int slotSize = gameP.tileSize+4;

        // Draw Items
        for (int i = 0; i < entity.inventory.size(); i++){

            if (entity.inventory.get(i) == entity.currentWeapon || entity.inventory.get(i) == entity.currentShield || entity.inventory.get(i) == entity.currentLight){
                g2d.setColor(new Color(240, 190, 90));
                g2d.fillRoundRect(slotX, slotY, gameP.tileSize, gameP.tileSize, 10, 10);
            }

            g2d.drawImage(entity.inventory.get(i).down1, slotX, slotY, null);

            // Display Amount
            if (entity == gameP.player && entity.inventory.get(i).amount > 1){
                g2d.setFont(Oswald.deriveFont(28f));
                int amountX, amountY;

                String qty = "" + entity.inventory.get(i).amount;
                amountX = getXForAlignToRightText(qty, slotX+44);
                amountY = slotY + gameP.tileSize;

                // Shadow
                g2d.setColor(new Color(60, 60, 60));
                g2d.drawString(qty, amountX, amountY);

                // Quantity
                g2d.setColor(Color.white);
                g2d.drawString(qty, amountX-3, amountY-3);

            }

            slotX += slotSize;

            if (i == 4 || i == 9 || i == 14){
                slotX = slotXStart;
                slotY += slotSize;
            }
        }

        // Cursor
        if (cursor == true){

            int cursorX = slotXStart + (slotSize * slotCol);
            int cursorY = slotYStart + (slotSize * slotRow);
            int cursorWidth = gameP.tileSize;
            int cursorHeight = gameP.tileSize;

            g2d.setColor(Color.white);
            g2d.setStroke(new BasicStroke(3));
            g2d.drawRoundRect(cursorX, cursorY, cursorWidth, cursorHeight, 10, 10);

            /* Description window */
            int dWindowX = windowX;
            int dWindowY = windowY + windowHeight;
            int dWindowWidth = windowWidth;
            int dWindowHeight = gameP.tileSize*3;

            // Text
            int textX = dWindowX + gameP.tileSize/2;
            int textY = dWindowY + gameP.tileSize/2 + 16;
            g2d.setFont(Oswald.deriveFont(28f));

            int itemIndex = getItemIndexOnSlot(slotCol, slotRow);
            if (itemIndex < entity.inventory.size()){
                drawSubWindow(dWindowX, dWindowY, dWindowWidth, dWindowHeight);
                for (String line : entity.inventory.get(itemIndex).itemDescription.split("\n")){
                    g2d.drawString(line, textX, textY);
                    textY += gameP.tileSize/2+16;
                }
            }
        }
        /* ===== */
    }

    public void drawOptionScreen(){
        g2d.setColor(Color.white);
        g2d.setFont(Cinzel.deriveFont(21f));

        int windowX = gameP.tileSize * 6;
        int windowY = gameP.tileSize;
        int windowWidth = gameP.tileSize * 8;
        int windowHeight = gameP.tileSize * 10;
        drawSubWindow(windowX, windowY, windowWidth, windowHeight);

        switch (optionScreenState){
            case 0:
                options_top(windowX, windowY);
                break;
            case 1:
                options_fullScreenNotification(windowX, windowY);
                break;
            case 2:
                options_control(windowX, windowY);
                break;
            case 3:
                options_endGameConfirmation(windowX, windowY);
                break;
        }

        gameP.keyH.enterPressed = false;
    }

    public void options_top(int windowX, int windowY) {
        int textX, textY;

        // Title
        String text = "Options";
        textX = getXForCenteredText(text);
        textY = windowY + gameP.tileSize;
        g2d.drawString(text, textX, textY);

        /* Text */
        // Full Screen Setting
        textX = windowX + gameP.tileSize;
        textY += gameP.tileSize * 1;
        g2d.drawString("Full Screen", textX, textY);
        if (commandNum == 0) {
            g2d.drawString(">", textX-25, textY);
        }

        // Music Setting
        textY += gameP.tileSize;
        g2d.drawString("Music", textX, textY);
        if (commandNum == 1) g2d.drawString(">", textX-25, textY);

        // Sound Effect Setting
        textY += gameP.tileSize;
        g2d.drawString("Sound Effect", textX, textY);
        if (commandNum == 2) g2d.drawString(">", textX-25, textY);

        // Control Setting
        textY += gameP.tileSize;
        g2d.drawString("Control", textX, textY);
        if (commandNum == 3) g2d.drawString(">", textX-25, textY);

        // End Game Setting
        textY += gameP.tileSize;
        g2d.drawString("End Game", textX, textY);
        if (commandNum == 4) g2d.drawString(">", textX-25, textY);

        // Back Setting
        textY = windowY + gameP.tileSize * 9;
        g2d.drawString("Back", textX, textY);
        if (commandNum == 5) g2d.drawString(">", textX-25, textY);
        /* ===== */

        /* Value */
        // Full Screen Checkbox
        textX += (int) (gameP.tileSize * 4);
        textY = windowY + (gameP.tileSize * 1) + (gameP.tileSize/2) + 4;
        g2d.setStroke(new BasicStroke(3));
        g2d.drawRect(textX, textY, gameP.tileSize/2, gameP.tileSize/2);
        if (gameP.fullScreenOn) g2d.fillRect(textX, textY, gameP.tileSize/2, gameP.tileSize/2);

        // Music
        textY += gameP.tileSize;
        g2d.drawRect(textX, textY, gameP.tileSize*2, gameP.tileSize/2);
        int volumeWidth = ((gameP.tileSize*2)/5) * gameP.music.volumeScale;
        g2d.fillRect(textX, textY, volumeWidth, gameP.tileSize/2);

        // SE
        textY += gameP.tileSize;
        g2d.drawRect(textX, textY, gameP.tileSize*2, gameP.tileSize/2);
        volumeWidth = ((gameP.tileSize*2)/5) * gameP.se.volumeScale;
        g2d.fillRect(textX, textY, volumeWidth, gameP.tileSize/2);

        /* ===== */

        gameP.config.saveConfig();
    }

    public void options_fullScreenNotification(int windowX, int windowY){
        // Title
        String text = "Notification";
        int textX = getXForCenteredText(text);
        int textY = windowY + gameP.tileSize;
        g2d.drawString(text, textX, textY);

        textX = windowX + gameP.tileSize;
        textY = windowY + gameP.tileSize * 2;

        currentDialogue = "The change will take \n" +
                "effect after restarting \n" +
                "the game";

        for (String line : currentDialogue.split("\n")){
            g2d.drawString(line, textX, textY);
            textY+= gameP.tileSize;
        }

        // Back
        textY = windowY + gameP.tileSize * 9;
        g2d.drawString("Back", textX, textY);
        g2d.drawString(">", textX-25, textY);
    }

    public void options_control(int windowX, int windowY){
        int textX;
        int textY;

        // Title
        String text = "Control";
        textX = getXForCenteredText(text);
        textY = windowY + gameP.tileSize;
        g2d.drawString(text, textX, textY);

        textX = windowX + gameP.tileSize;
        textY += gameP.tileSize * 1;
        g2d.drawString("Move" , textX, textY); textY+=gameP.tileSize;
        g2d.drawString("Confirm/Attack" , textX, textY); textY+=gameP.tileSize;
        g2d.drawString("Shoot/Cast" , textX, textY); textY+=gameP.tileSize;
        g2d.drawString("Character Screen" , textX, textY); textY+=gameP.tileSize;
        g2d.drawString("Pause" , textX, textY); textY+=gameP.tileSize;
        g2d.drawString("Options" , textX, textY); textY+=gameP.tileSize;

        textX += gameP.tileSize * 5;
        textY = windowY + gameP.tileSize * 2;
        g2d.drawString("WASD" , textX, textY); textY+=gameP.tileSize;
        g2d.drawString("Enter" , textX, textY); textY+=gameP.tileSize;
        g2d.drawString("F" , textX, textY); textY+=gameP.tileSize;
        g2d.drawString("C" , textX, textY); textY+=gameP.tileSize;
        g2d.drawString("P" , textX, textY); textY+=gameP.tileSize;
        g2d.drawString("ESC" , textX, textY); textY+=gameP.tileSize;

        // Back
        textX = windowX + gameP.tileSize;
        textY = windowY + gameP.tileSize * 9;
        g2d.drawString("Back", textX, textY);
        g2d.drawString(">", textX-25, textY);
    }

    public void options_endGameConfirmation(int windowX, int windowY){
        // Title
        String text = "Notification";
        int textX = getXForCenteredText(text);
        int textY = windowY + gameP.tileSize;
        g2d.drawString(text, textX, textY);

        textX = windowX + gameP.tileSize;
        textY = windowY + gameP.tileSize * 2;

        currentDialogue = "Quit the game and \n" +
                "return to title screen";

        for (String line : currentDialogue.split("\n")){
            g2d.drawString(line, textX, textY);
            textY+= gameP.tileSize;
        }

        // Yes
        text = "Yes";
        textX = getXForCenteredText(text);
        textY += gameP.tileSize;
        g2d.drawString(text, textX, textY);
        if (commandNum == 0) g2d.drawString(">", textX-25, textY);

        // No
        text = "No";
        textX = getXForCenteredText(text);
        textY += gameP.tileSize;
        g2d.drawString(text, textX, textY);
        if (commandNum == 1) g2d.drawString(">", textX-25, textY);

    }

    public void drawGameOverScren(){
        g2d.setColor(new Color(0, 0, 0, 150));
        g2d.fillRect(0, 0, gameP.screenWidth, gameP.screenHeight);

        int x, y;
        String text;
        g2d.setFont(Cinzel.deriveFont(Font.BOLD, 110f));

        text = "Game Over";
        // Shadow
        g2d.setColor(Color.BLACK);
        x = getXForCenteredText(text);
        y = gameP.tileSize * 4;
        g2d.drawString(text, x, y);
        // Main
        g2d.setColor(Color.WHITE);
        g2d.drawString(text, x-4, y-4);

        // Retry
        g2d.setFont(g2d.getFont().deriveFont(50f));
        text = "Retry";
        x = getXForCenteredText(text);
        y += gameP.tileSize * 4;
        g2d.drawString(text, x, y);
        if (commandNum == 0) g2d.drawString(">", x - 40, y);

        // Quit
        g2d.setFont(g2d.getFont().deriveFont(50f));
        text = "Quit";
        x = getXForCenteredText(text);
        y += gameP.tileSize + (gameP.tileSize/2);
        g2d.drawString(text, x, y);
        if (commandNum == 1) g2d.drawString(">", x - 40, y);

    }

    public void drawTradeScreen(){
        switch (tradeScreenState) {
            case 0:
                trade_select();
                break;
            case 1:
                trade_buy();
                break;
            case 2:
                trade_sell();
                break;
        }

        gameP.keyH.enterPressed = false;
    }

    public void trade_select(){
        drawDialogueScreen();

        int x = gameP.tileSize * 13;
        int y = gameP.tileSize * 4;
        int width = (int)(gameP.tileSize * 3.5);
        int height = (int)(gameP.tileSize * 3.5);
        drawSubWindow(x, y, width, height);

        x += gameP.tileSize;
        y += gameP.tileSize;
        g2d.drawString("Buy", x, y);
        if (commandNum == 0) g2d.drawString(">", x-25, y);

        y += gameP.tileSize;
        g2d.drawString("Sell", x, y);
        if (commandNum == 1) g2d.drawString(">", x-25, y);

        y += gameP.tileSize;
        g2d.drawString("Leave", x, y);
        if (commandNum == 2) g2d.drawString(">", x-25, y);


    }

    public void trade_buy(){
        drawInventory(gameP.player, false);
        drawInventory(npc, true);

        // Draw Hint Window
        int x = gameP.tileSize * 2;
        int y = gameP.tileSize * 9;
        int width = gameP.tileSize * 6;
        int height = gameP.tileSize * 2;
        drawSubWindow(x, y, width, height);
        g2d.drawString("[ESC] Back", x + gameP.tileSize/2, y + gameP.tileSize + (gameP.tileSize/4));

        // Draw Coin Window
        x = gameP.tileSize * 12;
        y = gameP.tileSize * 9;
        width = gameP.tileSize * 6;
        height = gameP.tileSize * 2;
        drawSubWindow(x, y, width, height);
        g2d.drawString("Your Coin: " + gameP.player.coin, x + gameP.tileSize/2, y + gameP.tileSize + (gameP.tileSize/4));

        // Draw Price Window
        int itemIndex = getItemIndexOnSlot(npcSlotCol, npcSlotRow);
        if (itemIndex < npc.inventory.size()){
            x = (int)(gameP.tileSize * 5.5);
            y = (int)(gameP.tileSize * 5.5);
            width = (int)(gameP.tileSize * 2.5);
            height = gameP.tileSize;
            drawSubWindow(x, y, width, height);
            g2d.drawImage(coin, x+10, y+8, 32, 32, null);

            int price = npc.inventory.get(itemIndex).price;
            String text = "" + price;
            x = getXForAlignToRightText(text, gameP.tileSize*8-20);
            g2d.drawString(text, x, y+34);

            // Buy Item
            if (gameP.keyH.enterPressed){
                if (npc.inventory.get(itemIndex).price > gameP.player.coin){
                    tradeScreenState = 0;
                    gameP.gameState = gameP.dialogueState;
                    currentDialogue = "Coin tidak cukup";
                    drawDialogueScreen();
                } else {
                    if (gameP.player.canObtainItem(npc.inventory.get(itemIndex))) {
                        gameP.player.coin -= npc.inventory.get(itemIndex).price;
                    } else {
                        tradeScreenState = 0;
                        gameP.gameState = gameP.dialogueState;
                        currentDialogue = "Inventory Penuh";
                    }
                }
            }
        }
    }

    public void trade_sell(){
        drawInventory(gameP.player, true);

        // Draw Hint Window
        int x = gameP.tileSize * 2;
        int y = gameP.tileSize * 9;
        int width = gameP.tileSize * 6;
        int height = gameP.tileSize * 2;
        drawSubWindow(x, y, width, height);
        g2d.drawString("[ESC] Back", x + gameP.tileSize/2, y + gameP.tileSize + (gameP.tileSize/4));

        // Draw Coin Window
        x = gameP.tileSize * 12;
        y = gameP.tileSize * 9;
        width = gameP.tileSize * 6;
        height = gameP.tileSize * 2;
        drawSubWindow(x, y, width, height);
        g2d.drawString("Your Coin: " + gameP.player.coin, x + gameP.tileSize/2, y + gameP.tileSize + (gameP.tileSize/4));

        // Draw Price Window
        int itemIndex = getItemIndexOnSlot(playerSlotCol, playerSlotRow);
        if (itemIndex < gameP.player.inventory.size()){
            x = (int)(gameP.tileSize * 15.5);
            y = (int)(gameP.tileSize * 5.5);
            width = (int)(gameP.tileSize * 2.5);
            height = gameP.tileSize;
            drawSubWindow(x, y, width, height);
            g2d.drawImage(coin, x+10, y+8, 32, 32, null);

            int price = gameP.player.inventory.get(itemIndex).price/2;
            String text = "" + price;
            x = getXForAlignToRightText(text, gameP.tileSize*18-20);
            g2d.drawString(text, x, y+34);

            // Sell Item
            if (gameP.keyH.enterPressed){
                if (gameP.player.inventory.get(itemIndex) == gameP.player.currentWeapon || gameP.player.inventory.get(itemIndex) == gameP.player.currentShield){
                    tradeScreenState = 0;
                    gameP.gameState = gameP.dialogueState;
                    currentDialogue = "You can't sell an \nequipped item!";
                }else {
                    if (gameP.player.inventory.get(itemIndex).amount > 1) gameP.player.inventory.get(itemIndex).amount--;
                    else gameP.player.inventory.remove(itemIndex);
                    gameP.player.coin += price;
                }
            }
        }
    }

    public void drawSleepScreen(){
        counter++;

        if (counter < 120){
            gameP.envM.lighting.filterAlpha += 0.01f;

            if (gameP.envM.lighting.filterAlpha > 1f){
                gameP.envM.lighting.filterAlpha = 1f;
            }
        }

        if (counter >= 120){
            gameP.envM.lighting.filterAlpha -= 0.01f;

            if (gameP.envM.lighting.filterAlpha <= 0f){
                gameP.envM.lighting.filterAlpha = 0f;
                counter = 0;
                gameP.envM.lighting.currentDayState = gameP.envM.lighting.dayState;
                gameP.envM.lighting.dayCounter = 0;
                gameP.gameState = gameP.playState;
                gameP.player.getPlayerImage();
            }
        }
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

        if (gameP.gameState == gameP.titleState) drawTitleScreen();

        if (gameP.gameState ==  gameP.characterState){
            drawCharacterScreen();
            drawInventory(gameP.player, true);
        }

        if (gameP.gameState ==  gameP.optionState) drawOptionScreen();

        if (gameP.gameState == gameP.gameOverState) drawGameOverScren();

        if (gameP.gameState == gameP.transitionState) drawTransition();

        if (gameP.gameState == gameP.tradeState) drawTradeScreen();

        if (gameP.gameState == gameP.sleepState) drawSleepScreen();
    }
}
