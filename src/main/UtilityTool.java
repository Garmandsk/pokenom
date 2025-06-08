package main;

import entity.monster.MON_SkeletonLord;
import font.Fonts;
import tile.Tile;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

public class UtilityTool {
    GamePanel gameP;

    public UtilityTool(){

    }

    public UtilityTool(GamePanel gameP){
        this.gameP = gameP;
    }

    public BufferedImage scaleImage(BufferedImage original, int width, int height){
        BufferedImage scaledImage = new BufferedImage(width, height, original.getType());
        Graphics2D g2d = scaledImage.createGraphics();
        g2d.drawImage(original, 0, 0, width, height, null);
        g2d.dispose();

        return scaledImage;
    }

    public void setUp(Tile[] tile, int index, String imagePath, boolean collision){

        try {
            tile[index] = new Tile();
            tile[index].image = ImageIO.read(getClass().getResourceAsStream("/tiles/" + imagePath));
            tile[index].image = scaleImage(tile[index].image, gameP.tileSize, gameP.tileSize);
            tile[index].collision = collision;
        } catch (IOException e){
            e.printStackTrace();
        }
    }

    public BufferedImage setUp(String imagePath, boolean giant) {
        BufferedImage scaledImage = null;

        if (giant) {
            try {
                scaledImage = ImageIO.read(getClass().getResourceAsStream(imagePath + ".png"));
                scaledImage = scaleImage(scaledImage, gameP.tileSize * MON_SkeletonLord.entityScaleSize, gameP.tileSize * MON_SkeletonLord.entityScaleSize);
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            try {
                scaledImage = ImageIO.read(getClass().getResourceAsStream(imagePath + ".png"));
                scaledImage = scaleImage(scaledImage, gameP.tileSize, gameP.tileSize);
            } catch (IOException e){
                e.printStackTrace();
            }
        }

        return scaledImage;
    }

    public BufferedImage setUp(String imagePath) {
        BufferedImage scaledImage = null;

        try {
            scaledImage = ImageIO.read(getClass().getResourceAsStream(imagePath + ".png"));
            scaledImage = scaleImage(scaledImage, gameP.tileSize, gameP.tileSize);
        } catch (IOException e){
            e.printStackTrace();
        }

        return scaledImage;
    }

    public BufferedImage setUpJFIF(String imagePath) {
        BufferedImage scaledImage = null;

        try {
            scaledImage = ImageIO.read(getClass().getResourceAsStream(imagePath + ".jfif"));
            scaledImage = scaleImage(scaledImage, gameP.tileSize, gameP.tileSize);
        } catch (IOException e){
            e.printStackTrace();
        }

        return scaledImage;
    }

    public BufferedImage setUp(String imagePath, int width, int height){
        BufferedImage scaledImage = null;

        try {
            scaledImage = ImageIO.read(getClass().getResourceAsStream(imagePath + ".png"));
            scaledImage = scaleImage(scaledImage, width, height);
        } catch (IOException e){
            e.printStackTrace();
        }

        return scaledImage;
    }

    public BufferedImage setUpJFIF(String imagePath, int width, int height){
        BufferedImage scaledImage = null;

        try {
            scaledImage = ImageIO.read(getClass().getResourceAsStream(imagePath + ".jfif"));
            scaledImage = scaleImage(scaledImage, width, height);
        } catch (IOException e){
            e.printStackTrace();
        }

        return scaledImage;
    }

    public void setUp(Fonts[] fonts, int index, String fontPath){

        try {
            InputStream is = getClass().getResourceAsStream("/fonts/" + fontPath + ".ttf");
            fonts[index] = new Fonts();
            fonts[index].font = Font.createFont(Font.TRUETYPE_FONT, is);
            GraphicsEnvironment.getLocalGraphicsEnvironment().registerFont(fonts[index].font);
        } catch (Exception e){
            e.printStackTrace();
        }
    }

    public void setUp(URL[] url, int index, String soundPath){
        url[index] = getClass().getResource("/sounds/" + soundPath + ".wav");
    }

    public void changeAlpha(Graphics2D g2d, float value){
        g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, value));
    }

}
