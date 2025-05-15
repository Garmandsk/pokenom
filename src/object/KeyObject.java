package object;

import entity.Entity;
import main.GamePanel;

import javax.imageio.ImageIO;
import java.io.IOException;

public class KeyObject extends Entity {

    public KeyObject(GamePanel gameP){
        super(gameP);
        name = "Key";
        down1 = uTool.setUp("/objects/key");
        itemDescription = "[ " + name + " ]\nOpen Something.";
        price = 5;
    }
}
