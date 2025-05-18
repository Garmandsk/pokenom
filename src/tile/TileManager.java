package tile;

import main.GamePanel;
import main.UtilityTool;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class TileManager {
    GamePanel gameP;
    public Tile[] tile;
    public int[][][] mapTileNum;
    boolean drawPath = true;

    public TileManager(GamePanel gameP){
        this.gameP = gameP;
        tile = new Tile[50];
        this.mapTileNum = new int[gameP.maxMap][gameP.maxWorldCol][gameP.maxWorldRow];
        getTileImage();
        loadMap("/maps/worldV3.txt", 0);
        loadMap("/maps/interior01.txt", 1);

    }

    public void getTileImage(){
        UtilityTool uTool = new UtilityTool(gameP);

        uTool.setUp(tile, 0, "grass00", false);
        uTool.setUp(tile, 1, "grass00", false);
        uTool.setUp(tile, 2, "grass00", false);
        uTool.setUp(tile, 3, "grass00", false);
        uTool.setUp(tile, 4, "grass00", false);
        uTool.setUp(tile, 5, "grass00", false);
        uTool.setUp(tile, 6, "grass00", false);
        uTool.setUp(tile, 7, "grass00", false);
        uTool.setUp(tile, 8, "grass00", false);
        uTool.setUp(tile, 9, "grass00", false);

        uTool.setUp(tile, 10, "grass00", false);
        uTool.setUp(tile, 11, "grass01", false);
        uTool.setUp(tile, 12, "water00", true);
        uTool.setUp(tile, 13, "water01", true);
        uTool.setUp(tile, 14, "water02", true);
        uTool.setUp(tile, 15, "water03", true);
        uTool.setUp(tile, 16, "water04", true);
        uTool.setUp(tile, 17, "water05", true);
        uTool.setUp(tile, 18, "water06", true);
        uTool.setUp(tile, 19, "water07", true);
        uTool.setUp(tile, 20, "water08", true);
        uTool.setUp(tile, 21, "water09", true);
        uTool.setUp(tile, 22, "water10", true);
        uTool.setUp(tile, 23, "water11", true);
        uTool.setUp(tile, 24, "water12", true);
        uTool.setUp(tile, 25, "water13", true);
        uTool.setUp(tile, 26, "road00", false);
        uTool.setUp(tile, 27, "road01", false);
        uTool.setUp(tile, 28, "road02", false);
        uTool.setUp(tile, 29, "road03", false);
        uTool.setUp(tile, 30, "road04", false);
        uTool.setUp(tile, 31, "road05", false);
        uTool.setUp(tile, 32, "road06", false);
        uTool.setUp(tile, 33, "road07", false);
        uTool.setUp(tile, 34, "road08", false);
        uTool.setUp(tile, 35, "road09", false);
        uTool.setUp(tile, 36, "road10", false);
        uTool.setUp(tile, 37, "road11", false);
        uTool.setUp(tile, 38, "road12", false);
        uTool.setUp(tile, 39, "earth", false);
        uTool.setUp(tile, 40, "wall", true);
        uTool.setUp(tile, 41, "tree", true);
        uTool.setUp(tile, 42, "hut", false);
        uTool.setUp(tile, 43, "floor01", false);
        uTool.setUp(tile, 44, "table01", true);

    }

    public void loadMap(String filePath, int map){
        try {
            InputStream inputS = getClass().getResourceAsStream(filePath);
            BufferedReader bufferedR = new BufferedReader(new InputStreamReader(inputS));

            int col = 0, row = 0;

            while (col < gameP.maxWorldCol && row < gameP.maxWorldCol){
                String line = bufferedR.readLine();

                while(col < gameP.maxWorldCol){
                    String numbers[] = line.split(" ");
                    int num = Integer.parseInt(numbers[col]);

                    mapTileNum[map][col][row] = num;
                    col++;
                }

                if(col == gameP.maxWorldCol){
                    col = 0;
                    row++;
                }
            }
            bufferedR.close();
        }catch (Exception e){

        }
    }

    public void draw(Graphics2D g2d){
        int worldCol = 0, worldRow = 0;

        while (worldCol < gameP.maxWorldCol && worldRow < gameP.maxWorldRow){
            int tileNum = mapTileNum[gameP.currentMap][worldCol][worldRow];
            int worldX = worldCol * gameP.tileSize;
            int worldY = worldRow * gameP.tileSize;
            int screenX = worldX - gameP.player.worldX + gameP.player.screenX;
            int screenY = worldY - gameP.player.worldY + gameP.player.screenY;

            /* Visible Area */
            if (    worldX + gameP.tileSize > gameP.player.worldX - gameP.player.screenX &&
                    worldX - gameP.tileSize < gameP.player.worldX + gameP.player.screenX &&
                    worldY + gameP.tileSize > gameP.player.worldY - gameP.player.screenY &&
                    worldY - gameP.tileSize < gameP.player.worldY + gameP.player.screenY    ){
//                System.out.println("\n\nWorld X: " + worldX);
//                System.out.println("Player World X: " + gameP.player.worldX);
//                System.out.println("Player Screen X: " + gameP.player.screenX);
//
//                System.out.println("\nWorld Y: " + worldY);
//                System.out.println("Player World Y: " + gameP.player.worldY);
//                System.out.println("Player Screen Y: " + gameP.player.screenY);

                g2d.drawImage(tile[tileNum].image, screenX , screenY, null);
            }
            worldCol++;

            if(worldCol == gameP.maxWorldCol){
                worldCol = 0;
                worldRow++;
            }
        }

        if (gameP.keyH.debug){
            if (drawPath){
                g2d.setColor(new Color(255, 0, 0, 70));

                for (int i = 0; i < gameP.pathF.pathList.size(); i++){
                    int worldX = gameP.pathF.pathList.get(i).col * gameP.tileSize;
                    int worldY = gameP.pathF.pathList.get(i).row * gameP.tileSize;
                    int screenX = worldX - gameP.player.worldX + gameP.player.screenX;
                    int screenY = worldY - gameP.player.worldY + gameP.player.screenY;

                    g2d.fillRect(screenX, screenY, gameP.tileSize, gameP.tileSize);
                }
            }
        }
    }
}
