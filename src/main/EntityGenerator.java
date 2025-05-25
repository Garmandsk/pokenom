package main;

import entity.Entity;
import object.*;

public class EntityGenerator {
    GamePanel gameP;

    public EntityGenerator(GamePanel gameP){
        this.gameP = gameP;
    }

    public Entity getObject(String itemName){
        Entity obj = switch (itemName) {
            case AxeObject.objName -> new AxeObject(gameP);
            case BootsObject.objName -> new BootsObject(gameP);
            case ChestObject.objName -> new ChestObject(gameP);
            case CoinBronzeObject.objName -> new CoinBronzeObject(gameP);
            case ConsumPotionRedObject.objName -> new ConsumPotionRedObject(gameP);
            case DoorObject.objName -> new DoorObject(gameP);
            case FireballObject.objName -> new FireballObject(gameP);
            case HeartObject.objName -> new HeartObject(gameP);
            case KeyObject.objName -> new KeyObject(gameP);
            case LanternObject.objName -> new LanternObject(gameP);
            case ManaCrystalObject.objName -> new ManaCrystalObject(gameP);
            case RockObject.objName -> new RockObject(gameP);
            case ShieldBlueObject.objName -> new ShieldBlueObject(gameP);
            case ShieldWoodObject.objName -> new ShieldWoodObject(gameP);
            case SwordNormalObject.objName -> new SwordNormalObject(gameP);
            case TentObject.objName -> new TentObject(gameP);
            default -> null;
        };

        return obj;
    }
}
