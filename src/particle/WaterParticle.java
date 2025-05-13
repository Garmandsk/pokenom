package particle;

import entity.Entity;
import main.GamePanel;

import java.awt.*;

public class WaterParticle extends Generator {

    public WaterParticle() {

    }

    public Color getParticleColor(){
        Color color = new Color(0, 161, 203);
        return color;
    }

    public int getParticleSize(){
        int size = 10;
        return size;
    }

    public int getParticleSpeed(){
        int speed = 1;
        return  speed;
    }

    public int getParticleMaxLife(){
        int maxLife = 20;
        return maxLife;
    }
}
