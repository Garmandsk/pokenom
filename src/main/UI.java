package main;

import entity.Entity;
import entity.monster.MON_GreenSlime;
import font.FontManager;
import font.Fonts;
import object.pickupOnly.PU_OBJ_CoinBronze;
import object.pickupOnly.PU_OBJ_Heart;
import object.pickupOnly.PU_OBJ_ManaCrystal;

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
    public String[][] battleDialogues = new String[20][20];
    public int battleDialogueSet = 0, battleDialogueIndex = 0;

    public boolean gameFinished;
    public int counter = 0;
//    public double playTime = 0;
//    DecimalFormat dFormat = new DecimalFormat("#0.00");

    /* Dialogue */
    public String currentDialogue = "", combinedText = "", battleDialogueText = "";
    public int charIndex = 0;

    /* State */
    public int subState = 0, targetSubState;
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

    public Entity npc, enemy;
    public int monsterBattleID;
    UtilityTool uTool;

    /* Turn */
    public final int playerTurn = 1;
    public final int enemyTurn = 2;
    public int turn = playerTurn;

    /* QTE */
    public boolean guardQTE_Active = false;
    public int qteBar_x, qteBar_y, qteBar_width, qteBar_height;
    public int qteZone_x, qteZone_width;
    public int qteIndicator_x, qteIndicator_speed, qteIndicator_direction;
    public int qteTimer;

    public UI(GamePanel gameP){
        this.gameP = gameP;
        uTool = new UtilityTool();
        fontM.setFont(fonts);

        Cinzel = fonts[0].font; // Untuk Title
        Open_Sans = fonts[1].font; // Untuk Dialogue
        Oswald = fonts[2].font; // Untuk Status

//        KeyObject key = new KeyObject(gameP);
//        keyImage = key.image;
        Entity heart = new PU_OBJ_Heart(gameP);
        heartBlank = heart.image;
        heartFull = heart.image2;
        heartHalf = heart.image3;

        Entity manaCrystal = new PU_OBJ_ManaCrystal(gameP);
        manaCrystalBlank = manaCrystal.image;
        manaCrystalFull = manaCrystal.image2;

        Entity bronzeCoin = new PU_OBJ_CoinBronze(gameP);
        coin = bronzeCoin.down1;
        enemy = new MON_GreenSlime(gameP);

        setBattleDialogues();
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

    public int getXForCenteredTextByWindow(String text, int windowWidth){
        int length = (int)g2d.getFontMetrics().getStringBounds(text, g2d).getWidth();
        return windowWidth/2 - length/2;
    }

    public int getYForCenteredTextByWindow(int windowHeight){
        return windowHeight/2;
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

    public String getElementTypeNameByID(int idElementType){
        String elementTypeName = "";

        switch (idElementType){
            case 1 -> elementTypeName = "Water";
            case 2 -> elementTypeName = "Fire";
            case 3 -> elementTypeName = "Earth";
            case 4 -> elementTypeName = "Thunder";
        }

        return elementTypeName;
    }

    public void setBattleDialogues(){
        battleDialogues[0][0] = "It's Effective";
        battleDialogues[1][0] = "It's Super Effective";
        battleDialogues[2][0] = "It's Less Effective";
        battleDialogues[3][0] = "It's Not Effective";
        battleDialogues[4][0] = "Killed The " + enemy.name + "! \n" +
                "Gained " + enemy.exp + " Exp! ";
        battleDialogues[5][0] = enemy.name + " Hit You!";
    }

    public void startDialogue(int setNum){
        battleDialogueSet = setNum;
    }

    public void resetDialogue(){
        battleDialogueText = "";
        charIndex = 0;
        combinedText = "";
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

    public void drawSubWindow2(int x, int y, int width, int height){
//        System.out.println("Width: " + x);
        Color c = new Color(255, 255, 255);
        g2d.setColor(c);
        g2d.fillRoundRect(x, y, width, height, 35, 35);

        c = new Color(0, 0, 0, 220);
        g2d.setColor(c);
        g2d.setStroke(new BasicStroke(5));
        g2d.drawRoundRect(x+5, y+5, width-10, height-10, 25, 25);
    }

    public void drawPlayerLife(){
//        gameP.player.maxLife = 14;
//        gameP.player.life = 14;
        final int TILE_SIZE = gameP.tileSize;
        int x = TILE_SIZE / 2;
        int y = TILE_SIZE / 2;

        /* Heart */
        int maxHeartContainers = gameP.player.maxLife / 2;
        final int HEARTS_PER_ROW = 6; // Jumlah hati per baris

        for (int i = 0; i < maxHeartContainers; i++) {
            int row = i / HEARTS_PER_ROW;
            int col = i % HEARTS_PER_ROW;
            int currentX = x + (col * TILE_SIZE);
            int currentY = y + (row * TILE_SIZE);

            // Menentukan gambar hati mana yang akan digunakan
            if (gameP.player.life >= (i + 1) * 2) {
                // Jika life player cukup untuk mengisi penuh hati ini (2 poin)
                g2d.drawImage(heartFull, currentX, currentY, null);
            } else if (gameP.player.life % 2 == 1 && gameP.player.life / 2 == i) {
                // Jika life player hanya cukup untuk mengisi setengah hati ini (1 poin sisa)
                g2d.drawImage(heartHalf, currentX, currentY, null);
            } else {
                // Jika life player tidak cukup, gambar hati kosong
                g2d.drawImage(heartBlank, currentX, currentY, null);
            }
        }

        /* Mana Crystal */
        int lifeRows = (int) Math.ceil((double) maxHeartContainers / HEARTS_PER_ROW);
        x = TILE_SIZE / 2;
        y = (TILE_SIZE / 2) + (lifeRows * TILE_SIZE) + (TILE_SIZE / 2); // Tambah sedikit spasi

        final int MANA_PER_ROW = 8;
        final int MANA_SPACING = TILE_SIZE * 3 / 4;

        for (int i = 0; i < gameP.player.maxMana; i++) {
            int row = i / MANA_PER_ROW;
            int col = i % MANA_PER_ROW;
            int currentX = x + (col * MANA_SPACING);
            int currentY = y + (row * TILE_SIZE);

            // Logikanya lebih sederhana untuk mana
            if (i < gameP.player.mana) {
                // Jika 'i' masih di bawah jumlah mana player, gambar kristal penuh
                g2d.drawImage(manaCrystalFull, currentX, currentY, null);
            } else {
                // Jika tidak, gambar kristal kosong
                g2d.drawImage(manaCrystalBlank, currentX, currentY, null);
            }
        }
    }

    public void drawMonsterLife(){
        for (int i = 0; i < gameP.monster[1].length; i++){
            Entity monster = gameP.monster[gameP.currentMap][i];
            if (gameP.monster[gameP.currentMap][i] != null && gameP.monster[gameP.currentMap][i].inCamera()){
                if (monster.hpBarOn && monster.boss == false){
                    double oneScale = (double)gameP .tileSize/monster.maxLife;
                    double hpBarValue = oneScale * monster.life;

                    g2d.setColor(new Color(35, 35, 35));
                    g2d.fillRect(monster.getScreenX()-1, monster.getScreenY()-16, gameP.tileSize+2, 12);

                    g2d.setColor(new Color(255, 0, 30));
                    g2d.fillRect(monster.getScreenX(), monster.getScreenY()-15, (int)hpBarValue, 10);

                    monster.hpBarCounter++;

                    if (monster.hpBarCounter >= gameP.FPS*10){
                        monster.hpBarOn = false;
                        monster.hpBarCounter = 0;
                    }
                } else if (monster.boss){
                    double oneScale = (double)gameP.tileSize*8/monster.maxLife;
                    double hpBarValue = oneScale * monster.life;
                    int x = gameP.screenWidth/2 - gameP.tileSize*4;
                    int y = gameP.tileSize*10;

                    g2d.setColor(new Color(35, 35, 35));
                    g2d.fillRect(x-1, y-1, gameP.tileSize*8+2, 22);

                    g2d.setColor(new Color(255, 0, 30));
                    g2d.fillRect(x, y, (int)hpBarValue, 22);

                    g2d.setFont(Oswald.deriveFont(Font.BOLD, 24f));
                    g2d.setColor(Color.white);
                    g2d.drawString(monster.name, x+4, y-10);
                }
            }
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
            gameP.changeArea();
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

        if (npc.dialogues[npc.dialogueSet][npc.dialogueIndex] != null){
//            currentDialogue = npc.dialogues[npc.dialogueSet][npc.dialogueIndex];

            char[] characters = npc.dialogues[npc.dialogueSet][npc.dialogueIndex].toCharArray();
            if (charIndex < characters.length){
                gameP.playSE(15);
                String str = String.valueOf(characters[charIndex]);
                combinedText += str;
                currentDialogue = combinedText;
                charIndex++;
            }

            if (gameP.keyH.enterPressed){
                charIndex = 0;
                combinedText = "";

                if (gameP.gameState == gameP.dialogueState || gameP.gameState == gameP.cutsceneState){
                    npc.dialogueIndex++;
                    gameP.keyH.enterPressed = false;
                }
            }

        } else {
            npc.dialogueIndex = 0;
            if (gameP.gameState == gameP.dialogueState) gameP.gameState = gameP.playState;
            if (gameP.gameState == gameP.cutsceneState) gameP.csM.scenePhase++;
        }

        for (String line : currentDialogue.split("\n")){
            g2d.drawString(line, x, y);
            y += gameP.tileSize;
        }
    }

    public void drawBattleDialogueScreen(){
        /* Dialog */
        g2d.setFont(Oswald.deriveFont(40f));
        g2d.setColor(Color.white);
        int x = 0;
        int y = 9 * gameP.tileSize;
        int width = 20 * gameP.tileSize;
        int height = 3 * gameP.tileSize;
        drawSubWindow(x, y, width, height);

        x += (int)(0.5 * gameP.tileSize);
        y += (int)(1.5 * gameP.tileSize);

        if (battleDialogues[battleDialogueSet][battleDialogueIndex] != null){

            char[] characters = battleDialogues[battleDialogueSet][battleDialogueIndex].toCharArray();
            if (charIndex < characters.length){
                gameP.playSE(15);
                String str = String.valueOf(characters[charIndex]);
                combinedText += str;
                currentDialogue = combinedText;
                charIndex++;
            }

            System.out.println("Combined Text: " + combinedText);

            if (gameP.keyH.enterPressed){
                System.out.println("tes");
                charIndex = 0;
                combinedText = "";
                npc.dialogueIndex++;
                gameP.keyH.enterPressed = false;
            }

        } else {
            battleDialogueIndex = 0;
        }

        for (String line : currentDialogue.split("\n")){
            g2d.drawString(line, x, y);
            y += gameP.tileSize;
        }
    }

    public void drawBattleDialogueScreen(int targetSubState){
        subState = 4;
        this.targetSubState = targetSubState;

        g2d.setFont(Oswald.deriveFont(40f));
        g2d.setColor(Color.white);
        int x = 0;
        int y = 9 * gameP.tileSize;
        int width = 20 * gameP.tileSize;
        int height = 3 * gameP.tileSize;
        drawSubWindow(x, y, width, height);

        x += (int)(0.5 * gameP.tileSize);
        y += (int)(1.5 * gameP.tileSize);

        char[] characters = battleDialogueText.toCharArray();
        if (charIndex < characters.length){
            gameP.playSE(15);
            String str = String.valueOf(characters[charIndex]);
            combinedText += str;
            currentDialogue = combinedText;
            charIndex++;
        }

        for (String line : currentDialogue.split("\n")){
            g2d.drawString(line, x, y);
            y += gameP.tileSize;
        }
    }

    public void drawBattleDialogueScreenWithoutAnimation(){
        g2d.setFont(Oswald.deriveFont(40f));
        g2d.setColor(Color.white);
        int x = 0;
        int y = 9 * gameP.tileSize;
        int width = 20 * gameP.tileSize;
        int height = 3 * gameP.tileSize;
        drawSubWindow(x, y, width, height);

        x += (int)(0.5 * gameP.tileSize);
        y += (int)(1 * gameP.tileSize);

        for (String line : battleDialogueText.split("\n")){
            g2d.drawString(line, x, y);
            y += (int)(1 * gameP.tileSize);
        }
    }

    public void drawTextAnimation(String text){
        g2d.setFont(Oswald.deriveFont(40f));
        g2d.setColor(Color.white);
        int x = 0;
        int y = 9 * gameP.tileSize;
        int width = 20 * gameP.tileSize;
        int height = 3 * gameP.tileSize;
        drawSubWindow(x, y, width, height);

        x += (int)(0.5 * gameP.tileSize);
        y += (int)(1.5 * gameP.tileSize);

        char[] characters = text.toCharArray();
        if (charIndex < characters.length){
            gameP.playSE(15);
            String str = String.valueOf(characters[charIndex]);
            combinedText += str;
            currentDialogue = combinedText;
            charIndex++;
        }

        if (gameP.keyH.enterPressed){
            System.out.println("tes");
            charIndex = 0;
            combinedText = "";
            npc.dialogueIndex++;
            gameP.keyH.enterPressed = false;
        }

        for (String line : currentDialogue.split("\n")){
            g2d.drawString(line, x, y);
            y += gameP.tileSize;
        }
    }

    public void drawTitleScreen(){
        // Background
        g2d.setColor(Color.GRAY);
        g2d.fillRect(0, 0, gameP.screenWidth, gameP.screenHeight);

        switch (subState) {
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


        if (gameP.saveLoad.haveData()) {
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

        } else {
            text = "NEW GAME";
            x = getXForCenteredText(text);
            y += gameP.tileSize*4;
            g2d.drawString(text, x, y);
            if (commandNum ==  0) g2d.drawString(">", x - gameP.tileSize, y);

            g2d.setColor(Color.black);
            text = "CONTINUE";
            x = getXForCenteredText(text);
            y += gameP.tileSize*1 ;
            g2d.drawString(text, x, y);

            g2d.setColor(new Color(255, 203, 5));
            text = "EXIT";
            x = getXForCenteredText(text);
            y += gameP.tileSize*1;
            g2d.drawString(text, x, y);
            if (commandNum ==  1) g2d.drawString(">", x - gameP.tileSize, y);        }

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
        final int windowY = (int)(gameP.tileSize*0.5);
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
            windowY = (int)(gameP.tileSize*0.5);
            windowWidth = gameP.tileSize * (inventoryMaxCol + 1) + (gameP.tileSize/4);
            windowHeight = gameP.tileSize * (inventoryMaxRow + 1);
            slotCol = playerSlotCol;
            slotRow = playerSlotRow;
        } else {
            windowX = gameP.tileSize * (gameP.maxScreenCol/10);
            windowY = (int)(gameP.tileSize*0.5);
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

        // Hint
        int hWindowX = 12 * gameP.tileSize;
        int hWindowY = (int)(8.5 * gameP.tileSize);
        int hWindowWidth = gameP.tileSize * (inventoryMaxCol + 1) + (gameP.tileSize/4);
        int hWindowheight = gameP.tileSize * 2;
        drawSubWindow(hWindowX, hWindowY, hWindowWidth, hWindowheight);
        g2d.drawString("[ESC] Back", hWindowX + gameP.tileSize/2, hWindowY + gameP.tileSize + (gameP.tileSize/4));

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

        switch (subState){
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

        gameP.keyH.escPressed = false;
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
        switch (subState) {
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
        npc.dialogueSet = 0;
        drawDialogueScreen();

        int x = gameP.tileSize * 13;
        int y = gameP.tileSize * 4;
        int width = (int)(gameP.tileSize * 3.5);
        int height = (int)(gameP.tileSize * 3.5);
        drawSubWindow(x, y, width, height);

        x += gameP.tileSize;
        y += gameP.tileSize;
        g2d.drawString("Buy", x, y);
        if (commandNum == 0) {
            g2d.drawString(">", x-25, y);
            if (gameP.keyH.enterPressed) subState = 1;
        }

        y += gameP.tileSize;
        g2d.drawString("Sell", x, y);
        if (commandNum == 1) {
            g2d.drawString(">", x-25, y);
            if (gameP.keyH.enterPressed) subState = 2;
        }
        y += gameP.tileSize;
        g2d.drawString("Leave", x, y);
        if (commandNum == 2) {
            g2d.drawString(">", x-25, y);
            if (gameP.keyH.enterPressed) {
                commandNum = 0;
                npc.startDialogue(npc, 1);
            }
        }
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
                    subState = 0;
                    npc.startDialogue(npc, 2);
                } else {
                    if (gameP.player.canObtainItem(npc.inventory.get(itemIndex))) {
                        gameP.player.coin -= npc.inventory.get(itemIndex).price;
                    } else {
                        subState = 0;
                        npc.startDialogue(npc, 3);
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
                if (gameP.player.inventory.get(itemIndex) == gameP.player.currentWeapon || gameP.player.inventory.get(itemIndex) == gameP.player.currentShield || gameP.player.inventory.get(itemIndex) == gameP.player.currentLight){
                    subState = 0;
                    commandNum = 0;
                    npc.startDialogue(npc, 4);
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
                gameP.envM.lighting.timeState = gameP.envM.lighting.dayState;
                gameP.envM.lighting.dayCounter = 0;
                gameP.gameState = gameP.playState;
                gameP.player.getImage();
            }
        }
    }

    public void drawBattleScreen(){
        BufferedImage battleBG = uTool.setUpJFIF("/bg/battle2", gameP.screenWidth, gameP.screenHeight);
        g2d.drawImage(battleBG, 0, 0, null);
        g2d.setColor(new Color(94, 237, 100));

//        g2d.fillRect(0, 0, gameP.screenWidth, gameP.screenHeight);
//        monster = new MON_GreenSlime(gameP);

        if (turn == playerTurn) {
            switch (subState) {
                case 0:
                    battle_select();
                    break;
                case 1:
                    battle_fight();
                    break;
                case 2:
                    battle_pokenom();
                    break;
                case 3:
                    battle_bag();
                    break;
                case 4:
                    battle_dialogue();
                    break;
                case 5:
                    battle_playerWin();
                    break;
                case 6:
                    drawBattleUI();
                    drawGuardQTE();
            }
        } else if (turn == enemyTurn) {

            switch (subState){
                case 0:
                    battle_enemy();
                    break;
                case 1:
                    battle_enemyWin();
                    break;
                case 2:
                    battle_enemyDialogue();
                    break;
            }
        }

        gameP.keyH.enterPressed = false;
    }

    public void drawBattleUI(){
        int x = 0, y = 0, width = 0, height = 0;
        int windowX, windowY, windowWidth, windowHeight;
        int tailX;
        int hpBarWidth, hpBarHeight, hpBarValueWidth, length;
        String text;

        // Monster Image
        x = 13 * gameP.tileSize;
        y = 1 * gameP.tileSize;
        g2d.drawImage(enemy.down1, x, y, 5 * gameP.tileSize, 5 * gameP.tileSize, null);

        // Player Image
        x = 3 * gameP.tileSize;
        y = 5 * gameP.tileSize;
        g2d.drawImage(gameP.player.up1, x, y, 5 * gameP.tileSize, 5 * gameP.tileSize,null);

        g2d.setColor(Color.white);
        g2d.setFont(Oswald.deriveFont(50f));

        /* Display Monster */
        windowX = 0;
        windowY = gameP.tileSize/4;
        windowWidth = 8 * gameP.tileSize;
        windowHeight = (int)(2.5 * gameP.tileSize);
        drawSubWindow(windowX, windowY, windowWidth, windowHeight);

        // Text Monster Name
        text = enemy.name;
        x = windowX + gameP.tileSize/4;
        y = windowY + gameP.tileSize + gameP.tileSize/6;
        g2d.drawString(text, x, y);

        // Text Monster Level
        text = "Lv. " + String.valueOf(enemy.level);
        tailX = (windowX + windowWidth) - (gameP.tileSize);
        x = getXForAlignToRightText(text, tailX);
        g2d.drawString(text, x, y);

        // Hp Bar Plain
        g2d.setFont(Cinzel.deriveFont(35f));
        text = "HP";
        x = getXForCenteredTextByWindow(text, windowWidth) - gameP.tileSize;
        y += gameP.tileSize - gameP.tileSize/5;
        g2d.drawString(text, x, y);

        x += 4;
        y -= gameP.tileSize/2;
        hpBarWidth = gameP.tileSize * 4 - gameP.tileSize/3;
        hpBarHeight = gameP.tileSize/2;
        length = (int)g2d.getFontMetrics().getStringBounds(text, g2d).getWidth();

        g2d.setColor(Color.lightGray);
        g2d.drawRoundRect(x+length, y, hpBarWidth, hpBarHeight, 10, 10);

//        monster.life = 1;
        g2d.setColor(Color.green);
        if (enemy.maxLife <= 0) {
            g2d.fillRoundRect(x + length, y, 0, hpBarHeight, 10, 10);
        } else {
            double lifeRatio = (double) enemy.life / enemy.maxLife;
            lifeRatio = Math.max(0.0, Math.min(lifeRatio, 1.0));

            int currentHpBarWidth = (int) (hpBarWidth * lifeRatio);
            g2d.fillRoundRect(x + length, y, currentHpBarWidth, hpBarHeight, 10, 10);
        }

        g2d.setColor(Color.white);
        g2d.setFont(Oswald.deriveFont(50f));

        /* Display Player */
        windowX = 11 * gameP.tileSize;
        windowY = 5 * gameP.tileSize;
        windowWidth = 9 * gameP.tileSize;
        windowHeight = (int)(3.5 * gameP.tileSize);
        drawSubWindow(windowX, windowY, windowWidth, windowHeight);

        // Text Player Name
        text = gameP.player.name;
        x = windowX + gameP.tileSize/2;
        y = windowY + gameP.tileSize + gameP.tileSize/6;
        g2d.drawString(text, x, y);

        // Text Player Level
        text = "Lv. " + String.valueOf(gameP.player.level);
        tailX = (windowX + windowWidth) - (gameP.tileSize);
        x = getXForAlignToRightText(text, tailX);
        g2d.drawString(text, x, y);

        // Hp Bar Plain
        g2d.setFont(Cinzel.deriveFont(35f));
        text = "HP";
        x = windowX + gameP.tileSize/4 + getXForCenteredTextByWindow(text, windowWidth) - gameP.tileSize/2;
        y += gameP.tileSize - gameP.tileSize/5;
        g2d.drawString(text, x, y);

        x += gameP.tileSize/12;
        y -= gameP.tileSize/2;
        hpBarWidth = gameP.tileSize * 4 - gameP.tileSize/3;
        hpBarHeight = gameP.tileSize/2;
        length = (int)g2d.getFontMetrics().getStringBounds(text, g2d).getWidth();

        g2d.setColor(Color.lightGray);
        g2d.drawRoundRect(x+length, y, hpBarWidth, hpBarHeight, 10, 10);

        g2d.setColor(Color.green);
        if (gameP.player.maxLife <= 0) {
            g2d.fillRoundRect(x + length, y, 0, hpBarHeight, 10, 10);
        } else {
            double lifeRatio = (double) gameP.player.life / gameP.player.maxLife;
            lifeRatio = Math.max(0.0, Math.min(lifeRatio, 1.0));

            int currentHpBarWidth = (int) (hpBarWidth * lifeRatio);
            g2d.fillRoundRect(x + length, y, currentHpBarWidth, hpBarHeight, 10, 10);
        }

        // Text Player Hp Value
        g2d.setFont(Cinzel.deriveFont(40f));
        g2d.setColor(Color.white);
        text = gameP.player.life + "/" + gameP.player.maxLife;
        length = (int)g2d.getFontMetrics().getStringBounds(text, g2d).getWidth();
        x = windowX + windowWidth/4 + (int)(gameP.tileSize*5) - length;
        y += gameP.tileSize + gameP.tileSize/2;
        g2d.drawString(text, x, y);
    }

    public void drawBattleMiniDialogue(String text){
        int x, y;

        /* Dialogue Text */
        x = (int)(0.5 * gameP.tileSize);
        y = (int)(10 * gameP.tileSize);
        g2d.drawString(text, x, y);
    }

    public void drawBattleMiniDialogue(String text1, String text2Left, String text2Right, String text3Left, String text3Right, int tailX){
        int x, y;

        x = (int)(0.5 * gameP.tileSize);
        y = (int)(9.8 * gameP.tileSize);
        g2d.drawString(text1, x, y);

        g2d.drawString(text2Left, x, y += (int)(0.85 * gameP.tileSize));
        x = getXForAlignToRightText(text2Right, tailX);
        g2d.drawString(text2Right, x, y);

        g2d.drawString(text3Left, (int)(0.5 * gameP.tileSize), y += (int)(0.85 * gameP.tileSize));
        x = getXForAlignToRightText(text3Right, tailX);
        g2d.drawString(text3Right, x, y);
    }

    public void drawGuardQTE() {
        // Gambar bar latar belakang (misal: abu-abu gelap)
        g2d.setColor(new Color(93, 173, 226));
        g2d.fillRect(qteBar_x, qteBar_y, qteBar_width, qteBar_height);

        // Gambar zona "Perfect Guard" (misal: kuning cerah)
        g2d.setColor(new Color(30, 144, 255));
        g2d.fillRect(qteZone_x, qteBar_y, qteZone_width, qteBar_height);

        // Gambar indikator yang bergerak (misal: putih)
        g2d.setColor(Color.WHITE);
        g2d.fillRect(qteIndicator_x, qteBar_y, 4, qteBar_height);

        // Gambar teks instruksi
        g2d.setFont(g2d.getFont().deriveFont(Font.BOLD, 32F));
        String text = "Press ENTER!";
        int textX = getXForCenteredText(text);
        int textY = qteBar_y - 20;
        g2d.drawString(text, textX, textY);
    }

    public void battle_select(){
        int x, y, width, height, tailX;
        String text;

        // BG, Monster & Player Display
        drawBattleUI();

        // Dialogue
        g2d.setFont(Oswald.deriveFont(50f));
        g2d.setColor(Color.white);
        x = 0;
        y = 9 * gameP.tileSize;
        width = 8 * gameP.tileSize;
        height = 3 * gameP.tileSize;
        drawSubWindow(x, y, width, height);

        /* Dialogue Text */
        x = (int)(0.5 * gameP.tileSize);
        y = (int)(10.5 * gameP.tileSize);
        g2d.drawString("What Will You Do ?", x, y);

        /* Opsi Serang */
        g2d.setFont(Cinzel.deriveFont(40f));

        // Fight
        if (commandNum == 0) {
            x = (8 * gameP.tileSize);
            y = (int)(9 * gameP.tileSize);
            width = 6 * gameP.tileSize;
            height = (int)(1.5 * gameP.tileSize);
            drawSubWindow2(x, y, width, height);

            g2d.setColor(Color.black);
            text = "FIGHT";
            x += getXForCenteredTextByWindow(text, width);
            y += (int) (1 * gameP.tileSize);
            g2d.drawString(text, x, y);
        } else {
            x = (8 * gameP.tileSize);
            y = (int)(9 * gameP.tileSize);
            width = 6 * gameP.tileSize;
            height = (int)(1.5 * gameP.tileSize);
            drawSubWindow(x, y, width, height);

            g2d.setColor(Color.white);
            text = "FIGHT";
            x += getXForCenteredTextByWindow(text, width);
            y += (int) (1 * gameP.tileSize);
            g2d.drawString(text, x, y);
        }

        // Pokenom
        if (commandNum == 1){
            x = (8 * gameP.tileSize);
            y = (int)(10.5 * gameP.tileSize);
            width = 6 * gameP.tileSize;
            height = (int)(1.5 * gameP.tileSize);
            drawSubWindow2(x, y, width, height);

            g2d.setColor(Color.black);
            text = "POKéNOM";
            x += getXForCenteredTextByWindow(text, width);
            y += 1 * gameP.tileSize;
            g2d.drawString(text, x, y);
        } else {
            x = (8 * gameP.tileSize);
            y = (int)(10.5 * gameP.tileSize);
            width = 6 * gameP.tileSize;
            height = (int)(1.5 * gameP.tileSize);
            drawSubWindow(x, y, width, height);

            text = "POKéNOM";
            x += getXForCenteredTextByWindow(text, width);
            y += 1 * gameP.tileSize;
            g2d.drawString(text, x, y);
        }

        // Bag
        if (commandNum == 2){
            x = (14 * gameP.tileSize);
            y = (int)(9 * gameP.tileSize);
            width = 6 * gameP.tileSize;
            height = (int)(1.5 * gameP.tileSize);
            drawSubWindow2(x, y, width, height);

            g2d.setColor(Color.BLACK);
            text = "BAG";
            x += getXForCenteredTextByWindow(text, width);
            y += 1 * gameP.tileSize;
            g2d.drawString(text, x, y);

        } else {
            x = (14 * gameP.tileSize);
            y = (int)(9 * gameP.tileSize);
            width = 6 * gameP.tileSize;
            height = (int)(1.5 * gameP.tileSize);
            drawSubWindow(x, y, width, height);

            text = "BAG";
            x += getXForCenteredTextByWindow(text, width);
            y += 1 * gameP.tileSize;
            g2d.drawString(text, x, y);

        }

        // Run
        if (commandNum == 3){
            x = (14 * gameP.tileSize);
            y = (int)(10.5 * gameP.tileSize);
            width = 6 * gameP.tileSize;
            height = (int)(1.5 * gameP.tileSize);
            drawSubWindow2(x, y, width, height);

            g2d.setColor(Color.BLACK);
            text = "RUN";
            x += getXForCenteredTextByWindow(text, width);
            y += 1 * gameP.tileSize;
            g2d.drawString(text, x, y);
        } else {
            x = (14 * gameP.tileSize);
            y = (int)(10.5 * gameP.tileSize);
            width = 6 * gameP.tileSize;
            height = (int)(1.5 * gameP.tileSize);
            drawSubWindow(x, y, width, height);

            text = "RUN";
            x += getXForCenteredTextByWindow(text, width);
            y += 1 * gameP.tileSize;
            g2d.drawString(text, x, y);
        }
    }

    public void battle_fight(){
        int x, y, width, height, tailX;
        String text;

        drawBattleUI();

        /* Dialog */
        g2d.setFont(Cinzel.deriveFont(30f));
        x = 0;
        y = 9 * gameP.tileSize;
        width = 8 * gameP.tileSize;
        height = 3 * gameP.tileSize;
        drawSubWindow(x, y, width, height);

        tailX = (x + width) - (gameP.tileSize);
        if (commandNum == 0) drawBattleMiniDialogue("TYPE/" + getElementTypeNameByID(gameP.player.currentWeapon.elementType));
        else if (commandNum == 1) drawBattleMiniDialogue("TYPE/" + getElementTypeNameByID(gameP.player.projectile.elementType), "Your Mana: ", String.valueOf(gameP.player.mana), "Cost Mana: ", String.valueOf(gameP.player.projectile.useCost), tailX);
        else if (commandNum == 2) drawBattleMiniDialogue("TYPE/" + getElementTypeNameByID(gameP.player.currentShield.elementType));
        else if (commandNum == 3) drawBattleMiniDialogue("TYPE/TYPE");

        /* Opsi Serang */
        g2d.setFont(Cinzel.deriveFont(40f));

        // Hit
        if (commandNum == 0) {
            x = (8 * gameP.tileSize);
            y = (int)(9 * gameP.tileSize);
            width = 6 * gameP.tileSize;
            height = (int)(1.5 * gameP.tileSize);
            drawSubWindow2(x, y, width, height);

            g2d.setColor(Color.black);
            text = "HIT";
            x += getXForCenteredTextByWindow(text, width);
            y += (int) (1 * gameP.tileSize);
            g2d.drawString(text, x, y);
        } else {
            x = (8 * gameP.tileSize);
            y = (int)(9 * gameP.tileSize);
            width = 6 * gameP.tileSize;
            height = (int)(1.5 * gameP.tileSize);
            drawSubWindow(x, y, width, height);

            g2d.setColor(Color.white);
            text = "HIT";
            x += getXForCenteredTextByWindow(text, width);
            y += (int) (1 * gameP.tileSize);
            g2d.drawString(text, x, y);
        }

        // Fireball
        if (commandNum == 1){
            x = (8 * gameP.tileSize);
            y = (int)(10.5 * gameP.tileSize);
            width = 6 * gameP.tileSize;
            height = (int)(1.5 * gameP.tileSize);
            drawSubWindow2(x, y, width, height);

            g2d.setColor(Color.black);
            text = gameP.player.projectile.name;
            x += getXForCenteredTextByWindow(text, width);
            y += 1 * gameP.tileSize;
            g2d.drawString(text, x, y);
        } else {
            x = (8 * gameP.tileSize);
            y = (int)(10.5 * gameP.tileSize);
            width = 6 * gameP.tileSize;
            height = (int)(1.5 * gameP.tileSize);
            drawSubWindow(x, y, width, height);

            text = "FIREBALL";
            x += getXForCenteredTextByWindow(text, width);
            y += 1 * gameP.tileSize;
            g2d.drawString(text, x, y);
        }

        // Guard
        if (commandNum == 2){
            x = (14 * gameP.tileSize);
            y = (int)(9 * gameP.tileSize);
            width = 6 * gameP.tileSize;
            height = (int)(1.5 * gameP.tileSize);
            drawSubWindow2(x, y, width, height);

            g2d.setColor(Color.BLACK);
            text = "GUARD";
            x += getXForCenteredTextByWindow(text, width);
            y += 1 * gameP.tileSize;
            g2d.drawString(text, x, y);

        } else {
            x = (14 * gameP.tileSize);
            y = (int)(9 * gameP.tileSize);
            width = 6 * gameP.tileSize;
            height = (int)(1.5 * gameP.tileSize);
            drawSubWindow(x, y, width, height);

            text = "GUARD";
            x += getXForCenteredTextByWindow(text, width);
            y += 1 * gameP.tileSize;
            g2d.drawString(text, x, y);

        }

        // Tambahan
        if (commandNum == 3){
            x = (14 * gameP.tileSize);
            y = (int)(10.5 * gameP.tileSize);
            width = 6 * gameP.tileSize;
            height = (int)(1.5 * gameP.tileSize);
            drawSubWindow2(x, y, width, height);

            g2d.setColor(Color.BLACK);
            text = "CS";
            x += getXForCenteredTextByWindow(text, width);
            y += 1 * gameP.tileSize;
            g2d.drawString(text, x, y);
        } else {
            x = (14 * gameP.tileSize);
            y = (int)(10.5 * gameP.tileSize);
            width = 6 * gameP.tileSize;
            height = (int)(1.5 * gameP.tileSize);
            drawSubWindow(x, y, width, height);

            text = "CS";
            x += getXForCenteredTextByWindow(text, width);
            y += 1 * gameP.tileSize;
            g2d.drawString(text, x, y);
        }
    }

    public void battle_pokenom(){
        System.out.println("Ini adalah pokenom substate");

    }

    public void battle_bag(){
//        System.out.println("Ini adalah bag substate");
        drawCharacterScreen();
        drawInventory(gameP.player, true);
    }

    public void battle_dialogue(){
        drawBattleUI();
        drawBattleDialogueScreenWithoutAnimation();
    }

    public void battle_playerWin(){
        drawBattleUI();
        drawBattleDialogueScreenWithoutAnimation();

    }

    public void battle_enemy(){
        drawBattleUI();
    }

    public void battle_enemyWin(){
        drawBattleUI();
        drawBattleDialogueScreenWithoutAnimation();
    }

    public void battle_enemyDialogue(){
        drawBattleUI();
        drawBattleDialogueScreenWithoutAnimation();
    }

    public void draw(Graphics2D g2d){
        this.g2d = g2d;

        if (gameP.gameState == gameP.playState){
            drawPlayerLife();
            drawMonsterLife();
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

        if (gameP.gameState == gameP.battleState) drawBattleScreen();
    }
}
