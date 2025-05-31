package tile_interactive;

import entity.Entity;
import main.GamePanel;

public class InteractiveTile extends Entity {
    GamePanel gameP;
    public boolean destructible;

    public InteractiveTile(GamePanel gameP, int worldX, int worldY) {
        super(gameP);
        this.gameP = gameP;
    }

    public boolean isCorrectItem(Entity entity){
        boolean isCorrectItem = false;

        if (entity.currentWeapon.type == axeType){
            isCorrectItem = true;
        }

        return isCorrectItem;
    }

    public void playSE(){}

    public InteractiveTile getDestroyedForm(){
        return null;
    }

    public void update() {
        if (this.invicible){
            this.invicibleCounter++;

            if (invicibleCounter >= 20){
                this.invicible = false;
                invicibleCounter = 0;
            }
        }
    }
}
