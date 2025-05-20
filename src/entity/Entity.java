package entity;

import main.GamePanel;
import main.UtilityTool;
import particle.Generator;
import particle.Particle;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

public class Entity {
    public GamePanel gameP;
    public UtilityTool uTool;

    public int worldX, worldY;

    public BufferedImage up1, up2, down1, down2, left1, left2, right1, right2;
    public BufferedImage attackUp1, attackUp2, attackDown1, attackDown2, attackLeft1, attackLeft2, attackRight1, attackRight2;
    public String direction = "down";

    public int spriteCounter = 0, spriteNum = 1;

    public Rectangle solidArea = new Rectangle(0, 0, 48, 48);
    public Rectangle attackArea = new Rectangle(0, 0, 0, 0);
    public int defaultSolidAreaX, defaultSolidAreaY;
    public boolean collisionOn;

    public boolean collision = false;
    public String name;
    public BufferedImage image, image2, image3;

    public int actionLockCounter;

    String[] dialogue = new String[20];
    public int dialogueIndex = 0;

    public boolean hpBarOn;
    public int hpBarCounter = 0;

    /* Character Status */
    public boolean invicible;
    public int invicibleCounter = 0;
    public boolean alive = true, dying;
    int dyingCounter = 0;
    public boolean attacking;
    public int shotAvailableCounter = 0;
    public boolean onPath;
    public boolean knockback;
    public int knockbackCounter = 0;

    public int maxLife, life;
    public int maxMana, mana;
    public int ammo;
    public int speed, defaultSpeed, level, exp, nextLevelExp, coin;
    public int strength, dexterity, attackPower, defensePower;
    public Entity currentWeapon, currentShield, currentLight;
    public Projectile projectile;

    /* Item Status */
    public ArrayList<Entity> inventory = new ArrayList<>();
    public int maxInventorySize = 20;
    public int value;
    public int attackValue, defenseValue, healingValue;
    public int knockbackPower;
    public int useCost;
    public int price;
    public String itemDescription = "";
    public boolean stackable;
    public int amount = 1;
    public int lightRadius;

    /* Type */
    public int type;
    public final int playerType = 0;
    public final int npcType = 1;
    public final int monsterType = 2;
    public final int swordType = 3;
    public final int shieldType = 4;
    public final int axeType = 5;
    public final int consumType = 6;
    public final int pickupOnlyType = 7;
    public final int obstacleType = 8;
    public final int lightType = 9;

    public Entity(GamePanel gameP) {
        this.gameP = gameP;
        uTool = new UtilityTool(this.gameP);
        solidArea = new Rectangle(0, 0, gameP.tileSize, gameP.tileSize);
    }

    public void setAction(){}
    public void damageReaction(){}
    public boolean use(Entity entity) { return false; }
    public void checkDrop(){}
    public void interact(){}

    public int getTopY(){
        return worldY + solidArea.y;
    }

    public int getBottomY(){
        return worldY + solidArea.y + solidArea.height;
    }

    public int getLeftX(){
        return worldX + solidArea.x;
    }

    public int getRightX(){
        return worldX + solidArea.x + solidArea.width;
    }

    public int getCOl(){
        return (worldX + solidArea.x)/gameP.tileSize;
    }

    public int getRow(){
        return (worldY + solidArea.y)/gameP.tileSize;
    }

    public void checkCollision(){
        collisionOn = false;
        gameP.cChecker.checkTile(this);
        gameP.cChecker.checkObject(this, false);
        gameP.cChecker.checkEntity(this, gameP.npc);
        gameP.cChecker.checkEntity(this, gameP.monster);
        gameP.cChecker.checkEntity(this, gameP.iTile);

        boolean contactPlayer =  gameP.cChecker.checkPlayer(this);
        if (this.type == monsterType && contactPlayer == true){
            damagePlayer(this.attackPower);
        }
    }

    public void generateParticle(Generator generator, Entity target){
        Color color = generator.getParticleColor();
        int size = generator.getParticleSize();
        int speed = generator.getParticleSpeed();
        int maxLife = generator.getParticleMaxLife();

        Particle p1 = new Particle(gameP, generator, color, size, speed, maxLife, target.worldX, target.worldY, -2, -1);
        Particle p2 = new Particle(gameP, generator, color, size, speed, maxLife, target.worldX, target.worldY, 2, -1);
        Particle p3 = new Particle(gameP, generator, color, size, speed, maxLife, target.worldX, target.worldY, -2, 1);
        Particle p4 = new Particle(gameP, generator, color, size, speed, maxLife, target.worldX, target.worldY, 2, 1);
        gameP.particleList.add(p1);
        gameP.particleList.add(p2);
        gameP.particleList.add(p3);
        gameP.particleList.add(p4);

//        System.out.println(color);
//        System.out.println(size);
//        System.out.println(speed);
//        System.out.println(maxLife);
//
//        Particle p1 = new Particle(gameP, generator, color, size, speed, maxLife, -1, -1);
//        Particle p2 = new Particle(gameP, generator, color, size, speed, maxLife, 1, -1);
//        Particle p3 = new Particle(gameP, generator, color, size, speed, maxLife, -1, 1);
//        Particle p4 = new Particle(gameP, generator, color, size, speed, maxLife, 1, 1);


//        Particle p1 = new Particle(gameP, generator, -1, -1);
//        Particle p2 = new Particle(gameP, generator, 1, -1);
//        Particle p3 = new Particle(gameP, generator, -1, 1);
//        Particle p4 = new Particle(gameP, generator, 1, 1);
//        gameP.particleList.add(p1);
//        gameP.particleList.add(p2);
//        gameP.particleList.add(p3);
//        gameP.particleList.add(p4);

//        System.out.println("halo");
//        gameP.particleList.add(new Particle2(gameP, generator, -1, -1));
//        gameP.particleList.add(new Particle2(gameP, generator, 1, -1));
//        gameP.particleList.add(new Particle2(gameP, generator, -1, 1));
//        gameP.particleList.add(new Particle2(gameP, generator, 1, 1));

    }

