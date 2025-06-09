package entity.projectile;

import entity.Entity;
import main.GamePanel;
import java.awt.Graphics2D;

public class PRO_Fireball extends Projectile {
    GamePanel gameP;
    public static final String proName = "Fireball";

    public PRO_Fireball(GamePanel gameP) {
        super(gameP);
        this.gameP =  gameP;

        name = proName;
        elementType = fireElement;
        speed = 5;
        maxLife = 80;
        life =  maxLife;
        attackPower = 2;
        knockbackPower = 3;
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

    @Override
    public void update() {
        super.update();
        for (Entity child : children) {
            child.update();
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
