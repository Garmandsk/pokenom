package object;

import main.GamePanel;

import javax.imageio.ImageIO;
import java.io.IOException;

public class BootsObject extends SuperObject {
    GamePanel gameP;

    public BootsObject(GamePanel gameP){
        this.gameP = gameP;
        nameObject = "Boots";

        try {
            image = ImageIO.read(getClass().getResourceAsStream("/objects/boots.png"));
            uTool.scaleImage(image, gameP.tileSize, gameP.tileSize);
        } catch (IOException e){
            e.printStackTrace();
        }
    }
}
