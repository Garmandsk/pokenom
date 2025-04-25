package entity;

import main.GamePanel;

import java.awt.*;
import java.awt.image.BufferedImage;

public class Entity {
    GamePanel gameP;

    public int worldX, worldY, speed;

    public BufferedImage up1, up2, down1, down2, left1, left2, right1, right2;
    public String direction;

    public int spriteCounter = 0, spriteNum = 1;

    public Rectangle solidArea;
    public int defaultSolidAreaX, defaultSolidAreaY;
    public boolean collisionOn;

    public int actionLockCounter;

    public Entity(GamePanel gameP){
        this.gameP = gameP;
        solidArea = new Rectangle(0, 0, gameP.tileSize, gameP.tileSize);
    }

    public void setAction(){}

    public void update(){
        setAction();
        collisionOn = false;
        gameP.cChecker.checkTile(this);
        gameP.cChecker.checkObject(this, false);
        gameP.cChecker.checkPlayer(this);

        if(collisionOn == false){
            switch (direction){
                case "up":
                    this.worldY -= this.speed;
                    break;
                case "down":
                    this.worldY += this.speed;
                    break;
                case "left":
                    this.worldX -= this.speed;
                    break;
                case "right":
                    this.worldX += this.speed;
                    break;
            }
        }

        spriteCounter++;
        if (spriteCounter > 8){
            if (spriteNum == 1){
                spriteNum = 2;
            } else if (spriteNum == 2) {
                spriteNum = 1;
            }
            spriteCounter = 0;
        }
    }

    public void draw(Graphics2D g2d){
//        g2d.setColor(Color.green);
//        g2d.fillRect(this.worldX, this.y, gameP.tileSize, gameP.tileSize);

        BufferedImage image = null;
        int screenX = worldX - gameP.player.worldX + gameP.player.screenX;
        int screenY = worldY - gameP.player.worldY + gameP.player.screenY;

        /* Visible Area */
        if (    worldX + gameP.tileSize > gameP.player.worldX - gameP.player.screenX &&
                worldX - gameP.tileSize < gameP.player.worldX + gameP.player.screenX &&
                worldY + gameP.tileSize > gameP.player.worldY - gameP.player.screenY &&
                worldY - gameP.tileSize < gameP.player.worldY + gameP.player.screenY    ){

            switch (direction){
                case "up":
                    if (spriteNum == 1) image = up1;
                    if (spriteNum == 2) image = up2;
                    break;
                case "down":
                    if (spriteNum == 1) image = down1;
                    if (spriteNum == 2) image = down2;
                    break;
                case "left":
                    if (spriteNum == 1) image = left1;
                    if (spriteNum == 2) image = left2;
                    break;
                case "right":
                    if (spriteNum == 1) image = right1;
                    if (spriteNum == 2) image = right2;
                    break;
            }
            g2d.drawImage(image, screenX , screenY,null);
        }

    }
}
