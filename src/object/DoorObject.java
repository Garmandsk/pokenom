package object;

import entity.Entity;
import main.GamePanel;

import javax.imageio.ImageIO;
import java.io.IOException;

public class DoorObject extends Entity {
    public static final String objName = "Door";

    GamePanel gameP;

    public DoorObject(GamePanel gameP){
        super(gameP);
        this.gameP = gameP;

        type = obstacleType;
        name = objName;
        down1 = uTool.setUp("/objects/door");
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
        dialogues[0][0] = "You need a key to \nopen this";

    }

    public void interact(){
        gameP.gameState = gameP.dialogueState;
        startDialogue(this, 0);
    }

}
