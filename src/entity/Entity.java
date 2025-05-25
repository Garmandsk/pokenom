package entity;

import main.GamePanel;
import main.UtilityTool;
import particle.Generator;
import particle.Particle;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Random;
import java.util.UUID;

public class Entity {
    public GamePanel gameP;
    public UtilityTool uTool;

    public int worldX, worldY;

    public BufferedImage up1, up2, down1, down2, left1, left2, right1, right2,
            attackUp1, attackUp2, attackDown1, attackDown2, attackLeft1, attackLeft2, attackRight1, attackRight2,
            guardUp, guardDown, guardLeft, guardRight;
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

    public String[][] dialogues = new String[20][20];
    public int dialogueIndex = 0;
    public int dialogueSet = 0;

    public boolean hpBarOn;
    public int hpBarCounter = 0;
    public int motion1Duration, motion2Duration;

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
    public String knockbackDirection;
    public boolean guarding;
    public int guardCounter = 0;
    public boolean offBalance;
    public int offBalanceCounter = 0;
    public boolean transparent;
    public Entity loot;
    public boolean opened;

    public int maxLife, life;
    public int maxMana, mana;
    public int ammo;
    public int speed, defaultSpeed, level, exp, nextLevelExp, coin;
    public int strength, dexterity, attackPower, defensePower;
    public Entity currentWeapon, currentShield, currentLight;
    public Entity attacker;
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

    /* Element Type */
    public int elementType;
    public int waterElement = 1;
    public int fireElement = 2;
    public int earthElement = 3;
    public int thunderElement = 4;

    public Entity(GamePanel gameP) {
        this.gameP = gameP;
        uTool = new UtilityTool(this.gameP);
        solidArea = new Rectangle(0, 0, gameP.tileSize, gameP.tileSize);
    }

    public void setAction(){}
    public void setLoot(Entity loot){}
    public void setDialogue(){}
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

    public int getXdistance(Entity target){
        return Math.abs(worldX - target.worldX);
    }

    public int getYdistance(Entity target){
        return Math.abs(worldY - target.worldY);
    }

    public int getTileDistance(Entity target){
        return (getXdistance(target) + getYdistance(target))/gameP.tileSize;
    }

    public int getGoalCol(Entity target){
        return (target.worldX + target.solidArea.x)/gameP.tileSize;
    }

    public int getGoalRow(Entity target){
        return (target.worldY + target.solidArea.y)/gameP.tileSize;
    }

