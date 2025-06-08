package main;

import object.pickupOnly.PU_OBJ_CoinBronze;
import object.pickupOnly.PU_OBJ_Heart;
import object.pickupOnly.PU_OBJ_ManaCrystal;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.Random;

public class KeyHandler implements KeyListener {
    public boolean upPressed, downPressed, leftPressed, rightPressed, enterPressed, shotKeyPressed, spacePressed, escPressed;
    public boolean debug, godMode;
    GamePanel gameP;

    public KeyHandler(GamePanel gameP){
        this.gameP = gameP;
    }

    public void playerInventory(int code){
        if (code == KeyEvent.VK_W || code == KeyEvent.VK_UP){
            if (gameP.ui.playerSlotRow != 0){
                gameP.ui.playerSlotRow--;
                gameP.playSE(7);
            } else if (!(gameP.ui.playerSlotCol == 0 && gameP.ui.playerSlotRow == 0)) {
                gameP.ui.playerSlotRow = gameP.ui.inventoryMaxRow - 1;
                gameP.ui.playerSlotCol--;
                gameP.playSE(7);
            }
        }

        if (code == KeyEvent.VK_S || code == KeyEvent.VK_DOWN){
            if (gameP.ui.playerSlotRow != gameP.ui.inventoryMaxRow - 1){
                gameP.ui.playerSlotRow++;
                gameP.playSE(7);
            } else if (!(gameP.ui.playerSlotCol == gameP.ui.inventoryMaxCol - 1 && gameP.ui.playerSlotRow == gameP.ui.inventoryMaxRow - 1)) {
                gameP.ui.playerSlotRow = 0;
                gameP.ui.playerSlotCol++;
                gameP.playSE(7);
            }
        }

        if (code == KeyEvent.VK_A || code == KeyEvent.VK_LEFT){
            if (gameP.ui.playerSlotCol != 0){
                gameP.ui.playerSlotCol--;
                gameP.playSE(7);
            } else if (!(gameP.ui.playerSlotCol == 0 && gameP.ui.playerSlotRow == 0)){
                gameP.ui.playerSlotCol = gameP.ui.inventoryMaxCol - 1;
                gameP.ui.playerSlotRow--;
                gameP.playSE(7);
            }
        }

        if (code == KeyEvent.VK_D || code == KeyEvent.VK_RIGHT){
            if (gameP.ui.playerSlotCol != gameP.ui.inventoryMaxCol - 1){
                gameP.ui.playerSlotCol++;
                gameP.playSE(7);
            } else if (!(gameP.ui.playerSlotCol == gameP.ui.inventoryMaxCol - 1 && gameP.ui.playerSlotRow == gameP.ui.inventoryMaxRow - 1)) {
                gameP.ui.playerSlotCol = 0;
                gameP.ui.playerSlotRow++;
                gameP.playSE(7);
            }
        }
    }

    public void npcInventory(int code){
        if (code == KeyEvent.VK_W || code == KeyEvent.VK_UP){
            if (gameP.ui.npcSlotRow != 0){
                gameP.ui.npcSlotRow--;
                gameP.playSE(7);
            } else if (!(gameP.ui.npcSlotCol == 0 && gameP.ui.npcSlotRow == 0)) {
                gameP.ui.npcSlotRow = gameP.ui.inventoryMaxRow - 1;
                gameP.ui.npcSlotCol--;
                gameP.playSE(7);
            }
        }

        if (code == KeyEvent.VK_S || code == KeyEvent.VK_DOWN){
            if (gameP.ui.npcSlotRow != gameP.ui.inventoryMaxRow - 1){
                gameP.ui.npcSlotRow++;
                gameP.playSE(7);
            } else if (!(gameP.ui.npcSlotCol == gameP.ui.inventoryMaxCol - 1 && gameP.ui.npcSlotRow == gameP.ui.inventoryMaxRow - 1)) {
                gameP.ui.npcSlotRow = 0;
                gameP.ui.npcSlotCol++;
                gameP.playSE(7);
            }
        }

        if (code == KeyEvent.VK_A || code == KeyEvent.VK_LEFT){
            if (gameP.ui.npcSlotCol != 0){
                gameP.ui.npcSlotCol--;
                gameP.playSE(7);
            } else if (!(gameP.ui.npcSlotCol == 0 && gameP.ui.npcSlotRow == 0)){
                gameP.ui.npcSlotCol = gameP.ui.inventoryMaxCol - 1;
                gameP.ui.npcSlotRow--;
                gameP.playSE(7);
            }
        }

        if (code == KeyEvent.VK_D || code == KeyEvent.VK_RIGHT){
            if (gameP.ui.npcSlotCol != gameP.ui.inventoryMaxCol - 1){
                gameP.ui.npcSlotCol++;
                gameP.playSE(7);
            } else if (!(gameP.ui.npcSlotCol == gameP.ui.inventoryMaxCol - 1 && gameP.ui.npcSlotRow == gameP.ui.inventoryMaxRow - 1)) {
                gameP.ui.npcSlotCol = 0;
                gameP.ui.npcSlotRow++;
                gameP.playSE(7);
            }
        }
    }

