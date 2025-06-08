package object.equipment;

import entity.Entity;
import main.GamePanel;

public class OBJ_ShieldWood extends Entity {
    public static final String objName = "Wooden Shield";


    public OBJ_ShieldWood(GamePanel gameP){
        super(gameP);

        type = shieldType;
        name = objName;
        elementType = earthElement;
        down1 = uTool.setUp("/objects/shield_wood");
        defenseValue = 1;
        itemDescription = "[ " + name + " ]\nWoodie.";
        price = 1;
    }
}
