package entity;

import main.GamePanel;
import main.KeyHandler;
import main.UtilityTool;
import object.*;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

public class Player extends Entity {
    public final int screenX, screenY;
    int standCounter;
    //    int doorOpened = 0;
    //    private int hasKey = 0;
    public boolean attackCanceled;
    public ArrayList<Entity> inventory = new ArrayList<>();
    private int maxInventorySize = 0;

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

//        attackArea.width = gameP.tileSize/2 + 16;
//        attackArea.height = gameP.tileSize/2 + 16;

        this.gameP = gameP;

        setDefaultValue();
        setItems();
        getPlayerImage();
        getPlayerAttackImage();
    }

    public void setDefaultValue(){

        // Posisi Awal Player
        this.worldX = gameP.tileSize * 23;
        this.worldY = gameP.tileSize * 21;
        this.speed = 4;
        this.direction = "down";

        // Stats Player
        this.maxLife = 6;
        life = maxLife;
        maxMana = 4;
        mana = maxMana;
        ammo = 10;
        level = 1;
        exp = 0;
        nextLevelExp = 5;
        coin = 0;
        strength = 1; // Menaikkan attackPower
        dexterity = 1; // Menaikkan defensePower
        currentWeapon = new SwordNormalObject(gameP);
        currentShield = new ShieldWoodObject(gameP);
        projectile = new FireballObject(gameP);
//        projectile = new RockObject(gameP);
        attackPower = getAttack(); // Terakumulasi dari playerStrength dan weaponAttackPower
        defensePower = getDefense(); // terakumulasi dari playerDexterity dan shiekdDefensePower
    }

    public void setItems(){
        maxInventorySize = gameP.ui.inventoryMaxCol * gameP.ui.inventoryMaxRow;

        inventory.add(currentWeapon);
        inventory.add(currentShield);
        inventory.add(new KeyObject(gameP));
        inventory.add(new KeyObject(gameP));

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

    public void getPlayerAttackImage(){
        UtilityTool uTool = new UtilityTool(gameP);

        if (currentWeapon.type == swordType){
            attackUp1 = uTool.setUp("/player/boy_attack_up_1", gameP.tileSize, gameP.tileSize*2);
            attackUp2 = uTool.setUp("/player/boy_attack_up_2", gameP.tileSize, gameP.tileSize*2);
            attackDown1 = uTool.setUp("/player/boy_attack_down_1", gameP.tileSize, gameP.tileSize*2);
            attackDown2 = uTool.setUp("/player/boy_attack_down_2", gameP.tileSize, gameP.tileSize*2);
            attackLeft1 = uTool.setUp("/player/boy_attack_left_1", gameP.tileSize*2, gameP.tileSize);
            attackLeft2 = uTool.setUp("/player/boy_attack_left_2", gameP.tileSize*2, gameP.tileSize);
            attackRight1 = uTool.setUp("/player/boy_attack_right_1", gameP.tileSize*2, gameP.tileSize);
            attackRight2 = uTool.setUp("/player/boy_attack_right_2", gameP.tileSize*2, gameP.tileSize);
        } else if (currentWeapon.type == axeType){
            attackUp1 = uTool.setUp("/player/boy_axe_up_1", gameP.tileSize, gameP.tileSize*2);
            attackUp2 = uTool.setUp("/player/boy_axe_up_2", gameP.tileSize, gameP.tileSize*2);
            attackDown1 = uTool.setUp("/player/boy_axe_down_1", gameP.tileSize, gameP.tileSize*2);
            attackDown2 = uTool.setUp("/player/boy_axe_down_2", gameP.tileSize, gameP.tileSize*2);
            attackLeft1 = uTool.setUp("/player/boy_axe_left_1", gameP.tileSize*2, gameP.tileSize);
            attackLeft2 = uTool.setUp("/player/boy_axe_left_2", gameP.tileSize*2, gameP.tileSize);
            attackRight1 = uTool.setUp("/player/boy_axe_right_1", gameP.tileSize*2, gameP.tileSize);
            attackRight2 = uTool.setUp("/player/boy_axe_right_2", gameP.tileSize*2, gameP.tileSize);
        }
    }

    public int getAttack(){
        attackArea = currentWeapon.attackArea;
        return strength * currentWeapon.attackValue;
    }

    public int getDefense(){
        return dexterity * currentShield.defenseValue;
    }

    public int getMaxInventorySize(){
        return maxInventorySize;
    }

    void pickUpObject(int i) {
        if (i != 999) {

            /* Pickup Only Items */
            if (gameP.obj[i].type == pickupOnlyType){
                gameP.obj[i].use(this);
                gameP.obj[i] = null;
            }

            /* Inventory Items */
            else {
                String text;

                if (inventory.size() != maxInventorySize){
                    inventory.add(gameP.obj[i]);
                    System.out.println(i);
                    gameP.playSE(1);

                    text = "Got A " + gameP.obj[i].name + "!";
                } else {
                    text = "Your Inventory Is Full!";
                }

                gameP.ui.addMessage(text);
                gameP.obj[i] = null;
            }
        }
    }

    public void selectItem(){
        int itemIndex = gameP.ui.getItemIndexOnSlot();

        if (itemIndex < inventory.size()){
            Entity selectedItem = inventory.get(itemIndex);
//            System.out.println("Item Index: " + itemIndex);
//            System.out.println("Entity Type: " + selectedItem.type);
//            System.out.println("Entity Name: " + selectedItem.name);

            if (selectedItem.type == swordType || selectedItem.type == axeType){
                currentWeapon = selectedItem;
                attackPower = getAttack();
                getPlayerAttackImage();
            }

            if (selectedItem.type == shieldType){
                currentShield = selectedItem;
                defensePower = getDefense();
            }

            if (selectedItem.type == consumType){
//                System.out.println("tes");
                selectedItem.use(this);
                inventory.remove(itemIndex);
            }
        }
    }

    void interactNPC(int i) {
        if (gameP.keyH.enterPressed){
            if (i != 999) {
                attackCanceled = true;
                gameP.gameState = gameP.dialogueState;
                gameP.npc[i].speak();
            }
        }
    }

    void contactMonster(int i){
        if (i != 999){
            if (this.invicible == false && gameP.monster[i].dying == false){
                gameP.playSE(5);

                int damage = gameP.monster[i].attackPower - this.defensePower;
                if (damage < 0) damage = 0;

                this.life -= damage;
                if (this.life <= 0) this.life = 0;
                this.invicible = true;
            }
        }
    }

    public void damageMonster(int i, int attackPower){
        if (i != 999){
            if (gameP.monster[i].invicible == false){
                gameP.playSE(4);

                int damage = attackPower - gameP.monster[i].defensePower;
                if (damage < 0) damage = 0;

                gameP.monster[i].life -= damage;
                gameP.ui.addMessage(damage + " Damage!");

                gameP.monster[i].invicible = true;

                gameP.monster[i].damageReaction();

                if (gameP.monster[i].life <= 0) {
                    gameP.monster[i].dying = true;
                    gameP.ui.addMessage("Killed The " + gameP.monster[i].name + "!");

                    this.exp += gameP.monster[i].exp;
                    gameP.ui.addMessage("Gained " + gameP.monster[i].exp + " Exp!");

                    checkLevelUp();
                }
            }
        }
    }

    public void attacking(){
        spriteCounter++;

        if (spriteCounter <= 5) spriteNum = 1;

        if (spriteCounter > 5 && spriteCounter <= 25) {
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

            int monsterIndex = gameP.cChecker.checkEntity(this, gameP.monster);
            damageMonster(monsterIndex, this.attackPower);

            worldX = currentWorldX;
            worldY = currentWorldY;
            solidArea.width = solidAreaWidth;
            solidArea.height = solidAreaHeight;
        }

        if (spriteCounter > 25){
            spriteNum = 1;
            spriteCounter = 0;
            attacking = false;
        }
    }

    void checkLevelUp(){

        if (this.exp >= this.nextLevelExp){
            gameP.playSE(6);

            level++;
            nextLevelExp *= 2;
            maxLife += 2;
            strength++;
            dexterity++;
            attackPower = getAttack();
            defensePower = getDefense();

            gameP.gameState = gameP.dialogueState;
            gameP.ui.currentDialogue = "You Are Level " + level + "\n Well Played!";
        }
    }

    public void update(){
        int moveX = 0, moveY = 0;

        if (attacking){
            attacking();
        }

        if (this.invicible == true){
            this.invicibleCounter++;

            if (invicibleCounter >= 60){
                this.invicible = false;
                invicibleCounter = 0;
            }
        }

        if(gameP.keyH.upPressed || gameP.keyH.downPressed || gameP.keyH.leftPressed || gameP.keyH.rightPressed || gameP.keyH.enterPressed){

            if (gameP.keyH.upPressed == true){
//            System.out.println("atas");
                direction = "up";
                moveY -= speed;
            }

            if (gameP.keyH.downPressed == true){
//            System.out.println("bawah");
                direction = "down";
                moveY += speed;
            }

            if (gameP.keyH.leftPressed == true){
//            System.out.println("kiri");
                direction = "left";
                moveX -= speed;
            }

            if (gameP.keyH.rightPressed == true){
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
            int npcIndex = gameP.cChecker.checkEntity(this, gameP.npc);
            interactNPC(npcIndex);

            int monsterIndex = gameP.cChecker.checkEntity(this, gameP.monster);
            contactMonster(monsterIndex);

            /* Check Event */
            gameP.eventH.checkEvent();


            if(collisionOn == false && gameP.keyH.enterPressed == false){
//                if (direction == "up") this.worldY -= this.speed;
//                if (direction == "down") this.worldY += this.speed;
//                if (direction == "left") this.worldX -= this.speed;
//                if (direction == "right") this.worldX += this.speed;
                this.worldX += moveX;
                this.worldY += moveY;
            }

            if (gameP.keyH.enterPressed && attackCanceled == false){
                attacking = true;
                spriteCounter = 0;
            }

            attackCanceled = false;
            gameP.keyH.enterPressed = false;

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

        if (gameP.keyH.shotKeyPressed && projectile.alive == false && shotAvailableCounter == 30 && projectile.haveResources(this) == true){
            projectile.set(this .worldX, this.worldY, this.direction, true, this);
            projectile.substractResources(this);
            gameP.projectileList.add(projectile);
            gameP.playSE(8);

            shotAvailableCounter = 0;
        }

        if (shotAvailableCounter < 30) shotAvailableCounter++;

        if (this.life > this.maxLife) this.life = this.maxLife;
        if (this.mana > this.maxMana) this.mana = this.maxMana;
    }

    public void draw(Graphics2D g2d){
//        g2d.setColor(Color.green);
//        g2d.fillRect(this.worldX, this.y, gameP.tileSize, gameP.tileSize);

        BufferedImage image = null;
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

        if (image != null){
//            System.out.println("ScreenX: " + this.screenX);
//            System.out.println("ScreenY: " + this.screenY);

            if (this.invicible == true){
                g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.4f));
            }

            g2d.drawImage(image, tempScreenX, tempScreenY, null);
            g2d.setColor(Color.red);
            g2d.drawRect(screenX + solidArea.x, screenY + solidArea.y, solidArea.width, solidArea.height);
//            g2d.drawRect(screenX + interactionArea.x, screenY + interactionArea.y, interactionArea.width, interactionArea.height);

//            g2d.setFont(new Font("Arial", Font.PLAIN, 26));
//            g2d.setColor(Color.white);
//            g2d.drawString("Invicible: " + invicibleCounter, 10, 400);

            g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1f));
        }
    }

}
