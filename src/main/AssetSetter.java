package main;

import entity.MON_GreenSlime;
import entity.NPC_Merchant;
import entity.NPC_OldMan;
import object.*;
import tile_interactive.IT_DryTree;

public class AssetSetter {
    GamePanel gameP;

    AssetSetter(GamePanel gameP) {
        this.gameP = gameP;

    }

    public void setObject(){
        int mapNum = 0;
        int i = 0;

        gameP.obj[mapNum][i] = new CoinBronzeObject(gameP);
        gameP.obj[mapNum][i].worldX = gameP.tileSize * 26;
        gameP.obj[mapNum][i].worldY = gameP.tileSize * 21;
        i++;

        gameP.obj[mapNum][i] = new AxeObject(gameP);
        gameP.obj[mapNum][i].worldX = gameP.tileSize * 33;
        gameP.obj[mapNum][i].worldY = gameP.tileSize * 21;
        i++;

        gameP.obj[mapNum][i] = new ShieldBlueObject(gameP);
        gameP.obj[mapNum][i].worldX = gameP.tileSize * 34;
        gameP.obj[mapNum][i].worldY = gameP.tileSize * 21;
        i++;

        gameP.obj[mapNum][i] = new ConsumPotionRedObject(gameP);
        gameP.obj[mapNum][i].worldX = gameP.tileSize * 23;
        gameP.obj[mapNum][i].worldY = gameP.tileSize * 25;
        i++;

        gameP.obj[mapNum][i] = new HeartObject(gameP);
        gameP.obj[mapNum][i].worldX = gameP.tileSize * 24;
        gameP.obj[mapNum][i].worldY = gameP.tileSize * 26;
        i++;

        gameP.obj[mapNum][i] = new ManaCrystalObject(gameP);
        gameP.obj[mapNum][i].worldX = gameP.tileSize * 24;
        gameP.obj[mapNum][i].worldY = gameP.tileSize * 27;
        i++;
    }

    public void setNPC(){
        int mapNum = 0;
        int i = 0;

        gameP.npc[mapNum][i] = new NPC_OldMan(gameP);
        gameP.npc[mapNum][i].worldX = 10 * gameP.tileSize;
        gameP.npc[mapNum][i].worldY = 12 * gameP.tileSize;
        i++;

        mapNum = 1;
        i = 0;

        gameP.npc[mapNum][i] = new NPC_Merchant(gameP);
        gameP.npc[mapNum][i].worldX = 12 * gameP.tileSize;
        gameP.npc[mapNum][i].worldY = 7 * gameP.tileSize;
    }

    public void setMonster(){
        int mapNum = 0;
        int i = 0;

        gameP.monster[mapNum][i] = new MON_GreenSlime(gameP);
        gameP.monster[mapNum][i].worldX = 23 * gameP.tileSize;
        gameP.monster[mapNum][i].worldY = 39 * gameP.tileSize;
        i++;

        gameP.monster[mapNum][i] = new MON_GreenSlime(gameP);
        gameP.monster[mapNum][i].worldX = 24 * gameP.tileSize;
        gameP.monster[mapNum][i].worldY = 38 * gameP.tileSize;
        i++;
    }

    public void setInteractiveTile(){
        int mapNum = 0;
        int i = 0;

        gameP.iTile[mapNum][i] = new IT_DryTree(gameP, 16, 23); i++;
        gameP.iTile[mapNum][i] = new IT_DryTree(gameP, 17, 23); i++;
        gameP.iTile[mapNum][i] = new IT_DryTree(gameP, 18, 23); i++;
        gameP.iTile[mapNum][i] = new IT_DryTree(gameP, 19, 23); i++;
        gameP.iTile[mapNum][i] = new IT_DryTree(gameP, 20, 23); i++;
        gameP.iTile[mapNum][i] = new IT_DryTree(gameP, 21, 23); i++;

    }
}