    public void initQTE(){
        gameP.ui.guardQTE_Active = true;

        // Inisialisasi posisi dan ukuran bar QTE (sesuaikan dengan layar Anda)
        gameP.ui.qteBar_width = gameP.screenWidth / 2;
        gameP.ui.qteBar_height = 24;
        gameP.ui.qteBar_x = gameP.screenWidth / 2 - gameP.ui.qteBar_width / 2;
        gameP.ui.qteBar_y = gameP.screenHeight / 2 + gameP.tileSize * 5;

        // Inisialisasi zona "Perfect" (buat ukurannya kecil)
        gameP.ui.qteZone_width = 30;
        // Randomize posisi zona perfect agar tidak monoton
        int randomZoneOffset = new java.util.Random().nextInt(gameP.ui.qteBar_width - gameP.ui.qteZone_width);
        gameP.ui.qteZone_x = gameP.ui.qteBar_x + randomZoneOffset;

        // Inisialisasi Indikator
        gameP.ui.qteIndicator_x = gameP.ui.qteBar_x;
        gameP.ui.qteIndicator_speed = 5; // Kecepatan, bisa diubah untuk tingkat kesulitan
        gameP.ui.qteIndicator_direction = 1; // Mulai bergerak ke kanan

        // Inisialisasi Timer (misal: 3 detik atau 180 frame)
        gameP.ui.qteTimer = 180;
    }

    public void titleState(int code){
        if (gameP.ui.subState == 0){
            if (gameP.saveLoad.haveData()){
                if (code == KeyEvent.VK_W || code == KeyEvent.VK_UP){
                    if (gameP.ui.commandNum == 0) gameP.ui.commandNum = 2;
                    else gameP.ui.commandNum--;
                }

                if (code == KeyEvent.VK_S || code == KeyEvent.VK_DOWN){
                    if (gameP.ui.commandNum == 2) gameP.ui.commandNum = 0;
                    else gameP.ui.commandNum++;
                }

                if (code == KeyEvent.VK_ENTER){
                    if (gameP.ui.commandNum == 0) gameP.ui.subState = 1;
                    else if (gameP.ui.commandNum == 1) {
                        gameP.resetGame(false, true);
                        gameP.saveLoad.load();
                        gameP.gameState = gameP.playState;
                        gameP.changeMusic(0);
                    }
                    else if (gameP.ui.commandNum == 2) System.exit(0);
                }
            } else {
                if (code == KeyEvent.VK_W || code == KeyEvent.VK_UP){
                    if (gameP.ui.commandNum == 0) gameP.ui.commandNum = 1;
                    else gameP.ui.commandNum--;
                }

                if (code == KeyEvent.VK_S || code == KeyEvent.VK_DOWN){
                    if (gameP.ui.commandNum == 1) gameP.ui.commandNum = 0;
                    else gameP.ui.commandNum++;
                }

                if (code == KeyEvent.VK_ENTER){
                    if (gameP.ui.commandNum == 0) gameP.ui.subState = 1;
                    else if (gameP.ui.commandNum == 1) System.exit(0);
                }
            }
        }

        else if (gameP.ui.subState == 1) {
            if (code == KeyEvent.VK_W || code == KeyEvent.VK_UP){
                if (gameP.ui.commandNum == 0) gameP.ui.commandNum = 4;
                else gameP.ui.commandNum--;
            }

            if (code == KeyEvent.VK_S || code == KeyEvent.VK_DOWN){
                if (gameP.ui.commandNum == 4) gameP.ui.commandNum = 0;
                else gameP.ui.commandNum++;
            }

            if (code == KeyEvent.VK_ENTER){
                if (gameP.ui.commandNum == 0) {
                    gameP.player.elementType = gameP.player.waterElement;
                    gameP.gameState = gameP.playState;
                    gameP.resetGame(true, true);
                    gameP.changeMusic(0);
                }
                else if (gameP.ui.commandNum == 1) {
                    gameP.player.elementType = gameP.player.fireElement;
                    gameP.gameState = gameP.playState;
                    gameP.resetGame(true, true);
                    gameP.changeMusic(0);
                }
                else if (gameP.ui.commandNum == 2) {
                    gameP.player.elementType = gameP.player.earthElement;
                    gameP.gameState = gameP.playState;
                    gameP.resetGame(true, true);
                    gameP.changeMusic(0);
                }
                else if (gameP.ui.commandNum == 3) {
                    gameP.player.elementType = gameP.player.thunderElement;
                    gameP.gameState = gameP.playState;
                    gameP.resetGame(true, true);
                    gameP.changeMusic(0);
                }
                else if (gameP.ui.commandNum == 4) {
                    gameP.ui.subState = 0;
                    gameP.ui.commandNum = 0;
                }
            }
        }

        gameP.playSE(7);
    }