    public void dropItem(Entity droppedItem){
        for (int i = 0; i < gameP.obj[1].length; i++){
            if (gameP.obj[gameP.currentMap][i] == null){
                gameP.obj[gameP.currentMap][i] = droppedItem;
                gameP.obj[gameP.currentMap][i].worldX = this.worldX;
                gameP.obj[gameP.currentMap][i].worldY = this.worldY;
                break;
            }
        }
    }

    public int getDetected(Entity user, Entity[][] target, String targetName){
        int index = 999;

        int nextWorldX = user.getLeftX();
        int nextWorldY = user.getTopY();

        switch (user.direction){
            case "up":
                nextWorldY = user.getTopY()-user.speed;
                break;
            case "down":
                nextWorldY = user.getBottomY()+user.speed;
                break;
            case "left":
                nextWorldX = user.getLeftX()-user.speed;
                break;
            case "right":
                nextWorldX = user.getRightX()+user.speed;
                break;
        }

        int col = nextWorldX/gameP.tileSize;
        int row = nextWorldY/gameP.tileSize;

        for (int i = 0; i < target[1].length; i++){
            if (target[gameP.currentMap][i] != null){
                if (target[gameP.currentMap][i].getCOl() == col && target[gameP.currentMap][i].getRow() == row && target[gameP.currentMap][i].name.equals(targetName)){
                    index = i;
                    break;
                }
            }
        }

        return index;
    }

    public void speak(){
        if (dialogue[dialogueIndex] == null) dialogueIndex = 0;
        gameP.ui.currentDialogue = dialogue[dialogueIndex];
        this.dialogueIndex++;

        switch (gameP.player.direction){
            case "up":
                this.direction = "down";
                break;
            case "down":
                this.direction = "up";
                break;
            case "left":
                this.direction = "right";
                break;
            case "right":
                this.direction = "left";
                break;
        }
    }

    public void damagePlayer(int attackPower){
        if (gameP.player.invicible == false){
            int damage = attackPower - gameP.player.defensePower;
            if (damage < 0) damage = 0;

            gameP.playSE(5);
            gameP.player.life -= damage;
            gameP.player.invicible = true;
        }
    }

    public void dyingAnimation(Graphics2D g2d){
        dyingCounter++;

        int i = 5;

        if (dyingCounter <= i) uTool.changeAlpha(g2d, 0f);
        if (dyingCounter > i && dyingCounter <= i*2) uTool.changeAlpha(g2d, 1f);
        if (dyingCounter > i*2 && dyingCounter <= i*3) uTool.changeAlpha(g2d, 0f);
        if (dyingCounter > i*3 && dyingCounter <= i*4) uTool.changeAlpha(g2d, 1f);
        if (dyingCounter > i*4 && dyingCounter <= i*5) uTool.changeAlpha(g2d, 0f);
        if (dyingCounter > i*5 && dyingCounter <= i*6) uTool.changeAlpha(g2d, 1f);
        if (dyingCounter > i*6 && dyingCounter <= i*7) uTool.changeAlpha(g2d, 0f);
        if (dyingCounter > i*7 && dyingCounter <= i*8) uTool.changeAlpha(g2d, 1f);
        if (dyingCounter > i*8){
            alive = false;
        }
    }

