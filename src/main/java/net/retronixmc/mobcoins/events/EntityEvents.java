package net.retronixmc.mobcoins.events;

import com.bgsoftware.wildstacker.api.WildStackerAPI;
import net.retronixmc.mobcoins.Main;
import net.retronixmc.mobcoins.RetronixMobcoinsAPI;
import net.retronixmc.mobcoins.chance.DropChance;
import net.retronixmc.mobcoins.profile.Profile;
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
        Profile profile = RetronixMobcoinsAPI.getDataHandler().getProfile(killer);
        DropChance dropChance = RetronixMobcoinsAPI.getChanceManager().getChance(event.getEntityType());

        if (dropChance == null)
            return;
        Random random = new Random();

        if (Main.getInstance().hasWildStacker() &&
                WildStackerAPI.getStackedEntity(event.getEntity()) != null) {
            int stackedAmount = WildStackerAPI.getEntityAmount(event.getEntity());
            int amount = 0;

            for (int i = 0; i < stackedAmount; i++) {
                if (random.nextInt(100) <= dropChance.getChance() - 1) {
                    amount++;
                }
            }
            if (amount == 0)
                return;
            MobCoinsReceiveEvent mobCoinsReceiveEvent = new MobCoinsReceiveEvent(profile, amount);
            Main.getInstance().getServer().getPluginManager().callEvent(mobCoinsReceiveEvent);

            return;
        }

        if (random.nextInt(100) > dropChance.getChance() - 1)
            return;
        MobCoinsReceiveEvent mobCoinsReceiveEvent = new MobCoinsReceiveEvent(profile, 1);
        Main.getInstance().getServer().getPluginManager().callEvent(mobCoinsReceiveEvent);
    }
}
