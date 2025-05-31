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

        String direction = entity.direction;
        if (entity.knockback) direction = entity.knockbackDirection;

        switch (direction){
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

        String direction = entity.direction;
        if (entity.knockback) direction = entity.knockbackDirection;

        for(int i = 0; i < gameP.obj[1].length; i++){
            Entity targetObj = gameP.obj[gameP.currentMap][i];

            if(targetObj != null){
                entity.solidArea.x += entity.worldX;
                entity.solidArea.y += entity.worldY;
                targetObj.solidArea.x += targetObj.worldX;
                targetObj.solidArea.y += targetObj.worldY;

                switch (direction){
                    case "up" -> entity.solidArea.y -= entity.speed;
                    case "down" -> entity.solidArea.y += entity.speed;
                    case "left" -> entity.solidArea.x -= entity.speed;
                    case "right" -> entity.solidArea.y += entity.speed;
                }

                if (entity.solidArea.intersects(targetObj.solidArea)){
                    if (targetObj.collision) entity.collisionOn = true;
                    if (player) index = i;
                }

                entity.solidArea.x = entity.defaultSolidAreaX;
                entity.solidArea.y = entity.defaultSolidAreaY;
                targetObj.solidArea.x = targetObj.defaultSolidAreaX;
                targetObj.solidArea.y = targetObj.defaultSolidAreaY;
            }
        }

        return index;
    }

    public int checkEntity(Entity entity, Entity[][] target){
        int index = 999;

        String direction = entity.direction;
        if (entity.knockback) direction = entity.knockbackDirection;

        for(int i = 0; i < target[1].length; i++){
            Entity targetEntity = target[gameP.currentMap][i];
            if(targetEntity != null){

                /* Yang Nabrak */
                entity.solidArea.x += entity.worldX;
                entity.solidArea.y += entity.worldY;

                /* Yang Ditabrak */
                targetEntity.solidArea.x += targetEntity.worldX;
                targetEntity.solidArea.y += targetEntity.worldY;

                switch (direction){
                    case "up" -> entity.solidArea.y -= entity.speed;
                    case "down" -> entity.solidArea.y += entity.speed;
                    case "left" -> entity.solidArea.x -= entity.speed;
                    case "right" -> entity.solidArea.x += entity.speed;
                }

                if (entity.solidArea.intersects(targetEntity.solidArea)){
                    if (targetEntity != entity){
                        entity.collisionOn = true;
                        index = i;
                    }
                }

                entity.solidArea.x = entity.defaultSolidAreaX;
                entity.solidArea.y = entity.defaultSolidAreaY;
                targetEntity.solidArea.x = targetEntity.defaultSolidAreaX;
                targetEntity.solidArea.y = targetEntity.defaultSolidAreaY;

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
            case "up" -> entity.solidArea.y -= entity.speed;
            case "down" -> entity.solidArea.y += entity.speed;
            case "left" -> entity.solidArea.x -= entity.speed;
            case "right" -> entity.solidArea.y += entity.speed;
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