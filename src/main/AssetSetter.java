package main;

import data.Progress;
import entity.monster.*;
import entity.npc.NPC_BigRock;
import entity.npc.NPC_Merchant;
import entity.npc.NPC_OldMan;
import object.consum.CONSUM_OBJ_PotionRed;
import object.consum.CONSUM_OBJ_Key;
import object.consum.CONSUM_OBJ_Tent;
import object.equipment.OBJ_Axe;
import object.equipment.OBJ_Lantern;
import object.equipment.OBJ_Pickaxe;
import object.equipment.OBJ_ShieldBlue;
import object.obstacle.OBS_OBJ_Chest;
import object.obstacle.OBS_OBJ_DoorIron;
import object.obstacle.OBS_OBJ_Door;
import object.pickupOnly.PU_OBJ_BlueHeart;
import object.pickupOnly.PU_OBJ_CoinBronze;
import object.pickupOnly.PU_OBJ_Heart;
import object.pickupOnly.PU_OBJ_ManaCrystal;
import tile_interactive.IT_DestructibleWall;
import tile_interactive.IT_DryTree;
import tile_interactive.IT_MetalPlate;

public class AssetSetter {
    GamePanel gameP;

    AssetSetter(GamePanel gameP) {
        this.gameP = gameP;

    }

    public void setObject(){
        int mapNum = 0;
        int i = 0;

        gameP.obj[mapNum][i] = new PU_OBJ_CoinBronze(gameP);
        gameP.obj[mapNum][i].worldX = gameP.tileSize * 26;
        gameP.obj[mapNum][i].worldY = gameP.tileSize * 21;
        i++;

        gameP.obj[mapNum][i] = new OBJ_Axe(gameP);
        gameP.obj[mapNum][i].worldX = gameP.tileSize * 33;
        gameP.obj[mapNum][i].worldY = gameP.tileSize * 21;
        i++;

        gameP.obj[mapNum][i] = new OBJ_ShieldBlue(gameP);
        gameP.obj[mapNum][i].worldX = gameP.tileSize * 34;
        gameP.obj[mapNum][i].worldY = gameP.tileSize * 21;
        i++;

        gameP.obj[mapNum][i] = new OBJ_Lantern(gameP);
        gameP.obj[mapNum][i].worldX = gameP.tileSize * 18;
        gameP.obj[mapNum][i].worldY = gameP.tileSize * 20;
        i++;

        gameP.obj[mapNum][i] = new CONSUM_OBJ_Tent(gameP);
        gameP.obj[mapNum][i].worldX = gameP.tileSize * 19;
        gameP.obj[mapNum][i].worldY = gameP.tileSize * 20;
        i++;

        gameP.obj[mapNum][i] = new OBS_OBJ_Door(gameP);
        gameP.obj[mapNum][i].worldX = gameP.tileSize * 25;
        gameP.obj[mapNum][i].worldY = gameP.tileSize * 21;
        i++;

        gameP.obj[mapNum][i] = new CONSUM_OBJ_PotionRed(gameP);
        gameP.obj[mapNum][i].worldX = gameP.tileSize * 23;
        gameP.obj[mapNum][i].worldY = gameP.tileSize * 25;
        i++;

        gameP.obj[mapNum][i] = new PU_OBJ_Heart(gameP);
        gameP.obj[mapNum][i].worldX = gameP.tileSize * 24;
        gameP.obj[mapNum][i].worldY = gameP.tileSize * 26;
        i++;

        gameP.obj[mapNum][i] = new PU_OBJ_ManaCrystal(gameP);
        gameP.obj[mapNum][i].worldX = gameP.tileSize * 24;
        gameP.obj[mapNum][i].worldY = gameP.tileSize * 27;
        i++;

        gameP.obj[mapNum][i] = new OBS_OBJ_Door(gameP);
        gameP.obj[mapNum][i].worldX = gameP.tileSize * 14;
        gameP.obj[mapNum][i].worldY = gameP.tileSize * 28;
        i++;

        gameP.obj[mapNum][i] = new OBS_OBJ_Door(gameP);
        gameP.obj[mapNum][i].worldX = gameP.tileSize * 12;
        gameP.obj[mapNum][i].worldY = gameP.tileSize * 12;
        i++;

        mapNum = 1;
        i = 0;

        gameP.obj[mapNum][i] = new OBS_OBJ_Chest(gameP);
        gameP.obj[mapNum][i].setLoot(new CONSUM_OBJ_Key(gameP));
        gameP.obj[mapNum][i].worldX = gameP.tileSize * 10;
        gameP.obj[mapNum][i].worldY = gameP.tileSize * 7;
        i++;

        gameP.obj[mapNum][i] = new OBS_OBJ_Chest(gameP);
        gameP.obj[mapNum][i].setLoot(new OBJ_Axe(gameP));
        gameP.obj[mapNum][i].worldX = gameP.tileSize * 14;
        gameP.obj[mapNum][i].worldY = gameP.tileSize * 7;
        i++;

        mapNum = 2;
        i = 0;

        gameP.obj[mapNum][i] = new OBS_OBJ_Chest(gameP);
        gameP.obj[mapNum][i].setLoot(new OBJ_Pickaxe(gameP));
        gameP.obj[mapNum][i].worldX = gameP.tileSize * 40;
        gameP.obj[mapNum][i].worldY = gameP.tileSize * 41;
        i++;

        gameP.obj[mapNum][i] = new OBS_OBJ_Chest(gameP);
        gameP.obj[mapNum][i].setLoot(new CONSUM_OBJ_PotionRed(gameP));
        gameP.obj[mapNum][i].worldX = gameP.tileSize * 13;
        gameP.obj[mapNum][i].worldY = gameP.tileSize * 16;
        i++;

        gameP.obj[mapNum][i] = new OBS_OBJ_Chest(gameP);
        gameP.obj[mapNum][i].setLoot(new CONSUM_OBJ_PotionRed(gameP));
        gameP.obj[mapNum][i].worldX = gameP.tileSize * 26;
        gameP.obj[mapNum][i].worldY = gameP.tileSize * 34;
        i++;

        gameP.obj[mapNum][i] = new OBS_OBJ_Chest(gameP);
        gameP.obj[mapNum][i].setLoot(new CONSUM_OBJ_PotionRed(gameP));
        gameP.obj[mapNum][i].worldX = gameP.tileSize * 27;
        gameP.obj[mapNum][i].worldY = gameP.tileSize * 15;
        i++;

        gameP.obj[mapNum][i] = new OBS_OBJ_DoorIron(gameP);
        gameP.obj[mapNum][i].worldX = gameP.tileSize * 18;
        gameP.obj[mapNum][i].worldY = gameP.tileSize * 23;
        i++;

        mapNum = 3;
        i = 0;

        gameP.obj[mapNum][i] = new OBS_OBJ_DoorIron(gameP);
        gameP.obj[mapNum][i].worldX = gameP.tileSize * 25;
        gameP.obj[mapNum][i].worldY = gameP.tileSize * 15;
        i++;

        gameP.obj[mapNum][i] = new PU_OBJ_BlueHeart(gameP);
        gameP.obj[mapNum][i].worldX = gameP.tileSize * 25;
        gameP.obj[mapNum][i].worldY = gameP.tileSize * 8;
        i++;
    }

