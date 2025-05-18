package object;

import entity.Entity;
import main.GamePanel;

import javax.imageio.ImageIO;
import java.io.IOException;

public class DoorObject extends Entity {
    GamePanel gameP;

    public DoorObject(GamePanel gameP){
        super(gameP);
        this.gameP = gameP;

        type = obstacleType;
        name = "Door";
        down1 = uTool.setUp("/objects/door");
        collision = true;

        solidArea.x = 0;
        solidArea.y = 16;
        solidArea.width = 48;
        solidArea.height = 32;
        defaultSolidAreaX = solidArea.x;
        defaultSolidAreaY = solidArea.y;
    }

    public void interact(){
        gameP.gameState = gameP.dialogueState;
        gameP.ui.currentDialogue = "You need a key to \nopen this";
    }

}
