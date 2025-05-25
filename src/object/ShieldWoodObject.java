package object;

import entity.Entity;
import main.GamePanel;

public class ShieldWoodObject extends Entity {
    public static final String objName = "Wooden Shield";


    public ShieldWoodObject(GamePanel gameP){
        super(gameP);

        type = shieldType;
        name = objName;
        down1 = uTool.setUp("/objects/shield_wood");
        defenseValue = 1;
        itemDescription = "[ " + name + " ]\nWoodie.";
        price = 1;
    }
}
