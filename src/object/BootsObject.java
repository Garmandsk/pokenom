package object;

import entity.Entity;
import main.GamePanel;

public class BootsObject extends Entity {

    public BootsObject(GamePanel gameP){
        super(gameP);

        name = "Boots";
        down1 = uTool.setUp("/objects/boots");
        price = 2;
    }
}
