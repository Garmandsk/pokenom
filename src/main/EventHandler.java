package main;

import data.Progress;
import entity.Entity;
import entity.npc.NPC_BigRock;

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

            else if (hit(0, 23, 12, "up")) healingPool(gameP.dialogueState);

            else if (hit(0, 21, 19, "any")) teleport(1, 12, 13, gameP.indoor); // to merchant's house
            else if (hit(0, 10, 39, "any")) teleport(1, 12, 13, gameP.indoor); // to merchant's house
            else if (hit(1, 12, 13, "any")) teleport(0, 10, 39, gameP.outside); // to outside
            else if (hit(1, 12, 9, "up")) speak(gameP.npc[1][0]);

            else if (hit(0, 12, 9, "any")) teleport(2, 9, 41, gameP.dungeon); // to dungeon
            else if (hit(2, 9, 41, "any")) teleport(0, 12, 9, gameP.outside); // to outside
            else if (hit(2, 8, 7, "any")) teleport(3, 26, 41, gameP.dungeon); // to B2
            else if (hit(3, 26, 41, "any")) teleport(2, 8, 7, gameP.dungeon); // to B1
            else if (hit(3, 25, 27, "any")) skeletonLord(); // Trigger cutscene skeleton lord

            else if (hit(2, 18, 24, "any")) resetBigRock(); // Reset big rock
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

    public void teleport(int map, int col, int row, int area){
        gameP.playSE(11);

        gameP.gameState = gameP.transitionState;
        gameP.nextArea = area;
        tempMap = map;
        tempCol = col;
        tempRow = row;
        canTouchEvent = false;
    }

    public void speak(Entity entity){
        if (gameP.keyH.enterPressed) {
            gameP.gameState = gameP.dialogueState;
//            entity.startDialogue(entity, 1);
            gameP.player.attackCanceled = true;
            entity.speak();
        }
    }

    public void skeletonLord(){
        if (gameP.bossBattleOn == false && Progress.skeletonLordDefeated == false){
            gameP.gameState = gameP.cutsceneState;
            gameP.csM.sceneNum = gameP.csM.skeletonLord;
        }
    }

    public void resetBigRock(){
        int br = 0;

        for (int i = 0; i < gameP.npc[1].length; i++){
            if (gameP.npc[gameP.currentMap][i] != null && gameP.npc[gameP.currentMap][i].name != null && gameP.npc[gameP.currentMap][i].name.equals(NPC_BigRock.npcName)){
                if (br == 0) {
                    gameP.npc[gameP.currentMap][i].worldX = 20 * gameP.tileSize;
                    gameP.npc[gameP.currentMap][i].worldY = 25 * gameP.tileSize;
                    br++;
                } else if (br == 1) {
                    gameP.npc[gameP.currentMap][i].worldX = 11 * gameP.tileSize;
                    gameP.npc[gameP.currentMap][i].worldY = 18 * gameP.tileSize;
                    br++;
                } else if (br == 2) {
                    gameP.npc[gameP.currentMap][i].worldX = 23 * gameP.tileSize;
                    gameP.npc[gameP.currentMap][i].worldY = 14 * gameP.tileSize;
                }
            }
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
                g2.setColor(Color.red);      // semi-transparan merah
                g2.drawRect(screenX, screenY, er.width, er.height);
            }
        }
    }

}
