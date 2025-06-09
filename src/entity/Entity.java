package entity;

import entity.projectile.Projectile;
import main.GamePanel;
import main.UtilityTool;
import particle.*;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Entity {
    protected List<Entity> children = new ArrayList<>();

    public void addChild(Entity e) {
        children.add(e);
    }

    public void removeChild(Entity e) {
        children.remove(e);
    }

    public List<Entity> getChildren() {
        return children;
    }

    public GamePanel gameP;
    public UtilityTool uTool;

    public int worldX, worldY;

    public BufferedImage up1, up2, down1, down2, left1, left2, right1, right2,
            attackUp1, attackUp2, attackDown1, attackDown2, attackLeft1, attackLeft2, attackRight1, attackRight2,
            guardUp, guardDown, guardLeft, guardRight;
    public String direction = "down";

    public int spriteCounter = 0, spriteNum = 1;

    public Rectangle solidArea;
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

    /* Particle */
    public EarthParticle earthParticle;
    public FireParticle fireParticle;
    public ThunderParticle thunderParticle;
    public WaterParticle waterParticle;
    public WallParticle wallParticle;
    /* ===== */

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
    public boolean guarding, guardingPerfect;
    public int guardCounter = 0;
    public boolean offBalance;
    public int offBalanceCounter = 0;
    public boolean transparent;
    public Entity loot;
    public boolean opened;
    public boolean inRage;
    public boolean sleep;
    public boolean drawing = true;
    public boolean canEnterBattleState = true, inBattleState = false;

    public int maxLife, life;
    public int maxMana, mana;
    public int ammo;
    public int speed, defaultSpeed, level, exp, nextLevelExp, coin;
    public int strength, dexterity, attackPower, defensePower;
    public Entity currentWeapon, currentShield, currentLight;
    public Entity attacker;
    public Entity linkedEntity;
    public Projectile projectile;
    public boolean boss;
    public boolean temp;

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
    public final int pickaxeType = 10;

    /* Element Type */
    public int elementType;
    public final int waterElement = 1;
    public final int fireElement = 2;
    public final int earthElement = 3;
    public final int thunderElement = 4;

    public Entity(GamePanel gameP) {
        this.gameP = gameP;
        uTool = new UtilityTool(this.gameP);
        solidArea = new Rectangle(0, 0, gameP.tileSize, gameP.tileSize);

        earthParticle = new EarthParticle();
        fireParticle = new FireParticle();
        thunderParticle = new ThunderParticle();
        waterParticle = new WaterParticle();
        wallParticle = new WallParticle();
    }

    public void setAction(){}
    public void setLoot(Entity loot){}
    public void setDialogue(){}
    public void damageReaction(){}
    public boolean use(Entity entity) { return false; }
    public void checkDrop(){}
    public void interact(){}
    public void speak(){}
    public void move(String direction) {}

    public int getScreenX(){
        return worldX - gameP.player.worldX + gameP.player.screenX;
    }

    public int getScreenY(){
        return worldY - gameP.player.worldY + gameP.player.screenY;
    }

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

    public int getCol(){
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

    public int getCenterX(){
        return worldX + left1.getWidth()/2;
    }

    public int getCenterY(){
        return worldY + up1.getHeight()/2;
    }

    public int getXDistance(Entity target){
        return Math.abs(getCenterX() - target.getCenterX());
    }

    public int getYDistance(Entity target){
        return Math.abs(getCenterY() - target.getCenterY());
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

    public void getRandomDirection(int interval){
        actionLockCounter++;

        Random RAND = new Random();
        if (actionLockCounter >= interval) {
            int i = RAND.nextInt(4) + 1;
            direction = switch (i) {
                case 1 -> "up";
                case 2 -> "down";
                case 3 -> "left";
                case 4 -> "right";
                default -> "";
            };
            actionLockCounter = 0;
        }
    }

    public int getDetected(Entity user, Entity[][] target, String targetName){
        int index = 999;

        int nextWorldX = user.getLeftX();
        int nextWorldY = user.getTopY();

        switch (user.direction){
            case "up": nextWorldY = user.getTopY()-user.speed; break;
            case "down": nextWorldY = user.getBottomY()+user.speed; break;
            case "left": nextWorldX = user.getLeftX()-user.speed; break;
            case "right": nextWorldX = user.getRightX()+user.speed; break;
        }

        int col = nextWorldX/gameP.tileSize;
        int row = nextWorldY/gameP.tileSize;

        for (int i = 0; i < target[1].length; i++){
            if (target[gameP.currentMap][i] != null && target[gameP.currentMap][i].getCol() == col && target[gameP.currentMap][i].getRow() == row && target[gameP.currentMap][i].name.equals(targetName)){
                index = i;
                break;
            }
        }

        return index;
    }

    public String getOppositeDirection(String direction){

        return switch (direction) {
            case "up" -> "down";
            case "down" -> "up";
            case "left" -> "right";
            case "right" -> "left";
            default -> "";
        };
    }

    public void setKnockback(Entity target, Entity attacker, int knockbackPower){
        this.attacker = attacker;
        target.knockbackDirection = attacker.direction;
        target.speed += knockbackPower;
        target.knockback = true;
    }

    public void facePlayerDirection(){
        switch (gameP.player.direction){
            case "up" -> this.direction = "down";
            case "down" -> this.direction = "up";
            case "left" -> this.direction = "right";
            case "right" -> this.direction = "left";
        }
    }

    public void moveTowardPlayer(int interval){
        actionLockCounter++;

        if (actionLockCounter >= interval){
            if (getXdistance(gameP.player) > getYdistance(gameP.player)){
                if (getCenterX() > gameP.player.getCenterX()) direction = "left";
                if (getCenterX() < gameP.player.getCenterX()) direction = "right";
            } else if (getXdistance(gameP.player) < getYdistance(gameP.player)) {
                if (getCenterY() > gameP.player.getCenterY()) direction = "up";
                if (getCenterY() < gameP.player.getCenterY()) direction = "down";
            }

            actionLockCounter = 0;
        }
    }

    public void moveDependsOnDirectionandSpeed(String directionType){
        switch (directionType) {
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
                if (gameP.player.getCenterY() < this.getCenterY() && xDistance < straight && yDistance < horizontal) targetInRange = true;
                break;
            case "down":
                if (gameP.player.getCenterY() > this.getCenterY() && xDistance < straight && yDistance < horizontal) targetInRange = true;
                break;
            case "left":
                if (gameP.player.getCenterX() < this.getCenterX() && xDistance < straight && yDistance < horizontal) targetInRange = true;
                break;
            case "right":
                if (gameP.player.getCenterX() > this.getCenterX() && xDistance < straight && yDistance < horizontal) targetInRange = true;
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
    }

    public void dropItem(Entity droppedItem){
        for (int i = 0; i < gameP.obj[1].length; i++){
            Entity targetDropItem = gameP.obj[gameP.currentMap][i];

            if (targetDropItem == null){
                targetDropItem = droppedItem;
                targetDropItem.worldX = this.worldX;
                targetDropItem.worldY = this.worldY;
                break;
            }
        }
    }

    public void startDialogue(Entity entity, int setNum){
        gameP.gameState = gameP.dialogueState;
        gameP.ui.npc = entity;
        dialogueSet = setNum;
    }

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
                case "up" -> worldY -= attackArea.height;
                case "down" -> worldY += attackArea.height;
                case "left" -> worldX -= attackArea.width;
                case "right" -> worldX += attackArea.width;
            }

            solidArea.width = attackArea.width;
            solidArea.height = attackArea.height;

            if (type == monsterType){
                if (gameP.cChecker.checkPlayer(this) == true) {
//                    System.out.println("monster Mengenai player 1"); // ketrigger hanya saat di hit orc
                    damagePlayer(attackPower);
                }
            } else {

                int monsterIndex = gameP.cChecker.checkEntity(this, gameP.monster);

                if (monsterIndex != 999) gameP.player.damageMonster(monsterIndex, this, this.attackPower, currentWeapon.knockbackPower);

                int iTileIndex = gameP.cChecker.checkEntity(this, gameP.iTile);
                gameP.player.damageInteractiveTile(iTileIndex);

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

            int damage = (int) ((attackPower - gameP.player.defensePower) * multipleDamageByElement(elementType, gameP.player.elementType));
            if (damage < 0) damage = 0;

            String canGuardDirection = getOppositeDirection(direction);
            if (gameP.player.guarding && gameP.player.direction.equals(canGuardDirection)){

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

                System.out.println("tes");

            } else {
                gameP.playSE(5);
                if (damage < 1) damage = 1;

                if (canEnterBattleState){
                    int monsterID = 999;

                    for (int mapNum = 0; mapNum < gameP.maxMap; mapNum++){
                        for (int i = 0; i < gameP.monster[1].length; i++){
                            if (gameP.monster[mapNum][i] == this)  monsterID = i;
                        }
                    }

                    damage = 0;

                    gameP.ui.enemy = this;
                    gameP.ui.monsterBattleID = monsterID;
                    gameP.changeGameState(gameP.battleState);
                    gameP.player.inBattleState = true;
                }
            }

            if (damage != 0) {
                System.out.println("tes");
                gameP.player.transparent = true;
                setKnockback(gameP.player, this, this.knockbackPower);
            }

            gameP.player.life -= damage;
            gameP.player.invicible = true;
        }
    }

    public double multipleDamageByElement(int attackerElementType, int defenderElementType){
        double multiplierDamage = 0;

        if (attackerElementType == waterElement && defenderElementType == fireElement ||
                attackerElementType == fireElement && defenderElementType == earthElement ||
                attackerElementType == earthElement && defenderElementType == thunderElement ||
                attackerElementType == thunderElement && defenderElementType == waterElement
        ) multiplierDamage = 2;
        else if (attackerElementType == earthElement && defenderElementType == fireElement ||
                attackerElementType == thunderElement && defenderElementType == earthElement ||
                attackerElementType == waterElement && defenderElementType == thunderElement ||
                attackerElementType == fireElement && defenderElementType == waterElement
        ) multiplierDamage = 0.5;
        else multiplierDamage = 1;

        System.out.println("Attacker Element: " + attackerElementType);
        System.out.println("Defender Element: " + defenderElementType);
        System.out.println("Multiplier: " + multiplierDamage);

        return multiplierDamage;
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

    public boolean inCamera() {
        boolean inCamera = worldX + gameP.tileSize * 5 > gameP.player.worldX - gameP.player.screenX &&
                worldX - gameP.tileSize < gameP.player.worldX + gameP.player.screenX &&
                worldY + gameP.tileSize * 5 > gameP.player.worldY - gameP.player.screenY &&
                worldY - gameP.tileSize < gameP.player.worldY + gameP.player.screenY;

        return inCamera;
    }

    public void update(){

        if (sleep){

        } else {
            if (knockback){
                checkCollision();

                if (collisionOn){
                    knockbackCounter = 0;
                    knockback = false;
                    speed = defaultSpeed;
                } else if (!collisionOn) {
                    moveDependsOnDirectionandSpeed(knockbackDirection);
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
                    moveDependsOnDirectionandSpeed(direction);
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

        for (Entity child : children) {
            child.update();
        }
    }

    public void draw(Graphics2D g2d){
        BufferedImage image = null;
        int screenX = getScreenX();
        int screenY = getScreenY();

        /* Visible Area */
        if (inCamera()){
            int tempScreenX = screenX;
            int tempScreenY = screenY;

            switch (direction){
                case "up":
                    if (attacking){
                        tempScreenY -= up1.getHeight();
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
                        tempScreenX -= left1.getWidth();
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

            if (this.invicible == true){
                hpBarOn = true;
                hpBarCounter = 0;
                uTool.changeAlpha(g2d, 0.4f);
            }

            if (dying){
                dyingAnimation(g2d);
            }

            g2d.drawImage(image, tempScreenX , tempScreenY,null);
            g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1f));
        }

        for (Entity child : children) {
            child.draw(g2d);
        }
    }
}
