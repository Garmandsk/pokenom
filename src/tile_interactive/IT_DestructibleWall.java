package tile_interactive;

import entity.Entity;
import main.GamePanel;

import java.awt.*;

public class IT_DestructibleWall extends InteractiveTile {
    GamePanel gameP;
    public static final String iTileName = "Wall";

    public IT_DestructibleWall(GamePanel gameP, int worldX, int worldY) {
        super(gameP, worldX, worldY);
        this.gameP = gameP;

        name = iTileName;
        destructible = true;
        life = 3;

        this.worldX = gameP.tileSize * worldX;
        this.worldY = gameP.tileSize * worldY;
        down1 = uTool.setUp("/tiles_interactive/destructiblewall");
    }

    public void playSE(){
        gameP.playSE(18);
    }

    public boolean isCorrectItem(Entity entity){
        boolean isCorrectItem = entity.currentWeapon.type == pickaxeType;

        return  isCorrectItem;
    }

    public InteractiveTile getDestroyedForm(){
        InteractiveTile tile = null;
        return  tile;
    }

//    public void checkDrop(){
//        int i = new Random().nextInt(100)+1;
//
//        if (i < 50) dropItem(new CoinBronzeObject(gameP));
//        if (i >= 50 && i < 75) dropItem(new HeartObject(gameP));
//        if (i >= 75 && i < 100) dropItem(new ManaCrystalObject(gameP));
//    }

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
