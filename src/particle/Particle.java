package particle;

import entity.Entity;
import main.GamePanel;

import java.awt.*;

public class Particle extends Entity {
    Generator generator;
    Color color;
    int size;
    int xd, yd;

    public Particle(GamePanel gameP, Generator generator, Color color, int size, int speed, int maxLife, int targetWorldX, int targetWorldY, int xd, int yd) {
        super(gameP);

        this.generator = generator;
        this.color = color;
        this.size = size;
        this.speed = speed;
        this.maxLife = maxLife;
        this.xd = xd;
        this.yd = yd;

        life = maxLife;
        int offset = (gameP.tileSize/2) - (size/2);
        this.worldX = targetWorldX + offset;
        this.worldY = targetWorldY + offset;
    }

    public void update(){
        life--;

        if (life < maxLife/3) yd++;

        worldX += xd*speed;
        worldY += yd*speed;

        if (life <= 0) alive = false;

    }

    public void draw(Graphics2D g2d){
        int screenX = worldX - gameP.player.worldX + gameP.player.screenX;
        int screenY = worldY - gameP.player.worldY + gameP.player.screenY;

        g2d.setColor(this.color);
        g2d.fillRect(screenX, screenY, size, size);
    }
}
