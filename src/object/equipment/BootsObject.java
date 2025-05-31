package object.equipment;

import entity.Entity;
import main.GamePanel;

public class BootsObject extends Entity {
    public static final String objName = "Boots";

    public BootsObject(GamePanel gameP){
        super(gameP);

        name = objName;
        down1 = uTool.setUp("/objects/boots");
        price = 2;
    }
}
