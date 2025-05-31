package entity;

import main.GamePanel;
import main.UtilityTool;

public class PlayerDummy extends Entity{
    public static final String npcName = "Dummy";

    public PlayerDummy(GamePanel gameP) {
        super(gameP);

        name = npcName;
        getImage();
    }

    public void getImage(){
        up1 = uTool.setUp("/player/boy_up_1");
        up2 = uTool.setUp("/player/boy_up_2");
        down1 = uTool.setUp("/player/boy_down_1");
        down2 = uTool.setUp("/player/boy_down_2");
        left1 = uTool.setUp("/player/boy_left_1");
        left2 = uTool.setUp("/player/boy_left_2");
        right1 = uTool.setUp("/player/boy_right_1");
        right2 = uTool.setUp("/player/boy_right_2");
    }
}
