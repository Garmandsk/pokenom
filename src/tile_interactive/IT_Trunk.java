package tile_interactive;
import entity.Entity;
import main.GamePanel;
import java.awt.Graphics2D;

public class IT_Trunk extends InteractiveTile {
    GamePanel gameP;
    public static final String iTileName = "Trunk";

    public IT_Trunk(GamePanel gameP, int worldX, int worldY) {
        super(gameP, worldX, worldY);
        this.gameP = gameP;

        name = iTileName;
        this.worldX = gameP.tileSize * worldX;
        this.worldY = gameP.tileSize * worldY;
        down1 = uTool.setUp("/tiles_interactive/trunk");

        solidArea.x = 0;
        solidArea.y = 0;
        solidArea.width = 0;
        solidArea.height = 0;
        defaultSolidAreaX = solidArea.x;
        defaultSolidAreaY = solidArea.y;
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
