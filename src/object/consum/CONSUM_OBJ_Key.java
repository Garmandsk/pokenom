package object.consum;

import entity.Entity;
import main.GamePanel;

public class CONSUM_OBJ_Key extends Entity {
    public static final String objName = "Key";


    public CONSUM_OBJ_Key(GamePanel gameP){
        super(gameP);

        type = consumType;
        stackable = true;
        name = objName;
        down1 = uTool.setUp("/objects/key");
        itemDescription = "[ " + name + " ]\nOpen Something.";
        price = 5;

        setDialogue();
    }

    public void setDialogue(){
        dialogues[0][0] = "You use the " + name + "\nand open the door";
        dialogues[1][0] = "What are you doing ?";

    }

    public boolean use(Entity entity){
        gameP.gameState = gameP.dialogueState;

        int objIndex = getDetected(entity, gameP.obj, "Door");
//        System.out. ("Object Index: " + objIndex);

        if (objIndex != 999){
            gameP.playSE(7);
//            gameP.playSE(11);
            gameP.obj[gameP.currentMap][objIndex] = null;
            startDialogue(this, 0);

            return true;
        } else {
            startDialogue(this, 1);
        }

        return false;
    }
}
