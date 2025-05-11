package main;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class KeyHandler implements KeyListener {
    public boolean upPressed, downPressed, leftPressed, rightPressed, enterPressed, shotKeyPressed;
    public boolean debug;
    GamePanel gameP;

    public KeyHandler(GamePanel gameP){
        this.gameP = gameP;
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
                gameP.playMusic(0);
            }
        }
    }

    public void playState(int code){
        if (code == KeyEvent.VK_W || code == KeyEvent.VK_UP) upPressed = true;
        if (code == KeyEvent.VK_S || code == KeyEvent.VK_DOWN) downPressed = true;
        if (code == KeyEvent.VK_A || code == KeyEvent.VK_LEFT) leftPressed = true;
        if (code == KeyEvent.VK_D || code == KeyEvent.VK_RIGHT) rightPressed = true;
        if (code == KeyEvent.VK_F) shotKeyPressed = true;
        if (code == KeyEvent.VK_B) debug = !debug;
        if (code == KeyEvent.VK_R) gameP.tileM.loadMap("/maps/worldV2.txt");
        if (code == KeyEvent.VK_P) gameP.gameState = gameP.pauseState;
        if (code == KeyEvent.VK_C) gameP.gameState = gameP.characterState;
        if (code == KeyEvent.VK_ENTER) enterPressed = true;
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

        if (code == KeyEvent.VK_W || code == KeyEvent.VK_UP){
            if (gameP.ui.slotRow != 0){
                gameP.ui.slotRow--;
                gameP.playSE(7);
            } else if (!(gameP.ui.slotCol == 0 && gameP.ui.slotRow == 0)) {
                gameP.ui.slotRow = gameP.ui.inventoryMaxRow - 1;
                gameP.ui.slotCol--;
                gameP.playSE(7);
            }
        }

        if (code == KeyEvent.VK_S || code == KeyEvent.VK_DOWN){
            if (gameP.ui.slotRow != gameP.ui.inventoryMaxRow - 1){
                gameP.ui.slotRow++;
                gameP.playSE(7);
            } else if (!(gameP.ui.slotCol == gameP.ui.inventoryMaxCol - 1 && gameP.ui.slotRow == gameP.ui.inventoryMaxRow - 1)) {
                gameP.ui.slotRow = 0;
                gameP.ui.slotCol++;
                gameP.playSE(7);
            }
        }

        if (code == KeyEvent.VK_A || code == KeyEvent.VK_LEFT){
            if (gameP.ui.slotCol != 0){
                gameP.ui.slotCol--;
                gameP.playSE(7);
            } else if (!(gameP.ui.slotCol == 0 && gameP.ui.slotRow == 0)){
                gameP.ui.slotCol = gameP.ui.inventoryMaxCol - 1;
                gameP.ui.slotRow--;
                gameP.playSE(7);
            }
        }

        if (code == KeyEvent.VK_D || code == KeyEvent.VK_RIGHT){
            if (gameP.ui.slotCol != gameP.ui.inventoryMaxCol - 1){
                gameP.ui.slotCol++;
                gameP.playSE(7);
            } else if (!(gameP.ui.slotCol == gameP.ui.inventoryMaxCol - 1 && gameP.ui.slotRow == gameP.ui.inventoryMaxRow - 1)) {
                gameP.ui.slotCol = 0;
                gameP.ui.slotRow++;
                gameP.playSE(7);
            }
        }
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
