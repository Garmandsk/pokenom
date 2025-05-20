package main;

import entity.Entity;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class KeyHandler implements KeyListener {
    public boolean upPressed, downPressed, leftPressed, rightPressed, enterPressed, shotKeyPressed;
    public boolean debug;
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

    public void titleState(int code){
        if (gameP.ui.titleScreenState == 0){
            if (code == KeyEvent.VK_W || code == KeyEvent.VK_UP){
                if (gameP.ui.commandNum == 0) gameP.ui.commandNum = 2;
                else gameP.ui.commandNum--;
            }

            if (code == KeyEvent.VK_S || code == KeyEvent.VK_DOWN){
                if (gameP.ui.commandNum == 2) gameP.ui.commandNum = 0;
                else gameP.ui.commandNum++;
            }

            if (code == KeyEvent.VK_ENTER){
                if (gameP.ui.commandNum == 0){
                    gameP.ui.titleScreenState = 1;
                } else if (gameP.ui.commandNum == 1) {}
                else if (gameP.ui.commandNum == 2) System.exit(0);
            }
        }

        else if (gameP.ui.titleScreenState == 1) {
            if (code == KeyEvent.VK_W || code == KeyEvent.VK_UP){
                if (gameP.ui.commandNum == 0) gameP.ui.commandNum = 4;
                else gameP.ui.commandNum--;
            }

            if (code == KeyEvent.VK_S || code == KeyEvent.VK_DOWN){
                if (gameP.ui.commandNum == 4) gameP.ui.commandNum = 0;
                else gameP.ui.commandNum++;
            }

            if (code == KeyEvent.VK_ENTER){
                if (gameP.ui.commandNum == 0) gameP.gameState = gameP.playState;
                else if (gameP.ui.commandNum == 1) gameP.gameState = gameP.playState;
                else if (gameP.ui.commandNum == 2) gameP.gameState = gameP.playState;
                else if (gameP.ui.commandNum == 3) gameP.gameState = gameP.playState;
                else if (gameP.ui.commandNum == 4) {
                    gameP.ui.titleScreenState = 0;
                    gameP.ui.commandNum = 0;
                }
                gameP.stopMusic();
                gameP.playMusic(0);
            }
        }
    }

    public void playState(int code){
        if (code == KeyEvent.VK_W || code == KeyEvent.VK_UP) upPressed = true;
        if (code == KeyEvent.VK_S || code == KeyEvent.VK_DOWN) downPressed = true;
        if (code == KeyEvent.VK_A || code == KeyEvent.VK_LEFT) leftPressed = true;
        if (code == KeyEvent.VK_D || code == KeyEvent.VK_RIGHT) rightPressed = true;
        if (code == KeyEvent.VK_ENTER) enterPressed = true;
        if (code == KeyEvent.VK_F) shotKeyPressed = true;
        if (code == KeyEvent.VK_P) gameP.gameState = gameP.pauseState;
        if (code == KeyEvent.VK_C) gameP.gameState = gameP.characterState;
        if (code == KeyEvent.VK_M) gameP.gameState = gameP.mapState;
        if (code == KeyEvent.VK_X) gameP.map.miniMapOn = !gameP.map.miniMapOn;
        if (code == KeyEvent.VK_ESCAPE) gameP.gameState = gameP.optionState;
        if (code == KeyEvent.VK_B) debug = !debug;
        if (code == KeyEvent.VK_R) {
            switch (gameP.currentMap){
                case 0:
                    gameP.tileM.loadMap("/maps/worldV3.txt", 0);
                    break;
                case 1:
                    gameP.tileM.loadMap("/maps/interior01.txt", 1);
                    break;
            }
        }
    }

    public void pauseState(int code){
        if (code == KeyEvent.VK_P) gameP.gameState = gameP.playState;
    }

    public void dialogueState(int code){
        if (code == KeyEvent.VK_ENTER) gameP.gameState = gameP.playState;
    }

    public void characterState(int code){
        if (code == KeyEvent.VK_C) gameP.gameState = gameP.playState;
        if (code == KeyEvent.VK_ENTER) gameP.player.selectItem();

        playerInventory(code);
    }

    public void optionState(int code){

        if (code == KeyEvent.VK_ESCAPE) gameP.gameState = gameP.playState;
        if (code == KeyEvent.VK_ENTER) enterPressed = true;

        if (gameP.ui.optionScreenState == 0){
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
                    gameP.ui.optionScreenState = 1;
                }
                else if (gameP.ui.commandNum == 3) gameP.ui.optionScreenState = 2;
                else if (gameP.ui.commandNum == 4) {
                    gameP.ui.commandNum = 0;
                    gameP.ui.optionScreenState = 3;
                }
                else if (gameP.ui.commandNum == 5) gameP.gameState = gameP.playState;
            }
        }

        else if (gameP.ui.optionScreenState == 1) {
            if (gameP.keyH.enterPressed) {
                gameP.ui.commandNum = 0;
                gameP.ui.optionScreenState = 0;
            }
        }

        else if (gameP.ui.optionScreenState == 2) {
            if (gameP.keyH.enterPressed) {
                gameP.ui.commandNum = 3;
                gameP.ui.optionScreenState = 0;
            }
        }

        else if (gameP.ui.optionScreenState == 3) {
            if (code == KeyEvent.VK_W || code == KeyEvent.VK_UP){
                if (gameP.ui.commandNum == 0) gameP.ui.commandNum = 1;
                else gameP.ui.commandNum--;
            }

            if (code == KeyEvent.VK_S || code == KeyEvent.VK_DOWN){
                if (gameP.ui.commandNum == 1) gameP.ui.commandNum = 0;
                else gameP.ui.commandNum++;
            }

            if (gameP.keyH.enterPressed) {
                if (gameP.ui.commandNum == 0) {
                    gameP.ui.optionScreenState = 0;
                    gameP.gameState = gameP.titleState;
                }

                // No
                if (gameP.ui.commandNum == 1) {
                    gameP.ui.commandNum = 4;
                    gameP.ui.optionScreenState = 0;
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
            if (gameP.ui.commandNum == 0) {
                gameP.gameState = gameP.playState;
                gameP.retry();
                gameP.playMusic(0);
            }
            else if (gameP.ui.commandNum == 1) {
                gameP.gameState = gameP.titleState;
                gameP.restart();
            }
        }

        gameP.playSE(7);
    }

    public void tradeState(int code){

        if (gameP.ui.tradeScreenState == 0){
            if (code == KeyEvent.VK_W || code == KeyEvent.VK_UP){
                if (gameP.ui.commandNum == 0) gameP.ui.commandNum = 2;
                else gameP.ui.commandNum--;
            }

            if (code == KeyEvent.VK_S || code == KeyEvent.VK_DOWN){
                if (gameP.ui.commandNum == 2) gameP.ui.commandNum = 0;
                else gameP.ui.commandNum++;
            }

            if (code == KeyEvent.VK_ENTER){
                if (gameP.ui.commandNum == 0) {
                    gameP.ui.tradeScreenState = 1;
                }
                else if (gameP.ui.commandNum == 1) {
                    gameP.ui.tradeScreenState = 2;
                }
                else if (gameP.ui.commandNum == 2) {
                    gameP.ui.commandNum = 0;
                    gameP.gameState = gameP.dialogueState;
                    gameP.ui.currentDialogue = "Sampai Jumpa!";
                }
            }
        } else if (gameP.ui.tradeScreenState == 1) {
            npcInventory(code);
            if (code == KeyEvent.VK_ESCAPE) gameP.ui.tradeScreenState = 0;
            if (code == KeyEvent.VK_ENTER) enterPressed = true;
        } else if (gameP.ui.tradeScreenState == 2) {
            playerInventory(code);
            if (code == KeyEvent.VK_ESCAPE) gameP.ui.tradeScreenState = 0;
            if (code == KeyEvent.VK_ENTER) enterPressed = true;
        }

        gameP.playSE(7);
    }

    public void mapState(int code){
        if (code == KeyEvent.VK_M) gameP.gameState = gameP.playState;
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
        else if (gameP.gameState == gameP.dialogueState) dialogueState(code);
        else if (gameP.gameState == gameP.characterState) characterState(code);
        else if (gameP.gameState == gameP.optionState) optionState(code);
        else if (gameP.gameState == gameP.gameOverState) gameOverState(code);
        else if (gameP.gameState == gameP.tradeState) tradeState(code);
        else if (gameP.gameState == gameP.mapState) mapState(code);

    }

    @Override
    public void keyReleased(KeyEvent e) {
        int code = e.getKeyCode();

        if (code == KeyEvent.VK_W || code == KeyEvent.VK_UP) upPressed = false;
        if (code == KeyEvent.VK_S || code == KeyEvent.VK_DOWN) downPressed = false;
        if (code == KeyEvent.VK_A || code == KeyEvent.VK_LEFT) leftPressed = false;
        if (code == KeyEvent.VK_D || code == KeyEvent.VK_RIGHT) rightPressed = false;
        if (code == KeyEvent.VK_F) shotKeyPressed = false;
    }
}
