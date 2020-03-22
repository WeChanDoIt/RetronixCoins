package net.retronixmc.mobcoins;

import net.retronixmc.mobcoins.chance.ChanceManager;
import net.retronixmc.mobcoins.commands.Coins;
import net.retronixmc.mobcoins.commands.MGenerator;
import net.retronixmc.mobcoins.events.*;
import net.retronixmc.mobcoins.generator.GeneratorManager;
import net.retronixmc.mobcoins.generator.MobCoinGenerator;
import net.retronixmc.mobcoins.hooks.PlaceholderAPIHook;
import net.retronixmc.mobcoins.shop.ShopManager;
import net.retronixmc.mobcoins.utils.DataHandler;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.ArrayList;

public class Main extends JavaPlugin {

    private DataHandler dataHandler = new DataHandler();
    private ChanceManager chanceManager;
    private ShopManager shopManager;
    private GeneratorManager generatorManager;
    public static Main plugin;

    public void onEnable()
    {
        plugin = this;
        dataHandler.retrieveData();
        chanceManager = new ChanceManager(this);
        shopManager = new ShopManager(this);
        generatorManager = new GeneratorManager(this);

        loadEvents();
        loadCommands();
        registerPlaceholders();
        runnablerunner();
    }

    public void onDisable()
    {
        dataHandler.saveData();
        generatorManager.saveData();
    }

    private void loadEvents()
    {
        Bukkit.getServer().getPluginManager().registerEvents(new PlayerEvents(generatorManager), this);
        Bukkit.getServer().getPluginManager().registerEvents(new MobCoinsEvent(), this);
        Bukkit.getServer().getPluginManager().registerEvents(new EntityEvents(), this);
        Bukkit.getServer().getPluginManager().registerEvents(new GeneratorEvents(generatorManager), this);

    }

    public void reload()
    {
        chanceManager = null;
        shopManager = null;
        dataHandler = new DataHandler();
        generatorManager = null;

        dataHandler.retrieveData();
        chanceManager = new ChanceManager(this);
        shopManager = new ShopManager(this);
        generatorManager = new GeneratorManager(this);
    }

    private void registerPlaceholders() {
        if (getServer().getPluginManager().isPluginEnabled("PlaceholderAPI")) {
            new PlaceholderAPIHook(this).register();
            System.out.println("Placeholder registered!");
        }
    }

    private void loadCommands()
    {
        getCommand("coins").setExecutor(new Coins());
        getCommand("mobcoingenerators").setExecutor(new MGenerator());
    }

    protected DataHandler getDataHandler()
    {
        return dataHandler;
    }

    public ChanceManager getChanceManager() {return chanceManager;}

    public ShopManager getShopManager() {return shopManager;}

    public static Main getInstance()
    {
        return plugin;
    }

    public boolean hasWildStacker() {
        if (getServer().getPluginManager().getPlugin("WildStacker") != null) {
            return true;
        }
        return false;
    }

    public GeneratorManager getGeneratorManager()
    {
        return generatorManager;
    }

    public void runnablerunner() {
        new BukkitRunnable() {

            @Override
            public void run() {

                ArrayList<MobCoinGenerator> generators = generatorManager.getGenerators();
                if (generators == null) return;

                for (MobCoinGenerator generator : generators)
                {
                    if (generator.update())
                    {
                        MobCoinsGenerateEvent mobCoinsGenerateEvent = new MobCoinsGenerateEvent(generator, 1);
                        getServer().getPluginManager().callEvent(mobCoinsGenerateEvent);
                    }
                }

            }

        }.runTaskTimer(this, 0, 20);
    }
}
