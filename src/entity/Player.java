package entity;

import main.EntityGenerator;
import main.GamePanel;
import main.KeyHandler;
import main.UtilityTool;
import object.*;
import object.projectile.FireballObject;
import particle.EarthParticle;

import java.awt.*;
import java.awt.image.BufferedImage;

public class Player extends Entity {
    public final int screenX, screenY;
    int standCounter;
    //    int doorOpened = 0;
    //    private int hasKey = 0;
    public boolean attackCanceled;
    public boolean lightUpdated;

    public Player(GamePanel gameP, KeyHandler keyH){
        super(gameP);

        this.screenX = gameP.screenWidth/2 - (gameP.tileSize/2);
        this.screenY = gameP.screenHeight/2 - (gameP.tileSize/2);

        solidArea.x = 6;
        solidArea.y = 16;
        solidArea.width = gameP.tileSize - (solidArea.x * 2);
        solidArea.height = gameP.tileSize - 16;
        defaultSolidAreaX = solidArea.x;
        defaultSolidAreaY = solidArea.y;

//        attackArea.width = gameP.tileSize/2 + 16;
//        attackArea.height = gameP.tileSize/2 + 16;

        this.gameP = gameP;

        setDefaultValue();
    }

    public void setDefaultValue(){

        // Posisi Awal Player
        this.worldX = gameP.tileSize * 23;
        this.worldY = gameP.tileSize * 21;

//        this.worldX = gameP.tileSize * 12;
//        this.worldY = gameP.tileSize * 13;
        defaultSpeed = 4;
        this.speed = defaultSpeed;
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
        coin = 50;
        strength = 1; // Menaikkan attackPower
        dexterity = 1; // Menaikkan defensePower
        currentWeapon = new SwordNormalObject(gameP);
//        currentWeapon = new AxeObject(gameP);
        currentShield = new ShieldWoodObject(gameP);
        currentLight = null;
        projectile = new FireballObject(gameP);
//        projectile = new RockObject(gameP);
        attackPower = getAttack(); // Terakumulasi dari playerStrength dan weaponAttackPower
        defensePower = getDefense(); // terakumulasi dari playerDexterity dan shiekdDefensePower

        setItems();
        getImage();
        setDialogue();
        getAttackImage();
        getGuardImage();
    }

    public void setDefaultPositions(){
        this.worldX = gameP.tileSize * 23;
        this.worldY = gameP.tileSize * 21;
        this.direction = "down";
    }

    public void setDialogue(){
        dialogues[0][0] = "Ikuzo Minnaa!";
        startDialogue(this, 0);
        dialogues[1][0] = "You Are Level " + level + "\n Well Played!";

    }

    public void restoreStatus(){
        life = maxLife;
        mana = maxMana;
        speed = defaultSpeed;
        invicible = false;
        transparent = false;
        attacking = false;
        guarding = false;
        knockback = false;
        lightUpdated = true;
    }

    public void setItems(){
        inventory.clear();
        inventory.add(currentWeapon);
        inventory.add(currentShield);
        inventory.add(new KeyObject(gameP));

    }

