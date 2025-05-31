package object.consum;

import entity.Entity;
import main.GamePanel;

public class CONSUM_OBJ_PotionRed extends Entity {
    public static final String objName = "Red Potion";

    GamePanel gameP;

    public CONSUM_OBJ_PotionRed(GamePanel gameP){
        super(gameP);
        this.gameP = gameP;

        type = consumType;
        stackable = true;
        name = objName;
        down1 = uTool.setUp("/objects/potion_red");
        healingValue = 5;
        itemDescription = "[ " + name + " ]\nHeals +" + healingValue + "!";
        price = 20;

        setDialogue();
    }

    public void setDialogue(){
        dialogues[0][0] = "Drinked The " + name + "!\nYour Life has been recovered \nby " + healingValue + ".";

    }

    public boolean use(Entity entity){
        gameP.playSE(2);
        entity.life += healingValue;

        gameP.gameState = gameP.dialogueState;
        startDialogue(this, 0);

        return true;
    }
}
