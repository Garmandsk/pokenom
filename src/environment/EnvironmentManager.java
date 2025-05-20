package environment;

import main.GamePanel;

import java.awt.*;

public class EnvironmentManager {
    GamePanel gameP;
    public Lighting lighting;

    public EnvironmentManager(GamePanel gameP){
        this.gameP = gameP;
    }

    public void setup(){
        lighting = new Lighting(gameP);
    }

    public void update(){
        lighting.update();
    }

    public void draw(Graphics2D g2d){
        lighting.draw(g2d);
    }
}
