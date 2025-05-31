package entity.npc;

import entity.Entity;
import main.GamePanel;
import object.consum.ConsumPotionRedObject;
import object.consum.KeyObject;
import object.consum.TentObject;
import object.equipment.AxeObject;
import object.equipment.ShieldBlueObject;
import object.equipment.ShieldWoodObject;
import object.equipment.SwordNormalObject;

public class NPC_Merchant extends Entity {
    public static final String npcName = "Merchant";

    public NPC_Merchant(GamePanel gameP){
        super(gameP);

        type = npcType;
        name = npcName;
        direction = "down";
        speed = 1;
        getMerchantImage();
        setDialogue();
        setItems();
    }

    public void getMerchantImage(){
//        System.out.println("=====OldMan=====");
//        System.out.println("Solid Area X: " + this.solidArea.x);
//        System.out.println("Solid Area Y: " + this.solidArea.y);
//        System.out.println("Solid Area Width: " + this.solidArea.width);
//        System.out.println("Solid Area Height: " + this.solidArea.height);

        up1 = uTool.setUp("/npc/merchant_down_1");
        up2 = uTool.setUp("/npc/merchant_down_2");
        down1 = uTool.setUp("/npc/merchant_down_1");
        down2 = uTool.setUp("/npc/merchant_down_2");
        left1 = uTool.setUp("/npc/merchant_down_1");
        left2 = uTool.setUp("/npc/merchant_down_2");
        right1 = uTool.setUp("/npc/merchant_down_1");
        right2 = uTool.setUp("/npc/merchant_down_2");
    }

    public void setDialogue(){
        dialogues[0][0] = "Maling atau Pembeli\n" +
                "Kau yang mana ?";
        dialogues[1][0] = "Sampai Jumpa!";
        dialogues[2][0] = "Coin tidak cukup";
        dialogues[3][0] = "Inventory Penuh";
        dialogues[4][0] = "You can't sell an \nequipped item!";

    }

    public void setItems(){
        inventory.add(new ConsumPotionRedObject(gameP));
        inventory.add(new AxeObject(gameP));
        inventory.add(new SwordNormalObject(gameP));
        inventory.add(new KeyObject(gameP));
        inventory.add(new ShieldBlueObject(gameP));
        inventory.add(new ShieldWoodObject(gameP));
        inventory.add(new TentObject(gameP));

    }

    public void speak(){
        gameP.gameState = gameP.tradeState;
        gameP.ui.npc = this;
    }
}
