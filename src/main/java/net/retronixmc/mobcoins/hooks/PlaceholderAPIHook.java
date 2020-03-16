package net.retronixmc.mobcoins.hooks;

import me.clip.placeholderapi.expansion.PlaceholderExpansion;
import net.retronixmc.mobcoins.Main;
import net.retronixmc.mobcoins.RetronixMobcoinsAPI;
import net.retronixmc.mobcoins.objects.Profile;
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

    public String getIdentifier() { return "retronixmobcoins"; }

    public String getAuthor() { return this.instance.getDescription().getAuthors().toString(); }

    public String getVersion() { return this.instance.getDescription().getVersion(); }

    public String onPlaceholderRequest(Player player, String params) {
        Profile profile;
        if (player == null) return null;

        if ("mobcoins".equals(params)) {
            profile = RetronixMobcoinsAPI.getDataHandler().getProfile(player);

            if (profile == null) return null;

            return String.valueOf(profile.getMobCoins());
        } else if ("mobcoinsformatted".equals(params)) {
            DecimalFormat df = new DecimalFormat("#,##0.00");
            profile = RetronixMobcoinsAPI.getDataHandler().getProfile(player);

            if (profile == null) return null;

            return String.valueOf(df.format(profile.getMobCoins()));
        }


        return null;
    }
}

