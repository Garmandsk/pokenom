package object;

import entity.Entity;
import main.GamePanel;

public class HeartObject extends Entity {
    GamePanel gameP;

    public HeartObject(GamePanel gameP){
        super(gameP);
        this.gameP = gameP;

        type = pickupOnlyType;
        name = "Heart";
        healingValue = 2;

        down1 = uTool.setUp("/objects/heart_full");
        image = uTool.setUp("/objects/heart_blank");
        image2 = uTool.setUp("/objects/heart_full");
        image3 = uTool.setUp("/objects/heart_half");
    }

    public void use(Entity entity){
        gameP.playSE(2);
        entity.life += healingValue;

        gameP.ui.addMessage("Life + " + healingValue);
    }
}