    public void playState(int code){
        if (code == KeyEvent.VK_W || code == KeyEvent.VK_UP) upPressed = true;
        if (code == KeyEvent.VK_S || code == KeyEvent.VK_DOWN) downPressed = true;
        if (code == KeyEvent.VK_A || code == KeyEvent.VK_LEFT) leftPressed = true;
        if (code == KeyEvent.VK_D || code == KeyEvent.VK_RIGHT) rightPressed = true;
        if (code == KeyEvent.VK_ENTER) enterPressed = true;
        if (code == KeyEvent.VK_F) shotKeyPressed = true;
        if (code == KeyEvent.VK_SPACE) spacePressed = true;
        if (code == KeyEvent.VK_P) gameP.gameState = gameP.pauseState;
        if (code == KeyEvent.VK_C) gameP.gameState = gameP.characterState;
        if (code == KeyEvent.VK_M) gameP.gameState = gameP.mapState;
        if (code == KeyEvent.VK_X) gameP.map.miniMapOn = !gameP.map.miniMapOn;
        if (code == KeyEvent.VK_ESCAPE) gameP.changeGameState(gameP.optionState);
        if (code == KeyEvent.VK_B) debug = !debug;
        if (code == KeyEvent.VK_G) godMode = !godMode;
        if (code == KeyEvent.VK_R) {
            switch (gameP.currentMap){
                case 0 -> gameP.tileM.loadMap("/maps/worldV3.txt", 0);
                case 1 -> gameP.tileM.loadMap("/maps/interior01.txt", 1);
            }
        }
    }

    public void pauseState(int code){
        if (code == KeyEvent.VK_P) gameP.gameState = gameP.playState;
    }

    public void dialogueState(int code){
        if (code == KeyEvent.VK_ENTER) enterPressed = true;
    }

    public void characterState(int code){
        if (code == KeyEvent.VK_C || code == KeyEvent.VK_ESCAPE) gameP.gameState = gameP.playState;
        if (code == KeyEvent.VK_ENTER) gameP.player.selectItem();

        playerInventory(code);
    }

