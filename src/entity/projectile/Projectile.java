package entity.projectile;

import entity.Entity;
import main.GamePanel;
import java.awt.Graphics2D;

public class Projectile extends Entity {
    Entity user;

    public Projectile(GamePanel gameP) {
        super(gameP);
    }

    public boolean haveResources(Entity user){ return false; }
    public void substractResources(Entity user) {}

    public void set(int worldX, int worldY, String direction, boolean alive, Entity user){
        canEnterBattleState = false;

        this.user = user;
        this.life = maxLife;
        this.alive = alive;
        this.worldX = worldX;
        this.worldY = worldY;
        this.direction = direction;
    }

    public void update(){
        if (user.type == playerType){
            int monsterIndex = gameP.cChecker.checkEntity(this, gameP.monster);
            if (monsterIndex != 999){
//                System.out.println(this.elementType);
//                System.out.println(elementType);
                gameP.player.damageMonsterByProjectile(monsterIndex, this, this.attackPower, this.knockbackPower);
                if (elementType == waterElement) generateParticle(waterParticle, gameP.monster[gameP.currentMap][monsterIndex]);
                else if (elementType == fireElement) generateParticle(fireParticle, gameP.monster[gameP.currentMap][monsterIndex]);
                else if (elementType == earthElement) generateParticle(earthParticle, gameP.monster[gameP.currentMap][monsterIndex]);
                else if (elementType == thunderElement) generateParticle(thunderParticle, gameP.monster[gameP.currentMap][monsterIndex]);

                alive = false;
            }
        } else {
            boolean contactPlayer = gameP.cChecker.checkPlayer(this);
            if (gameP.player.invicible == false && contactPlayer){
                damagePlayer(attackPower);
//                System.out.println(this.name);
                if (elementType == waterElement) generateParticle(waterParticle, gameP.player);
                else if (elementType == fireElement) generateParticle(fireParticle, gameP.player);
                else if (elementType == earthElement) generateParticle(earthParticle, gameP.player);
                else if (elementType == thunderElement) generateParticle(thunderParticle, gameP.player);

                alive = false;
            }
        }

        moveDependsOnDirectionandSpeed(direction);

        // berkurang sesuai game loop
        life--;
        if (life <= 0) alive = false;

        spriteCounter++;
        if (spriteCounter > 12){
            if (spriteNum == 1) spriteNum = 2;
            else if (spriteNum == 2) spriteNum = 1;
            spriteCounter = 0;
        }

        // Panggil update pada children (composite pattern)
        for (Entity child : children) {
            child.update();
        }
    }

    public void draw(Graphics2D g2d){
        super.draw(g2d);
        // Panggil draw pada children (composite pattern)
        for (Entity child : children) {
            child.draw(g2d);
        }
    }
}
