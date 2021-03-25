package net.retronixmc.coins;

import net.retronixmc.coins.chance.ChanceManager;
import net.retronixmc.coins.commands.Coins;
import net.retronixmc.coins.commands.CoinsTab;
import net.retronixmc.coins.config.ConfigHandler;
import net.retronixmc.coins.events.EntityEvents;
import net.retronixmc.coins.events.MobCoinsEvent;
import net.retronixmc.coins.events.PlayerEvents;
import net.retronixmc.coins.hooks.PlaceholderAPIHook;
import net.retronixmc.coins.shop.ShopManager;
import net.retronixmc.coins.utils.DataHandler;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

public class Main extends JavaPlugin {

    private DataHandler dataHandler = new DataHandler();
    private ChanceManager chanceManager;
    private ShopManager shopManager;
    private ConfigHandler configHandler;
    public static Main plugin;

    public void onEnable()
    {
        plugin = this;
        saveDefaultConfig();
        dataHandler.retrieveData();
        chanceManager = new ChanceManager(this);
        shopManager = new ShopManager(this);
        configHandler = new ConfigHandler();
        Bukkit.getScheduler().scheduleSyncDelayedTask(Main.getInstance(), new Runnable() {
            public void run() {
                loadEvents();
                loadCommands();
                registerPlaceholders();
            }
        }, 1L);

    }

    public void onDisable()
    {
        dataHandler.saveData();
    }

    private void loadEvents()
    {
        Bukkit.getServer().getPluginManager().registerEvents(new PlayerEvents(), this);
        Bukkit.getServer().getPluginManager().registerEvents(new EntityEvents(), this);
        Bukkit.getServer().getPluginManager().registerEvents(new MobCoinsEvent(), this);

    }

    public void reload()
    {
        dataHandler.saveData();
        chanceManager = null;
        shopManager = null;
        dataHandler = new DataHandler();

        dataHandler.retrieveData();
        chanceManager = new ChanceManager(this);
        shopManager = new ShopManager(this);
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
        getCommand("coins").setTabCompleter(new CoinsTab());
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

    public boolean hasUltimateStacker() {
        if (getServer().getPluginManager().getPlugin("UltimateStacker") != null) {
            return true;
        }
        return false;
    }
}
