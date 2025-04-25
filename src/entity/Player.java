package entity;

import main.GamePanel;
import main.KeyHandler;
import main.UtilityTool;
import tile.Tile;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;

public class Player extends Entity {
    public final int screenX, screenY;
    int standCounter;
    //    int doorOpened = 0;
    //    private int hasKey = 0;

    KeyHandler keyH;

    public Player(GamePanel gameP, KeyHandler keyH){
        super(gameP);

        this.screenX = gameP.screenWidth/2 - (gameP.tileSize/2);
        this.screenY = gameP.screenHeight/2 - (gameP.tileSize/2);

        solidArea.x = 6;
        solidArea.y = 16;
        solidArea.width = gameP.tileSize - 12;
        solidArea.height = gameP.tileSize - 16;
        defaultSolidAreaX = solidArea.x;
        defaultSolidAreaY = solidArea.y;

        this.gameP = gameP;
        this.keyH = keyH;

        setDefaultValue();
        getPlayerImage();
    }

    public void setDefaultValue(){

        // Posisi Awal Player
        this.worldX = gameP.tileSize * 23;
        this.worldY = gameP.tileSize * 21;
        this.speed = 4;
        this.direction = "down";
    }

    public void getPlayerImage(){
        UtilityTool uTool = new UtilityTool(gameP);

        up1 = uTool.setUp("/player/boy_up_1");
        up2 = uTool.setUp("/player/boy_up_2");
        down1 = uTool.setUp("/player/boy_down_1");
        down2 = uTool.setUp("/player/boy_down_2");
        left1 = uTool.setUp("/player/boy_left_1");
        left2 = uTool.setUp("/player/boy_left_2");
        right1 = uTool.setUp("/player/boy_right_1");
        right2 = uTool.setUp("/player/boy_right_2");
    }

    void pickUpObject(int i) {
        if (i != 999) {

        }
    }

    void interactNPC(int i) {
        if (i != 999) {
            System.out.println("You Hitted Someone");
        }
    }

    public void update(){
        int moveX = 0, moveY = 0;

        if(keyH.upPressed || keyH.downPressed || keyH.leftPressed || keyH.rightPressed){

            if (keyH.upPressed == true){
//            System.out.println("atas");
                direction = "up";
                moveY -= speed;
            }

            if (keyH.downPressed == true){
//            System.out.println("bawah");
                direction = "down";
                moveY += speed;
            }

            if (keyH.leftPressed == true){
//            System.out.println("kiri");
                direction = "left";
                moveX -= speed;
            }

            if (keyH.rightPressed == true){
//            System.out.println("kanan");
                direction = "right";
                moveX += speed;
            }

            /* Check Tile Collision */
            collisionOn = false;
            gameP.cChecker.checkTile(this);

            /* Check Object Collision */
            int objectIndex = gameP.cChecker.checkObject(this, true);
            pickUpObject(objectIndex);

            /* Check Entity Collision */
            int entityIndex = gameP.cChecker.checkEntity(this, gameP.npc);
            interactNPC(entityIndex);

            if(collisionOn == false){
//                if (direction == "up") this.worldY -= this.speed;
//                if (direction == "down") this.worldY += this.speed;
//                if (direction == "left") this.worldX -= this.speed;
//                if (direction == "right") this.worldX += this.speed;
                this.worldX += moveX;
                this.worldY += moveY;
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
        }else {
            standCounter++;
            if (standCounter > 20){
                spriteNum = 1;
                standCounter = 0;
            }
        }
    }

    public void draw(Graphics2D g2d){
//        g2d.setColor(Color.green);
//        g2d.fillRect(this.worldX, this.y, gameP.tileSize, gameP.tileSize);

        BufferedImage image = null;

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

        if (image != null){
//            System.out.println("ScreenX: " + this.screenX);
//            System.out.println("ScreenY: " + this.screenY);

            g2d.drawImage(image, this.screenX, this.screenY, null);
//            g2d.setColor(Color.red);
//            g2d.drawRect(screenX + solidArea.x, screenY + solidArea.y, solidArea.width, solidArea.height);
        }
    }

}
