package object;

import main.GamePanel;

import javax.imageio.ImageIO;
import java.io.IOException;

public class KeyObject extends SuperObject {
    GamePanel gameP;

    public KeyObject(GamePanel gameP){
        this.gameP = gameP;
        nameObject = "Key";

        try {
            image = ImageIO.read(getClass().getResourceAsStream("/objects/key.png"));
            uTool.scaleImage(image, gameP.tileSize, gameP.tileSize);
        } catch (IOException e){
            e.printStackTrace();
        }
    }
}
