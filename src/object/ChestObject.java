package object;

import entity.Entity;
import main.GamePanel;

public class ChestObject extends Entity {

    public ChestObject(GamePanel gameP){
        super(gameP);

        name = "Chest";
        down1 = uTool.setUp("/objects/chest");
    }
}