    public void setNPC(){
        int mapNum = 0;
        int i = 0;

        gameP.npc[mapNum][i] = new NPC_OldMan(gameP);
        gameP.npc[mapNum][i].worldX = 41 * gameP.tileSize;
        gameP.npc[mapNum][i].worldY = 7 * gameP.tileSize;
        i++;

        mapNum = 1;
        i = 0;

        gameP.npc[mapNum][i] = new NPC_Merchant(gameP);
        gameP.npc[mapNum][i].worldX = 12 * gameP.tileSize;
        gameP.npc[mapNum][i].worldY = 7 * gameP.tileSize;
        i++;

        mapNum = 2;
        i = 0;

        gameP.npc[mapNum][i] = new NPC_BigRock(gameP);
        gameP.npc[mapNum][i].worldX = 20 * gameP.tileSize;
        gameP.npc[mapNum][i].worldY = 25 * gameP.tileSize;
        i++;

        gameP.npc[mapNum][i] = new NPC_BigRock(gameP);
        gameP.npc[mapNum][i].worldX = 11 * gameP.tileSize;
        gameP.npc[mapNum][i].worldY = 18 * gameP.tileSize;
        i++;

        gameP.npc[mapNum][i] = new NPC_BigRock(gameP);
        gameP.npc[mapNum][i].worldX = 23 * gameP.tileSize;
        gameP.npc[mapNum][i].worldY = 14 * gameP.tileSize;
        i++;
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

        gameP.monster[mapNum][i] = new MON_RedSlime(gameP);
        gameP.monster[mapNum][i].worldX = 37 * gameP.tileSize;
        gameP.monster[mapNum][i].worldY = 10 * gameP.tileSize;
        i++;

        gameP.monster[mapNum][i] = new MON_RedSlime(gameP);
        gameP.monster[mapNum][i].worldX = 37 * gameP.tileSize;
        gameP.monster[mapNum][i].worldY = 9 * gameP.tileSize;
        i++;

        gameP.monster[mapNum][i] = new MON_Orc(gameP);
        gameP.monster[mapNum][i].worldX = 12 * gameP.tileSize;
        gameP.monster[mapNum][i].worldY = 33 * gameP.tileSize;
        i++;

        mapNum = 2;
        i = 0;

        gameP.monster[mapNum][i] = new MON_Bat(gameP);
        gameP.monster[mapNum][i].worldX = 34 * gameP.tileSize;
        gameP.monster[mapNum][i].worldY = 39 * gameP.tileSize;
        i++;

        gameP.monster[mapNum][i] = new MON_Bat(gameP);
        gameP.monster[mapNum][i].worldX = 36 * gameP.tileSize;
        gameP.monster[mapNum][i].worldY = 25 * gameP.tileSize;
        i++;

        gameP.monster[mapNum][i] = new MON_Bat(gameP);
        gameP.monster[mapNum][i].worldX = 39 * gameP.tileSize;
        gameP.monster[mapNum][i].worldY = 26 * gameP.tileSize;
        i++;

        mapNum = 3;
        i = 0;

        if (Progress.skeletonLordDefeated == false){
            gameP.monster[mapNum][i] = new MON_SkeletonLord(gameP);
            gameP.monster[mapNum][i].worldX = 23 * gameP.tileSize;
            gameP.monster[mapNum][i].worldY = 16 * gameP.tileSize;
            i++;
        }
    }

