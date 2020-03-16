package net.retronixmc.mobcoins.events;

import net.retronixmc.mobcoins.RetronixMobcoinsAPI;
import net.retronixmc.mobcoins.objects.Profile;
import net.retronixmc.mobcoins.utils.DataHandler;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public class PlayerEvents implements Listener {

    @EventHandler
    public static void onPlayerJoin(PlayerJoinEvent event)
    {
        Player player = event.getPlayer();
        DataHandler dataHandler = RetronixMobcoinsAPI.getDataHandler();
        Profile profile = new Profile(player.getUniqueId());
        if (dataHandler.getProfile(player) == null)
        {
            profile.setMobCoins(0);
            dataHandler.getProfiles().add(profile);
        }
    }
}
