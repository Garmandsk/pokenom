package entity.monster;

import entity.Entity;
import main.GamePanel;
import object.pickupOnly.PU_OBJ_CoinBronze;
import object.pickupOnly.PU_OBJ_Heart;
import object.pickupOnly.PU_OBJ_ManaCrystal;
import entity.projectile.PRO_Rock;

import java.util.Random;

public class MON_GreenSlime extends Entity {
    public static final String monName = "Green Slime";

    public MON_GreenSlime(GamePanel gameP){
        super(gameP);

        type = monsterType;
        name = monName;
        elementType = earthElement;
        level = 2;
        defaultSpeed = 1;
        speed = defaultSpeed;
        maxLife = 4;
        life = maxLife;
        attackPower = 5;
        defensePower = 0;
        exp = 3;
        projectile = new PRO_Rock(gameP);

        this.solidArea.x = 3;
        this.solidArea.y = 18;
        this.solidArea.width = 42;
        this.solidArea.height = 30;
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
        int xDistance = Math.abs(worldX - gameP.player.worldX);
        int yDistance = Math.abs(worldY - gameP.player.worldY);
        int tileDistance = (xDistance + yDistance)/gameP.tileSize;

        if (onPath == true){

            // Check if it stop chasing
            checkStopChasingOrNot(gameP.player, 10, 100);

            // It's destination
            searchPath(getGoalCol(gameP.player), getGoalRow(gameP.player));

            // Check if it shoots projectile
//            checkShotOrNot(200, 30);

        } else {
            // Check if it start chasing
            checkStartChasingOrNot(gameP.player, 10, 100);

            // Get Random direction
            getRandomDirection(120);
        }
    }

    public void damageReaction(){
        actionLockCounter = 0;
//        direction = gameP.player.direction;
        onPath = true;
    }

    public void checkDrop(){
        int i = new Random().nextInt(100)+1;

        if (i < 50) dropItem(new PU_OBJ_CoinBronze(gameP));
        if (i >= 50 && i < 75) dropItem(new PU_OBJ_Heart(gameP));
        if (i >= 75 && i < 100) dropItem(new PU_OBJ_ManaCrystal(gameP));
    }

    public void update(){
        super.update();
    }
}
