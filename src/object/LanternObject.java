package object;

import entity.Entity;
import main.GamePanel;

public class LanternObject extends Entity {

    public LanternObject(GamePanel gameP) {
        super(gameP);

        type = lightType;
        name = "Lantern";
        down1 = uTool.setUp("/objects/lantern");
        itemDescription = "[ " + name + " ] \nIlluminates your \nsurroundings";
        price = 200;
        lightRadius = 250;
    }
}
