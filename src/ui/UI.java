package ui;

import font.FontManager;
import main.GamePanel;

import java.awt.*;
import java.text.DecimalFormat;

public class UI {
    public GamePanel gameP;
    Graphics2D g2d;

    /* Font Setting */
    public FontManager fontM = new FontManager();
    Font Open_Sans, Cinzel;
    /* ===== */

//    BufferedImage keyImage;

    public boolean messageOn;
    public String message = "";
    int messageCounter;

    public boolean gameFinished;
    public double playTime = 0;
    DecimalFormat dFormat = new DecimalFormat("#0.00");

    public UI(GamePanel gameP){
        this.gameP = gameP;
        fontM.setFont();

        Cinzel = fontM.fonts[0].font;
        Open_Sans = fontM.fonts[1].font;

//        KeyObject key = new KeyObject(gameP);
//        keyImage = key.image;
    }

    public void showMessage(String text){
        message = text;
        messageOn = true;
    }

    public int getXForCenteredText(String text){
        int length = (int)g2d.getFontMetrics().getStringBounds(text, g2d).getWidth();
        return gameP.screenWidth/2 - length/2;
    }

    public void drawPauseScreen(){
        g2d.setFont(g2d.getFont().deriveFont(Font.PLAIN, 80f));

        String text = "PAUSED";
        int x = getXForCenteredText(text);
        int y = gameP.screenHeight/2;

        g2d.drawString(text, x, y);
    }

    public void draw(Graphics2D g2d){
        this.g2d = g2d;
//        System.out.println(fontM.fonts[1].font);
        g2d.setFont(Open_Sans);
        g2d.setColor(Color.white);

        if (gameP.gameState == gameP.playState){

        }
        if (gameP.gameState == gameP.pauseState){
            drawPauseScreen();
        }
    }
}