    public void optionState(int code){
        if (code == KeyEvent.VK_ESCAPE) gameP.gameState = gameP.playState;
        if (code == KeyEvent.VK_ENTER) enterPressed = true;

//        System.out.println("Options State: " + gameP.ui.subState);

        // Main Window Option
        if (gameP.ui.subState == 0){
            if (code == KeyEvent.VK_W || code == KeyEvent.VK_UP){
                if (gameP.ui.commandNum == 0) gameP.ui.commandNum = 5;
                else gameP.ui.commandNum--;
            }

            if (code == KeyEvent.VK_S || code == KeyEvent.VK_DOWN){
                if (gameP.ui.commandNum == 5) gameP.ui.commandNum = 0;
                else gameP.ui.commandNum++;
            }

            if (code == KeyEvent.VK_A || code == KeyEvent.VK_LEFT) {
                if (gameP.ui.commandNum == 1 && gameP.music.volumeScale > 0){
                    gameP.music.volumeScale--;
                    gameP.music.checkVolume();
                }
                if (gameP.ui.commandNum == 2 && gameP.se.volumeScale > 0){
                    gameP.se.volumeScale--;
                }
            }

            if (code == KeyEvent.VK_D || code == KeyEvent.VK_RIGHT) {
                if (gameP.ui.commandNum == 1 && gameP.music.volumeScale < 5){
                    gameP.music.volumeScale++;
                    gameP.music.checkVolume();
                }
                if (gameP.ui.commandNum == 2 && gameP.se.volumeScale < 5){
                    gameP.se.volumeScale++;
                }
            }

            if (code == KeyEvent.VK_ENTER){
                if (gameP.ui.commandNum == 0){
                    gameP.fullScreenOn = !gameP.fullScreenOn;
                    gameP.ui.subState = 1;
                } else if (gameP.ui.commandNum == 3) {
                    gameP.ui.subState = 2;
                } else if (gameP.ui.commandNum == 4) {
                    gameP.ui.commandNum = 0;
                    gameP.ui.subState = 3;
                } else if (gameP.ui.commandNum == 5) {
                    gameP.gameState = gameP.playState;
                }

                gameP.player.attackCanceled = true;
            }
        }

        // Notification Fullscreen
        else if (gameP.ui.subState == 1) {
            if (gameP.keyH.enterPressed) {
                gameP.ui.commandNum = 0;
                gameP.ui.subState = 0;
            }
        }

        // Control Window
        else if (gameP.ui.subState == 2) {
            if (gameP.keyH.enterPressed) {
                gameP.ui.commandNum = 3;
                gameP.ui.subState = 0;
            }
        }

        // Notification Quit Game
        else if (gameP.ui.subState == 3) {
            if (code == KeyEvent.VK_W || code == KeyEvent.VK_UP){
                if (gameP.ui.commandNum == 0) gameP.ui.commandNum = 1;
                else gameP.ui.commandNum--;
            }

            if (code == KeyEvent.VK_S || code == KeyEvent.VK_DOWN){
                if (gameP.ui.commandNum == 1) gameP.ui.commandNum = 0;
                else gameP.ui.commandNum++;
            }

            if (gameP.keyH.enterPressed) {
                // Yes
                if (gameP.ui.commandNum == 0) {
                    gameP.changeGameState(gameP.titleState);
                    gameP.changeMusic(3); // Belum ada title music
                }

                // No
                if (gameP.ui.commandNum == 1) {
                    gameP.ui.subState = 0;
                    gameP.ui.commandNum = 4;
                }
            }
        }

        gameP.playSE(7);
    }

    public void gameOverState(int code){
        if (code == KeyEvent.VK_ENTER) enterPressed = true;

        if (code == KeyEvent.VK_W || code == KeyEvent.VK_UP){
            if (gameP.ui.commandNum == 0) gameP.ui.commandNum = 1;
            else gameP.ui.commandNum--;
        }

        if (code == KeyEvent.VK_S || code == KeyEvent.VK_DOWN){
            if (gameP.ui.commandNum == 1) gameP.ui.commandNum = 0;
            else gameP.ui.commandNum++;
        }

        if (code == KeyEvent.VK_ENTER){

            // Retry
            if (gameP.ui.commandNum == 0) {
                gameP.resetGame(false, false);
                gameP.gameState = gameP.playState;
                gameP.player.attackCanceled = true;
                gameP.changeMusic(0);
            }

            // Quit
            else if (gameP.ui.commandNum == 1) {
                gameP.changeGameState(gameP.titleState);
            }
        }

        gameP.playSE(7);
    }

