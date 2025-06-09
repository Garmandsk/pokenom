package tile_interactive;

import entity.Entity;
import main.GamePanel;

import java.awt.*;
import java.awt.image.BufferedImage;

public class IT_DryTree extends InteractiveTile {
    GamePanel gameP;

    public static final String iTileName = "Dry Tree";

    public IT_DryTree(GamePanel gameP, int worldX, int worldY) {
        super(gameP, worldX, worldY);
        this.gameP = gameP;

        name = iTileName;
        destructible = true;
        life = 3;

        this.worldX = gameP.tileSize * worldX;
        this.worldY = gameP.tileSize * worldY;
        down1 = uTool.setUp("/tiles_interactive/drytree");
    }

    public void playSE(){
        gameP.playSE(9);
    }

    public InteractiveTile getDestroyedForm(){
        InteractiveTile tile = new IT_Trunk(gameP, this.worldX/gameP.tileSize, this.worldY/ gameP.tileSize);
        return  tile;
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
