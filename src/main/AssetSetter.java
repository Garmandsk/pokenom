package main;

import entity.MON_GreenSlime;
import entity.NPC_OldMan;
import object.*;

public class AssetSetter {
    GamePanel gameP;

    AssetSetter(GamePanel gameP) {
        this.gameP = gameP;

    }

    public void setObject(){
        int i = 0;

        gameP.obj[i] = new CoinBronzeObject(gameP);
        gameP.obj[i].worldX = gameP.tileSize * 25;
        gameP.obj[i].worldY = gameP.tileSize * 23;
        i++;

        gameP.obj[i] = new CoinBronzeObject(gameP);
        gameP.obj[i].worldX = gameP.tileSize * 21;
        gameP.obj[i].worldY = gameP.tileSize * 19;
        i++;

        gameP.obj[i] = new CoinBronzeObject(gameP);
        gameP.obj[i].worldX = gameP.tileSize * 26;
        gameP.obj[i].worldY = gameP.tileSize * 21;
        i++;

        gameP.obj[i] = new AxeObject(gameP);
        gameP.obj[i].worldX = gameP.tileSize * 33;
        gameP.obj[i].worldY = gameP.tileSize * 21;
        i++;

        gameP.obj[i] = new ShieldBlueObject(gameP);
        gameP.obj[i].worldX = gameP.tileSize * 34;
        gameP.obj[i].worldY = gameP.tileSize * 21;
        i++;

        gameP.obj[i] = new ConsumPotionRedObject(gameP);
        gameP.obj[i].worldX = gameP.tileSize * 23;
        gameP.obj[i].worldY = gameP.tileSize * 25;
        i++;

        gameP.obj[i] = new HeartObject(gameP);
        gameP.obj[i].worldX = gameP.tileSize * 24;
        gameP.obj[i].worldY = gameP.tileSize * 26;
        i++;

        gameP.obj[i] = new ManaCrystalObject(gameP);
        gameP.obj[i].worldX = gameP.tileSize * 24;
        gameP.obj[i].worldY = gameP.tileSize * 27;
        i++;
    }

    public void setNPC(){
        int i = 0;

        gameP.npc[i] = new NPC_OldMan(gameP);
        gameP.npc[i].worldX = 10 * gameP.tileSize;
        gameP.npc[i].worldY = 12 * gameP.tileSize;
        i++;
    }

    public void setMonster(){
        int i = 0;

        gameP.monster[i] = new MON_GreenSlime(gameP);
        gameP.monster[i].worldX = 23 * gameP.tileSize;
        gameP.monster[i].worldY = 39 * gameP.tileSize;
        i++;

        gameP.monster[i] = new MON_GreenSlime(gameP);
        gameP.monster[i].worldX = 24 * gameP.tileSize;
        gameP.monster[i].worldY = 38 * gameP.tileSize;
        i++;
    }
}
