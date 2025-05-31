package main;

import entity.Entity;
import entity.projectile.PRO_Fireball;
import entity.projectile.PRO_Rock;
import object.consum.CONSUM_OBJ_PotionRed;
import object.consum.CONSUM_OBJ_Key;
import object.consum.CONSUM_OBJ_Tent;
import object.equipment.*;
import object.obstacle.OBS_OBJ_Chest;
import object.obstacle.OBS_OBJ_DoorIron;
import object.obstacle.OBS_OBJ_Door;
import object.pickupOnly.PU_OBJ_BlueHeart;
import object.pickupOnly.PU_OBJ_CoinBronze;
import object.pickupOnly.PU_OBJ_Heart;
import object.pickupOnly.PU_OBJ_ManaCrystal;

public class EntityGenerator {
    GamePanel gameP;

    public EntityGenerator(GamePanel gameP){
        this.gameP = gameP;
    }

    public Entity getObject(String itemName){
        Entity obj = switch (itemName) {
            case OBJ_Axe.objName -> new OBJ_Axe(gameP);
            case PU_OBJ_BlueHeart.objName -> new PU_OBJ_BlueHeart(gameP);
            case OBJ_Boots.objName -> new OBJ_Boots(gameP);
            case OBS_OBJ_Chest.objName -> new OBS_OBJ_Chest(gameP);
            case PU_OBJ_CoinBronze.objName -> new PU_OBJ_CoinBronze(gameP);
            case CONSUM_OBJ_PotionRed.objName -> new CONSUM_OBJ_PotionRed(gameP);
            case OBS_OBJ_DoorIron.objName -> new OBS_OBJ_DoorIron(gameP);
            case OBS_OBJ_Door.objName -> new OBS_OBJ_Door(gameP);
            case PRO_Fireball.projectileName -> new PRO_Fireball(gameP);
            case PU_OBJ_Heart.objName -> new PU_OBJ_Heart(gameP);
            case CONSUM_OBJ_Key.objName -> new CONSUM_OBJ_Key(gameP);
            case OBJ_Lantern.objName -> new OBJ_Lantern(gameP);
            case PU_OBJ_ManaCrystal.objName -> new PU_OBJ_ManaCrystal(gameP);
            case OBJ_Pickaxe.objName -> new OBJ_Pickaxe(gameP);
            case PRO_Rock.proName -> new PRO_Rock(gameP);
            case OBJ_ShieldBlue.objName -> new OBJ_ShieldBlue(gameP);
            case OBJ_ShieldWood.objName -> new OBJ_ShieldWood(gameP);
            case OBJ_SwordNormal.objName -> new OBJ_SwordNormal(gameP);
            case CONSUM_OBJ_Tent.objName -> new CONSUM_OBJ_Tent(gameP);
            default -> null;
        };

        return obj;
    }
}
