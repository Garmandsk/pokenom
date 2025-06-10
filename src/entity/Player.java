package entity;

import main.GamePanel;
import object.consum.CONSUM_OBJ_PotionRed;
import object.equipment.OBJ_Lantern;
import object.equipment.OBJ_ShieldWood;
import object.equipment.OBJ_SwordNormal;
import entity.projectile.PRO_Fireball;
import tile_interactive.IT_DestructibleWall;
import tile_interactive.IT_DryTree;
import tile_interactive.InteractiveTile;

import java.awt.*;
import java.awt.image.BufferedImage;

public class Player extends Entity {
    public final int screenX, screenY;
    int standCounter;
    public boolean attackCanceled;
    public boolean lightUpdated;

    public Entity selectedItem = null;

    public Player(GamePanel gameP){
        super(gameP);
        this.gameP = gameP;

        solidArea.x = 6;
        solidArea.y = 16;
        solidArea.width = gameP.tileSize - (solidArea.x * 2);
        solidArea.height = gameP.tileSize - 16;
        defaultSolidAreaX = solidArea.x;
        defaultSolidAreaY = solidArea.y;

        this.screenX = gameP.screenWidth/2 - (gameP.tileSize/2);
        this.screenY = gameP.screenHeight/2 - (gameP.tileSize/2);

        setDefaultValue();
    }

    public void getImage(){
        up1 = uTool.setUp("/player/cowok_up_1");
        up2 = uTool.setUp("/player/cowok_up_2");
        down1 = uTool.setUp("/player/cowok_down_1");
        down2 = uTool.setUp("/player/cowok_down_2");
        left1 = uTool.setUp("/player/cowok_left_1");
        left2 = uTool.setUp("/player/cowok_left_2");
        right1 = uTool.setUp("/player/cowok_right_1");
        right2 = uTool.setUp("/player/cowok_right_2");
    }

    public void getAttackImage(){
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
        } else if (currentWeapon.type == pickaxeType){
            attackUp1 = uTool.setUp("/player/boy_pick_up_1", gameP.tileSize, gameP.tileSize*2);
            attackUp2 = uTool.setUp("/player/boy_pick_up_2", gameP.tileSize, gameP.tileSize*2);
            attackDown1 = uTool.setUp("/player/boy_pick_down_1", gameP.tileSize, gameP.tileSize*2);
            attackDown2 = uTool.setUp("/player/boy_pick_down_2", gameP.tileSize, gameP.tileSize*2);
            attackLeft1 = uTool.setUp("/player/boy_pick_left_1", gameP.tileSize*2, gameP.tileSize);
            attackLeft2 = uTool.setUp("/player/boy_pick_left_2", gameP.tileSize*2, gameP.tileSize);
            attackRight1 = uTool.setUp("/player/boy_pick_right_1", gameP.tileSize*2, gameP.tileSize);
            attackRight2 = uTool.setUp("/player/boy_pick_right_2", gameP.tileSize*2, gameP.tileSize);
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

    public void setDefaultValue(){
        name = "Player";
        defaultSpeed = 4;
        this.speed = defaultSpeed;
        this.direction = "down";

        // Stats Player
        this.maxLife = 6;
        life = 3;
        maxMana = 4;
        mana = maxMana;
        ammo = 10;
        level = 1;
        exp = 0;
        nextLevelExp = 5;
        coin = 50;
        strength = 10; // Menaikkan attackPower
        dexterity = 1; // Menaikkan defensePower

        setItems();
        setDialogue();
        getImage();
        getAttackImage();
        getGuardImage();
    }

    public void setDefaultPositions(){

        if (gameP.saveLoad.haveData()){
            gameP.currentMap = gameP.saveLoad.dataStorage.currentMap;
            this.worldX = gameP.saveLoad.dataStorage.playerWorldX;
            this.worldY = gameP.saveLoad.dataStorage.playerWorldY;

//            System.out.println("Player World X: " + this.worldX);
//            System.out.println("Player World Y: " + this.worldY);

        } else {
            // Map Outside
            this.worldX = gameP.tileSize * 23;
            this.worldY = gameP.tileSize * 21;

            // Map Dungeon 1
//        this.worldX = gameP.tileSize * 9;
//        this.worldY = gameP.tileSize * 40;

            // Map Dungeon 2
//            this.worldX = gameP.tileSize * 25;
//            this.worldY = gameP.tileSize * 10;
        }

        this.direction = "down";
    }

    public void setItems(){
        inventory.clear();

        currentWeapon = new OBJ_SwordNormal(gameP);
        currentShield = new OBJ_ShieldWood(gameP);
        currentLight = null;
        projectile = new PRO_Fireball(gameP);
        inventory.add(currentWeapon);
        inventory.add(currentShield);
//        inventory.add(new KeyObject(gameP));
        inventory.add(new OBJ_Lantern(gameP));
        inventory.add(new CONSUM_OBJ_PotionRed(gameP));

        attackPower = getAttack(); // Terakumulasi dari playerStrength dan weaponAttackPower
        defensePower = getDefense(); // terakumulasi dari playerDexterity dan shiekdDefensePower
    }

    public void setDialogue(){
        dialogues[0][0] = "Ikuzo Minna!";
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

            if (selectedItem.type == swordType || selectedItem.type == axeType || selectedItem.type == pickaxeType){
                currentWeapon = selectedItem;
                attackPower = getAttack();
                getAttackImage();

                System.out.println("tes");
                this.selectedItem = selectedItem;
            } else if (selectedItem.type == shieldType){
                currentShield = selectedItem;
                defensePower = getDefense();

                System.out.println("tes");
                this.selectedItem = selectedItem;
            } else if (selectedItem.type == lightType){
                if (currentLight == selectedItem) currentLight = null;
                else currentLight = selectedItem;

                lightUpdated = true;

                System.out.println("tes");
                this.selectedItem = selectedItem;
            } else if (selectedItem.type == consumType){
                if (selectedItem.use(this)) {
                    if (selectedItem.amount > 1) selectedItem.amount--;
                    else inventory.remove(itemIndex);

                    System.out.println("tes");
                    this.selectedItem = selectedItem;
                }
            }
        }
    }

