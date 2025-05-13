package particle;

import java.awt.*;

public class EarthParticle extends Generator {

    public EarthParticle() {

    }

    public Color getParticleColor(){
        Color color = new Color(65, 50, 30);
        return color;
    }

    public int getParticleSize(){
        int size = 6;
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
