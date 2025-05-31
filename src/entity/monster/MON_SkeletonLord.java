package entity.monster;

import data.Progress;
import entity.Entity;
import main.GamePanel;
import object.pickupOnly.CoinBronzeObject;
import object.obstacle.DoorIronObject;
import object.pickupOnly.HeartObject;
import object.pickupOnly.ManaCrystalObject;

import java.util.Random;

public class MON_SkeletonLord extends Entity {
    public static final String monName = "Skeleton Lord";
    public static final int entityScaleSize = 5;

    public MON_SkeletonLord(GamePanel gameP){
        super(gameP);

        type = monsterType;
        boss = true;
        name = monName;
        defaultSpeed = 1;
        speed = defaultSpeed;
        maxLife = 50;
        life = maxLife;
        attackPower = 10;
        defensePower = 2;
        knockbackPower = 5;
        exp = 50;
        sleep = true;

        int size = gameP.tileSize * entityScaleSize;
        this.solidArea.x = gameP.tileSize;
        this.solidArea.y = gameP.tileSize;
        this.solidArea.width = size - gameP.tileSize*2;
        this.solidArea.height = size - gameP.tileSize;
        this.defaultSolidAreaX = solidArea.x;
        this.defaultSolidAreaY = solidArea.y;

        attackArea.width = gameP.tileSize*3;
        attackArea.height = gameP.tileSize*3;
        motion1Duration = 25;
        motion2Duration = 50;

        getImage();
        getMonsterAttackImage();
        setDialogue();
    }

    public void getImage(){
        if (inRage){
            up1 = uTool.setUp("/monster/skeletonlord_phase2_up_1", true);
            up2 = uTool.setUp("/monster/skeletonlord_phase2_up_2", true);
            down1 = uTool.setUp("/monster/skeletonlord_phase2_down_1", true);
            down2 = uTool.setUp("/monster/skeletonlord_phase2_down_2", true);
            left1 = uTool.setUp("/monster/skeletonlord_phase2_left_1", true);
            left2 = uTool.setUp("/monster/skeletonlord_phase2_left_2", true);
            right1 = uTool.setUp("/monster/skeletonlord_phase2_right_1", true);
            right2 = uTool.setUp("/monster/skeletonlord_phase2_right_2", true);
        } else {
            up1 = uTool.setUp("/monster/skeletonlord_up_1", true);
            up2 = uTool.setUp("/monster/skeletonlord_up_2", true);
            down1 = uTool.setUp("/monster/skeletonlord_down_1", true);
            down2 = uTool.setUp("/monster/skeletonlord_down_2", true);
            left1 = uTool.setUp("/monster/skeletonlord_left_1", true);
            left2 = uTool.setUp("/monster/skeletonlord_left_2", true);
            right1 = uTool.setUp("/monster/skeletonlord_right_1", true);
            right2 = uTool.setUp("/monster/skeletonlord_right_2", true);
        }
    }

    public void getMonsterAttackImage(){
        if (inRage){
            attackUp1 = uTool.setUp("/monster/skeletonlord_phase2_attack_up_1", gameP.tileSize*entityScaleSize, gameP.tileSize*entityScaleSize*2);
            attackUp2 = uTool.setUp("/monster/skeletonlord_phase2_attack_up_2", gameP.tileSize*entityScaleSize, gameP.tileSize*entityScaleSize*2);
            attackDown1 = uTool.setUp("/monster/skeletonlord_phase2_attack_down_1", gameP.tileSize*entityScaleSize, gameP.tileSize*entityScaleSize*2);
            attackDown2 = uTool.setUp("/monster/skeletonlord_phase2_attack_down_2", gameP.tileSize*entityScaleSize, gameP.tileSize*entityScaleSize*2);
            attackLeft1 = uTool.setUp("/monster/skeletonlord_phase2_attack_left_1", gameP.tileSize*entityScaleSize*2, gameP.tileSize*entityScaleSize);
            attackLeft2 = uTool.setUp("/monster/skeletonlord_phase2_attack_left_2", gameP.tileSize*entityScaleSize*2, gameP.tileSize*entityScaleSize);
            attackRight1 = uTool.setUp("/monster/skeletonlord_phase2_attack_right_1", gameP.tileSize*entityScaleSize*2, gameP.tileSize*entityScaleSize);
            attackRight2 = uTool.setUp("/monster/skeletonlord_phase2_attack_right_2", gameP.tileSize*entityScaleSize*2, gameP.tileSize*entityScaleSize);
        } else {
            attackUp1 = uTool.setUp("/monster/skeletonlord_attack_up_1", gameP.tileSize*entityScaleSize, gameP.tileSize*entityScaleSize*2);
            attackUp2 = uTool.setUp("/monster/skeletonlord_attack_up_2", gameP.tileSize*entityScaleSize, gameP.tileSize*entityScaleSize*2);
            attackDown1 = uTool.setUp("/monster/skeletonlord_attack_down_1", gameP.tileSize*entityScaleSize, gameP.tileSize*entityScaleSize*2);
            attackDown2 = uTool.setUp("/monster/skeletonlord_attack_down_2", gameP.tileSize*entityScaleSize, gameP.tileSize*entityScaleSize*2);
            attackLeft1 = uTool.setUp("/monster/skeletonlord_attack_left_1", gameP.tileSize*entityScaleSize*2, gameP.tileSize*entityScaleSize);
            attackLeft2 = uTool.setUp("/monster/skeletonlord_attack_left_2", gameP.tileSize*entityScaleSize*2, gameP.tileSize*entityScaleSize);
            attackRight1 = uTool.setUp("/monster/skeletonlord_attack_right_1", gameP.tileSize*entityScaleSize*2, gameP.tileSize*entityScaleSize);
            attackRight2 = uTool.setUp("/monster/skeletonlord_attack_right_2", gameP.tileSize*entityScaleSize*2, gameP.tileSize*entityScaleSize);
        }
    }

    public void setAction(){
        if (inRage == false && life < maxLife/2){
            inRage = true;
            getImage();
            getMonsterAttackImage();
            defaultSpeed++;
            speed = defaultSpeed;
            attackPower *= 2;
        }
        if (getTileDistance(gameP.player) < 10){
            moveTowardPlayer(60);
        } else {

            // Get Random direction
            getRandomDirection(120);
        }

        if (attacking == false) checkAttackOrNot(60, 7*gameP.tileSize, 5*gameP.tileSize);
    }

    public void setDialogue(){
        dialogues[0][0] = "No one can steal my treasure!";
        dialogues[0][1] = "You will die here!";
        dialogues[0][2] = "WELCOME TO YOUR DOOM!";
    }

    public void damageReaction(){
        actionLockCounter = 0;

    }

    public void checkDrop(){
        gameP.bossBattleOn = false;
        Progress.skeletonLordDefeated = true;

        gameP.stopMusic();
        gameP.playMusic(17);

        // Remove Iron Door
        for (int i = 0; i < gameP.obj[1].length; i++){
            if (gameP.obj[gameP.currentMap][i] != null && gameP.obj[gameP.currentMap][i].name.equals(DoorIronObject.objName)){
                gameP.playSE(19);
                gameP.obj[gameP.currentMap][i] = null;
            }
        }

        int i = new Random().nextInt(100)+1;

        if (i < 50) dropItem(new CoinBronzeObject(gameP));
        if (i >= 50 && i < 75) dropItem(new HeartObject(gameP));
        if (i >= 75 && i < 100) dropItem(new ManaCrystalObject(gameP));
    }

    public void update(){
        super.update();
    }
}
