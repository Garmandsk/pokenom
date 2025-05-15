package main;

import entity.Entity;

public class CollisionChecker {
    GamePanel gameP;

    CollisionChecker(GamePanel gameP){
        this.gameP = gameP;
    }

    public void checkTile(Entity entity){
        int entityTopWorldY = entity.worldY + entity.solidArea.y;
        int entityBottomWorldY = entity.worldY + entity.solidArea.y + entity.solidArea.height;
        int entityLeftWorldX = entity.worldX + entity.solidArea.x;
        int entityRightWorldX = entity.worldX + entity.solidArea.x + entity.solidArea.width;

        int entityTopRow = entityTopWorldY /gameP.tileSize;
        int entityBottomRow = entityBottomWorldY / gameP.tileSize;
        int entityLeftCol = entityLeftWorldX/gameP.tileSize;
        int entityRightCol = entityRightWorldX/gameP.tileSize;

        int tileNum1, tileNum2;

        switch (entity.direction){
            case "up":
                entityTopRow = (entityTopWorldY - entity.speed)/gameP.tileSize;
                tileNum1 = gameP.tileM.mapTileNum[gameP.currentMap][entityLeftCol][entityTopRow];
                tileNum2 = gameP.tileM.mapTileNum[gameP.currentMap][entityRightCol][entityTopRow];

                if (gameP.tileM.tile[tileNum1].collision == true || gameP.tileM.tile[tileNum2].collision == true){
                    entity.collisionOn = true;
                }
                break;
            case "down":
                entityBottomRow = (entityBottomWorldY + entity.speed)/gameP.tileSize;
                tileNum1 = gameP.tileM.mapTileNum[gameP.currentMap][entityLeftCol][entityBottomRow];
                tileNum2 = gameP.tileM.mapTileNum[gameP.currentMap][entityRightCol][entityBottomRow];

                if (gameP.tileM.tile[tileNum1].collision == true || gameP.tileM.tile[tileNum2].collision == true){
                    entity.collisionOn = true;
                }
                break;
            case "left":
                entityLeftCol = (entityLeftWorldX - entity.speed)/gameP.tileSize;
                tileNum1 = gameP.tileM.mapTileNum[gameP.currentMap][entityLeftCol][entityTopRow];
                tileNum2 = gameP.tileM.mapTileNum[gameP.currentMap][entityLeftCol][entityBottomRow];

                if (gameP.tileM.tile[tileNum1].collision == true || gameP.tileM.tile[tileNum2].collision == true){
                    entity.collisionOn = true;
                }
                break;
            case "right":
                entityRightCol = (entityRightWorldX + entity.speed)/gameP.tileSize;
                tileNum1 = gameP.tileM.mapTileNum[gameP.currentMap][entityRightCol][entityTopRow];
                tileNum2 = gameP.tileM.mapTileNum[gameP.currentMap][entityRightCol][entityBottomRow];

                if (gameP.tileM.tile[tileNum1].collision == true || gameP.tileM.tile[tileNum2].collision == true){
                    entity.collisionOn = true;
                }
                break;

        }
    }

    public int checkObject(Entity entity, boolean player){
        int index = 999;

        for(int i = 0; i < gameP.obj[1].length; i++){
            if(gameP.obj[gameP.currentMap][i] != null){
                entity.solidArea.x += entity.worldX;
                entity.solidArea.y += entity.worldY;
                gameP.obj[gameP.currentMap][i].solidArea.x += gameP.obj[gameP.currentMap][i].worldX;
                gameP.obj[gameP.currentMap][i].solidArea.y += gameP.obj[gameP.currentMap][i].worldY;

                switch (entity.direction){
                    case "up":
                        entity.solidArea.y -= entity.speed;
                        break;
                    case "down":
                        entity.solidArea.y += entity.speed;
                        break;
                    case "left":
                        entity.solidArea.x -= entity.speed;
                        break;
                    case "right":
                        entity.solidArea.y += entity.speed;
                        break;
                }

                if (entity.solidArea.intersects(gameP.obj[gameP.currentMap][i].solidArea)){
                    if (gameP.obj[gameP.currentMap][i].collision == true){
                        entity.collisionOn = true;
                    }
                    if (player == true){
                        index = i;
                    }
                }

                entity.solidArea.x = entity.defaultSolidAreaX;
                entity.solidArea.y = entity.defaultSolidAreaY;
                gameP.obj[gameP.currentMap][i].solidArea.x = gameP.obj[gameP.currentMap][i].defaultSolidAreaX;
                gameP.obj[gameP.currentMap][i].solidArea.y = gameP.obj[gameP.currentMap][i].defaultSolidAreaY;
            }
        }
        return index;
    }

    public int checkEntity(Entity entity, Entity[][] target){
        int index = 999;

        for(int i = 0; i < target[1].length; i++){
            if(target[gameP.currentMap][i] != null){
                /* Yang Nabrak */
                entity.solidArea.x += entity.worldX;
                entity.solidArea.y += entity.worldY;

                /* Yang Ditabrak */
                target[gameP.currentMap][i].solidArea.x += target[gameP.currentMap][i].worldX;
                target[gameP.currentMap][i].solidArea.y += target[gameP.currentMap][i].worldY;

                switch (entity.direction){
                    case "up":
                        entity.solidArea.y -= entity.speed;
                        break;
                    case "down":
                        entity.solidArea.y += entity.speed;
                        break;
                    case "left":
                        entity.solidArea.x -= entity.speed;
                        break;
                    case "right":
                        entity.solidArea.x += entity.speed;
                        break;
                }

                if (entity.solidArea.intersects(target[gameP.currentMap][i].solidArea)){
                    if (target[gameP.currentMap][i] != entity){
                        entity.collisionOn = true;
                        index = i;
                    }
                }

                entity.solidArea.x = entity.defaultSolidAreaX;
                entity.solidArea.y = entity.defaultSolidAreaY;
                target[gameP.currentMap][i].solidArea.x = target[gameP.currentMap][i].defaultSolidAreaX;
                target[gameP.currentMap][i].solidArea.y = target[gameP.currentMap][i].defaultSolidAreaY;

            }
        }
        return index;
    }

    public boolean checkPlayer(Entity entity){

        boolean contactPlayer = false;

        /* Yang Nabrak */
        entity.solidArea.x += entity.worldX;
        entity.solidArea.y += entity.worldY;

        /* Yang Ditabrak */
        gameP.player.solidArea.x += gameP.player.worldX;
        gameP.player.solidArea.y += gameP.player.worldY;

        switch (entity.direction){
            case "up":
                entity.solidArea.y -= entity.speed;
                break;
            case "down":
                entity.solidArea.y += entity.speed;
                break;
            case "left":
                entity.solidArea.x -= entity.speed;
                break;
            case "right":
                entity.solidArea.y += entity.speed;
                break;
        }

        if (entity.solidArea.intersects(gameP.player.solidArea)){
            entity.collisionOn = true;
            contactPlayer = true;
        }

        entity.solidArea.x = entity.defaultSolidAreaX;
        entity.solidArea.y = entity.defaultSolidAreaY;
        gameP.player.solidArea.x = gameP.player.defaultSolidAreaX;
        gameP.player.solidArea.y = gameP.player.defaultSolidAreaY;

        return contactPlayer;
    }

}