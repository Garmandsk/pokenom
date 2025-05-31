package tile;

import main.GamePanel;
import main.UtilityTool;

import java.awt.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

public class TileManager {
    GamePanel gameP;
    public Tile[] tile;
    public int[][][] mapTileNum;
    boolean drawPath = true;
    ArrayList<String> fileNames = new ArrayList<>();
    ArrayList<String> collisionStatus = new ArrayList<>();

    public TileManager(GamePanel gameP){
        this.gameP = gameP;

        InputStream is = getClass().getResourceAsStream("/maps/tiledata.txt");
        BufferedReader br  = new BufferedReader(new InputStreamReader(is));

        /* Pengambilan nama file dan status collision */
        try {
            String line;
            while ((line = br.readLine()) != null){
                fileNames.add(line);
                collisionStatus.add(br.readLine());
            }
            br.close();

        } catch (IOException e){
            e.printStackTrace();
        }

        tile = new Tile[fileNames.size()];
        getTileImage();

        is = getClass().getResourceAsStream("/maps/worldMap.txt");
        br = new BufferedReader(new InputStreamReader(is));

        /* Pengambilan max world col dan row */
        try {
            String line = br.readLine();
            String[] maxTile = line.split(" ");

            gameP.maxWorldCol = maxTile.length;
            gameP.maxWorldRow = maxTile.length;
            this.mapTileNum = new int[gameP.maxMap][gameP.maxWorldCol][gameP.maxWorldRow];

            br.close();
        } catch (IOException e){
            e.printStackTrace();
        }

        loadMap("/maps/worldMap.txt", 0);
        loadMap("/maps/indoor01.txt", 1);
        loadMap("/maps/dungeon01.txt", 2);
        loadMap("/maps/dungeon02.txt", 3);

    }

    public void getTileImage(){
        UtilityTool uTool = new UtilityTool(gameP);

        for (int i = 0; i < fileNames.size(); i++){
            String fileName;
            boolean collision;

            // Get a file name
            fileName = fileNames.get(i);

            // Get a collison status
            collision = collisionStatus.get(i).equals("true");

            uTool.setUp(tile, i, fileName, collision);
        }

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
                g2d.drawImage(tile[tileNum].image, screenX , screenY, null);
            }
            worldCol++;

            if(worldCol == gameP.maxWorldCol){
                worldCol = 0;
                worldRow++;
            }
        }

        if (gameP.keyH.debug && drawPath){
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
