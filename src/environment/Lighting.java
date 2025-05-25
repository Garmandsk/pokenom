package environment;

import main.GamePanel;

import java.awt.*;
import java.awt.image.BufferedImage;

public class Lighting {
    GamePanel gameP;
    BufferedImage darknessFilter;

    public int dayCounter;
    public float filterAlpha = 0f;

    /* Day State */
    public final int dawnState = 0;
    public final int dayState = 1;
    public final int duskState = 2;
    public final int nightState = 3;
    public int currentDayState = dayState ;

    public Lighting(GamePanel gameP){
        this.gameP = gameP;
        setLightSource();
    }

    public void setLightSource(){

        // Create a buffered image
        darknessFilter = new BufferedImage(gameP.screenWidth, gameP.screenHeight, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2d = (Graphics2D)darknessFilter.getGraphics();

        if (gameP.player.currentLight == null){
            g2d.setColor(new Color(0, 0, 0.1f, 0.98f));
        } else {

            // Get the center x and y of the light circle
            int centerX = gameP.player.screenX + (gameP.tileSize)/2;
            int centerY = gameP.player.screenY + (gameP.tileSize)/2;

            // Create a gradation effect within the light circle
            Color[] color = new Color[12];
            float[] fraction = new float[12];

            color[0] = new Color(0, 0, 0.1f, 0.1f);
            color[1] = new Color(0, 0, 0.1f, 0.42f);
            color[2] = new Color(0, 0, 0.1f, 0.52f);
            color[3] = new Color(0, 0, 0.1f, 0.61f);
            color[4] = new Color(0, 0, 0.1f, 0.69f);
            color[5] = new Color(0, 0, 0.1f, 0.76f);
            color[6] = new Color(0, 0, 0.1f, 0.82f);
            color[7] = new Color(0, 0, 0.1f, 0.87f);
            color[8] = new Color(0, 0, 0.1f, 0.91f);
            color[9] = new Color(0, 0, 0.1f, 0.94f);
            color[10] = new Color(0, 0, 0.1f, 0.96f);
            color[11] = new Color(0, 0, 0.1f, 0.99f);

            fraction[0] = 0f;
            fraction[1] = 0.4f;
            fraction[2] = 0.5f;
            fraction[3] = 0.6f;
            fraction[4] = 0.65f;
            fraction[5] = 0.7f;
            fraction[6] = 0.75f;
            fraction[7] = 0.8f;
            fraction[8] = 0.85f;
            fraction[9] = 0.9f;
            fraction[10] = 0.95f;
            fraction[11] = 1f;

            // Create a gradation paint settings for the light circle
            RadialGradientPaint gPaint = new RadialGradientPaint(centerX, centerY, gameP.player.currentLight.lightRadius, fraction, color);
            // Set the gradient data on g2d
            g2d.setPaint(gPaint);
        }

        g2d.fillRect(0, 0,  gameP.screenWidth, gameP.screenHeight);
        g2d.dispose();
    }

    public void resetDay(){
        currentDayState = dayState;
        filterAlpha = 0f;
    }

    public void update(){
        if (gameP.player.lightUpdated) {
            setLightSource();
            gameP.player.lightUpdated = false;
        }

        if (currentDayState == dawnState){
            filterAlpha -= 0.001f;

            if (filterAlpha < 0f) {
                filterAlpha = 0f;
                currentDayState = dayState;
            }
        }

        if (currentDayState == dayState){
            dayCounter++;

            if (dayCounter > 600){
                currentDayState = duskState;
                dayCounter = 0;
            }
        }

        if (currentDayState == duskState){
            filterAlpha += 0.001f;

            if (filterAlpha > 1f) {
                filterAlpha = 1f;
                currentDayState = nightState;
            }

        }

        if (currentDayState == nightState){
            dayCounter++;

            if (dayCounter > 36000){
                currentDayState = dawnState;
                dayCounter = 0;
            }
        }
    }

    public void draw(Graphics2D g2d){
        g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, filterAlpha));
        g2d.drawImage(darknessFilter, 0, 0, null);
        g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER,1f));

        String situation = switch (currentDayState) {
            case dawnState -> "Dawn";
            case dayState -> "Day";
            case duskState -> "Dusk";
            case nightState -> "Night";
            default -> "";
        };

        g2d.setColor(Color.white);
        g2d.setFont(gameP.ui.Oswald.deriveFont(50f));
        g2d.drawString(situation, 800, 500);
    }
}
