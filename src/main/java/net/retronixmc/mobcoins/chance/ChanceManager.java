package net.retronixmc.mobcoins.chance;

import net.retronixmc.mobcoins.Main;
import net.retronixmc.mobcoins.objects.Profile;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.EntityType;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class ChanceManager {
    private Main instance;
    private File file = new File("plugins/RetronixMobcoins/", "dropchances.yml");
    private FileConfiguration data = YamlConfiguration.loadConfiguration(file);
    private Map<EntityType, DropChance> dropChances;

    public ChanceManager(Main instance) {
        this.dropChances = new HashMap();
        this.instance = instance;

        loadChances();
    }


    public DropChance getChance(EntityType entityType) { return (DropChance)this.dropChances.get(entityType); }

    public void saveDefaultData() {

        data.set("Chance", new ArrayList<String>());
        try {
            data.save(file);
        } catch (IOException e) {
            e.printStackTrace();
        }
        Map<EntityType, DropChance> defaultMap = new HashMap<>();
        defaultMap.put(EntityType.ZOMBIE, new DropChance(5));
        defaultMap.put(EntityType.SKELETON, new DropChance(5));
        defaultMap.put(EntityType.BLAZE, new DropChance(5));
        defaultMap.put(EntityType.ENDERMAN, new DropChance(8));
        defaultMap.put(EntityType.CREEPER, new DropChance(8));
        defaultMap.put(EntityType.WITHER, new DropChance(50));

        for (EntityType entityType : defaultMap.keySet()) {
            data.set("Chance." + entityType.name() + ".type", entityType.name());
            data.set("Chance." + entityType.name() + ".chance", defaultMap.get(entityType).getChance());
        }
        try {
            data.save(file);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void loadChances() {
        if (data.getConfigurationSection("Chance") == null) {
            saveDefaultData();
            reloadChances();
            return;
        }
        for (String s : data.getConfigurationSection("Chance").getKeys(false)) {
            EntityType entityType = EntityType.valueOf(data.getString("Chance." + s + ".type"));
            int chance = data.getInt("Chance." + s + ".chance");
            this.dropChances.put(entityType, new DropChance(chance));
        }
    }

    public void reloadChances() {
        this.dropChances.clear();
        loadChances();
    }


    public Map<EntityType, DropChance> getDropChances() { return this.dropChances; }

}
