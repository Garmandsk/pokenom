package object;

import main.GamePanel;

import javax.imageio.ImageIO;
import java.io.IOException;

public class ChestObject extends SuperObject {
    GamePanel gameP;

    public ChestObject(GamePanel gameP){
        this.gameP = gameP;
        nameObject = "Chest";

        try {
            image = ImageIO.read(getClass().getResourceAsStream("/objects/chest.png"));
            uTool.scaleImage(image, gameP.tileSize, gameP.tileSize);
        } catch (IOException e){
            e.printStackTrace();
        }
    }
}
