package object;

import entity.Entity;
import main.GamePanel;

public class LanternObject extends Entity {
    public static final String objName = "Lantern";


    public LanternObject(GamePanel gameP) {
        super(gameP);

        type = lightType;
        name = objName;
        down1 = uTool.setUp("/objects/lantern");
        itemDescription = "[ " + name + " ] \nIlluminates your \nsurroundings";
        price = 200;
        lightRadius = 250;
    }
}