    public void getImage(){
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

    public void getAttackImage(){
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

    public void getSleepingImage(BufferedImage image){
        up1 = image;
        up2 = image;
        down1 = image;
        down2 = image;
        left1 = image;
        left2 = image;
        right1 = image;
        right2 = image;
    }

    public void getGuardImage(){
        UtilityTool uTool = new UtilityTool(gameP);

        guardUp = uTool.setUp("/player/boy_guard_up");
        guardDown = uTool.setUp("/player/boy_guard_down");
        guardLeft = uTool.setUp("/player/boy_guard_left");
        guardRight = uTool.setUp("/player/boy_guard_right");
    }

    public int getAttack(){
        attackArea = currentWeapon.attackArea;
        this.motion1Duration = currentWeapon.motion1Duration;
        this.motion2Duration = currentWeapon.motion2Duration;
        return strength * currentWeapon.attackValue;
    }

    public int getDefense(){
        return dexterity * currentShield.defenseValue;
    }

    public int getCurrentWeaponSlot(){
        int currentWeaponSlot = 0;
        for (int i = 0; i < inventory.size(); i++){
           if (inventory.get(i) == currentWeapon) currentWeaponSlot = i;
        }

        return currentWeaponSlot;
    }

    public int getCurrentShieldSlot(){
        int currentShieldSlot = 0;
        for (int i = 0; i < inventory.size(); i++){
            if (inventory.get(i) == currentShield) currentShieldSlot = i;
        }

        return currentShieldSlot;
    }

    void pickUpObject(int i) {
        if (i != 999) {

            /* Pickup Only Items */
            if (gameP.obj[gameP.currentMap][i].type == pickupOnlyType){
                gameP.obj[gameP.currentMap][i].use(this);
                gameP.obj[gameP.currentMap][i] = null;
            }

            /* Obstacle Items */
            else if (gameP.obj[gameP.currentMap][i].type == obstacleType) {
                if (gameP.keyH.enterPressed) {
                    attackCanceled = true;
                    gameP.obj[gameP.currentMap][i].interact();
                }
            }

            /* Inventory Items */
            else {
                String text;

                if (canObtainItem(gameP.obj[gameP.currentMap][i]) == true){
                    gameP.playSE(1);
                    text = "Got A " + gameP.obj[gameP.currentMap][i].name + "!";
                } else text = "Your Inventory Is Full!";

                gameP.ui.addMessage(text);
                gameP.obj[gameP.currentMap][i] = null;
            }
        }
    }

    public void selectItem() {
        int itemIndex = gameP.ui.getItemIndexOnSlot(gameP.ui.playerSlotCol, gameP.ui.playerSlotRow);

        if (itemIndex < inventory.size()){
            Entity selectedItem = inventory.get(itemIndex);

            if (selectedItem.type == swordType || selectedItem.type == axeType){
                currentWeapon = selectedItem;
                attackPower = getAttack();
                getAttackImage();
            }

            if (selectedItem.type == shieldType){
                currentShield = selectedItem;
                defensePower = getDefense();
            }

            if (selectedItem.type == lightType){
                if (currentLight == selectedItem) currentLight = null;
                else currentLight = selectedItem;

                lightUpdated = true;
            }

            if (selectedItem.type == consumType){
                if (selectedItem.use(this)) {
                    if (selectedItem.amount > 1) selectedItem.amount--;
                    else inventory.remove(itemIndex);
                }
            }
        }
    }

    void interactNPC(int i) {
        if (i != 999){
            if (gameP.keyH.enterPressed){
                attackCanceled = true;
                gameP.npc[gameP.currentMap][i].speak();
            }
        }
    }

    void contactMonster(int i){
        if (i != 999){
            if (this.invicible == false && gameP.monster[gameP.currentMap][i].dying == false){
                gameP.playSE(5);

                int damage = gameP.monster[gameP.currentMap][i].attackPower - this.defensePower;
                if (damage < 1) damage = 1;

                this.life -= damage;
                if (this.life <= 0) this.life = 0;
                this.invicible = true;
                this.transparent = true;
            }
        }
    }

    public void damageMonster(int i, Entity attacker, int attackPower, int knockbackPower){
        if (i != 999){
            if (gameP.monster[gameP.currentMap][i].invicible == false){
                gameP.playSE(4);

                if (knockbackPower > 0) setKnockback(gameP.monster[gameP.currentMap][i], attacker, knockbackPower);

                if (gameP.monster[gameP.currentMap][i].offBalance) attackPower *= 5;

                int damage = attackPower - gameP.monster[gameP.currentMap][i].defensePower;
                if (damage < 0) damage = 0;

                gameP.monster[gameP.currentMap][i].life -= damage;
                gameP.ui.addMessage(damage + " Damage!");

                gameP.monster[gameP.currentMap][i].invicible = true;

                gameP.monster[gameP.currentMap][i].damageReaction();

                if (gameP.monster[gameP.currentMap][i].life <= 0) {
                    gameP.monster[gameP.currentMap][i].dying = true;
                    gameP.ui.addMessage("Killed The " + gameP.monster[gameP.currentMap][i].name + "!");

                    this.exp += gameP.monster[gameP.currentMap][i].exp;
                    gameP.ui.addMessage("Gained " + gameP.monster[gameP.currentMap][i].exp + " Exp!");

                    checkLevelUp();
                }
            }
        }
    }

    public void damageInreractiveTile(int i){
        if (i != 999 && gameP.iTile[gameP.currentMap][i].destructible == true && gameP.iTile[gameP.currentMap][i].isCorrectItem(this) == true && gameP.iTile[gameP.currentMap][i].invicible == false){
            gameP.iTile[gameP.currentMap][i].playSE();
            gameP.iTile[gameP.currentMap][i].life--;
            gameP.iTile[gameP.currentMap][i].invicible = true;

            generateParticle(new EarthParticle(), gameP.iTile[gameP.currentMap][i]);

            if (gameP.iTile[gameP.currentMap][i].life <= 0) gameP.iTile[gameP.currentMap][i] = gameP.iTile[gameP.currentMap][i].getDestroyedForm();
        }
    }

    public void damageProjectile(int i){
        if (i != 999){
            Entity projectile = gameP.projectile[gameP.currentMap][i];
            projectile.alive = false;
            generateParticle(new EarthParticle(), projectile);
        }
    }

    public void checkLevelUp(){

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

//            dialogues[1][0] = "You Are Level " + level + "\n Well Played!";
            setDialogue();
            startDialogue(this, 1);
        }
    }

    public int searchItemInInventory(String itemName){
        int itemIndex = 999;

        for (int i = 0; i < inventory.size(); i++){
            if (inventory.get(i).name.equals((itemName))){
                itemIndex = i;
                break;
            }
        }

        return itemIndex;
    }

    public boolean canObtainItem(Entity item){
        boolean canObtain = false;
        Entity newItem = gameP.entGen.getObject(item.name);

        if (newItem.stackable){
            int index = searchItemInInventory(newItem.name);

            if (index != 999) {
                inventory.get(index).amount++;
                canObtain = true;
            } else {
                if (inventory.size() != maxInventorySize){
                    inventory.add(newItem);
                    canObtain = true;
                }
            }
        } else {
            if (inventory.size() != maxInventorySize){
                inventory.add(newItem);
                canObtain = true;
            }
        }

        return canObtain;
    }

    public void update(){
        double moveX = 0, moveY = 0;

        if (knockback){

            collisionOn = false;
            gameP.cChecker.checkTile(this);
            gameP.cChecker.checkObject(this, true);
            gameP.cChecker.checkEntity(this, gameP.npc);
            gameP.cChecker.checkEntity(this, gameP.monster);
            gameP.cChecker.checkEntity(this, gameP.iTile);

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
        } else if (gameP.keyH.spacePressed) {
            guarding = true;
            guardCounter++;
        } else if(gameP.keyH.upPressed || gameP.keyH.downPressed || gameP.keyH.leftPressed || gameP.keyH.rightPressed || gameP.keyH.enterPressed){

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

            if (moveX != 0 && moveY != 0) {
                double length = Math.sqrt(moveX * moveX + moveY * moveY);
                moveX = moveX / length * speed;
                moveY = moveY / length * speed;
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

            int iTileIndex = gameP.cChecker.checkEntity(this, gameP.iTile);


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
            guarding = false;
            guardCounter = 0;

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
            guarding = false;
            guardCounter = 0;
        }

        if (gameP.keyH.shotKeyPressed && projectile.alive == false && shotAvailableCounter == 30 && projectile.haveResources(this) == true){
            projectile.set(this .worldX, this.worldY, this.direction, true, this);
            projectile.substractResources(this);

            for (int i = 0; i < gameP.projectile[1].length; i++){
                if (gameP.projectile[gameP.currentMap][i] == null) gameP.projectile[gameP.currentMap][i] = projectile; break;
            }
            gameP.playSE(8);

            shotAvailableCounter = 0;
        }

        if (shotAvailableCounter < 30) shotAvailableCounter++;

        if (this.life > this.maxLife) this.life = this.maxLife;
        if (this.mana > this.maxMana) this.mana = this.maxMana;

        if(life <= 0) {
            gameP.stopMusic();
            gameP.playSE(10);
            gameP.gameState = gameP.gameOverState;
            gameP.ui.commandNum = -1;
        }

        if (this.invicible == true){
            this.invicibleCounter++;

            if (invicibleCounter >= 60){
                this.invicible = false;
                this.transparent = false;
                invicibleCounter = 0;
            }
        }
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
                if (guarding) image = guardUp;
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
                if (guarding) image = guardDown;
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
                if (guarding) image = guardLeft;
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
                if (guarding) image = guardRight;
                break;
        }

        if (image != null){
//            System.out.println("ScreenX: " + this.screenX);
//            System.out.println("ScreenY: " + this.screenY);

            if (this.transparent == true){
                g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.4f));
            }

            g2d.drawImage(image, tempScreenX, tempScreenY, null);

            g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1f));
        }
    }

}
