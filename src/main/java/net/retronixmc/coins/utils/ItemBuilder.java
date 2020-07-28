package net.retronixmc.coins.utils;

import net.retronixmc.coins.nbt.NBT;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;

public class ItemBuilder {

    public static ItemStack getSkullFromBase64(String base64)
    {
        ItemStack item = UMaterial.PLAYER_HEAD_ITEM.getItemStack();
        notNull(base64, "base64");

        UUID hashAsId = new UUID(base64.hashCode(), base64.hashCode());
        return Bukkit.getUnsafe().modifyItemStack(item,
                "{SkullOwner:{Id:\"" + hashAsId + "\",Properties:{textures:[{Value:\"" + base64 + "\"}]}}}");
    }

    public static ItemStack getSkullFromName(String name) {
        ItemStack item = UMaterial.PLAYER_HEAD_ITEM.getItemStack();
        notNull(name, "name");

        return Bukkit.getUnsafe().modifyItemStack(item,
                "{SkullOwner:\"" + name + "\"}"
        );
    }

    private static void notNull(Object o, String name) {
        if (o == null) {
            throw new NullPointerException(name + " should not be null!");
        }
    }

    public static ItemStack getItemStack(Material material, int amount, short data)
    {
        return new ItemStack(material, amount, data);
    }

    public static ItemStack getItemStack(Material material, int amount, short data, String name)
    {
        ItemStack itemStack = new ItemStack(material, amount, data);
        ItemMeta meta = itemStack.getItemMeta();
        meta.setDisplayName(ChatUtils.chat(name));
        itemStack.setItemMeta(meta);
        return itemStack;
    }

    public static ItemStack getItemStack(Material material, int amount, short data, String name, List<String> lore)
    {
        ItemStack itemStack = new ItemStack(material, amount, data);
        ItemMeta meta = itemStack.getItemMeta();
        meta.setDisplayName(name);
        meta.setLore(lore);
        itemStack.setItemMeta(meta);
        return itemStack;
    }

    public static ItemStack getItemStack(ItemStack itemStack, String name, List<String> lore)
    {
        ItemMeta meta = itemStack.getItemMeta();
        meta.setDisplayName(name);
        meta.setLore(lore);
        itemStack.setItemMeta(meta);
        return itemStack;
    }

    public static ItemStack getItemStack(ItemStack itemStack, String name)
    {
        ItemMeta meta = itemStack.getItemMeta();
        meta.setDisplayName(name);
        itemStack.setItemMeta(meta);
        return itemStack;
    }

    public static ItemStack getItemStack(ItemStack itemStack, String name, String NBTname, int value)
    {
        ItemMeta meta = itemStack.getItemMeta();
        meta.setDisplayName(name);
        itemStack.setItemMeta(meta);
        NBT nbt = NBT.get(itemStack);
        nbt.setInt(NBTname, value);
        return nbt.apply(itemStack);
    }

    public static ItemStack getGeneratorStack(int amount, int level) {
        NBT nbt;

        ItemStack generator = new ItemStack(UMaterial.CAULDRON_ITEM.getMaterial());
        ItemMeta meta = generator.getItemMeta();
        meta.setDisplayName(ChatUtils.chat("&c&lMobCoin Generator"));
        meta.setLore(Arrays.asList(ChatUtils.chat("&fThis generator will generate coins"), ChatUtils.chat("&fthat you can collect!"), "", ChatUtils.chat("&fThis generator will generate"), ChatUtils.chat("&b" + level*2 + " &fmobcoins per minute!")));
        generator.setItemMeta(meta);
        generator.setAmount(amount);

        nbt = NBT.get(generator);
        nbt.setInt("Level", level);

        generator = nbt.apply(generator);

        return generator;
    }

    public static boolean isGenerator(ItemStack item)
    {
        if (!item.hasItemMeta() || !item.getItemMeta().hasDisplayName() || !item.getItemMeta().hasLore()) return false;
        List<String> lore = item.getItemMeta().getLore();
        for (String line : lore)
        {
            if (line.equals(ChatUtils.chat("&fThis generator will generate coins"))) return true;
        }
        return false;
    }

}
