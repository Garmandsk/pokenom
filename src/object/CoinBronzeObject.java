package object;

import entity.Entity;
import main.GamePanel;

public class CoinBronzeObject extends Entity {
    public static final String objName = "Bronze Coin";

    GamePanel gameP;

    public CoinBronzeObject(GamePanel gameP) {
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
