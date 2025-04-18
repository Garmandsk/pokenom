package entity;

import main.GamePanel;
import main.KeyHandler;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;

public class Player extends Entity {
    private int hasKey = 0;

    public final int screenX, screenY;

    GamePanel gameP;
    KeyHandler keyH;

    int doorOpened = 0;

    public Player(GamePanel gameP, KeyHandler keyH){

        this.screenX = gameP.screenWidth/2 - (gameP.tileSize/2);
        this.screenY = gameP.screenHeight/2 - (gameP.tileSize/2);

        solidArea = new Rectangle(8, 16, gameP.tileSize - 16, gameP.tileSize - 20);
        defaultSolidAreaX = solidArea.x;
        defaultSolidAreaY = solidArea.y;

        this.gameP = gameP;
        this.keyH = keyH;

        setDefaultValue();
        getPlayerImage();
    }

    public void setDefaultValue(){
        this.worldX = gameP.tileSize * 25;
        this.worldY = gameP.tileSize * 25;
        this.speed = 5;
        this.direction = "down";
    }

    public void getPlayerImage(){
        try {
            up1 = ImageIO.read(getClass().getResourceAsStream("/player/boy_up_1.png"));
            up2 = ImageIO.read(getClass().getResourceAsStream("/player/boy_up_2.png"));
            down1 = ImageIO.read(getClass().getResourceAsStream("/player/boy_down_1.png"));
            down2 = ImageIO.read(getClass().getResourceAsStream("/player/boy_down_2.png"));
            left1 = ImageIO.read(getClass().getResourceAsStream("/player/boy_left_1.png"));
            left2 = ImageIO.read(getClass().getResourceAsStream("/player/boy_left_2.png"));
            right1 = ImageIO.read(getClass().getResourceAsStream("/player/boy_right_1.png"));
            right2 = ImageIO.read(getClass().getResourceAsStream("/player/boy_right_2.png"));
        }catch (IOException e){
            e.printStackTrace();
        }
    }

    void pickUpObject(int indexObject){
        if (indexObject != 999){
            String nameObject = gameP.obj[indexObject].nameObject;

            switch (nameObject){
                case "Key":
//                    System.out.printf("Key: %d\n", hasKey);
                    hasKey++;
                    gameP.obj[indexObject] = null;
                    gameP.ui.showMessage("You Got A Key!");
                    break;
                case "Door":
                    if (hasKey > 0){
//                        System.out.printf("Key: %d\n", hasKey);
                        gameP.obj[indexObject] = null;
                        hasKey--;
                        doorOpened++;
//                        System.out.printf("Door Opened: %d\n", this.doorOpened);
                        if (doorOpened == 4){
                            gameP.ui.gameFinished = true;
                        }else{
                            gameP.ui.showMessage("You Opened The Door!");
                        }
                    }else {
                        gameP.ui.showMessage("Key Needed!");
                    }
                    break;
                case "Boots":
//                    System.out.println("Speed Up!");
                    this.speed += 2;
                    gameP.obj[indexObject] = null;
                    gameP.ui.showMessage("You Got Sepatu Superrr!");
                    break;
                case "Chest":
                    gameP.ui.showMessage("You Found Mysterious Chest!");
//                    gameP.ui.gameFinished = true;
                    break;
            }
        }
    }

    public int getHasKey(){
        return this.hasKey;
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
            int indexObject = gameP.cChecker.checkObject(this, true);
            pickUpObject(indexObject);

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
            g2d.drawImage(image, this.screenX, this.screenY, gameP.tileSize, gameP.tileSize, null);

        }
    }

}
