package entity;

import main.GamePanel;
import main.UtilityTool;

import java.util.Random;

public class NPC_OldMan extends Entity {
    public NPC_OldMan(GamePanel gameP){
        super(gameP);

        direction = "down";
        speed = 1;
        getOldManImage();
    }

    public void getOldManImage(){
        UtilityTool uTool = new UtilityTool(gameP);

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
        actionLockCounter++;

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
    }
}
