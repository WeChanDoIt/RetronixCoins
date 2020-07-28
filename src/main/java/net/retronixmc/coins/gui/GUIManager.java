package net.retronixmc.coins.gui;

import net.retronixmc.coins.RetronixCoinsAPI;
import net.retronixmc.coins.profile.Profile;
import net.retronixmc.coins.utils.ChatUtils;
import net.retronixmc.coins.utils.EXPUtils;
import net.retronixmc.coins.utils.ItemBuilder;
import net.retronixmc.coins.utils.UMaterial;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.SkullMeta;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class GUIManager {
    public static Inventory openTop() {
        Inventory gui = Bukkit.createInventory(null, 18,
                ChatColor.translateAlternateColorCodes('&', "&8&nCoin Top"));

        List<Profile> top = RetronixCoinsAPI.getDataHandler().getTopMobcoins();
        Integer count = 0;
        for (Profile profile : top) {
            if (count < 10) {
                count += 1;
                Integer points = profile.getCoins();
                String owner = Bukkit.getOfflinePlayer(profile.getUUID()).getName();
                List<String> lore = new ArrayList<String>();

                ItemStack skull = UMaterial.PLAYER_HEAD_ITEM.getItemStack();
                SkullMeta skullMeta = (SkullMeta) skull.getItemMeta();
                skullMeta.setOwningPlayer(Bukkit.getOfflinePlayer(profile.getUUID()));

                skullMeta.setDisplayName(ChatUtils.chat("&b#" + count + "&3 - &b" + owner));

                lore.add(ChatUtils.chat("&bCoins: &b" + points));

                skullMeta.setLore(lore);

                skull.setItemMeta(skullMeta);
                gui.setItem(count - 1, skull);
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

    public static Inventory openConversionInventory(Player player) {
        Profile profile = RetronixCoinsAPI.getDataHandler().getProfile(player);
        Inventory gui = Bukkit.createInventory(null, 27, ChatUtils.chat("&8&nConvert XP to Coins"));

        ItemStack fiftyItem = ItemBuilder.getItemStack(UMaterial.SUNFLOWER.getItemStack(), ChatUtils.chat("&3&l[!] &bConvert 100 levels to 50 coins"), "value", 100);
        ItemStack eightItem = ItemBuilder.getItemStack(UMaterial.SUNFLOWER.getItemStack(), ChatUtils.chat("&3&l[!] &bConvert 50 levels to 8 coins"), "value", 50);
        ItemStack twoItem = ItemBuilder.getItemStack(UMaterial.SUNFLOWER.getItemStack(), ChatUtils.chat("&3&l[!] &bConvert 30 levels to 2 coins"), "value", 30);
        ItemStack exp = ItemBuilder.getItemStack(UMaterial.EXPERIENCE_BOTTLE.getItemStack(), ChatUtils.chat("&3&l[!] Player Information:"), Arrays.asList(ChatUtils.chat("&3&l[!] &bPlayer EXP: " + EXPUtils.getTotalExperience(player)), ChatUtils.chat("&3&l[!] &bPlayer Level: " + player.getLevel())));

        gui.setItem(10, twoItem);
        gui.setItem(13, eightItem);
        gui.setItem(16, fiftyItem);
        gui.setItem(22, exp);

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
