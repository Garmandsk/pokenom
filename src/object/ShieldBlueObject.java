package object;

import entity.Entity;
import main.GamePanel;

public class ShieldBlueObject extends Entity {

    public ShieldBlueObject(GamePanel gameP){
        super(gameP);

        type = shieldType;
        name = "Blue Shield";
        down1 = uTool.setUp("/objects/shield_blue");
        defenseValue = 2;
        itemDescription = "[ " + name + " ]\nBlue as the glacier’s \nancient heart";
        price = 8;
    }
}
