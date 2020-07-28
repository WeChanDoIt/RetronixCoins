package net.retronixmc.coins.hooks;

import me.clip.placeholderapi.expansion.PlaceholderExpansion;
import net.retronixmc.coins.Main;
import net.retronixmc.coins.RetronixCoinsAPI;
import net.retronixmc.coins.profile.Profile;
import org.bukkit.entity.Player;

import java.text.DecimalFormat;

public class PlaceholderAPIHook
        extends PlaceholderExpansion
{
    private Main instance;

    public PlaceholderAPIHook(Main instance) { this.instance = instance; }

    public boolean persist(){
        return true;
    }

    public boolean canRegister() { return true; }

    public String getIdentifier() { return "retronixcoins"; }

    public String getAuthor() { return this.instance.getDescription().getAuthors().toString(); }

    public String getVersion() { return this.instance.getDescription().getVersion(); }

    public String onPlaceholderRequest(Player player, String params) {
        Profile profile;
        if (player == null) return null;

        if ("coins".equals(params)) {
            profile = RetronixCoinsAPI.getDataHandler().getProfile(player);

            if (profile == null) return null;

            return String.valueOf(profile.getCoins());
        } else if ("coinsformatted".equals(params)) {
            DecimalFormat df = new DecimalFormat("#,##0.00");
            profile = RetronixCoinsAPI.getDataHandler().getProfile(player);

            if (profile == null) return null;

            return String.valueOf(df.format(profile.getCoins()));
        }


        return null;
    }
}