    void interactNPC(int i) {
        if (i != 999){
            Entity targetNpc = gameP.npc[gameP.currentMap][i];

            if (gameP.keyH.enterPressed){
                gameP.ui.subState = 0;
                attackCanceled = true;
                targetNpc.speak();
            }

            targetNpc.move(this.direction);
        }
    }

    void contactMonster(int i){
        if (i != 999){
//            System.out.println("player Mengenai monster");
//            System.out.println("Masuk ke battle state dari player");
            gameP.ui.enemy = gameP.monster[gameP.currentMap][i];
            gameP.ui.monsterBattleID = i;
            gameP.changeGameState(gameP.battleState);
            inBattleState = true;

//            Entity targetMonster = gameP.monster[gameP.currentMap][i];
//            if (this.invicible == false && targetMonster.dying == false){
//                gameP.playSE(5);
//
//                int damage = targetMonster.attackPower - this.defensePower;
//                if (damage < 1) damage = 1;
//
//                this.life -= damage;
//                if (this.life <= 0) this.life = 0;
//                this.invicible = true;
//                this.transparent = true;
//            }
        }
    }

    public void damageMonster(int i, Entity attacker, int attackPower, int knockbackPower){
        if (i != 999){
            Entity targetMonster = gameP.monster[gameP.currentMap][i];

            System.out.println("Attack Power Dari Player: " + attackPower);
            if (targetMonster.invicible == false){
                gameP.playSE(4);

                if (knockbackPower > 0) setKnockback(targetMonster, attacker, knockbackPower);

                if (targetMonster.offBalance) attackPower *= 5;

                int damage = (int) ((attackPower - targetMonster.defensePower) * multipleDamageByElement(this.currentWeapon.elementType, targetMonster.elementType));
                if (damage < 0) damage = 0;

                System.out.println("Damage Hit Biasa: " + damage + "\n");

                targetMonster.life -= damage;
                gameP.ui.addMessage(damage + " Damage!");

                targetMonster.invicible = true;

                targetMonster.damageReaction();

                if (targetMonster.life <= 0) {
                    targetMonster.dying = true;
                    gameP.ui.addMessage("Killed The " + targetMonster.name + "!");

                    this.exp += targetMonster.exp;
                    gameP.ui.addMessage("Gained " + targetMonster.exp + " Exp!");

                    checkLevelUp();
                }
            }
        }
    }

    public void damageMonsterByProjectile(int i, Entity attacker, int attackPower, int knockbackPower){
        if (i != 999){
            Entity targetMonster = gameP.monster[gameP.currentMap][i];

            System.out.println("Attack Power Dari Projectile Player: " + attackPower);
            if (targetMonster.invicible == false){
                gameP.playSE(4);

                if (knockbackPower > 0) setKnockback(targetMonster, attacker, knockbackPower);

                if (targetMonster.offBalance) attackPower *= 5;

                int damage = (int) ((attackPower - targetMonster.defensePower) * multipleDamageByElement(this.projectile.elementType, targetMonster.elementType));
                if (damage < 0) damage = 0;

                System.out.println("Damage Projectile Biasa: " + damage + "\n");

                targetMonster.life -= damage;
                gameP.ui.addMessage(damage + " Damage!");

                targetMonster.invicible = true;

                targetMonster.damageReaction();

                if (targetMonster.life <= 0) {
                    targetMonster.dying = true;
                    gameP.ui.addMessage("Killed The " + targetMonster.name + "!");

                    this.exp += targetMonster.exp;
                    gameP.ui.addMessage("Gained " + targetMonster.exp + " Exp!");

                    checkLevelUp();
                }
            }
        }
    }

