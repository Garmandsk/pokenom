package entity.npc;

import entity.Entity;
import main.GamePanel;
import main.UtilityTool;

import java.util.Random;
import java.awt.Graphics2D;

public class NPC_OldMan extends Entity {
    public static final String npcName = "Old Man";

    public NPC_OldMan(GamePanel gameP){
        super(gameP);

        type = npcType;
        name = npcName;
        direction = "down";
        speed = 3;

        solidArea.x = 6;
        solidArea.y = 16;
        solidArea.width = gameP.tileSize - (solidArea.x * 2);
        solidArea.height = gameP.tileSize - 16;
        defaultSolidAreaX = solidArea.x;
        defaultSolidAreaY = solidArea.y;

        dialogueSet = -1;

        getImage();
        setDialogue();
    }

    public void getImage(){
//        System.out.println("=====OldMan=====");
//        System.out.println("Solid Area X: " + this.solidArea.x);
//        System.out.println("Solid Area Y: " + this.solidArea.y);
//        System.out.println("Solid Area Width: " + this.solidArea.width);
//        System.out.println("Solid Area Height: " + this.solidArea.height);

        up1 = uTool.setUp("/npc/oldman_up_1");
        up2 = uTool.setUp("/npc/oldman_up_2");
        down1 = uTool.setUp("/npc/oldman_down_1");
        down2 = uTool.setUp("/npc/oldman_down_2");
        left1 = uTool.setUp("/npc/oldman_left_1");
        left2 = uTool.setUp("/npc/oldman_left_2");
        right1 = uTool.setUp("/npc/oldman_right_1");
        right2 = uTool.setUp("/npc/oldman_right_2");
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

    public void setDialogue(){
        dialogues[0][0] = "Halo Anak Muda..";
        dialogues[0][1] = "Hari Yang Indah \nUntuk Berpetualang";
        dialogues[0][2] = "Semoga Beruntung!";

        dialogues[1][0] = "Jalan - jalan ke jepang";
        dialogues[1][1] = "Gatau mau nulis apa";
        dialogues[1][2] = "Perjalanan masih panjang";
        dialogues[1][3] = "Untuk kita lewati bersama";

        dialogues[2][0] = "Langit yang indah";
        dialogues[2][1] = "Sayang sekali, aku hanyalah " + name + ".";
    }

    public void speak(){
        facePlayerDirection();
        startDialogue(this, dialogueSet);
        dialogueSet++;

        if (gameP.player.life < gameP.player.maxLife/3) dialogueSet = 1;
        else if (dialogues[dialogueSet][0] == null) dialogueSet = 0;
//        onPath = true;
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
