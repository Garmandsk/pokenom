package object;

import main.GamePanel;

import java.awt.*;
import java.awt.image.BufferedImage;

public class SuperObject {
    public int worldX, worldY;
    public boolean collision = false;
    public String nameObject;
    public BufferedImage image;
    public Rectangle solidArea = new Rectangle(0, 0, 48, 48);
    public int defaultSolidAreaX = 0;
    public int defaultSolidAreaY = 0;

    public void draw(Graphics2D g2d, GamePanel gameP){
        int screenX = worldX - gameP.player.worldX + gameP.player.screenX;
        int screenY = worldY - gameP.player.worldY + gameP.player.screenY;

        /* Visible Area */
        if (    worldX + gameP.tileSize > gameP.player.worldX - gameP.player.screenX &&
                worldX - gameP.tileSize < gameP.player.worldX + gameP.player.screenX &&
                worldY + gameP.tileSize > gameP.player.worldY - gameP.player.screenY &&
                worldY - gameP.tileSize < gameP.player.worldY + gameP.player.screenY    ){
            g2d.drawImage(image, screenX , screenY, gameP.tileSize, gameP.tileSize, null);
        }
    }
}
