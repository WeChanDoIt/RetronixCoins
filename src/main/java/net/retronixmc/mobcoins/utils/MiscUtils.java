package net.retronixmc.mobcoins.utils;

import net.retronixmc.mobcoins.shop.Category;

import java.util.ArrayList;
import java.util.Arrays;

public class MiscUtils {

    public static String meowthHead = "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvMTgzNWNkNmZiOTc3NzQwMzYyODQ3ODUyN2VhZDM5MWZiMjhjMzExNWQyZTA3Y2EzZjRkYWY0N2UwZWYzZDQ3YyJ9fX0=";

    public static ArrayList<Category> getDefaultCategories()
    {
        ArrayList<Category> categories = new ArrayList<>();
        categories.add(new Category(ItemBuilder.getItemStack(UMaterial.DIAMOND_SWORD.getMaterial(), 1, (short) 0, "&c&lCombat", Arrays.asList(new String[]{ChatUtils.chat("&7Here you can find all the"), ChatUtils.chat("&7combat related items")})), "combat", 10));
        categories.add(new Category(ItemBuilder.getItemStack(UMaterial.SPAWNER.getMaterial(), 1, (short) 0, "&3&lSpawners", Arrays.asList(new String[]{ChatUtils.chat("&7Here you can find all"), ChatUtils.chat("&7Non-hostile and hostile mobs")})), "spawners", 12));
        categories.add(new Category(ItemBuilder.getItemStack(UMaterial.FEATHER.getMaterial(), 1, (short) 0, "&7&lPerks", Arrays.asList(new String[]{ChatUtils.chat("&7Here you can unlock all the"), ChatUtils.chat("&7perks that donator ranks have")})), "perks", 14));
        categories.add(new Category(ItemBuilder.getItemStack(UMaterial.FIREWORK_ROCKET.getMaterial(), 1, (short) 0, "&d&lCosmetics", Arrays.asList(new String[]{ChatUtils.chat("&7Here you can find"), ChatUtils.chat("&7fancy commands and particle trails!")})), "cosmetics", 16));
        categories.add(new Category(ItemBuilder.getItemStack(UMaterial.INK_SAC.getMaterial(), 1, (short) 0, "&9&lBlack Market", Arrays.asList(new String[]{ChatUtils.chat("&7Here you can find"), ChatUtils.chat("&7all the custom items")})), "blackmarket", 22));
        return categories;
    }
}
