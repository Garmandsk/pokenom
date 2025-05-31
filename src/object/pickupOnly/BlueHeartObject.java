package object.pickupOnly;

import entity.Entity;
import main.GamePanel;

public class BlueHeartObject extends Entity {
    GamePanel gameP;
    public static final String objName = "Blue Heart";

    public BlueHeartObject(GamePanel gameP) {
        super(gameP);
        this.gameP = gameP;

        type = pickupOnlyType;
        name = objName;
        down1 = uTool.setUp("/objects/blueheart");

        setDialogues();
    }

    public void setDialogues(){
        dialogues[0][0] = "You pick up a beautiful \n" +
                "blue gem.";
        dialogues[0][1] = "You find the Blue Heart, \n" +
                "the legendary treasure!";
    }

    public boolean use(Entity entity){
        gameP.gameState = gameP.cutsceneState;
        gameP.csM.sceneNum = gameP.csM.ending;

        return true;
    }
}
