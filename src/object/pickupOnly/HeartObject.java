package object.pickupOnly;

import entity.Entity;
import main.GamePanel;

public class HeartObject extends Entity {
    public static final String objName = "Heart";

    GamePanel gameP;

    public HeartObject(GamePanel gameP){
        super(gameP);
        this.gameP = gameP;

        type = pickupOnlyType;
        name = objName;
        healingValue = 2;

        down1 = uTool.setUp("/objects/heart_full");
        image = uTool.setUp("/objects/heart_blank");
        image2 = uTool.setUp("/objects/heart_full");
        image3 = uTool.setUp("/objects/heart_half");
    }

    public boolean use(Entity entity){
        gameP.playSE(2);
        entity.life += healingValue;

        gameP.ui.addMessage("Life + " + healingValue);

        return true;
    }
}
