package object;

import entity.Entity;
import entity.Projectile;
import main.GamePanel;

public class FireballObject extends Projectile {
    public static final String objName = "Fireball";

    GamePanel gameP;

    public FireballObject(GamePanel gameP) {
        super(gameP);
        this.gameP =  gameP;

        name = objName;
        speed = 5;
        maxLife = 80;
        life =  maxLife;
        attackValue = 2;
        knockbackPower = 0;
        useCost = 1;
        alive = false;
        getImage();
    }

    public void getImage(){
        up1 = uTool.setUp("/projectiles/fireball_up_1");
        up2 = uTool.setUp("/projectiles/fireball_up_2");
        down1 = uTool.setUp("/projectiles/fireball_down_1");
        down2 = uTool.setUp("/projectiles/fireball_down_2");
        left1 = uTool.setUp("/projectiles/fireball_left_1");
        left2 = uTool.setUp("/projectiles/fireball_left_2");
        right1 = uTool.setUp("/projectiles/fireball_right_1");
        right2 = uTool.setUp("/projectiles/fireball_right_2");
    }

    public boolean haveResources(Entity user){
        return user.mana >= useCost;
    }

    public void substractResources(Entity user){
        user.mana -= useCost;
    }
}
