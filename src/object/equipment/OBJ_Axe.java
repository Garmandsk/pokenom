package object.equipment;

import entity.Entity;
import main.GamePanel;

public class OBJ_Axe extends Entity {
    public static final String objName = "Woodcutter's Axe";

    public OBJ_Axe(GamePanel gameP){
        super(gameP);

        type = axeType;
        name = objName;
        down1 = uTool.setUp("/objects/axe");
        attackValue = 2;
        knockbackPower = 5;
        attackArea.width = gameP.tileSize/2 + 16;
        attackArea.height = gameP.tileSize/2 + 16;
        itemDescription = "[ " + name + " ]\nChopp Chopp!";
        price = 10;

        motion1Duration = 20;
        motion2Duration = 40;
    }
}
