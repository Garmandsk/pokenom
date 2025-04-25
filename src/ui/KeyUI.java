package ui;

import main.GamePanel;
import object.KeyObject;

public class KeyUI {
    GamePanel gameP;

    String text;
    int textLength, x, y;

    KeyUI(GamePanel gameP){
        this.gameP = gameP;

//        text = "x " + gameP.player.getHasKey();
//        keyTextLength = (int)g2d.getFontMetrics().getStringBounds(keyText, g2d).getWidth();
        x = gameP.screenWidth/8;
        y = gameP.screenHeight/8;
    }
}
