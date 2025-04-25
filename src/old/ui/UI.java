package old.ui;

import main.GamePanel;
import object.KeyObject;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.text.DecimalFormat;

public class UI {
    public GamePanel gameP;
    Graphics2D g2d;

    Font Arial_60, Arial_80B;
//    BufferedImage keyImage;

    public boolean messageOn;
    public String message = "";
    int messageCounter;

    public boolean gameFinished;
    public double playTime = 0;
    DecimalFormat dFormat = new DecimalFormat("#0.00");

    public UI(GamePanel gameP){
        this.gameP = gameP;

        Arial_60 = new Font("Arial", Font.PLAIN, 60);
        Arial_80B = new Font("Arial", Font.BOLD, 80);
//        KeyObject key = new KeyObject(gameP);
//        keyImage = key.image;
    }

    public void showMessage(String text){
        message = text;
        messageOn = true;
    }

    public void draw(Graphics2D g2d){

//        if (gameFinished == true){
//            String text;
//            int textLength, x, y;
//
//            g2d.setFont(Arial_60);
//            g2d.setColor(Color.white);
//            text = "You Found The Secret!";
//            textLength = (int)g2d.getFontMetrics().getStringBounds(text, g2d).getWidth();
//            x = gameP.screenWidth/2 - textLength/2;
//            y = gameP.screenHeight/2 - (gameP.tileSize*3);
//            g2d.drawString(text, x, y);
//
//            text = "Your Playtime Is: " + dFormat.format(playTime) + "!";
//            textLength = (int)g2d.getFontMetrics().getStringBounds(text, g2d).getWidth();
//            x = gameP.screenWidth/2 - textLength/2;
//            y = gameP.screenHeight/2 + (gameP.tileSize*4);
//            g2d.drawString(text, x, y);
//
//            g2d.setFont(Arial_80B);
//            g2d.setColor(Color.yellow);
//            text = "Congratulations!";
//            textLength = (int)g2d.getFontMetrics().getStringBounds(text, g2d).getWidth();
//            x = gameP.screenWidth/2 - textLength/2;
//            y = gameP.screenHeight/2 + (gameP.tileSize*2);
//            g2d.drawString(text, x, y);
//
//            gameP.gameThread = null;
//        }
//
//        /* Static UI */
//        g2d.setFont(Arial_60);
//        g2d.setColor(Color.white);
//
//        /* Key UI */
//        String keyText;
//        int keyTextLength, keyX, keyY;
//
//        keyText = "x " + gameP.player.getHasKey();
//        keyTextLength = (int)g2d.getFontMetrics().getStringBounds(keyText, g2d).getWidth();
//        keyX = gameP.screenWidth/8;
//        keyY = gameP.screenHeight/8;
//        g2d.drawImage(keyImage, (keyX - gameP.tileSize)/2, keyY - gameP.tileSize, gameP.tileSize, gameP.tileSize, null);
//        g2d.drawString(keyText, keyX/2 + 32, keyY);
//
//        /* Time */
//        g2d.setFont(g2d.getFont().deriveFont(20f));
//        String timeText;
//        int timeTextLength, timeX, timeY;
//
//        playTime += (double)1/60;
//        timeText = "Time: " + dFormat.format(playTime);
//        timeTextLength = (int)g2d.getFontMetrics().getStringBounds(timeText, g2d).getWidth();
//        timeX = gameP.screenWidth - timeTextLength;
//        timeY = gameP.screenHeight - 10;
//        g2d.drawString(timeText, timeX, timeY);
//
//        /* ===== */
//
//        /* Message */
//        if (messageOn == true){
//            int messageLength, messageX, messageY;
//
//            messageLength = (int)g2d.getFontMetrics().getStringBounds(message, g2d).getWidth();
//            messageX = gameP.screenWidth - messageLength * 16 /10;
//            messageY = gameP.screenHeight/18;
//            g2d.setFont(g2d.getFont().deriveFont(30f));
//            g2d.drawString(message, messageX, messageY);
//
//            messageCounter++;
//
//            if (messageCounter == 120){
//                messageOn = false;
//                messageCounter = 0;
//            }
//        }
//        /* ===== */
    }
}
