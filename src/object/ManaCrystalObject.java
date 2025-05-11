package object;

import entity.Entity;
import main.GamePanel;

public class ManaCrystalObject extends Entity {
    GamePanel gameP;

    public ManaCrystalObject(GamePanel gameP) {
        super(gameP);
        this.gameP = gameP;

        type = pickupOnlyType;
        name = "Mana Crytsal";
        healingValue = 1;

        down1 = uTool.setUp("/objects/manacrystal_full");
        image = uTool.setUp("/objects/manacrystal_blank");
        image2 = uTool.setUp("/objects/manacrystal_full");
    }

    public void use(Entity entity){
        gameP.playSE(2);
        entity.mana += healingValue;

        gameP.ui.addMessage("Mana + " + healingValue);
    }
}