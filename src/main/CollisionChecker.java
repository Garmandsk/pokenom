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
                tileNum1 = gameP.tileM.mapTileNum[entityLeftCol][entityTopRow];
                tileNum2 = gameP.tileM.mapTileNum[entityRightCol][entityTopRow];

                if (gameP.tileM.tile[tileNum1].collision == true || gameP.tileM.tile[tileNum2].collision == true){
                    entity.collisionOn = true;
                }
                break;
            case "down":
                entityBottomRow = (entityBottomWorldY + entity.speed)/gameP.tileSize;
                tileNum1 = gameP.tileM.mapTileNum[entityLeftCol][entityBottomRow];
                tileNum2 = gameP.tileM.mapTileNum[entityRightCol][entityBottomRow];

                if (gameP.tileM.tile[tileNum1].collision == true || gameP.tileM.tile[tileNum2].collision == true){
                    entity.collisionOn = true;
                }
                break;
            case "left":
                entityLeftCol = (entityLeftWorldX - entity.speed)/gameP.tileSize;
                tileNum1 = gameP.tileM.mapTileNum[entityLeftCol][entityTopRow];
                tileNum2 = gameP.tileM.mapTileNum[entityLeftCol][entityBottomRow];

                if (gameP.tileM.tile[tileNum1].collision == true || gameP.tileM.tile[tileNum2].collision == true){
                    entity.collisionOn = true;
                }
                break;
            case "right":
                entityRightCol = (entityRightWorldX + entity.speed)/gameP.tileSize;
                tileNum1 = gameP.tileM.mapTileNum[entityRightCol][entityTopRow];
                tileNum2 = gameP.tileM.mapTileNum[entityRightCol][entityBottomRow];

                if (gameP.tileM.tile[tileNum1].collision == true || gameP.tileM.tile[tileNum2].collision == true){
                    entity.collisionOn = true;
                }
                break;

        }
    }

    public int checkObject(Entity entity, boolean player){
        int index = 999;

        for(int i = 0; i < gameP.obj.length; i++){
            if(gameP.obj[i] != null){
                entity.solidArea.x += entity.worldX;
                entity.solidArea.y += entity.worldY;
                gameP.obj[i].solidArea.x += gameP.obj[i].worldX;
                gameP.obj[i].solidArea.y += gameP.obj[i].worldY;

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

                if (entity.solidArea.intersects(gameP.obj[i].solidArea)){
                    if (gameP.obj[i].collision == true){
                        entity.collisionOn = true;
                    }
                    if (player == true){
                        index = i;
                    }
                }

                entity.solidArea.x = entity.defaultSolidAreaX;
                entity.solidArea.y = entity.defaultSolidAreaY;
                gameP.obj[i].solidArea.x = gameP.obj[i].defaultSolidAreaX;
                gameP.obj[i].solidArea.y = gameP.obj[i].defaultSolidAreaY;
            }
        }
        return index;
    }

    public int checkEntity(Entity entity, Entity[] target){
        int index = 999;

        for(int i = 0; i < target.length; i++){
            if(target[i] != null){
                /* Yang Nabrak */
                entity.solidArea.x += entity.worldX;
                entity.solidArea.y += entity.worldY;

                /* Yang Ditabrak */
                target[i].solidArea.x += target[i].worldX;
                target[i].solidArea.y += target[i].worldY;

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

                if (entity.solidArea.intersects(target[i].solidArea)){
                    if (target[i] != entity){
                        entity.collisionOn = true;
                        index = i;
                    }
                }

                entity.solidArea.x = entity.defaultSolidAreaX;
                entity.solidArea.y = entity.defaultSolidAreaY;
                target[i].solidArea.x = target[i].defaultSolidAreaX;
                target[i].solidArea.y = target[i].defaultSolidAreaY;

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