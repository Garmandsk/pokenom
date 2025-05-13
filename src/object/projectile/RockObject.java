package object.projectile;

import entity.Entity;
import entity.Projectile;
import main.GamePanel;

public class RockObject extends Projectile {
    GamePanel gameP;

    public RockObject(GamePanel gameP) {
        super(gameP);
        this.gameP =  gameP;

        name = "Rock";
        elementType = earthElement;
        speed = 8;
        maxLife = 80;
        life =  maxLife;
        attackPower = 2;
        useCost = 1;
        alive = false;
        getImage();
    }

    public void getImage(){
        up1 = uTool.setUp("/projectiles/rock_down_1");
        up2 = uTool.setUp("/projectiles/rock_down_1");
        down1 = uTool.setUp("/projectiles/rock_down_1");
        down2 = uTool.setUp("/projectiles/rock_down_1");
        left1 = uTool.setUp("/projectiles/rock_down_1");
        left2 = uTool.setUp("/projectiles/rock_down_1");
        right1 = uTool.setUp("/projectiles/rock_down_1");
        right2 = uTool.setUp("/projectiles/rock_down_1");
    }

    public boolean haveResources(Entity user){
        return user.ammo >= useCost;
    }

    public void substractResources(Entity user){
        user.ammo -= useCost;
    }
}
