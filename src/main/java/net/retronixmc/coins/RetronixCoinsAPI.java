package net.retronixmc.coins;

import net.retronixmc.coins.chance.ChanceManager;
import net.retronixmc.coins.profile.Profile;
import net.retronixmc.coins.shop.ShopManager;
import net.retronixmc.coins.utils.DataHandler;

import java.util.List;

public class RetronixCoinsAPI {
    private static Main instance;

    public RetronixCoinsAPI(Main main)
    {
        instance = main;
    }

    public static List<Profile> getProfiles()
    {
        return Main.getInstance().getDataHandler().getProfiles();
    }

    public static DataHandler getDataHandler()
    {
        return Main.getInstance().getDataHandler();
    }

    public static ChanceManager getChanceManager() { return Main.getInstance().getChanceManager(); }

    public static ShopManager getShopManager() { return Main.getInstance().getShopManager(); }

}
