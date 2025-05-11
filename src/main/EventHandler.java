package main;

import java.awt.*;

public class EventHandler {
    GamePanel gameP;

    EventRect[][] eventRect;
    int previousEventX, previousEventY;
    boolean canTouchEvent;

    EventHandler(GamePanel gameP){
        this.gameP = gameP;

        eventRect = new EventRect[gameP.maxWorldCol][gameP.maxWorldRow];

        int col = 0, row = 0;
        while (col < gameP.maxWorldCol && row < gameP.maxWorldRow){
            eventRect[col][row] = new EventRect();
            eventRect[col][row].y = 23;
            eventRect[col][row].width = 2;
            eventRect[col][row].height = 2;
            eventRect[col][row].defaultEventRectX = eventRect[col][row].x;
            eventRect[col][row].defaultEventrectY = eventRect[col][row].y;
            col++;
            if (col == gameP.maxWorldCol) {
                col = 0;
                row++;
            }
        }
    }

    public void checkEvent(){
        int xDistance = Math.abs(gameP.player.worldX - previousEventX);
        int yDistane = Math.abs(gameP.player.worldY - previousEventY);
        int distance = Math.max(xDistance, yDistane);

        if (distance > 1 * gameP.tileSize) canTouchEvent = true;
        if (canTouchEvent){
            if (hit(25, 20, "right")) damagePit(gameP.dialogueState);

            if (hit(21, 7, "down")) healingPool(gameP.dialogueState);
            if (hit(22, 7, "down")) healingPool(gameP.dialogueState);
            if (hit(23, 7, "down")) healingPool(gameP.dialogueState);
            if (hit(24, 7, "down")) healingPool(gameP.dialogueState);
            if (hit(25, 7, "down")) healingPool(gameP.dialogueState);
            if (hit(26, 7, "down")) healingPool(gameP.dialogueState);

            if (hit(21, 12, "up")) healingPool(gameP.dialogueState);
            if (hit(22, 12, "up")) healingPool(gameP.dialogueState);
            if (hit(23, 12, "up")) healingPool(gameP.dialogueState);
            if (hit(24, 12, "up")) healingPool(gameP.dialogueState);
            if (hit(25, 12, "up")) healingPool(gameP.dialogueState);
            if (hit(26, 12, "up")) healingPool(gameP.dialogueState);

//            if (hit(20, 7, "right")) healingPool(gameP.dialogueState);
//            if (hit(20, 8, "right")) healingPool(gameP.dialogueState);
//            if (hit(20, 9, "right")) healingPool(gameP.dialogueState);
//            if (hit(20, 10, "right")) healingPool(gameP.dialogueState);

            if (hit(21, 7, "right")) healingPool(gameP.dialogueState);
            if (hit(21, 8, "right")) healingPool(gameP.dialogueState);
            if (hit(21, 9, "right")) healingPool(gameP.dialogueState);
            if (hit(21, 10, "right")) healingPool(gameP.dialogueState);

//            if (hit(20, 7, "right")) healingPool(gameP.dialogueState);
//            if (hit(20, 8, "right")) healingPool(gameP.dialogueState);
//            if (hit(20, 9, "right")) healingPool(gameP.dialogueState);
//            if (hit(20, 10, "right")) healingPool(gameP.dialogueState);

            if (hit(21, 20, "left")) teleport(gameP.dialogueState);
        }
    }

    public boolean hit (int eventCol, int eventRow, String reqDirection){
        boolean hit = false;

        gameP.player.solidArea.x += gameP.player.worldX;
        gameP.player.solidArea.y += gameP.player.worldY;
        eventRect[eventCol][eventRow].x += eventCol * gameP.tileSize;
        eventRect[eventCol][eventRow].y += eventRow * gameP.tileSize;

        if (gameP.player.solidArea.intersects(eventRect[eventCol][eventRow]) && eventRect[eventCol][eventRow].eventDone == false){
            if (gameP.player.direction.contentEquals(reqDirection) || reqDirection.contentEquals("any")) {
                hit = true;

                previousEventX = gameP.player.worldX;
                previousEventY = gameP.player.worldY;
            }
        }

        gameP.player.solidArea.x = gameP.player.defaultSolidAreaX;
        gameP.player.solidArea.y = gameP.player.defaultSolidAreaY;
        eventRect[eventCol][eventRow].x = eventRect[eventCol][eventRow].defaultEventRectX;
        eventRect[eventCol][eventRow].y = eventRect[eventCol][eventRow].defaultEventrectY;

        return hit;
    }

    public void damagePit(int gameState){
        gameP.gameState = gameState;
        gameP.ui.currentDialogue = "Jatuh Kedalam Jurang!";
        gameP.player.life -= 1;
//        eventRect[eventCol][eventRow].eventDone = true;
        canTouchEvent = false;
    }

    public void healingPool(int gameState){
        if (gameP.keyH.enterPressed){
            gameP.gameState = gameState;
            gameP.player.attackCanceled = true;
            gameP.ui.currentDialogue = "Kolam Ajaib!";
            gameP.player.life = gameP.player.maxLife;
            gameP.player.mana = gameP.player.maxMana;
            gameP.aSetter.setMonster();
        }
    }

    public void teleport(int gameState){
        gameP.gameState = gameState;
        gameP.ui.currentDialogue = "Teleport!";
        gameP.player.worldX = 10 * gameP.tileSize;
        gameP.player.worldY = 8 * gameP.tileSize;
    }
}
