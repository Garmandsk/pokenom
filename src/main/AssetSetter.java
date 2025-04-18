package main;

import object.BootsObject;
import object.ChestObject;
import object.DoorObject;
import object.KeyObject;

public class AssetSetter {
    GamePanel gameP;

    AssetSetter(GamePanel gameP) {
        this.gameP = gameP;

    }

    public void setObject(){
        gameP.obj[0] = new KeyObject();
        gameP.obj[0].worldX = 25 * gameP.tileSize;
        gameP.obj[0].worldY = 20 * gameP.tileSize;

        gameP.obj[1] = new KeyObject();
        gameP.obj[1].worldX = 25 * gameP.tileSize;
        gameP.obj[1].worldY = 30 * gameP.tileSize;

        gameP.obj[2] = new KeyObject();
        gameP.obj[2].worldX = 23 * gameP.tileSize;
        gameP.obj[2].worldY = 23 * gameP.tileSize;

        gameP.obj[3] = new KeyObject();
        gameP.obj[3].worldX = 39 * gameP.tileSize;
        gameP.obj[3].worldY = 7 * gameP.tileSize;

        // Atas
        gameP.obj[4] = new DoorObject();
        gameP.obj[4].worldX = 25 * gameP.tileSize;
        gameP.obj[4].worldY = 22 * gameP.tileSize;

        // Bawah
        gameP.obj[5] = new DoorObject();
        gameP.obj[5].worldX = 25 * gameP.tileSize;
        gameP.obj[5].worldY = 28 * gameP.tileSize;

        // Kiri
        gameP.obj[6] = new DoorObject();
        gameP.obj[6].worldX = 22 * gameP.tileSize;
        gameP.obj[6].worldY = 25 * gameP.tileSize;

        // Kanan
        gameP.obj[7] = new DoorObject();
        gameP.obj[7].worldX = 28 * gameP.tileSize;
        gameP.obj[7].worldY = 25 * gameP.tileSize;

        gameP.obj[8] = new BootsObject();
        gameP.obj[8].worldX = 15 * gameP.tileSize;
        gameP.obj[8].worldY = 32 * gameP.tileSize;

        gameP.obj[9] = new ChestObject();
        gameP.obj[9].worldX = 27 * gameP.tileSize;
        gameP.obj[9].worldY = 23 * gameP.tileSize;
    }
}
