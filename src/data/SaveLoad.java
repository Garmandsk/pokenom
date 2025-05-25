package data;

import entity.Entity;
import main.GamePanel;
import object.*;

import java.io.*;

public class SaveLoad {
    GamePanel gameP;
    public DataStorage dataStorage = new DataStorage();

    public SaveLoad(GamePanel gameP){
        this.gameP = gameP;
    }

    public boolean haveData(){
        return dataStorage.level >= 1;
    }

    public void save(){
        try {
            ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream("save.dat"));

            // Kosongkan dataStorage dulu
            dataStorage.itemNames.clear();
            dataStorage.itemAmounts.clear();

            /* Player Stats */
            dataStorage.elementType = gameP.player.elementType;
            dataStorage.level = gameP.player.level;
            dataStorage.maxLife = gameP.player.maxLife;
            dataStorage.life = gameP.player.life;
            dataStorage.maxMana = gameP.player.maxMana;
            dataStorage.mana = gameP.player.mana;
            dataStorage.strength = gameP.player.strength;
            dataStorage.dexterity = gameP.player.dexterity;
            dataStorage.exp = gameP.player.exp;
            dataStorage.nextLevelExp = gameP.player.nextLevelExp;
            dataStorage.coin = gameP.player.coin;

            /* Player Inventory */
            for (int i = 0; i < gameP.player.inventory.size(); i++){
                dataStorage.itemNames.add(gameP.player.inventory.get(i).name);
                dataStorage.itemAmounts.add(gameP.player.inventory.get(i).amount);
            }

//            System.out.println("Inventory: " + gameP.player.inventory.size());
//            System.out.println("Save: " + dataStorage.itemNames.size());


            /* Player Equipment */
            dataStorage.currentWeaponSlot = gameP.player.getCurrentWeaponSlot();
            dataStorage.currentShieldSlot = gameP.player.getCurrentShieldSlot();

            /* Objects On Map */
            dataStorage.mapObjectNames = new String[gameP.maxMap][gameP.obj[1].length];
            dataStorage.mapObjectLootNames = new String[gameP.maxMap][gameP.obj[1].length];
            dataStorage.mapObjectWorldX = new int[gameP.maxMap][gameP.obj[1].length];
            dataStorage.mapObjectWorldY = new int[gameP.maxMap][gameP.obj[1].length];
            dataStorage.mapObjectOpened = new boolean[gameP.maxMap][gameP.obj[1].length];

            for (int mapNum = 0; mapNum < gameP.maxMap; mapNum++){
                for (int i = 0; i < gameP.obj[1].length; i++){
                    if (gameP.obj[mapNum][i] == null) dataStorage.mapObjectNames[mapNum][i] = "NA";
                    else {
                        dataStorage.mapObjectNames[mapNum][i] = gameP.obj[mapNum][i].name;
                        dataStorage.mapObjectWorldX[mapNum][i] = gameP.obj[mapNum][i].worldX;
                        dataStorage.mapObjectWorldY[mapNum][i] = gameP.obj[mapNum][i].worldY;
                        if (gameP.obj[mapNum][i].loot != null) dataStorage.mapObjectLootNames[mapNum][i] = gameP.obj[mapNum][i].loot.name;
                        dataStorage.mapObjectOpened[mapNum][i] = gameP.obj[mapNum][i].opened;
                    }
                }
            }

            // Write the data
            oos.writeObject(dataStorage);

        } catch (IOException e){
            e.printStackTrace();
        }
    }

    public void load(){
        try {
            ObjectInputStream ois = new ObjectInputStream(new FileInputStream("save.dat"));

            // Read the data
            dataStorage = (DataStorage)ois.readObject();

            gameP.player.elementType = dataStorage.elementType;
            gameP.player.level = dataStorage.level;
            gameP.player.maxLife = dataStorage.maxLife;
            gameP.player.life = dataStorage.life;
            gameP.player.maxMana = dataStorage.maxMana;
            gameP.player.mana = dataStorage.mana;
            gameP.player.strength = dataStorage.strength;
            gameP.player.dexterity = dataStorage.dexterity;
            gameP.player.exp = dataStorage.exp;
            gameP.player.nextLevelExp = dataStorage.nextLevelExp;
            gameP.player.coin = dataStorage.coin;

//            System.out.println("Load: " + dataStorage.itemNames.size());

            /* Player Inventory */
            gameP.player.inventory.clear();
            for (int i = 0; i < dataStorage.itemNames.size(); i++){
                gameP.player.inventory.add(gameP.entGen.getObject(dataStorage.itemNames.get(i)));
                gameP.player.inventory.get(i).amount = dataStorage.itemAmounts.get(i);
            }

            /* Player Equipment */
            gameP.player.currentWeapon = gameP.player.inventory.get(dataStorage.currentWeaponSlot);
            gameP.player.currentShield = gameP.player.inventory.get(dataStorage.currentShieldSlot);
            gameP.player.getAttack();
            gameP.player.getDefense();
            gameP.player.getAttackImage();

            /* Object on map */
            for (int mapNum = 0; mapNum < gameP.maxMap; mapNum++){
                for (int i = 0; i < gameP.obj[1].length; i++){
//                    System.out.println(dataStorage.mapObjectNames[mapNum][i]);
//                    System.out.println(dataStorage.mapObjectWorldX[mapNum][i]/gameP.tileSize);
//                    System.out.println(dataStorage.mapObjectWorldY[mapNum][i]/gameP.tileSize);

                    if (dataStorage.mapObjectNames[mapNum][i].equals("NA")) gameP.obj[mapNum][i] = null;
                    else {
//                        System.out.println(getObject(dataStorage.mapObjectNames[mapNum][i]));
                        gameP.obj[mapNum][i] = gameP.entGen.getObject(dataStorage.mapObjectNames[mapNum][i]);
                        gameP.obj[mapNum][i].worldX = dataStorage.mapObjectWorldX[mapNum][i];
                        gameP.obj[mapNum][i].worldY = dataStorage.mapObjectWorldY[mapNum][i];
                        if (dataStorage.mapObjectLootNames[mapNum][i] != null) gameP.obj[mapNum][i].loot = gameP.entGen.getObject(dataStorage.mapObjectLootNames[mapNum][i]);
                        gameP.obj[mapNum][i].opened = dataStorage.mapObjectOpened[mapNum][i];
                        if (gameP.obj[mapNum][i].opened) gameP.obj[mapNum][i].down1 = gameP.obj[mapNum][i].image2;
                    }
                }
            }

        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }


    }
}
