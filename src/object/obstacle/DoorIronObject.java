package object.obstacle;

import entity.Entity;
import main.GamePanel;

public class DoorIronObject extends Entity {
    GamePanel gameP;
    public static final String objName = "Iron Door";

    public DoorIronObject(GamePanel gameP){
        super(gameP);
        this.gameP = gameP;

        type = obstacleType;
        name = objName;
        down1 = uTool.setUp("/objects/door_iron");
        collision = true;

        solidArea.x = 0;
        solidArea.y = 16;
        solidArea.width = 48;
        solidArea.height = 32;
        defaultSolidAreaX = solidArea.x;
        defaultSolidAreaY = solidArea.y;

        setDialogue();
    }

    public void setDialogue(){
        dialogues[0][0] = "It won't budge.";

    }

    public void interact(){
        gameP.gameState = gameP.dialogueState;
        startDialogue(this, 0);
    }

}
