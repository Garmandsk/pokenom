package object.pickupOnly;

import entity.Entity;
import main.GamePanel;

public class PU_OBJ_ManaCrystal extends Entity {
    GamePanel gameP;
    public static final String objName = "Mana Crystal";

    public PU_OBJ_ManaCrystal(GamePanel gameP) {
        super(gameP);
        this.gameP = gameP;

        type = pickupOnlyType;
        name = objName;
        healingValue = 1;

        down1 = uTool.setUp("/objects/manacrystal_full");
        image = uTool.setUp("/objects/manacrystal_blank");
        image2 = uTool.setUp("/objects/manacrystal_full");
    }

    public boolean use(Entity entity){
        gameP.playSE(2);
        entity.mana += healingValue;

        gameP.ui.addMessage("Mana + " + healingValue);

        return true;
    }
}