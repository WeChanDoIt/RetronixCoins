package net.retronixmc.mobcoins.events;

import com.bgsoftware.superiorskyblock.api.SuperiorSkyblockAPI;
import com.bgsoftware.superiorskyblock.api.events.IslandDisbandEvent;
import net.retronixmc.mobcoins.Main;
import net.retronixmc.mobcoins.RetronixMobcoinsAPI;
import net.retronixmc.mobcoins.generator.GeneratorManager;
import net.retronixmc.mobcoins.generator.MobCoinGenerator;
import net.retronixmc.mobcoins.gui.GUIManager;
import net.retronixmc.mobcoins.nbt.NBT;
import net.retronixmc.mobcoins.profile.Profile;
import net.retronixmc.mobcoins.utils.ChatUtils;
import net.retronixmc.mobcoins.utils.ItemBuilder;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.UUID;

public class GeneratorEvents implements Listener {

    private GeneratorManager generatorManager;

    public GeneratorEvents(GeneratorManager generatorManager) {
        this.generatorManager = generatorManager;
    }

    @EventHandler
    public void onBlockPlace(BlockPlaceEvent event) {
        Player player = event.getPlayer();
        Block block = event.getBlock();
        int level = 1;
        if (ItemBuilder.isGenerator(event.getItemInHand()) && !event.isCancelled()) {
            NBT nbt = NBT.get(event.getItemInHand());
            if (nbt.hasKey("Level")) {
                level = nbt.getInt("Level");
            }
            player.sendMessage(ChatUtils.chat("&3&l[!] &bYou placed a &c&lMobCoin generator&b!"));
            generatorManager.getGenerators().add(new MobCoinGenerator(block.getLocation(), level, 2 * level, UUID.randomUUID().toString()));
        }
    }

    @EventHandler
    public void onPlayerInteract(PlayerInteractEvent event) {
        Player player = event.getPlayer();
        Block block = event.getClickedBlock();
        if (event.getAction().equals(Action.RIGHT_CLICK_BLOCK) && generatorManager.getGeneratorAtLocation(block.getLocation()) != null  && ((SuperiorSkyblockAPI.getIslandAt(block.getLocation()) != null && SuperiorSkyblockAPI.getIslandAt(block
                .getLocation()).equals(SuperiorSkyblockAPI.getPlayer((Player) event.getPlayer()).getIsland())) || event.getPlayer().isOp())) {
            player.openInventory(GUIManager.openGeneratorInv(generatorManager.getGeneratorAtLocation(block.getLocation())));
        }
    }

    @EventHandler
    public void onIslandDelete(IslandDisbandEvent event) {
        ArrayList<MobCoinGenerator> generators = generatorManager.getGenerators();
        for (MobCoinGenerator generator : generators) {
            if (SuperiorSkyblockAPI.getIslandAt(generator.getLocation()).equals(event.getIsland())) {
                generators.remove(generator);
            }
        }
    }

    @EventHandler
    public void onBlockBreak(BlockBreakEvent event) {
        Player player = event.getPlayer();
        Location location = event.getBlock().getLocation();
        int level = 1;
        boolean isEmpty = false;
        ArrayList<ItemStack> contents = new ArrayList<ItemStack>();
        if ((event.getBlock().getType().equals(Material.CAULDRON)) && (generatorManager.getGeneratorAtLocation(location) != null)) {
            MobCoinGenerator mobCoinGenerator = generatorManager.getGeneratorAtLocation(location);
            level = mobCoinGenerator.getLevel();
            int mobcoins = mobCoinGenerator.getMobcoins();
            generatorManager.getGenerators().remove(mobCoinGenerator);
            player.sendMessage(ChatUtils.chat("&3&l[!] &bYou removed a &c&lMobCoin generator&b!"));
            event.setDropItems(false);
            Profile profile = RetronixMobcoinsAPI.getDataHandler().getProfile(player);
            MobCoinsReceiveEvent mobCoinsReceiveEvent = new MobCoinsReceiveEvent(profile, mobcoins);
            Main.getInstance().getServer().getPluginManager().callEvent(mobCoinsReceiveEvent);
            if (player.getInventory().firstEmpty() != -1)
                player.getInventory().addItem(ItemBuilder.getGeneratorStack(1, level));
            else
                event.getBlock().getWorld().dropItemNaturally(event.getBlock().getLocation(), ItemBuilder.getGeneratorStack(1, level));

        }
    }


}
