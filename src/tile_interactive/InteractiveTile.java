package tile_interactive;

import entity.Entity;
import main.GamePanel;
import java.awt.Graphics2D;

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

    @Override
    public void update() {
        super.update();
        for (Entity child : children) {
            child.update();
        }
        if (this.invicible){
            this.invicibleCounter++;

            if (invicibleCounter >= 20){
                this.invicible = false;
                invicibleCounter = 0;
            }
        }
    }

    @Override
    public void draw(Graphics2D g2d) {
        super.draw(g2d);
        for (Entity child : children) {
            child.draw(g2d);
        }
    }
}
