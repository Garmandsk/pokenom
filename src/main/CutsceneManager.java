package main;

import entity.PlayerDummy;
import entity.monster.MON_SkeletonLord;
import object.pickupOnly.PU_OBJ_BlueHeart;
import object.obstacle.OBS_OBJ_DoorIron;

import java.awt.*;

public class CutsceneManager {
    GamePanel gameP;
    Graphics2D g2d;
    public int sceneNum, scenePhase;
    int counter;
    float alpha;
    int y;
    String endCredit;

    // Scene Number
    public final int NA = 0;
    public final int skeletonLord = 1;
    public final int ending = 2;

    public CutsceneManager(GamePanel gameP){
        this.gameP = gameP;

        endCredit = "Program/Music/Art \n" +
                "RyiSnow" +
                "\n\n\n" +
                "Thanks To \n" +
                "Someone \n" +
                "Someone \n" +
                "Someone \n\n" +
                "Thank You For Playing!";
    }

    public void backToNormal(){

        // Start drawing player
        gameP.player.drawing = true;

        // Reset
        sceneNum = NA;
        scenePhase = 0;
        gameP.gameState = gameP.playState;
//        gameP.resetGame(false, false);
    }

    public boolean counterReached(int target){
        boolean counterReached = false;

        counter++;
//        System.out.println("counter cs: " + counter);
        if (counter >= target) {
            counterReached = true;
            counter = 0;
        }

        return counterReached;
    }

