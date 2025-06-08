package tile_interactive;

import main.GamePanel;

public class IT_MetalPlateReset extends InteractiveTile {
    GamePanel gameP;
    public static final String itName = "Metal Plate Reset";

    public IT_MetalPlateReset(GamePanel gameP, int worldX, int worldY) {
        super(gameP, worldX, worldY);
        this.gameP = gameP;

        name = itName;
        this.worldX = gameP.tileSize * worldX;
        this.worldY = gameP.tileSize * worldY;
        down1 = uTool.setUp("/tiles_interactive/metalplatereset");

        solidArea.x = 0;
        solidArea.y = 0;
        solidArea.width = 0;
        solidArea.height = 0;
        defaultSolidAreaX = solidArea.x;
        defaultSolidAreaY = solidArea.y;
    }
}
