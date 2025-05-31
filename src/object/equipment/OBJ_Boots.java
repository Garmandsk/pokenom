package object.equipment;

import entity.Entity;
import main.GamePanel;

public class OBJ_Boots extends Entity {
    public static final String objName = "Boots";

    public OBJ_Boots(GamePanel gameP){
        super(gameP);

        name = objName;
        down1 = uTool.setUp("/objects/boots");
        price = 2;
    }
}
