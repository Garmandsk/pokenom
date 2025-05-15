package object;

import entity.Entity;
import main.GamePanel;

public class ShieldWoodObject extends Entity {

    public ShieldWoodObject(GamePanel gameP){
        super(gameP);

        type = shieldType;
        name = "Wooden Shield";
        down1 = uTool.setUp("/objects/shield_wood");
        defenseValue = 1;
        itemDescription = "[ " + name + " ]\nWoodie.";
        price = 1;
    }
}