    public void getRandomDirection(){
        actionLockCounter++;

        Random RAND = new Random();
        if (actionLockCounter >= 120) {
            int i = RAND.nextInt(4) + 1;
            direction = switch (i) {
                case 1 -> "up";
                case 2 -> "down";
                case 3 -> "left";
                case 4 -> "right";
                default -> throw new IllegalStateException("Unexpected value: " + i);
            };
            actionLockCounter = 0;
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

    public String getOppositeDirection(String direction){
        String oppositeDirection = "";
        switch (direction){
            case "up":
                oppositeDirection = "down";
                break;
            case "down":
                oppositeDirection = "up";
                break;
            case "left":
                oppositeDirection = "right";
                break;
            case "right":
                oppositeDirection = "left";
                break;
        }

        return oppositeDirection;
    }

    public void facePlayerDirection(){
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

    public void checkStartChasingOrNot(Entity target, int distance, int rate){
        if (getTileDistance(target) < distance){
            int i = new Random().nextInt(rate);
            if (i == 0) onPath = true;
        }
    }

    public void checkStopChasingOrNot(Entity target, int distance, int rate){
        if (getTileDistance(target) > distance){
            int i = new Random().nextInt(rate);
            if (i == 0) onPath = false;
        }
    }

    public void checkShotOrNot(int rate, int shotInterval){
        int i = new Random().nextInt(rate);
        if (i == 0 && projectile.alive == false && shotAvailableCounter == shotInterval){
            projectile.set(worldX, worldY, direction, true, this);

            for (int ii = 0; ii < gameP.projectile[1].length; ii++){
                if (gameP.projectile[gameP.currentMap][ii] == null) gameP.projectile[gameP.currentMap][ii] = projectile; break;
            }
            shotAvailableCounter = 0;
        }
    }

    public void checkAttackOrNot(int rate, int straight, int horizontal){
        boolean targetInRange = false;
        int xDistance = getXdistance(gameP.player);
        int yDistance = getYdistance(gameP.player);

        switch (direction){
            case "up":
                if (gameP.player.worldY < this.worldY && xDistance < straight && yDistance < horizontal) targetInRange = true;
                break;
            case "down":
                if (gameP.player.worldY > this.worldY && xDistance < straight && yDistance < horizontal) targetInRange = true;
                break;
            case "left":
                if (gameP.player.worldX < this.worldX && xDistance < straight && yDistance < horizontal) targetInRange = true;
                break;
            case "right":
                if (gameP.player.worldX > this.worldX && xDistance < straight && yDistance < horizontal) targetInRange = true;
                break;
        }

        if (targetInRange){
            int i = new Random().nextInt(rate);
            if (i == 0) {
                attacking = true;
                spriteNum = 1;
                spriteCounter = 0;
                shotAvailableCounter = 0;
            }
        }
    }

    public void resetCounter(){
        spriteCounter = 0;
        actionLockCounter = 0;
        invicibleCounter = 0;
        shotAvailableCounter = 0;
        dyingCounter = 0;
        hpBarCounter = 0;
        knockbackCounter = 0;
        guardCounter = 0;
        offBalanceCounter = 0;
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

    public void startDialogue(Entity entity, int setNum){
        gameP.gameState = gameP.dialogueState;
        gameP.ui.npc = entity;
        dialogueSet = setNum;
    }

    public void speak(){}

    public void attacking(){
        spriteCounter++;

        if (spriteCounter <= motion1Duration) spriteNum = 1;

        if (spriteCounter > motion1Duration && spriteCounter <= motion2Duration) {
            spriteNum = 2;

            int currentWorldX = worldX;
            int currentWorldY = worldY;
            int solidAreaWidth = solidArea.width;
            int solidAreaHeight = solidArea.height;

            switch (direction){
                case "up":
                    worldY -= attackArea.height;
                    break;
                case "down":
                    worldY += attackArea.height;
                    break;
                case "left":
                    worldX -= attackArea.width;
                    break;
                case "right":
                    worldX += attackArea.width;
                    break;
            }

            solidArea.width = attackArea.width;
            solidArea.height = attackArea.height;

            if (type == monsterType){
                if (gameP.cChecker.checkPlayer(this) == true) damagePlayer(attackPower);
            } else {
                int monsterIndex = gameP.cChecker.checkEntity(this, gameP.monster);
                gameP.player.damageMonster(monsterIndex, this, this.attackPower, currentWeapon.knockbackPower);

                int iTileIndex = gameP.cChecker.checkEntity(this, gameP.iTile);
                gameP.player.damageInreractiveTile(iTileIndex);

                int projectileIndex = gameP.cChecker.checkEntity(this, gameP.projectile);
                gameP.player.damageProjectile(projectileIndex);
            }


            worldX = currentWorldX;
            worldY = currentWorldY;
            solidArea.width = solidAreaWidth;
            solidArea.height = solidAreaHeight;
        }

        if (spriteCounter > motion2Duration){
            spriteNum = 1;
            spriteCounter = 0;
            attacking = false;
        }
    }

    public void damagePlayer(int attackPower){
        if (gameP.player.invicible == false){

            int damage = attackPower - gameP.player.defensePower;
            if (damage < 0) damage = 0;

            String canGuardDirection = getOppositeDirection(direction);
            if (gameP.player.guarding && gameP.player.direction == canGuardDirection){

                // Parry
                if (gameP.player.guardCounter < 10){
                    gameP.playSE(14);
                    damage = 0;
                    setKnockback(this, gameP.player, knockbackPower);
                    offBalance = true;
                    spriteCounter =- 60;
                }

                // Blocking
                else {
                    gameP.playSE(13);
                    damage /= 3;
                }

            } else {
                gameP.playSE(5);
                if (damage < 1) damage = 1;
            }

            if (damage != 0) {
                gameP.player.transparent = true;
                setKnockback(gameP.player, this, this.knockbackPower);
            }

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

    public void setKnockback(Entity target, Entity attacker, int knockbackPower){
        this.attacker = attacker;
        target.knockbackDirection = attacker.direction;
        target.speed += knockbackPower;
        target.knockback = true;
    }

    public void update(){

        if (knockback){
            checkCollision();

            if (collisionOn){
                knockbackCounter = 0;
                knockback = false;
                speed = defaultSpeed;
            } else if (!collisionOn) {
                switch (knockbackDirection) {
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

        } else if (attacking){
            attacking();
        }
        else {
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

            spriteCounter++;
            if (spriteCounter > 24){
                if (spriteNum == 1){
                    spriteNum = 2;
                } else if (spriteNum == 2) {
                    spriteNum = 1;
                }
                spriteCounter = 0;
            }
        }

        if (shotAvailableCounter < 30) shotAvailableCounter++;

        if (offBalance){
            offBalanceCounter++;
            if (offBalanceCounter >= 90){
                offBalance = false;
                offBalanceCounter = 0;
            }
        }

        if (this.invicible == true){
            this.invicibleCounter++;

            if (invicibleCounter >= 40){
                this.invicible = false;
                invicibleCounter = 0;
            }
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

            int tempScreenX = screenX;
            int tempScreenY = screenY;

            switch (direction){
                case "up":
                    if (attacking){
                        tempScreenY -= gameP.tileSize;
                        if (spriteNum == 1) image = attackUp1;
                        if (spriteNum == 2) image = attackUp2;
                    }
                    if (!attacking){
                        if (spriteNum == 1) image = up1;
                        if (spriteNum == 2) image = up2;
                    }
                    break;
                case "down":
                    if (attacking){
                        if (spriteNum == 1) image = attackDown1;
                        if (spriteNum == 2) image = attackDown2;
                    }
                    if (!attacking){
                        if (spriteNum == 1) image = down1;
                        if (spriteNum == 2) image = down2;
                    }
                    break;
                case "left":
                    if (attacking){
                        tempScreenX -= gameP.tileSize;
                        if (spriteNum == 1) image = attackLeft1;
                        if (spriteNum == 2) image = attackLeft2;
                    }
                    if (!attacking){
                        if (spriteNum == 1) image = left1;
                        if (spriteNum == 2) image = left2;
                    }
                    break;
                case "right":
                    if (attacking){
                        if (spriteNum == 1) image = attackRight1;
                        if (spriteNum == 2) image = attackRight2;
                    }
                    if (!attacking){
                        if (spriteNum == 1) image = right1;
                        if (spriteNum == 2) image = right2;
                    }
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

            g2d.drawImage(image, tempScreenX , tempScreenY,null);
//            g2d.setColor(Color.red);
//            g2d.drawRect(screenX + solidArea.x, screenY + solidArea.y, solidArea.width, solidArea.height);
//            g2d.drawRect(screenX + interactionArea.x, screenY + interactionArea.y, interactionArea.width, interactionArea.height);

            g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1f));
        }

    }
}