    public void drawBlackBackground(float alpha) {
        // Simpan Composite yang sedang aktif sebelum diubah
        Composite oldComposite = g2d.getComposite();

        // Terapkan alpha untuk background hitam
        g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, alpha));
        g2d.setColor(Color.black);
        g2d.fillRect(0, 0, gameP.screenWidth, gameP.screenHeight);

        // Kembalikan Composite ke keadaan semula
        g2d.setComposite(oldComposite);
    }

    public void drawString(float alpha, float fontSize, int y, String text, int lineHeight){
        g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, alpha));
        g2d.setColor(Color.white);
        g2d.setFont(gameP.ui.Oswald.deriveFont(fontSize));

        for (String line : text.split("\n")){
            int x = gameP.ui.getXForCenteredText(line);
            g2d.drawString(line, x, y);
            y += lineHeight;
        }

        g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1f));
    }

    public void drawTransition(){
        g2d.setColor(new Color(0, 0, 0, counter*3));
        g2d.fillRect(0, 0, gameP.screenWidth, gameP.screenHeight);
        if (counterReached(85)) scenePhase++;
    }

    public void changePositionandBackToNormal(int targetMap, int targetCol, int targetRow, String targetDirection, int targetArea){
        // Change Position
        gameP.currentMap = targetMap;
        gameP.player.worldX = targetCol * gameP.tileSize;
        gameP.player.worldY = targetRow * gameP.tileSize;
        gameP.player.direction = targetDirection;
        gameP.nextArea = targetArea;
        gameP.changeArea();

        // Reset
        sceneNum = NA;
        scenePhase = 0;
        gameP.gameState = gameP.playState;

        // Start drawing player
        gameP.player.drawing = true;
    }

    public void csSkeletonLord(){
        if (scenePhase == 0){
            gameP.bossBattleOn = true;

            // Shut the iron door
            for (int i = 0; i < gameP.obj[1].length; i++){
                if (gameP.obj[gameP.currentMap][i] == null){
                    gameP.obj[gameP.currentMap][i] = new OBS_OBJ_DoorIron(gameP);
                    gameP.obj[gameP.currentMap][i].worldX = gameP.tileSize * 25;
                    gameP.obj[gameP.currentMap][i].worldY = gameP.tileSize * 28;
                    gameP.obj[gameP.currentMap][i].temp = true;
                    gameP.playSE(19);
                }
            }

            // Search a vacant slot for the dummy
            for (int i = 0; i < gameP.npc[1].length; i++){
                if (gameP.npc[gameP.currentMap][i] == null){
                    gameP.npc[gameP.currentMap][i] = new PlayerDummy(gameP);
                    gameP.npc[gameP.currentMap][i].worldX = gameP.player.worldX;
                    gameP.npc[gameP.currentMap][i].worldY = gameP.player.worldY;
                    gameP.npc[gameP.currentMap][i].direction = gameP.player.direction;
                    break;
                }
            }

            gameP.player.drawing = false;
            scenePhase++;
        } else if (scenePhase == 1) {
            gameP.player.worldY -= 2;
            if (gameP.player.worldY < gameP.tileSize*16) scenePhase++;
        } else if (scenePhase == 2){

            // Search the boss
            for (int i = 0; i < gameP.monster[1].length; i++){
                if (gameP.monster[gameP.currentMap][i] != null && gameP.monster[gameP.currentMap][i].name.equals(MON_SkeletonLord.monName)){
                    gameP.monster[gameP.currentMap][i].sleep = false;
                    gameP.ui.npc = gameP.monster[gameP.currentMap][i];
                    scenePhase++;
                    break;
                }
            }
        } else if (scenePhase == 3) {
            // The boss speaks
            gameP.ui.drawDialogueScreen();
//            scenePhase++; Sudah diatur di game.ui.drawDialogueScreen()
        } else if (scenePhase == 4) {
            // Return to the player and Search the dummy
            for (int i = 0; i < gameP.npc[1].length; i++){
                if (gameP.npc[gameP.currentMap][i] != null && gameP.npc[gameP.currentMap][i].name.equals(PlayerDummy.npcName)){
                    gameP.player.worldX = gameP.npc[gameP.currentMap][i].worldX;
                    gameP.player.worldY = gameP.npc[gameP.currentMap][i].worldY;
                    gameP.player.direction = "up";

                    gameP.npc[gameP.currentMap][i] = null;
                    break;
                }
            }

            // Ganti Musik saat battle dimulai
            gameP.changeMusic(20);
            backToNormal();
        }
    }

    public void csEnding(){
        if (scenePhase == 0){
            gameP.stopMusic();
            gameP.ui.npc = new PU_OBJ_BlueHeart(gameP);
            scenePhase++;
        } else if (scenePhase == 1) {
            gameP.ui.drawDialogueScreen();
        } else if (scenePhase == 2) {
            gameP.playSE(3);
            scenePhase++;
        } else if (scenePhase == 3) {
            if (counterReached(300)) scenePhase++;
        } else if (scenePhase == 4) {
            alpha += 0.005f;
            if (alpha > 1f) alpha = 1f;
            drawBlackBackground(alpha);

            if (alpha == 1f){
                alpha = 0f;
                scenePhase++;
            }
        } else if (scenePhase == 5) {
            drawBlackBackground(1f);
            alpha += 0.005f;
            if (alpha > 1f) alpha = 1f;

            String text = "After the fierce battle with the Skeleton Lord, \n" +
                    "the Blue Boy finally found the legendary treasure. \n" +
                    "But this is not the end of his journey. \n" +
                    "The Blue Boy's adventure has just begun.";

            drawString(alpha, 38f, (gameP.tileSize*4)-(gameP.tileSize/4), text, (gameP.tileSize*2)-(gameP.tileSize/4));

            if (counterReached(600)) {
                gameP.playMusic(0);
                scenePhase++;
            }
        } else if (scenePhase == 6) {
            drawBlackBackground(1f);
            drawString(1f, 120f, gameP.screenHeight/2, "Pokénom", 0);

            if (counterReached(600)) scenePhase++;
        } else if (scenePhase == 7) {
            drawBlackBackground(1f);

            y = gameP.screenHeight/2;
            drawString(1f, 38f, y, endCredit, (gameP.tileSize*2)-(gameP.tileSize/4));
            if (counterReached(480)) scenePhase++;
        } else if (scenePhase == 8) {
            drawBlackBackground(1f);

            // Scrolling the credit
            y--;
            drawString(1f, 38f, y, endCredit, (gameP.tileSize*2)-(gameP.tileSize/4));

            if (counterReached(1200)) scenePhase++;
        } else if (scenePhase == 9) {
            drawTransition();
        } else if (scenePhase == 10) {
            gameP.envM.lighting.resetDay();
            changePositionandBackToNormal(0, 39, 39, "right", gameP.outside);
        }
    }

    public void draw(Graphics2D g2d){
        this.g2d = g2d;

        if (sceneNum == skeletonLord) csSkeletonLord();
        if (sceneNum == ending) csEnding();
    }
}
