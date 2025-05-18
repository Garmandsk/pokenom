package entity;

import main.GamePanel;
import object.CoinBronzeObject;
import object.HeartObject;
import object.ManaCrystalObject;
import object.projectile.RockObject;

import java.util.Random;

public class MON_GreenSlime extends Entity {

    public MON_GreenSlime(GamePanel gameP){
        super(gameP);

        type = monsterType;
        name = "Green Slime";
        speed = 1;
        maxLife = 4;
        life = maxLife;
        attackPower = 5;
        defensePower = 0;
        exp = 3;
        projectile = new RockObject(gameP);

        this.solidArea.x = 3;
        this.solidArea.y = 18;
        this.solidArea.width = 42;
        this.solidArea.height = 30;
//        this.solidArea.width = 58;
//        this.solidArea.height = 46;
        this.defaultSolidAreaX = solidArea.x;
        this.defaultSolidAreaY = solidArea.y;

        getImage();
    }

    public void getImage(){
//        System.out.println("=====Slime=====");
//        System.out.println("Solid Area X: " + this.solidArea.x);
//        System.out.println("Solid Area Y: " + this.solidArea.y);
//        System.out.println("Solid Area Width: " + this.solidArea.width);
//        System.out.println("Solid Area Height: " + this.solidArea.height);

        up1 = uTool.setUp("/monster/greenslime_down_1");
        up2 = uTool.setUp("/monster/greenslime_down_2");
        down1 = uTool.setUp("/monster/greenslime_down_1");
        down2 = uTool.setUp("/monster/greenslime_down_2");
        left1 = uTool.setUp("/monster/greenslime_down_1");
        left2 = uTool.setUp("/monster/greenslime_down_2");
        right1 = uTool.setUp("/monster/greenslime_down_1");
        right2 = uTool.setUp("/monster/greenslime_down_2");
    }

    public void setAction(){
        /*
        if (actionLockCounter >= 120){
            Random random = new Random();
            int i = random.nextInt(4)+1;

            if (i == 1){
                direction = "up";
            } else if (i == 2) {
                direction = "down";
            } else if (i == 3) {
                direction = "left";
            } else if (i == 4) {
                direction = "right";
            }

            actionLockCounter = 0;
        }
         */

        if (onPath){
//            int goalCol = 12, goalRow = 9;
            int goalCol = (gameP.player.worldX + gameP.player.solidArea.x)/gameP.tileSize;
            int goalRow = (gameP.player.worldY + gameP.player.solidArea.y)/gameP.tileSize;

            searchPath(goalCol, goalRow);

            int i = new Random().nextInt(200)+1;
            if (i > 197 && projectile.alive == false && shotAvailableCounter == 30){
                projectile.set(worldX, worldY, direction, true, this);
                gameP.projectileList.add(projectile);
                shotAvailableCounter = 0;
            }
        }else {
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
    }

    public void damageReaction(){
        actionLockCounter = 0;
//        direction = gameP.player.direction;
        onPath = true;
    }

    public void checkDrop(){
        int i = new Random().nextInt(100)+1;

        if (i < 50) dropItem(new CoinBronzeObject(gameP));
        if (i >= 50 && i < 75) dropItem(new HeartObject(gameP));
        if (i >= 75 && i < 100) dropItem(new ManaCrystalObject(gameP));
    }

    public void update(){
        super.update();

        int xDistance = Math.abs(worldX - gameP.player.worldX);
        int yDistance = Math.abs(worldY - gameP.player.worldY);
        int tileDistance = (xDistance + yDistance)/gameP.tileSize;

        if (onPath == false && tileDistance < 5){
            int i = new Random().nextInt(100)+1;
            if (i > 50) onPath = true;
        }

//        if (onPath == true && tileDistance > 20) onPath = false;
    }
}
