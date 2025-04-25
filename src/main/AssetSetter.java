package main;

import entity.NPC_OldMan;
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

    }

    public void setNPC(){
        gameP.npc[0] = new NPC_OldMan(gameP);
        gameP.npc[0].worldX = 38 * gameP.tileSize;
        gameP.npc[0].worldY = 25 * gameP.tileSize;
    }
}
