package net.retronixmc.coins.events;

import com.songoda.ultimatestacker.UltimateStacker;
import com.songoda.ultimatestacker.entity.EntityStackManager;
import net.retronixmc.coins.Main;
import net.retronixmc.coins.RetronixCoinsAPI;
import net.retronixmc.coins.chance.DropChance;
import net.retronixmc.coins.profile.Profile;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDeathEvent;

import java.util.Random;

public class EntityEvents implements Listener {
    @EventHandler(priority = EventPriority.LOWEST)
    public void onEntityDeath(EntityDeathEvent event) {
        Player killer = event.getEntity().getKiller();

        if (killer == null)
            return;
        Profile profile = RetronixCoinsAPI.getDataHandler().getProfile(killer);
        DropChance dropChance = RetronixCoinsAPI.getChanceManager().getChance(event.getEntityType());

        if (dropChance == null)
            return;
        Random random = new Random();
        EntityStackManager manager = UltimateStacker.getInstance().getEntityStackManager();

        if (random.nextInt(100) > dropChance.getChance() - 1)
            return;
        MobCoinsReceiveEvent mobCoinsReceiveEvent = new MobCoinsReceiveEvent(profile, 1);
        Main.getInstance().getServer().getPluginManager().callEvent(mobCoinsReceiveEvent);
    }
}