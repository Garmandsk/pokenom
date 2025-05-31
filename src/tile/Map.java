package tile;

import main.GamePanel;

import java.awt.*;
import java.awt.image.BufferedImage;

public class Map extends TileManager{
    GamePanel gameP;
    BufferedImage[] worldMap;
    public boolean miniMapOn;

    public Map(GamePanel gameP) {
        super(gameP);
        this.gameP = gameP;

        createWorldMap();
    }

    public void createWorldMap(){
        worldMap = new BufferedImage[gameP.maxMap];
        int worldMapWidth = gameP.tileSize * gameP.maxWorldCol;
        int worldMapHeight = gameP.tileSize * gameP.maxWorldRow;

        for (int i = 0; i < gameP.maxMap; i++){
            worldMap[i] = new BufferedImage(worldMapWidth, worldMapHeight, BufferedImage.TYPE_INT_ARGB);
            Graphics2D g2d = worldMap[i].createGraphics();

            int col = 0, row = 0;

            while (col < gameP.maxWorldCol && row < gameP.maxWorldRow){
                int tileNum = mapTileNum[i][col][row];
                int x = gameP.tileSize * col;
                int y = gameP.tileSize * row;
                g2d.drawImage(tile[tileNum].image, x, y, null);
                col++;

                if (col == gameP.maxWorldCol){
                    row++;
                    col = 0;
                }
            }

            g2d.dispose();
        }
    }

    public void drawFullMapScreen(Graphics2D g2d){

        // Draw Backgound
        g2d.setColor(Color.black);
        g2d.fillRect(0, 0, gameP.screenWidth, gameP.screenHeight);

        // Draw Map
        int width = 500;
        int height = 500;
        int x = gameP.screenWidth/2 - width/2;
        int y = gameP.screenHeight/2 - height/2;
        g2d.drawImage(worldMap[gameP.currentMap], x, y, width, height, null);

        // Draw Player
        double scale = (double)(gameP.tileSize * gameP.maxWorldCol)/width;
        int playerX = (int)(x + gameP.player.worldX/scale);
        int playerY = (int)(y + gameP.player.worldY/scale);
        int playerSize = (int)(gameP.tileSize/scale);
        g2d.drawImage(gameP.player.down1, playerX, playerY, playerSize, playerSize, null);

        // Hint
        g2d.setFont(gameP.ui.Cinzel.deriveFont(28f));
        g2d.setColor(Color.white);
        g2d.drawString("[M] Close",750, 550);

    }

    public void drawMiniMap(Graphics2D g2d){
        if (miniMapOn){
            // Draw Map
            int width = 200;
            int height = 200;
            int x = gameP.screenWidth - width - 50;
            int y = 50;
            g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.8f));
            g2d.drawImage(worldMap[gameP.currentMap], x, y, width, height, null);

            // Draw Player
            double scale = (double)(gameP.tileSize * gameP.maxWorldCol)/width;
            int playerX = (int)(x + gameP.player.worldX/scale);
            int playerY = (int)(y + gameP.player.worldY/scale);
            int playerSize = gameP.tileSize/4;
            g2d.drawImage(gameP.player.down1, playerX-3, playerY-3, playerSize, playerSize, null);
            g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1f));

        }
    }
}
