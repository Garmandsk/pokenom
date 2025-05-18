package object;

import entity.Entity;
import main.GamePanel;

public class SwordNormalObject extends Entity {

    public SwordNormalObject(GamePanel gameP){
        super(gameP);

        type = swordType;
        name = "Normal Sword";
        down1 = uTool.setUp("/objects/sword_normal");
        attackValue = 1;
        knockbackPower = 2;
        attackArea.width = gameP.tileSize/2 + 16;
        attackArea.height = gameP.tileSize/2 + 16;
        itemDescription = "[ " + name + " ]\n an Ol'Sword.";
        price = 1;
    }
}