    public void damageInteractiveTile(int i){
        if (i != 999){
            InteractiveTile targetITile = gameP.iTile[gameP.currentMap][i];

            if (targetITile.destructible && targetITile.isCorrectItem(this) && targetITile.invicible == false){
                targetITile.playSE();
                targetITile.life--;
                targetITile.invicible = true;

                if (targetITile.name.equals(IT_DryTree.iTileName)) generateParticle(earthParticle, targetITile);
                else if (targetITile.name.equals(IT_DestructibleWall.iTileName)) generateParticle(wallParticle, targetITile);

                if (targetITile.life <= 0) {
                    targetITile.checkDrop();
                    gameP.iTile[gameP.currentMap][i] = targetITile.getDestroyedForm();
                }
            }
        }
    }

    public void damageProjectile(int i){
        if (i != 999){
            Entity projectile = gameP.projectile[gameP.currentMap][i];
            projectile.alive = false;

//            Entity objekDefault = gameP.projectile[gameP.currentMap][i];
//            Class<?> kelasObjekDefault = objekDefault.getClass();
//            Field[] fieldsDefault = kelasObjekDefault.getDeclaredFields();
//
//            for (Field field : fieldsDefault) {
//                try {
//                    field.setAccessible(true);
//                    String namaField = field.getName();
//                    Object nilaiField = field.get(objekDefault);
//                    System.out.println("Nama Field: " + namaField + ", Tipe: " + field.getType().getSimpleName() + ", Nilai: " + nilaiField);
//                } catch (IllegalAccessException e) {
//                    e.printStackTrace();
//                }
//            }
//
//            System.out.println("Projectile Element: " + projectile.elementType);
//            if (projectile.name.equals(FireballObject.projectileName)) generateParticle(fireParticle, projectile);
            if (projectile.elementType == waterElement) generateParticle(waterParticle, projectile);
            else if (projectile.elementType == fireElement) generateParticle(fireParticle, projectile);
            else if (projectile.elementType == earthElement) generateParticle(earthParticle, projectile);
            else if (projectile.elementType == thunderElement) generateParticle(thunderParticle, projectile);
        }
    }

    public void checkLevelUp(){

        if (this.exp >= this.nextLevelExp){
            gameP.playSE(6);

            level++;
            nextLevelExp *= 2;
            maxLife += 2;
            life = maxLife;
            maxMana++;
            mana = maxMana;
            strength++;
            dexterity++;
            attackPower = getAttack();
            defensePower = getDefense();

            gameP.gameState = gameP.dialogueState;

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
            if (gameP.keyH.godMode == false){
                gameP.cChecker.checkTile(this);
            }

            /* Check Object Collision */
            int objectIndex = gameP.cChecker.checkObject(this, true);
            pickUpObject(objectIndex);

            /* Check Entity Collision */
            int npcIndex = gameP.cChecker.checkEntity(this, gameP.npc);
            interactNPC(npcIndex);

            /* Check Monster Collision */
            int monsterIndex = gameP.cChecker.checkEntity(this, gameP.monster);
            contactMonster(monsterIndex);

            /* Check Interactive Tile Collision */
            gameP.cChecker.checkEntity(this, gameP.iTile);

            /* Check Event */
            gameP.eventH.checkEvent();

            if(collisionOn == false && gameP.keyH.enterPressed == false){
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
            projectile.set(this.worldX, this.worldY, this.direction, true, this);
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

        if (this.invicible == true){
            this.invicibleCounter++;

            if (invicibleCounter >= 60){
                this.invicible = false;
                this.transparent = false;
                invicibleCounter = 0;
            }
        }

        if (gameP.keyH.godMode == false){
            if(life <= 0) {
                gameP.stopMusic();
                gameP.playSE(10);
                gameP.gameState = gameP.gameOverState;
                gameP.ui.commandNum = -1;
            }
        }

        if (gameP.keyH.escPressed) gameP.ui.subState = 0;

        // Panggil update pada children (composite pattern)
        for (Entity child : children) {
            child.update();
        }
    }

    public void draw(Graphics2D g2d){
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

        if (image != null){;
            if (this.transparent) g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.4f));
            if (drawing) g2d.drawImage(image, tempScreenX, tempScreenY, null);

            g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1f));
        }

        // Panggil draw pada children (composite pattern)
        for (Entity child : children) {
            child.draw(g2d);
        }
    }

}