    public void battleState(int code){

        if (code == KeyEvent.VK_ENTER) enterPressed = true;

        if (gameP.ui.turn == gameP.ui.playerTurn){

            System.out.println("Player Defense Power Sebelum Guarding: " + gameP.player.defensePower);

            // Main State
            if (gameP.ui.subState == 0){
                if (code == KeyEvent.VK_W || code == KeyEvent.VK_UP){
                    if (gameP.ui.commandNum == 0) gameP.ui.commandNum = 3;
                    else gameP.ui.commandNum--;

                    gameP.playSE(7);
                }

                if (code == KeyEvent.VK_S || code == KeyEvent.VK_DOWN){
                    if (gameP.ui.commandNum == 3) gameP.ui.commandNum = 0;
                    else gameP.ui.commandNum++;

                    gameP.playSE(7);
                }

                if (code == KeyEvent.VK_A || code == KeyEvent.VK_LEFT || code == KeyEvent.VK_D || code == KeyEvent.VK_RIGHT) {
                    if (gameP.ui.commandNum == 0){
                        gameP.ui.commandNum = 2;
                    } else if (gameP.ui.commandNum == 1){
                        gameP.ui.commandNum = 3;
                    } else if (gameP.ui.commandNum == 2){
                        gameP.ui.commandNum = 0;
                    } else if (gameP.ui.commandNum == 3){
                        gameP.ui.commandNum = 1;
                    }

                    gameP.playSE(7);
                }

                if (code == KeyEvent.VK_ENTER){

                    // Fight
                    if (gameP.ui.commandNum == 0){
                        gameP.ui.commandNum = 0;
                        gameP.ui.subState = 1;
                    }

                    // Pokenom
                    else if (gameP.ui.commandNum == 1) {
                        gameP.ui.commandNum = 0;
                        gameP.ui.subState = 2;
                    }

                    // Bag
                    else if (gameP.ui.commandNum == 2) {
                        gameP.ui.commandNum = 0;
                        gameP.ui.subState = 3;
                    }

                    // Run
                    else if (gameP.ui.commandNum == 3) {
                        gameP.player.inBattleState = true;
                        gameP.player.speed = gameP.player.defaultSpeed;
                        gameP.changeGameState(gameP.playState);
                    }

                    gameP.playSE(7);
                    gameP.player.attackCanceled = true;
                }
            }

            // Fight State
            else if (gameP.ui.subState == 1) {

                if (code == KeyEvent.VK_ESCAPE){
                    gameP.playSE(7);
                    gameP.ui.subState = 0;
                }

                if (code == KeyEvent.VK_W || code == KeyEvent.VK_UP){
                    if (gameP.ui.commandNum == 0) gameP.ui.commandNum = 3;
                    else gameP.ui.commandNum--;

                    gameP.playSE(7);
                }

                if (code == KeyEvent.VK_S || code == KeyEvent.VK_DOWN){
                    if (gameP.ui.commandNum == 3) gameP.ui.commandNum = 0;
                    else gameP.ui.commandNum++;

                    gameP.playSE(7);
                }

                if (code == KeyEvent.VK_A || code == KeyEvent.VK_LEFT || code == KeyEvent.VK_D || code == KeyEvent.VK_RIGHT) {
                    if (gameP.ui.commandNum == 0){
                        gameP.ui.commandNum = 2;
                    } else if (gameP.ui.commandNum == 1){
                        gameP.ui.commandNum = 3;
                    } else if (gameP.ui.commandNum == 2){
                        gameP.ui.commandNum = 0;
                    } else if (gameP.ui.commandNum == 3){
                        gameP.ui.commandNum = 1;
                    }

                    gameP.playSE(7);
                }

                if (code == KeyEvent.VK_ENTER){

                    // Hit
                    if (gameP.ui.commandNum == 0){
                        gameP.playSE(4);

                        System.out.println("Attack Power Dari Player: " + gameP.player.attackPower);
                        int damage = (int) ((gameP.player.attackPower - gameP.ui.enemy.defensePower) * gameP.player.multipleDamageByElement(gameP.player.currentWeapon.elementType, gameP.ui.enemy.elementType));
                        if (damage < 0) damage = 0;
                        System.out.println("Damage Hit Battle: " + damage + "\n");

                        gameP.ui.enemy.life -= damage;
                        gameP.ui.enemy.invicible = true;

                        if (damage <= 0) gameP.ui.battleDialogueText = "It's Not Effective!";
                        else if (damage > gameP.ui.enemy.maxLife*2/3) gameP.ui.battleDialogueText = "It's Super Effective!";
                        else if (damage < gameP.ui.enemy.maxLife/3) gameP.ui.battleDialogueText = "It's Less Effective";
                        else gameP.ui.battleDialogueText = "It's Effective";
                        gameP.ui.subState = 4;
                    }

                    // Fireball
                    else if (gameP.ui.commandNum == 1) {
                        if (gameP.player.projectile.haveResources(gameP.player)) {
                            gameP.playSE(4);

                            System.out.println("Projectile Power Dari Player: " + gameP.player.projectile.attackPower);
                            gameP.player.projectile.substractResources(gameP.player);
                            int damage = (int) ((gameP.player.projectile.attackPower - gameP.ui.enemy.defensePower) * gameP.player.multipleDamageByElement(gameP.player.projectile.elementType, gameP.ui.enemy.elementType));
                            if (damage < 0) damage = 0;
                            System.out.println("Damage Projectile Battle: " + damage + "\n");

                            gameP.ui.enemy.life -= damage;
                            gameP.ui.enemy.invicible = true;

                            if (damage <= 0) gameP.ui.battleDialogueText = "It's Not Effective!";
                            else if (damage > gameP.ui.enemy.maxLife*2/3) gameP.ui.battleDialogueText = "It's Super Effective!";
                            else if (damage < gameP.ui.enemy.maxLife/3) gameP.ui.battleDialogueText = "It's Less Effective";
                            else gameP.ui.battleDialogueText = "It's Effective";
                            gameP.ui.subState = 4;
                        } else {
                            gameP.ui.battleDialogueText = "Not Enough Resources";
                            gameP.ui.subState = 4;
                        }
                    }

                    // Guard
                    else if (gameP.ui.commandNum == 2) {
//                        // Di awal giliran pemain, pastikan defense sudah direset
//                        // Anda perlu menyimpan defense asli di suatu tempat
//
//                        gameP.player.defensePower *= 2; // Naikkan defense
//                        gameP.player.guarding = true; // Gunakan flag
//                        gameP.ui.battleDialogueText = gameP.player.name + " is guarding!";
//
//                        System.out.println("Player Defense Power Setelah Guarding: " + gameP.player.defensePower);
//
//                        // Ganti giliran ke musuh
//                        gameP.ui.subState = 4;

                        // BUKAN LAGI MENAIKKAN DEFENSE DI SINI
                        // KITA MEMULAI GUARD QTE

                        initQTE();

                        // Ganti ke subState khusus untuk QTE
                        gameP.ui.subState = 6; // Anggap 6 adalah state untuk Guard QTE

                        gameP.playSE(8); // Suara untuk memulai QTE
                    }

                    // Tambahan
                    else if (gameP.ui.commandNum == 3) {}

                    gameP.playSE(7);
                    gameP.player.attackCanceled = true;
                }
            }

            // Pokenom State
            else if (gameP.ui.subState == 2) {

                if (code == KeyEvent.VK_ESCAPE){
                    gameP.playSE(7);
                    gameP.ui.subState = 0;
                }
            }

            // Bag State
            else if (gameP.ui.subState == 3) {

                if (code == KeyEvent.VK_ESCAPE){
                    gameP.playSE(7);
                    gameP.ui.subState = 0;
                }

                if (code == KeyEvent.VK_ENTER) {
                    gameP.player.selectItem();
                    if (gameP.player.selectedItem.type == gameP.player.consumType) {
                        gameP.ui.battleDialogueText = gameP.player.selectedItem.dialogues[1][0];
                        gameP.ui.subState = 3;
                    }

                    enterPressed = false;
                }

                playerInventory(code);
            }

            // Dialog State
            else if (gameP.ui.subState == 4) {
                if (code == KeyEvent.VK_ENTER){
                    if (gameP.ui.enemy.life <= 0){
                        gameP.ui.battleDialogueText = "Defeated The " + gameP.ui.enemy.name + "! \n" +
                                "Gained " + gameP.ui.enemy.exp + " Exp! ";
                        gameP.ui.subState = 5;
                    } else {
                        gameP.ui.turn = gameP.ui.enemyTurn;
                        gameP.ui.subState = 0;

                        if (gameP.ui.enemy.guarding){
                            gameP.ui.enemy.defensePower /= 2;
                            gameP.ui.enemy.guarding = false;
                        }
                    }

                    gameP.playSE(7);

                    gameP.keyH.enterPressed = false;
                }
            }

            // Ending Battle State
            else if (gameP.ui.subState == 5) {
                if (code == KeyEvent.VK_ENTER){
                    gameP.player.exp += gameP.ui.enemy.exp;
                    gameP.monster[gameP.currentMap][gameP.ui.monsterBattleID].alive = false;

                    gameP.changeGameState(gameP.playState);
                    gameP.player.checkLevelUp();

                    gameP.keyH.enterPressed = false;
                }
            }

            // Guard QTE
            else if (gameP.ui.subState == 6){
                if (code == KeyEvent.VK_ENTER) {

                    // 1. Cek apakah kena ZONA PERFECT
                    if (gameP.ui.qteIndicator_x >= gameP.ui.qteZone_x &&
                            gameP.ui.qteIndicator_x <= gameP.ui.qteZone_x + gameP.ui.qteZone_width) {

                        gameP.player.defensePower *= 3; // Buff besar!
                        gameP.player.guardingPerfect = true;
                        gameP.ui.battleDialogueText = "PERFECT GUARD!";

                        // 2. Cek apakah kena di dalam BAR (Good Guard)
                    } else if (gameP.ui.qteIndicator_x >= gameP.ui.qteBar_x &&
                            gameP.ui.qteIndicator_x <= gameP.ui.qteBar_x + gameP.ui.qteBar_width) {

                        gameP.player.defensePower *= 2; // Buff normal
                        gameP.player.guarding = true;
                        gameP.ui.battleDialogueText = "Guard successful.";

                        // 3. Jika menekan di luar bar, GAGAL
                    } else {
                        gameP.ui.battleDialogueText = "Guard failed!";
                        // Tidak ada buff defense
                    }

                    System.out.println("Player Defense Power Setelah Guarding: " + gameP.player.defensePower);

                    // Matikan QTE dan kembali ke dialog player
                    gameP.ui.guardQTE_Active = false;
                    gameP.ui.subState = 4;
                }
            }
        } else if (gameP.ui.turn == gameP.ui.enemyTurn) {

            // Enemy Dialog State (misal: subState 2)
            if (gameP.ui.subState == 0) {
                gameP.playSE(4); // Mainkan suara serangan musuh

                int action = new Random().nextInt(100)+1;

                System.out.println("Enemy Defense Power Sebelum Guarding: " + gameP.ui.enemy.defensePower);

                // 50% kemungkinan menyerang
                if (action < 50) {
                    int damage = (int) ((gameP.ui.enemy.attackPower - gameP.player.defensePower) * gameP.player.multipleDamageByElement(gameP.ui.enemy.elementType, gameP.player.currentShield.elementType));
                    if (damage < 0) damage = 0;

                    gameP.player.life -= damage;
                    gameP.ui.battleDialogueText = gameP.ui.enemy.name + " menyerang dan memberikan " + damage + " kerusakan!";
                }

                // 50% kemungkinan guarding
                else if (action >= 50 && action < 100){
                    gameP.ui.battleDialogueText = gameP.ui.enemy.name + " is Guarding";
                    gameP.ui.enemy.defensePower *= 2;
                    System.out.println("Enemy Defense Power Setelah Guarding: " + gameP.ui.enemy.defensePower);
                    gameP.ui.enemy.guarding = true;
                }

                gameP.ui.subState = 2;
            }

            // Enemy Won State (misal: subState 1)
            else if (gameP.ui.subState == 1) {
                if (code == KeyEvent.VK_ENTER) {
                    gameP.stopMusic();
                    gameP.playSE(10);
                    gameP.gameState = gameP.gameOverState;
                    gameP.ui.commandNum = 0;
                    gameP.ui.subState = 0;
                    gameP.ui.turn = gameP.ui.playerTurn;
                }
            }

            // enemy dialogue
            else if (gameP.ui.subState == 2) {
                if (code == KeyEvent.VK_ENTER) {
                    if (gameP.player.life <= 0) {
                        // Kalah
                        gameP.ui.battleDialogueText = "Player Defeated By " + gameP.ui.enemy.name + "!";
                        gameP.ui.subState = 1; // Pindah ke state 'Enemy Won'
                    } else {
                        // Kembali ke giliran player
                        gameP.ui.commandNum = 0;
                        gameP.ui.turn = gameP.ui.playerTurn;
                        gameP.ui.subState = 0;

                        // Reset status Guard di sini!
                        if (gameP.player.guarding) {
                            gameP.player.defensePower /= 2; // Kembalikan defense
                            gameP.player.guarding = false;
                        } else if (gameP.player.guardingPerfect) {
                            gameP.player.defensePower /= 3; // Kembalikan defense
                            gameP.player.guardingPerfect = false;
                        }
                    }
                    gameP.playSE(7);
                    gameP.keyH.enterPressed = false;
                }
            }
        }
    }