    /* Versi Lebih Berat */
//    public void searchPath(int goalCol, int goalRow){
//        int startCol = (worldX + solidArea.x)/gameP.tileSize;
//        int startRow = (worldY + solidArea.y)/gameP.tileSize;
//
//        gameP.pathF.setNodes(startCol, startRow, goalCol, goalRow);
//
//        if (gameP.pathF.search() == true){
//            int nextX = gameP.pathF.pathList.get(0).col * gameP.tileSize;
//            int nextY = gameP.pathF.pathList.get(0).row * gameP.tileSize;
//
//            int enLeftX = worldX + solidArea.x;
//            int enRightX = worldX + solidArea.x + solidArea.width;
//            int enTopY = worldY + solidArea.y;
//            int enBottomY = worldY + solidArea.y + solidArea.height;
//
//            if (enTopY > nextY && enLeftX >= nextX && enRightX < nextX + gameP.tileSize) direction = "up";
//            else if (enTopY < nextY && enLeftX >= nextX && enRightX < nextX + gameP.tileSize) direction = "down";
//            else if (enTopY >= nextY && enBottomY < nextY + gameP.tileSize){
//                if (enLeftX > nextX) direction = "left";
//                if (enLeftX < nextX) direction = "right";
//            }
//            else if (enTopY > nextY && enLeftX > nextX){
//                direction = "up";
//                checkCollision();
//                if (collisionOn) direction = "left";
//            }
//            else if (enTopY > nextY && enLeftX < nextX){
//                direction = "up";
//                checkCollision();
//                if (collisionOn) direction = "right";
//            }
//            else if (enTopY < nextY && enLeftX > nextX){
//                direction = "down";
//                checkCollision();
//                if (collisionOn) direction = "left";
//            }
//            else if (enTopY < nextY && enLeftX < nextX){
//                direction = "down";
//                checkCollision();
//                if (collisionOn) direction = "right";
//            }
//
//            System.out.println("Direction: " + direction);
//
////            int nextCOl = gameP.pathF.pathList.get(0).col;
////            int nextRow = gameP.pathF.pathList.get(0).row;
////            if (nextCOl == goalCol && nextRow == goalRow) onPath = false;
//        }
//    }

    /* Versi Lebih Ringan */
    public void searchPath(int goalCol, int goalRow){
        int startCol = (worldX + solidArea.x) / gameP.tileSize;
        int startRow = (worldY + solidArea.y) / gameP.tileSize;

        gameP.pathF.setNodes(startCol, startRow, goalCol, goalRow);

        if (gameP.pathF.search()){
            int nextCol = gameP.pathF.pathList.get(0).col;
            int nextRow = gameP.pathF.pathList.get(0).row;
            int nextX = nextCol * gameP.tileSize;
            int nextY = nextRow * gameP.tileSize;

            int enX = worldX + solidArea.x;
            int enY = worldY + solidArea.y;

            int dx = nextX - enX;
            int dy = nextY - enY;

            // Tentukan arah utama
            if (Math.abs(dx) > Math.abs(dy)) {
                direction = dx < 0 ? "left" : "right";
            } else {
                direction = dy < 0 ? "up" : "down";
            }

            // Cek tabrakan; jika ada, pakai arah lainnya
            checkCollision();
            if (collisionOn) {
                if (direction.equals("left") || direction.equals("right")){
                    direction = dy < 0 ? "up" : "down";
                } else {
                    direction = dx < 0 ? "left" : "right";
                }
            }

            // Jika sudah mencapai goal, hentikan pathing
//            if (nextCol == goalCol && nextRow == goalRow) onPath = false;
        }
    }

    public void update(){

        if (knockback){
            checkCollision();

            if (collisionOn){
                knockbackCounter = 0;
                knockback = false;
                speed = defaultSpeed;
            } else if (!collisionOn) {
                switch (direction) {
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

            knockbackCounter++;
            if (knockbackCounter >= 10){
                knockbackCounter = 0;
                knockback = false;
                speed = defaultSpeed;
            }

        }else {
            setAction();
            checkCollision();

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
        }

        spriteCounter++;
        if (spriteCounter > 24){
            if (spriteNum == 1){
                spriteNum = 2;
            } else if (spriteNum == 2) {
                spriteNum = 1;
            }
            spriteCounter = 0;
        }

        if (this.invicible == true){
            this.invicibleCounter++;

            if (invicibleCounter >= 40){
                this.invicible = false;
                invicibleCounter = 0;
            }
        }

        if (shotAvailableCounter < 30) shotAvailableCounter++;
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

            /* Monster Hp Bar */
            if (type == monsterType && hpBarOn){
                double oneScale = (double)gameP .tileSize/maxLife;
                double hpBarValue = oneScale * life;

                g2d.setColor(new Color(35, 35, 35));
                g2d.fillRect(screenX-1, screenY-16, gameP.tileSize+2, 12);

                g2d.setColor(new Color(255, 0, 30));
                g2d.fillRect(screenX, screenY-15, (int)hpBarValue, 10);

                hpBarCounter++;

                if (hpBarCounter >= gameP.FPS*10){
                    hpBarOn = false;
                    hpBarCounter = 0;
                }
            }

            if (this.invicible == true){
                hpBarOn = true;
                hpBarCounter = 0;
                uTool.changeAlpha(g2d, 0.4f);
            }

            if (dying){
                dyingAnimation(g2d);
            }

            g2d.drawImage(image, screenX , screenY,null);
//            g2d.setColor(Color.red);
//            g2d.drawRect(screenX + solidArea.x, screenY + solidArea.y, solidArea.width, solidArea.height);
//            g2d.drawRect(screenX + interactionArea.x, screenY + interactionArea.y, interactionArea.width, interactionArea.height);

            g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1f));
        }

    }
}
