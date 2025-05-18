package object;

import entity.Entity;
import main.GamePanel;

public class ConsumPotionRedObject extends Entity {
    GamePanel gameP;

    public ConsumPotionRedObject(GamePanel gameP){
        super(gameP);
        this.gameP = gameP;

        type = consumType;
        name = "Red Potion";
        down1 = uTool.setUp("/objects/potion_red");
        healingValue = 5;
        itemDescription = "[ " + name + " ]\nHeals +" + healingValue + "!";
    }

    public boolean use(Entity entity){
        gameP.playSE(2);
        entity.life += healingValue;

        String text = "Drinked The " + name + "!\nYour Life has been recovered \nby " + healingValue + ".";
        gameP.gameState = gameP.dialogueState;
        gameP.ui.currentDialogue = text;

        return true;
    }
}
