package entity;

import main.GamePanel;
import particle.EarthParticle;
import particle.FireParticle;
import particle.ThunderParticle;
import particle.WaterParticle;

public class Projectile extends Entity {
    Entity user;
    public int elementType;
    public int waterElement = 1;
    public int fireElement = 2;
    public int earthElement = 3;
    public int thunderElement = 4;

    public Projectile(GamePanel gameP) {
        super(gameP);
    }

    public void set(int worldX, int worldY, String direction, boolean alive, Entity user){
        this.user = user;
        this.life = maxLife;
        this.alive = alive;
        this.worldX = worldX;
        this.worldY = worldY;
        this.direction = direction;

    }

    public boolean haveResources(Entity user){ return false; }
    public void substractResources(Entity user) {}

    public void update(){

//        System.out.println(user.type);
        if (user.type == playerType){
            int monsterIndex = gameP.cChecker.checkEntity(this, gameP.monster);
            if (monsterIndex != 999){
                gameP.player.damageMonster(monsterIndex, this.attackPower);
                if (elementType == waterElement) generateParticle(new WaterParticle(), gameP.monster[monsterIndex]);
                else if (elementType == fireElement) generateParticle(new FireParticle(), gameP.monster[monsterIndex]);
                else if (elementType == earthElement) generateParticle(new EarthParticle(), gameP.monster[monsterIndex]);
                else if (elementType == thunderElement) generateParticle(new ThunderParticle(), gameP.monster[monsterIndex]);

                alive = false;
            }
        } else {
            boolean contactPlayer = gameP.cChecker.checkPlayer(this);
            if (gameP.player.invicible == false && contactPlayer == true){
                damagePlayer(attackPower);
                if (elementType == waterElement) generateParticle(new WaterParticle(), gameP.player);
                else if (elementType == fireElement) generateParticle(new FireParticle(), gameP.player);
                else if (elementType == earthElement) generateParticle(new EarthParticle(), gameP.player);
                else if (elementType == thunderElement) generateParticle(new ThunderParticle(), gameP.player);

                alive = false;
            }


        }

        switch (direction){
            case "up":
                worldY -= speed;
                break;
            case "down":
                worldY += speed;
                break;
            case "left":
                worldX -= speed;
                break;
            case "right":
                worldX += speed;
                break;
        }

        // berkurang sesuai game loop
        life--;
        if (life <= 0){
            alive = false;
        }

        spriteCounter++;
        if (spriteCounter > 12){
            if (spriteNum == 1) spriteNum = 2;
            else if (spriteNum == 2) spriteNum = 1;
            spriteCounter = 0;
        }
    }
}
