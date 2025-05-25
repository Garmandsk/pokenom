package main;

import entity.Entity;

import java.awt.*;

public class EventHandler {
    GamePanel gameP;
    EventRect[][][] eventRect;
    Entity eventMaster;

    public int previousEventX;
    public int previousEventY;
    boolean canTouchEvent;
    public int tempMap, tempCol, tempRow;

    EventHandler(GamePanel gameP){
        this.gameP = gameP;
        eventMaster = new Entity(gameP);

        eventRect = new EventRect[gameP.maxMap][gameP.maxWorldCol][gameP.maxWorldRow];

        int map = 0, col = 0, row = 0;
        while (map < gameP.maxMap && col < gameP.maxWorldCol && row < gameP.maxWorldRow){
            eventRect[map][col][row] = new EventRect();
            eventRect[map][col][row].x = gameP.tileSize/2;
            eventRect[map][col][row].y = 23;
            eventRect[map][col][row].width = 2;
            eventRect[map][col][row].height = 2;
            eventRect[map][col][row].defaultEventRectX = eventRect[map][col][row].x;
            eventRect[map][col][row].defaultEventRectY = eventRect[map][col][row].y;
            col++;
            if (col == gameP.maxWorldCol) {
                col = 0;
                row++;

                if (row == gameP.maxWorldRow){
                    row = 0;
                    map++;
                }
            }
        }

        setDialogue();
    }

    public void setDialogue(){
        eventMaster.dialogues[0][0] = "Jatuh Kedalam Jurang!";
        eventMaster.dialogues[1][0] = "Kolam Ajaib! \n\nMenyimpan Permainan";

    }

    public void checkEvent(){
        int xDistance = Math.abs(gameP.player.worldX - previousEventX);
        int yDistane = Math.abs(gameP.player.worldY - previousEventY);
        int distance = Math.max(xDistance, yDistane);

        if (distance > 1 * gameP.tileSize) canTouchEvent = true;
        if (canTouchEvent){
            if (hit(0, 25, 20, "right")) damagePit(gameP.dialogueState);

//            if (hit(21, 7, "down")) healingPool(gameP.dialogueState);
//            if (hit(22, 7, "down")) healingPool(gameP.dialogueState);
//            if (hit(23, 7, "down")) healingPool(gameP.dialogueState);
//            if (hit(24, 7, "down")) healingPool(gameP.dialogueState);
//            if (hit(25, 7, "down")) healingPool(gameP.dialogueState);
//            if (hit(26, 7, "down")) healingPool(gameP.dialogueState);

//            if (hit(21, 12, "up")) healingPool(gameP.dialogueState);
//            if (hit(22, 12, "up")) healingPool(gameP.dialogueState);
            else if (hit(0, 23, 12, "up")) healingPool(gameP.dialogueState);
//            if (hit(24, 12, "up")) healingPool(gameP.dialogueState);
//            if (hit(25, 12, "up")) healingPool(gameP.dialogueState);
//            if (hit(26, 12, "up")) healingPool(gameP.dialogueState);

//            if (hit(20, 7, "right")) healingPool(gameP.dialogueState);
//            if (hit(20, 8, "right")) healingPool(gameP.dialogueState);
//            if (hit(20, 9, "right")) healingPool(gameP.dialogueState);
//            if (hit(20, 10, "right")) healingPool(gameP.dialogueState);

//            if (hit(21, 7, "right")) healingPool(gameP.dialogueState);
//            if (hit(21, 8, "right")) healingPool(gameP.dialogueState);
//            if (hit(21, 9, "right")) healingPool(gameP.dialogueState);
//            if (hit(21, 10, "right")) healingPool(gameP.dialogueState);

//            if (hit(20, 7, "right")) healingPool(gameP.dialogueState);
//            if (hit(20, 8, "right")) healingPool(gameP.dialogueState);
//            if (hit(20, 9, "right")) healingPool(gameP.dialogueState);
//            if (hit(20, 10, "right")) healingPool(gameP.dialogueState);

            else if (hit(0, 21, 20, "any")) teleport(1, 12, 13);

            else if (hit(0, 10, 39, "any")) teleport(1, 12, 13);
            else if (hit(1, 12, 13, "any")) teleport(0, 10, 39);
            else if (hit(1, 12, 9, "up")) speak(gameP.npc[1][0]);

        }
    }

    public boolean hit (int map, int eventCol, int eventRow, String reqDirection){
        boolean hit = false;

        if (map == gameP.currentMap){
            gameP.player.solidArea.x += gameP.player.worldX;
            gameP.player.solidArea.y += gameP.player.worldY;
            eventRect[map][eventCol][eventRow].x += (eventCol * gameP.tileSize);
            eventRect[map][eventCol][eventRow].y += (eventRow * gameP.tileSize);

            if (gameP.player.solidArea.intersects(eventRect[map][eventCol][eventRow]) && eventRect[map][eventCol][eventRow].eventDone == false){
                if (gameP.player.direction.contentEquals(reqDirection) || reqDirection.contentEquals("any")) {
                    hit = true;

                    previousEventX = gameP.player.worldX;
                    previousEventY = gameP.player.worldY;
                }
            }

            gameP.player.solidArea.x = gameP.player.defaultSolidAreaX;
            gameP.player.solidArea.y = gameP.player.defaultSolidAreaY;
            eventRect[map][eventCol][eventRow].x = eventRect[map][eventCol][eventRow].defaultEventRectX;
            eventRect[map][eventCol][eventRow].y = eventRect[map][eventCol][eventRow].defaultEventRectY;
        }

        return hit;
    }

    public void damagePit(int gameState){
        gameP.gameState = gameState;
        eventMaster.startDialogue(eventMaster, 0);
        gameP.player.life -= 1;
//        eventRect[eventCol][eventRow].eventDone = true;
        canTouchEvent = false;
    }

    public void healingPool(int gameState){
        if (gameP.keyH.enterPressed){
            gameP.gameState = gameState;
            gameP.player.attackCanceled = true;
            eventMaster.startDialogue(eventMaster, 1);
            gameP.player.life = gameP.player.maxLife;
            gameP.player.mana = gameP.player.maxMana;
            gameP.aSetter.setMonster();
            gameP.saveLoad.save();
        }
    }

    public void teleport(int map, int col, int row){
        gameP.playSE(11);

        gameP.gameState = gameP.transitionState;
        tempMap = map;
        tempCol = col;
        tempRow = row;
        canTouchEvent = false;
    }

    public void speak(Entity entity){
        if (gameP.keyH.enterPressed) {
            gameP.gameState = gameP.dialogueState;
            gameP.player.attackCanceled = true;
            entity.speak();
        }
    }

    // di kelas EventHandler
    public void render(Graphics2D g2) {
        int tileSize = gameP.tileSize;
        int camX = gameP.player.screenX - gameP.player.worldX;
        int camY = gameP.player.screenY - gameP.player.worldY;

        // hanya gambar untuk map yang aktif
        int map = gameP.currentMap;
        for (int col = 0; col < gameP.maxWorldCol; col++) {
            for (int row = 0; row < gameP.maxWorldRow; row++) {
                EventRect er = eventRect[map][col][row];

                // hitung posisi di layar
                int worldX = col * tileSize + er.x;
                int worldY = row * tileSize + er.y;
                int screenX = worldX + camX;
                int screenY = worldY + camY;

                // gambar rect
                g2.setColor(new Color(255, 0, 0, 150));      // semi-transparan merah
                g2.setStroke(new BasicStroke(2));
                g2.drawRect(screenX, screenY, er.width, er.height);
            }
        }
    }

}
