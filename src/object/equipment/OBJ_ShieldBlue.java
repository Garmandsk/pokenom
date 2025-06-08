package object.equipment;

import entity.Entity;
import main.GamePanel;

public class OBJ_ShieldBlue extends Entity {
    public static final String objName = "Blue Shield";


    public OBJ_ShieldBlue(GamePanel gameP){
        super(gameP);

        type = shieldType;
        name = objName;
        elementType = waterElement;
        down1 = uTool.setUp("/objects/shield_blue");
        defenseValue = 2;
        itemDescription = "[ " + name + " ]\nBlue as the glacier’s \nancient heart";
        price = 8;
    }
}
