package net.retronixmc.coins.config;

import net.retronixmc.coins.gui.GUIItem;
import net.retronixmc.coins.mobdrops.MobDrop;
import org.bukkit.entity.EntityType;

import java.util.HashMap;
import java.util.List;

public class ConfigData {
    // messages
    public static String spawnerAmountOnRightClick;
    public static String cannotPlaceSpawner;
    public static String mobcoinObtainActionBar;
    public static String totalXp;
    public static String totalCoins;
    public static String notEnoughXp;
    public static String notEnoughCoins;
    public static String buyItem;
    public static String notValidPlayer;
    public static String notValidNumber;
    public static String playerTotalCoins;
    public static String notPlayer;
    public static List<String> help;
    // variables
    public static int spawnerRadius;
    public static boolean isSpawnerMessageEnabled;
    public static boolean isSpawnerPlacementNerfEnabled;
    public static boolean disableMobAIOnSpawn;

    public static boolean isXpToCoinShopEnabled;
    public static String shopMenuName;
    public static int rows;
    public static List<GUIItem> shopFillerItems;

}
