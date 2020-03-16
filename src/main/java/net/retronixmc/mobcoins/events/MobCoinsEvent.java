package net.retronixmc.mobcoins.events;

import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.ComponentBuilder;
import net.retronixmc.mobcoins.objects.Profile;
import net.retronixmc.mobcoins.utils.ChatUtils;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;

public class MobCoinsEvent implements Listener {
    @EventHandler(priority = EventPriority.HIGHEST)
    public void onMobCoinsReceive(MobCoinsReceiveEvent event) {
        if (event.isCancelled())
            return;
        Profile profile = event.getProfile();
        Player player = profile.getPlayer();

        profile.setMobCoins(profile.getMobCoins() + event.getAmount());

        player.spigot().sendMessage(ChatMessageType.ACTION_BAR, new ComponentBuilder(ChatUtils.chat("&b+" + event.getAmount() + " mobcoins")).create());
    }
}
