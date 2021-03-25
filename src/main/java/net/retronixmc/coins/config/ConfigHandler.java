package net.retronixmc.coins.config;

import net.retronixmc.coins.gui.GUIItem;
import net.retronixmc.coins.mobdrops.MobDrop;
import net.retronixmc.coins.utils.ItemBuilder;
import org.bukkit.Material;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.EntityType;
import org.bukkit.inventory.ItemStack;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ConfigHandler {
    public ConfigHandler() {
        retrieveData();
    }

    private File file = new File("plugins/RetronixCoins/", "config.yml");
    private YamlConfiguration data = YamlConfiguration.loadConfiguration(file);

    public void retrieveData() {
        ConfigData.spawnerAmountOnRightClick = data.getString("messages.spawnerAmountOnRightClick");
        ConfigData.cannotPlaceSpawner = data.getString("messages.cannotPlaceSpawner");
        ConfigData.mobcoinObtainActionBar = data.getString("messages.mobcoinObtainActionBar");
        ConfigData.totalXp = data.getString("messages.totalXp");
        ConfigData.totalCoins = data.getString("messages.totalCoins");
        ConfigData.notEnoughXp = data.getString("messages.notEnoughXp");
        ConfigData.notEnoughCoins = data.getString("messages.notEnoughCoins");
        ConfigData.buyItem = data.getString("messages.buyItem");
        ConfigData.notValidPlayer = data.getString("messages.notValidPlayer");
        ConfigData.notValidNumber = data.getString("messages.notValidNumber");
        ConfigData.playerTotalCoins = data.getString("messages.playerTotalCoins");
        ConfigData.notPlayer = data.getString("messages.notPlayer");
        ConfigData.help = data.getStringList("messages.help");

        ConfigData.spawnerRadius = data.getInt("spawners.radius");
        ConfigData.isSpawnerMessageEnabled = data.getBoolean("spawners.messageEnabled");
        ConfigData.isSpawnerPlacementNerfEnabled = data.getBoolean("spawners.placementNerfEnabled");
        ConfigData.disableMobAIOnSpawn = data.getBoolean("spawners.disableMobAIOnSpawn");

        ConfigData.isXpToCoinShopEnabled = data.getBoolean("xpToMobcoinShop.enabled");

        if (data.getConfigurationSection("coinShop.mainMenu") != null) {
            ConfigData.shopMenuName = data.getString("coinShop.mainMenu.name");
            ConfigData.rows = data.getInt("coinShop.mainMenu.rows");
            ConfigData.shopFillerItems = new ArrayList<>();
            for (String icon : data.getConfigurationSection("coinShop.mainMenu.fills").getKeys(false)) {
                ItemStack item = ItemBuilder.getItemStackFromConfig(data, "coinShop.mainMenu.fills." + icon);
                List<Integer> slots = data.getIntegerList("coinShop.mainMenu.fills." + icon + ".slots");

                ConfigData.shopFillerItems.add(new GUIItem(item, slots));
            }
        }


    }

}
