package data;

import java.io.Serializable;
import java.util.ArrayList;

public class DataStorage implements Serializable {
    /* Player Stats */
    int elementType;
    public int level;
    int maxLife;
    int life;
    int maxMana;
    int mana;
    int strength;
    int dexterity;
    int exp;
    int nextLevelExp;
    int coin;

    /* Player Inventory */
    public ArrayList<String> itemId = new ArrayList<>();
    public ArrayList<String> itemNames = new ArrayList<>();
    public ArrayList<Integer> itemAmounts = new ArrayList<>();
    int currentWeaponSlot, currentShieldSlot;

    /* Objects On Map */
    String[][] mapObjectNames, mapObjectLootNames;
    int[][] mapObjectWorldX, mapObjectWorldY;
    boolean[][] mapObjectOpened;
}
