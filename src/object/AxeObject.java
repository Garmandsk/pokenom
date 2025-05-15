package object;

import entity.Entity;
import main.GamePanel;

public class AxeObject extends Entity {

    public AxeObject(GamePanel gameP){
        super(gameP);

        type = axeType;
        name = "Woodcutter's Axe";
        down1 = uTool.setUp("/objects/axe");
        attackValue = 2;
        attackArea.width = gameP.tileSize/2 + 16;
        attackArea.height = gameP.tileSize/2 + 16;
        itemDescription = "[ " + name + " ]\nChopp Chopp!";
        price = 10;
    }
}