    public void setInteractiveTile(){
        int mapNum = 0;
        int i = 0;

        gameP.iTile[mapNum][i] = new IT_DryTree(gameP, 21, 23); i++;

        mapNum = 2;
        i = 0;

        gameP.iTile[mapNum][i] = new IT_DestructibleWall(gameP, 18, 30); i++;
        gameP.iTile[mapNum][i] = new IT_DestructibleWall(gameP, 17, 31); i++;
        gameP.iTile[mapNum][i] = new IT_DestructibleWall(gameP, 17, 32); i++;
        gameP.iTile[mapNum][i] = new IT_DestructibleWall(gameP, 17, 34); i++;
        gameP.iTile[mapNum][i] = new IT_DestructibleWall(gameP, 18, 34); i++;
        gameP.iTile[mapNum][i] = new IT_DestructibleWall(gameP, 18, 33); i++;
        gameP.iTile[mapNum][i] = new IT_DestructibleWall(gameP, 10, 22); i++;
        gameP.iTile[mapNum][i] = new IT_DestructibleWall(gameP, 10, 24); i++;
        gameP.iTile[mapNum][i] = new IT_DestructibleWall(gameP, 38, 18); i++;
        gameP.iTile[mapNum][i] = new IT_DestructibleWall(gameP, 38, 19); i++;
        gameP.iTile[mapNum][i] = new IT_DestructibleWall(gameP, 38, 20); i++;
        gameP.iTile[mapNum][i] = new IT_DestructibleWall(gameP, 38, 21); i++;
        gameP.iTile[mapNum][i] = new IT_DestructibleWall(gameP, 18, 13); i++;
        gameP.iTile[mapNum][i] = new IT_DestructibleWall(gameP, 18, 14); i++;
        gameP.iTile[mapNum][i] = new IT_DestructibleWall(gameP, 22, 28); i++;
        gameP.iTile[mapNum][i] = new IT_DestructibleWall(gameP, 30, 28); i++;
        gameP.iTile[mapNum][i] = new IT_DestructibleWall(gameP, 22, 28); i++;

        gameP.iTile[mapNum][i] = new IT_MetalPlate(gameP, 20, 22); i++;
        gameP.iTile[mapNum][i] = new IT_MetalPlate(gameP, 8, 17); i++;
        gameP.iTile[mapNum][i] = new IT_MetalPlate(gameP, 39, 31); i++;

    }
}
