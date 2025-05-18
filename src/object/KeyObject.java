package object;

import entity.Entity;
import main.GamePanel;

import javax.imageio.ImageIO;
import java.io.IOException;

public class KeyObject extends Entity {

    public KeyObject(GamePanel gameP){
        super(gameP);

        type = consumType;
        name = "Key";
        down1 = uTool.setUp("/objects/key");
        itemDescription = "[ " + name + " ]\nOpen Something.";
        price = 5;
    }

    public boolean use(Entity entity){
        gameP.gameState = gameP.dialogueState;

        int objIndex = getDetected(entity, gameP.obj, "Door");
//        System.out.println("Object Index: " + objIndex);

        if (objIndex != 999){
            gameP.ui.currentDialogue = "You use the " + name + "\nand open the door";
            gameP.playSE(7);
//            gameP.playSE(11);
            gameP.obj[gameP.currentMap][objIndex] = null;

            return true;
        } else {
            gameP.ui.currentDialogue = "What are you doing ?";
        }

        return false;
    }
}