    public void tradeState(int code){
        if (code == KeyEvent.VK_ENTER) enterPressed = true;
        if (code == KeyEvent.VK_ESCAPE) escPressed = true;

        if (gameP.ui.subState == 0){

            if (code == KeyEvent.VK_W || code == KeyEvent.VK_UP){
                if (gameP.ui.commandNum == 0) gameP.ui.commandNum = 2;
                else gameP.ui.commandNum--;
            }

            if (code == KeyEvent.VK_S || code == KeyEvent.VK_DOWN){
                if (gameP.ui.commandNum == 2) gameP.ui.commandNum = 0;
                else gameP.ui.commandNum++;
            }
        } else if (gameP.ui.subState == 1) {
            npcInventory(code);
            if (code == KeyEvent.VK_ESCAPE) {
                gameP.ui.subState = 0;
                enterPressed = false;
            }
        } else if (gameP.ui.subState == 2) {
            playerInventory(code);
            if (code == KeyEvent.VK_ESCAPE) {
                gameP.ui.subState = 0;
                enterPressed = false;
            }
        }

        gameP.playSE(7);
    }

    public void mapState(int code){
        if (code == KeyEvent.VK_M || code == KeyEvent.VK_ESCAPE) gameP.gameState = gameP.playState;
    }

    @Override
    public void keyTyped(KeyEvent e) {
    }

