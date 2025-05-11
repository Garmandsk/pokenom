package entity;

import main.GamePanel;

public class Projectile extends Entity {
    Entity user;

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
                alive = false;
            }
        } else {
            boolean contactPlayer = gameP.cChecker.checkPlayer(this);
            if (gameP.player.invicible == false && contactPlayer == true){
                damagePlayer(attackPower);
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
