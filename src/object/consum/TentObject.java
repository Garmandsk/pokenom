package object.consum;

import entity.Entity;
import main.GamePanel;

public class TentObject extends Entity {
    GamePanel gamep;
    public static final String objName = "Tent";


    public TentObject(GamePanel gameP) {
        super(gameP);
        this.gamep = gameP;

        type = consumType;
        name = objName;
        down1 = uTool.setUp("/objects/tent");
        itemDescription = "[ " + name + " ] \nYou can sleep until \nnext morning.";
        price = 300;
        stackable = true;
    }

    public boolean use(Entity entity){
        gamep.playSE(12);
        gamep.gameState = gamep.sleepState;
        gamep.player.life = gamep.player.maxLife;
        gamep.player.mana = gamep.player.maxMana;
        gamep.player.getSleepingImage(down1);

        return  true;
    }
}
