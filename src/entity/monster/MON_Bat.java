package entity.monster;

import entity.Entity;
import main.GamePanel;
import object.pickupOnly.CoinBronzeObject;
import object.pickupOnly.HeartObject;
import object.pickupOnly.ManaCrystalObject;

import java.util.Random;

public class MON_Bat extends Entity {
    public static final String monName = "Bat";

    public MON_Bat(GamePanel gameP){
        super(gameP);

        type = monsterType;
        name = monName;
        defaultSpeed = 4;
        speed = defaultSpeed;
        maxLife = 7;
        life = maxLife;
        attackPower = 7;
        defensePower = 0;
        exp = 7;

        this.solidArea.x = 3;
        this.solidArea.y = 15;
        this.solidArea.width = 42;
        this.solidArea.height = 21;
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

        up1 = uTool.setUp("/monster/bat_down_1");
        up2 = uTool.setUp("/monster/bat_down_2");
        down1 = uTool.setUp("/monster/bat_down_1");
        down2 = uTool.setUp("/monster/bat_down_2");
        left1 = uTool.setUp("/monster/bat_down_1");
        left2 = uTool.setUp("/monster/bat_down_2");
        right1 = uTool.setUp("/monster/bat_down_1");
        right2 = uTool.setUp("/monster/bat_down_2");
    }

    public void setAction(){
        int xDistance = Math.abs(worldX - gameP.player.worldX);
        int yDistance = Math.abs(worldY - gameP.player.worldY);
        int tileDistance = (xDistance + yDistance)/gameP.tileSize;

        if (onPath == true){

            // Check if it stop chasing
//            checkStopChasingOrNot(gameP.player, 20, 100);

            // It's destination
//            searchPath(getGoalCol(gameP.player), getGoalRow(gameP.player));

            // Check if it shoots projectile
//            checkShotOrNot(200, 30);

        } else {
            // Check if it start chasing
//            checkStartChasingOrNot(gameP.player, 5, 100);

            // Get Random direction
            getRandomDirection(10);
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
    }
}
