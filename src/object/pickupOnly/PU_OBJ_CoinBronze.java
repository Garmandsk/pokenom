package object.pickupOnly;

import entity.Entity;
import main.GamePanel;

public class PU_OBJ_CoinBronze extends Entity {
    GamePanel gameP;
    public static final String objName = "Bronze Coin";

    public PU_OBJ_CoinBronze(GamePanel gameP) {
        super(gameP);
        this.gameP = gameP;

        this.type = pickupOnlyType;
        name = objName;
        value = 1;
        down1 = uTool.setUp("/objects/coin_bronze");
    }

    public boolean use(Entity entity){
        gameP.playSE(1);

       gameP.ui.addMessage("Coin + " + value);
       gameP.player.coin += value;

       return true;
    }
}
