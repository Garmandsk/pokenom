package tile_interactive;

import main.GamePanel;

public class IT_Trunk extends InteractiveTile {
    GamePanel gameP;

    public IT_Trunk(GamePanel gameP, int worldX, int worldY) {
        super(gameP, worldX, worldY);
        this.gameP = gameP;

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
}
