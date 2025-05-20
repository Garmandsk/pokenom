package object;

import entity.Entity;
import main.GamePanel;

public class ChestObject extends Entity {
    GamePanel gameP;
    Entity loot;
    boolean opened;

    public ChestObject(GamePanel gameP, Entity loot){
        super(gameP);
        this.gameP = gameP;
        this.loot = loot;

        type = obstacleType;
        name = "Chest";
        image = uTool.setUp("/objects/chest");
        image2 = uTool.setUp("/objects/chest_opened");
        down1 = image;
        collision = true;

        solidArea.x = 4;
        solidArea.y = 16;
        solidArea.width = 40;
        solidArea.height = gameP.tileSize - 16;
        defaultSolidAreaX = solidArea.x;
        defaultSolidAreaY = solidArea.y;
    }

    public void interact(){
        gameP.gameState = gameP.dialogueState;

        if (opened == false){
            gameP.playSE(7);

            StringBuilder sb = new StringBuilder();
            sb.append("You open the chest and find \na " + loot.name + "!");

            if (gameP.player.canObtainItem(loot)){
                sb.append("\nYou obtain the " + loot.name + "!");
                down1 = image2;
                opened = true;
            }
            else{
                sb.append("\n...but your Inventory is full");
            }
            gameP.ui.currentDialogue = sb.toString();
        }else {
            gameP.ui.currentDialogue = "it's a empty chest";
        }
    }
}
