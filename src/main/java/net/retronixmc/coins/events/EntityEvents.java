package net.retronixmc.coins.events;

import net.retronixmc.coins.Main;
import net.retronixmc.coins.RetronixCoinsAPI;
import net.retronixmc.coins.chance.DropChance;
import net.retronixmc.coins.profile.Profile;
import net.wechandoit.src.events.PlayerKillMobEvent;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;

import java.util.Random;

public class EntityEvents implements Listener {

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onDamageEntity(PlayerKillMobEvent event) {
        Player killer = event.getPlayer();
        EntityType entityType = event.getEntity().getType();

        Profile profile = RetronixCoinsAPI.getDataHandler().getProfile(killer);
        DropChance dropChance = RetronixCoinsAPI.getChanceManager().getChance(entityType);

        int coinsAmount = 0;
        Random random = new Random();
        for (int i = 0; i < event.getEntitiesKilled(); i++) {
            if (random.nextInt(100) <= dropChance.getChance() - 1)
                coinsAmount++;
        }

        if (dropChance != null && coinsAmount > 0) {

            MobCoinsReceiveEvent mobCoinsReceiveEvent = new MobCoinsReceiveEvent(profile, coinsAmount, false);

            Main.getInstance().getServer().getPluginManager().callEvent(mobCoinsReceiveEvent);

        }
    }

}