    @Override
    public void keyPressed(KeyEvent e) {
        int code = e.getKeyCode();

        if (gameP.gameState == gameP.titleState) titleState(code);
        else if (gameP.gameState == gameP.playState) playState(code);
        else if (gameP.gameState == gameP.pauseState) pauseState(code);
        else if (gameP.gameState == gameP.dialogueState || gameP.gameState == gameP.cutsceneState) dialogueState(code);
        else if (gameP.gameState == gameP.characterState) characterState(code);
        else if (gameP.gameState == gameP.optionState) optionState(code);
        else if (gameP.gameState == gameP.gameOverState) gameOverState(code);
        else if (gameP.gameState == gameP.tradeState) tradeState(code);
        else if (gameP.gameState == gameP.mapState) mapState(code);
        else if (gameP.gameState == gameP.battleState) battleState(code);
    }

    @Override
    public void keyReleased(KeyEvent e) {
        int code = e.getKeyCode();

        if (code == KeyEvent.VK_W || code == KeyEvent.VK_UP) upPressed = false;
        if (code == KeyEvent.VK_S || code == KeyEvent.VK_DOWN) downPressed = false;
        if (code == KeyEvent.VK_A || code == KeyEvent.VK_LEFT) leftPressed = false;
        if (code == KeyEvent.VK_D || code == KeyEvent.VK_RIGHT) rightPressed = false;
        if (code == KeyEvent.VK_F) shotKeyPressed = false;
        if (code == KeyEvent.VK_ENTER) enterPressed = false;
        if (code == KeyEvent.VK_SPACE) spacePressed = false;
        if (code == KeyEvent.VK_ESCAPE) escPressed = false;
    }
}
