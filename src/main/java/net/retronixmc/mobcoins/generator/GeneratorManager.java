package net.retronixmc.mobcoins.generator;

import net.retronixmc.mobcoins.Main;
import net.retronixmc.mobcoins.chance.DropChance;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.EntityType;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

public class GeneratorManager {
    private Main instance;
    private File file = new File("plugins/RetronixMobcoins/", "generators.yml");
    private FileConfiguration data = YamlConfiguration.loadConfiguration(file);
    private ArrayList<MobCoinGenerator> generators;

    public GeneratorManager(Main instance) {
        this.generators = new ArrayList<>();

        retrieveData();
    }


    public void saveData() {

        data.set("generators", new ArrayList<String>());
        try {
            data.save(file);
        } catch (IOException e) {
            e.printStackTrace();
        }
        if (generators == null || generators.isEmpty()) {
            return;
        }
        for (MobCoinGenerator generator : generators) {
            String genName = generator.getID();
            double x = generator.getLocation().getBlockX();
            double y = generator.getLocation().getBlockY();
            double z = generator.getLocation().getBlockZ();
            String world = generator.getLocation().getWorld().getName();
            data.set("generators." + genName + ".level", generator.getLevel());
            data.set("generators." + genName + ".location.x", x);
            data.set("generators." + genName + ".location.y", y);
            data.set("generators." + genName + ".location.z", z);
            data.set("generators." + genName + ".location.world", world);
            data.set("generators." + genName + ".interval", generator.getHowManyToGenerate());
            data.set("generators." + genName + ".totalgenerated", generator.getAmountGenerated());
            data.set("generators." + genName + ".balance", generator.getMobcoins());

        }
        try {
            data.save(file);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void retrieveData() {
        if (data.getConfigurationSection("generators") == null) {
            return;
        }
        for (String s : data.getConfigurationSection("generators").getKeys(false)) {
            double x = data.getDouble("generators." + s + ".location.x");
            double y = data.getDouble("generators." + s + ".location.y");
            double z = data.getDouble("generators." + s + ".location.z");
            World world = Bukkit.getWorld(data.getString("generators." + s + ".location.world"));
            int level = data.getInt("generators." + s + ".level");
            int interval = data.getInt("generators." + s + ".interval");
            int totalgenerated = data.getInt("generators." + s + ".totalgenerated");
            int balance = data.getInt("generators." + s + ".balance");
            Location location = new Location(world, x, y, z);
            MobCoinGenerator generator = new MobCoinGenerator(location, level, interval, s);
            generator.setAmountGenerated(totalgenerated);
            generator.setMobcoins(balance);
            generators.add(generator);
        }
    }

    public void reloadChances() {
        this.generators.clear();
        retrieveData();
    }

    public ArrayList<MobCoinGenerator> getGenerators()
    {
        return generators;
    }

    public MobCoinGenerator getGeneratorAtLocation(Location location)
    {
        if (generators.size() == 0) return null;
        for (MobCoinGenerator generator : generators)
        {
            if (generator.getLocation().equals(location))
            {
                return generator;
            }
        }
        return null;
    }
}
