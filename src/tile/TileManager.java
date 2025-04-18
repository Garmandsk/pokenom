package tile;

import main.GamePanel;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class TileManager {
    GamePanel gameP;
    public Tile[] tile;
    public int[][] mapTileNum;

    public TileManager(GamePanel gameP){
        this.gameP = gameP;
        tile = new Tile[10];
        this.mapTileNum = new int[gameP.maxWorldCol][gameP.maxWorldRow];
        getTileImage();
        loadMap("/maps/worldMap.txt");
    }

    public void getTileImage(){
        try {

            /* Grass */
            tile[0] = new Tile();
            tile[0].image = ImageIO.read(getClass().getResourceAsStream("/tiles/grass.png"));

            /* Water */
            tile[1] = new Tile();
            tile[1].image = ImageIO.read(getClass().getResourceAsStream("/tiles/water1.png"));
            tile[1].collision = true;

            /* Wall */
            tile[2] = new Tile();
            tile[2].image = ImageIO.read(getClass().getResourceAsStream("/tiles/wall.png"));
            tile[2].collision = true;

            /* Earth */
            tile[3] = new Tile();
            tile[3].image = ImageIO.read(getClass().getResourceAsStream("/tiles/earth.png"));

            /* Tree */
            tile[4] = new Tile();
            tile[4].image = ImageIO.read(getClass().getResourceAsStream("/tiles/tree.png"));
            tile[4].collision = true;

            /* Sand */
            tile[5] = new Tile();
            tile[5].image = ImageIO.read(getClass().getResourceAsStream("/tiles/sand.png"));

        }catch (IOException e){
            e.printStackTrace();
        }
    }

    public void loadMap(String filePath){
        try {
            InputStream inputS = getClass().getResourceAsStream(filePath);
            BufferedReader bufferedR = new BufferedReader(new InputStreamReader(inputS));

            int col = 0, row = 0;

            while (col < gameP.maxWorldCol && row < gameP.maxWorldCol){
                String line = bufferedR.readLine();

                while(col < gameP.maxWorldCol){
                    String numbers[] = line.split(" ");
                    int num = Integer.parseInt(numbers[col]);

                    mapTileNum[col][row] = num;
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
            int tileNum = mapTileNum[worldCol][worldRow];
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

                g2d.drawImage(tile[tileNum].image, screenX , screenY, gameP.tileSize, gameP.tileSize, null);
            }
            worldCol++;

            if(worldCol == gameP.maxWorldCol){
                worldCol = 0;
                worldRow++;
            }
        }
    }
}
