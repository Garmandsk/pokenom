package object.equipment;

import entity.Entity;
import main.GamePanel;

public class

PickaxeObject extends Entity {
    public static final String objName = "Pickaxe";

    public PickaxeObject(GamePanel gameP) {
        super(gameP);

        type = pickaxeType;
        name = objName;
        down1 = uTool.setUp("/objects/pickaxe");
        attackValue = 2;
        attackArea.width = 30;
        attackArea.height = 30;
        itemDescription = "[ " + name + "] \n" +
                "You will dig it!";
        price = 75;
        knockbackPower = 10;
        motion1Duration = 20;
        motion2Duration = 40;
    }
}
