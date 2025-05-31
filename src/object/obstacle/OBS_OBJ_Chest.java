package object.obstacle;

import entity.Entity;
import main.GamePanel;

public class OBS_OBJ_Chest extends Entity {
    GamePanel gameP;
    public static final String objName = "Chest";

    public OBS_OBJ_Chest(GamePanel gameP){
        super(gameP);
        this.gameP = gameP;

        type = obstacleType;
        name = objName;
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

    public void setLoot(Entity loot){
        this.loot = loot;

        setDialogue();
    }

    public void setDialogue(){
        dialogues[0][0] = "You open the chest and find \na " + loot.name + "!" + "\nYou obtain the " + loot.name + "!";
        dialogues[1][0] = "You open the chest and find \na " + loot.name + "!" + "\n...but your Inventory is full";
        dialogues[2][0] = "it's a empty chest";

    }

    public void interact(){
        if (opened == false){
            gameP.playSE(7);

            if (gameP.player.canObtainItem(loot)){
                down1 = image2;
                opened = true;
                startDialogue(this, 0);
            }
            else{
                startDialogue(this, 1);
            }
        } else {
            startDialogue(this, 2);
        }
    }
}
