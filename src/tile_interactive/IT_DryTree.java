package tile_interactive;

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

    public void draw(Graphics2D g2d){

        int screenX = worldX - gameP.player.worldX + gameP.player.screenX;
        int screenY = worldY - gameP.player.worldY + gameP.player.screenY;

        /* Visible Area */
        if (    worldX + gameP.tileSize > gameP.player.worldX - gameP.player.screenX &&
                worldX - gameP.tileSize < gameP.player.worldX + gameP.player.screenX &&
                worldY + gameP.tileSize > gameP.player.worldY - gameP.player.screenY &&
                worldY - gameP.tileSize < gameP.player.worldY + gameP.player.screenY    ){

            g2d.drawImage(down1, screenX , screenY,null);
        }

    }

}
