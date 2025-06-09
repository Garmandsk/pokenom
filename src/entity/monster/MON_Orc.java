package entity.monster;

import entity.Entity;
import main.GamePanel;
import main.UtilityTool;
import object.pickupOnly.PU_OBJ_CoinBronze;
import object.pickupOnly.PU_OBJ_Heart;
import object.pickupOnly.PU_OBJ_ManaCrystal;

import java.util.Random;
import java.awt.Graphics2D;

public class MON_Orc extends Entity {
    public static final String monName = "Orc";

    public MON_Orc(GamePanel gameP){
        super(gameP);

        type = monsterType;
        level = 10;
        name = monName;
        elementType = thunderElement;
        defaultSpeed = 1;
        speed = defaultSpeed;
        maxLife = 10;
        life = maxLife;
        attackPower = 8;
        defensePower = 2;
        knockbackPower = 5;
        exp = 10;

        this.solidArea.x = 4;
        this.solidArea.y = 4;
        this.solidArea.width = 40;
        this.solidArea.height = 44;
        this.defaultSolidAreaX = solidArea.x;
        this.defaultSolidAreaY = solidArea.y;

        attackArea.width = 48;
        attackArea.height = 48;
        motion1Duration = 45;
        motion2Duration = 85;

        getImage();
        getMonsterAttackImage();
    }

    public void getImage(){
        up1 = uTool.setUp("/monster/orc_up_1");
        up2 = uTool.setUp("/monster/orc_up_2");
        down1 = uTool.setUp("/monster/orc_down_1");
        down2 = uTool.setUp("/monster/orc_down_2");
        left1 = uTool.setUp("/monster/orc_left_1");
        left2 = uTool.setUp("/monster/orc_left_2");
        right1 = uTool.setUp("/monster/orc_right_1");
        right2 = uTool.setUp("/monster/orc_right_2");
    }

    public void getMonsterAttackImage(){
        UtilityTool uTool = new UtilityTool(gameP);

        attackUp1 = uTool.setUp("/monster/orc_attack_up_1", gameP.tileSize, gameP.tileSize*2);
        attackUp2 = uTool.setUp("/monster/orc_attack_up_2", gameP.tileSize, gameP.tileSize*2);
        attackDown1 = uTool.setUp("/monster/orc_attack_down_1", gameP.tileSize, gameP.tileSize*2);
        attackDown2 = uTool.setUp("/monster/orc_attack_down_2", gameP.tileSize, gameP.tileSize*2);
        attackLeft1 = uTool.setUp("/monster/orc_attack_left_1", gameP.tileSize*2, gameP.tileSize);
        attackLeft2 = uTool.setUp("/monster/orc_attack_left_2", gameP.tileSize*2, gameP.tileSize);
        attackRight1 = uTool.setUp("/monster/orc_attack_right_1", gameP.tileSize*2, gameP.tileSize);
        attackRight2 = uTool.setUp("/monster/orc_attack_right_2", gameP.tileSize*2, gameP.tileSize);
    }

    public void setAction(){
        if (onPath == true){

            // Check if it stop chasing
            checkStopChasingOrNot(gameP.player, 10, 100);

            // It's destination
            searchPath(getGoalCol(gameP.player), getGoalRow(gameP.player));

        } else {
            // Check if it start chasing
            checkStartChasingOrNot(gameP.player, 10, 100);

            // Get Random direction
            getRandomDirection(90);
        }

        if (attacking == false) checkAttackOrNot(30, 4*gameP.tileSize, 1*gameP.tileSize);
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

    @Override
    public void update() {
        super.update();
        for (Entity child : children) {
            child.update();
        }
    }

    @Override
    public void draw(Graphics2D g2d) {
        super.draw(g2d);
        for (Entity child : children) {
            child.draw(g2d);
        }
    }
}
