package net.retronixmc.mobcoins;

import javafx.scene.chart.PieChart;
import net.retronixmc.mobcoins.chance.ChanceManager;
import net.retronixmc.mobcoins.commands.Coins;
import net.retronixmc.mobcoins.events.EntityEvents;
import net.retronixmc.mobcoins.events.MobCoinsEvent;
import net.retronixmc.mobcoins.events.PlayerEvents;
import net.retronixmc.mobcoins.hooks.PlaceholderAPIHook;
import net.retronixmc.mobcoins.utils.DataHandler;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

public class Main extends JavaPlugin {

    private DataHandler dataHandler = new DataHandler();
    private ChanceManager chanceManager;
    public static Main plugin;

    public void onEnable()
    {
        plugin = this;
        dataHandler.retrieveData();
        chanceManager = new ChanceManager(this);
        loadEvents();
        loadCommands();
        registerPlaceholders();
    }

    public void onDisable()
    {
        dataHandler.saveData();
    }

    private void loadEvents()
    {
        Bukkit.getServer().getPluginManager().registerEvents(new PlayerEvents(), this);
        Bukkit.getServer().getPluginManager().registerEvents(new MobCoinsEvent(), this);
        Bukkit.getServer().getPluginManager().registerEvents(new EntityEvents(), this);

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
    }

    protected DataHandler getDataHandler()
    {
        return dataHandler;
    }

    protected ChanceManager getChanceManager() {return chanceManager;}

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
}
