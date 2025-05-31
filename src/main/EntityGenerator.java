package main;

import entity.Entity;
import entity.projectile.FireballObject;
import entity.projectile.RockObject;
import object.consum.ConsumPotionRedObject;
import object.consum.KeyObject;
import object.consum.TentObject;
import object.equipment.*;
import object.obstacle.ChestObject;
import object.obstacle.DoorIronObject;
import object.obstacle.DoorObject;
import object.pickupOnly.BlueHeartObject;
import object.pickupOnly.CoinBronzeObject;
import object.pickupOnly.HeartObject;
import object.pickupOnly.ManaCrystalObject;

public class EntityGenerator {
    GamePanel gameP;

    public EntityGenerator(GamePanel gameP){
        this.gameP = gameP;
    }

    public Entity getObject(String itemName){
        Entity obj = switch (itemName) {
            case AxeObject.objName -> new AxeObject(gameP);
            case BlueHeartObject.objName -> new BlueHeartObject(gameP);
            case BootsObject.objName -> new BootsObject(gameP);
            case ChestObject.objName -> new ChestObject(gameP);
            case CoinBronzeObject.objName -> new CoinBronzeObject(gameP);
            case ConsumPotionRedObject.objName -> new ConsumPotionRedObject(gameP);
            case DoorIronObject.objName -> new DoorIronObject(gameP);
            case DoorObject.objName -> new DoorObject(gameP);
            case FireballObject.projectileName -> new FireballObject(gameP);
            case HeartObject.objName -> new HeartObject(gameP);
            case KeyObject.objName -> new KeyObject(gameP);
            case LanternObject.objName -> new LanternObject(gameP);
            case ManaCrystalObject.objName -> new ManaCrystalObject(gameP);
            case PickaxeObject.objName -> new PickaxeObject(gameP);
            case RockObject.projectileName -> new RockObject(gameP);
            case ShieldBlueObject.objName -> new ShieldBlueObject(gameP);
            case ShieldWoodObject.objName -> new ShieldWoodObject(gameP);
            case SwordNormalObject.objName -> new SwordNormalObject(gameP);
            case TentObject.objName -> new TentObject(gameP);
            default -> null;
        };

        return obj;
    }
}
