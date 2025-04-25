package object;

import main.GamePanel;

import javax.imageio.ImageIO;
import java.io.IOException;

public class DoorObject extends SuperObject {
    GamePanel gameP;

    public DoorObject(GamePanel gameP){
        this.gameP = gameP;
        nameObject = "Door";

        try {
            image = ImageIO.read(getClass().getResourceAsStream("/objects/door.png"));
            uTool.scaleImage(image, gameP.tileSize, gameP.tileSize);
        } catch (IOException e){
            e.printStackTrace();
        }

        collision = true;
    }
}
