package net.retronixmc.mobcoins.gui;

import net.retronixmc.mobcoins.RetronixMobcoinsAPI;
import net.retronixmc.mobcoins.generator.MobCoinGenerator;
import net.retronixmc.mobcoins.nbt.NBT;
import net.retronixmc.mobcoins.profile.Profile;
import net.retronixmc.mobcoins.utils.ChatUtils;
import net.retronixmc.mobcoins.utils.ItemBuilder;
import net.retronixmc.mobcoins.utils.UMaterial;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.SkullMeta;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class GUIManager {
    public static Inventory openTop() {
        Inventory gui = Bukkit.createInventory(null, 18,
                ChatColor.translateAlternateColorCodes('&', "&8&nMobcoin Top"));

        List<Profile> top = RetronixMobcoinsAPI.getDataHandler().getTopMobcoins();
        Integer count = 0;
        if (!(count >= 10)) {
            for (Profile profile : top) {
                count += 1;
                Integer points = profile.getMobCoins();
                String owner = Bukkit.getOfflinePlayer(profile.getUUID()).getName();
                List<String> lore = new ArrayList<String>();

                ItemStack skull = UMaterial.PLAYER_HEAD_ITEM.getItemStack();
                SkullMeta skullMeta = (SkullMeta) skull.getItemMeta();
                skullMeta.setOwner(owner);

                skullMeta.setDisplayName(ChatUtils.chat("&b#" + count + "&3 - &b" + owner));

                lore.add(ChatUtils.chat("&bMobcoins: &b" + points));

                skullMeta.setLore(lore);

                skull.setItemMeta(skullMeta);
                gui.setItem(count-1, skull);
            }
        }

        ItemStack filler = ItemBuilder.getItemStack(UMaterial.BLACK_STAINED_GLASS_PANE.getItemStack(), " ");

        int x = 0;

        for (ItemStack itemStack : gui.getContents()) {
            if (itemStack == null)
                x++;
        }

        for (int j = 0; j < x; j++) {
            gui.setItem(gui.firstEmpty(), filler);
        }

        return gui;
    }

    public static Inventory openGeneratorInv(MobCoinGenerator generator)
    {
        Inventory gui = Bukkit.createInventory(null, 27, ChatUtils.chat("&8&nMobCoin Generator"));

        int x = (int)generator.getLocation().getX();
        int y = (int)generator.getLocation().getY();
        int z = (int)generator.getLocation().getZ();
        String coord = x + ", " + y + ", " + z;

        ItemStack collect = ItemBuilder.getItemStack(UMaterial.SUNFLOWER.getItemStack(), ChatUtils.chat("&fCollect &e" + generator.getMobcoins() + " &fmobcoins"));
        ItemStack stats = ItemBuilder.getItemStack(UMaterial.CLOCK.getItemStack(), ChatUtils.chat("&fMobCoin Generator Stats:"), Arrays.asList(new String[]{ChatUtils.chat("&7Location: " + coord), ChatUtils.chat("&7MobCoins generated per minute: " + generator.getHowManyToGenerate()), ChatUtils.chat("&7Total Mobcoins Generated: " + generator.getAmountGenerated())}));
        ItemStack upgrade = ItemBuilder.getItemStack(UMaterial.ANVIL.getItemStack(), ChatUtils.chat("&fUpgrades"), Arrays.asList(new String[]{ChatUtils.chat("&cUpgrade your MobCoin generator here!")}));

        NBT collectNBT = NBT.get(collect);
        collectNBT.setInt("mobcoins", generator.getMobcoins());
        collectNBT.setInt("locx", x);
        collectNBT.setInt("locy", y);
        collectNBT.setInt("locz", z);
        collectNBT.setString("world", generator.getLocation().getWorld().getName());
        collect = collectNBT.apply(collect);

        gui.setItem(11, collect);
        gui.setItem(13, stats);
        gui.setItem(15, upgrade);
        ItemStack filler = ItemBuilder.getItemStack(UMaterial.BLACK_STAINED_GLASS_PANE.getItemStack(), " ");

        int p = 0;

        for (ItemStack itemStack : gui.getContents()) {
            if (itemStack == null)
                p++;
        }

        for (int j = 0; j < p; j++) {
            gui.setItem(gui.firstEmpty(), filler);
        }
        return gui;
    }
}
