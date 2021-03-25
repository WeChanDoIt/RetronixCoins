package net.retronixmc.coins.utils;

import net.retronixmc.coins.shop.Category;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.EntityType;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.Arrays;

public class MiscUtils {

    public static ArrayList<Category> getDefaultCategories()
    {
        ArrayList<Category> categories = new ArrayList<>();
        categories.add(new Category(ItemBuilder.getItemStack(UMaterial.DIAMOND_SWORD.getMaterial(), 1, (short) 0, "&c&lCombat", Arrays.asList(new String[]{ChatUtils.chat("&7Here you can find all the"), ChatUtils.chat("&7combat related items")})), "combat", 10, 9));
        categories.add(new Category(ItemBuilder.getItemStack(UMaterial.SPAWNER.getMaterial(), 1, (short) 0, "&3&lSpawners", Arrays.asList(new String[]{ChatUtils.chat("&7Here you can find all"), ChatUtils.chat("&7Non-hostile and hostile mobs")})), "spawners", 12, 9));
        categories.add(new Category(ItemBuilder.getItemStack(UMaterial.FEATHER.getMaterial(), 1, (short) 0, "&7&lPerks", Arrays.asList(new String[]{ChatUtils.chat("&7Here you can unlock all the"), ChatUtils.chat("&7perks that donator ranks have")})), "perks", 14, 9));
        categories.add(new Category(ItemBuilder.getItemStack(UMaterial.FIREWORK_ROCKET.getMaterial(), 1, (short) 0, "&d&lCosmetics", Arrays.asList(new String[]{ChatUtils.chat("&7Here you can find"), ChatUtils.chat("&7fancy commands and particle trails!")})), "cosmetics", 16, 9));
        categories.add(new Category(ItemBuilder.getItemStack(UMaterial.INK_SAC.getMaterial(), 1, (short) 0, "&9&lBlack Market", Arrays.asList(new String[]{ChatUtils.chat("&7Here you can find"), ChatUtils.chat("&7all the custom items")})), "blackmarket", 22, 9));
        return categories;
    }

    public static String getInGameEnchantName(String arg) {
        switch (Enchantment.getByName(arg).getName()) {
            case "ARROW_DAMAGE":
                return "Power";
            case "ARROW_FIRE":
                return "Flame";
            case "ARROW_INFINITE":
                return "Infinity";
            case "ARROW_KNOCKBACK":
                return "Punch";
            case "BINDING_CURSE":
                return "Curse of Binding";
            case "DAMAGE_ALL":
                return "Sharpness";
            case "DAMAGE_ARTHROPODS":
                return "Bane of Arthropods";
            case "DAMAGE_UNDEAD":
                return "Smite";
            case "DEPTH_STRIDER":
                return "Depth Strider";
            case "DIG_SPEED":
                return "Efficiency";
            case "DURABILITY":
                return "Unbreaking";
            case "FIRE_ASPECT":
                return "Fire Aspect";
            case "FROST_WALKER":
                return "Frost Walker";
            case "KNOCKBACK":
                return "Knockback";
            case "LOOT_BONUS_BLOCKS":
                return "Fortune";
            case "LOOT_BONUS_MOBS":
                return "Looting";
            case "LUCK":
                return "Luck of the Sea";
            case "LURE":
                return "Lure";
            case "MENDING":
                return "Mending";
            case "OXYGEN":
                return "Respiration";
            case "PROTECTION_ENVIRONMENTAL":
                return "Protection";
            case "PROTECTION_EXPLOSIONS":
                return "Blast Protection";
            case "PROTECTION_FALL":
                return "Feather Falling";
            case "PROTECTION_FIRE":
                return "Fire Protection";
            case "PROTECTION_PROJECTILE":
                return "Projectile Protection";
            case "SILK_TOUCH":
                return "Silk Touch";
            case "SWEEPING_EDGE":
                return "Sweeping Edge";
            case "THORNS":
                return "Thorns";
            case "VANISHING_CURSE":
                return "Cure of Vanishing";
            case "WATER_WORKER":
                return "Aqua Affinity";
            default:
                return "Unknown";
        }
    }

    public ItemStack getSkullOfEntity(EntityType entityType)
    {
        switch (entityType)
        {
            case RABBIT:
                return ItemBuilder.getSkullFromName("MHF_Rabbit");
            case COW:
                return ItemBuilder.getSkullFromName("MHF_Cow");
            case PIG:
                return ItemBuilder.getSkullFromName("MHF_Pig");
            case CHICKEN:
                return ItemBuilder.getSkullFromName("MHF_Chicken");
            case SHEEP:
                return ItemBuilder.getSkullFromName("MHF_Sheep");
            case PIG_ZOMBIE:
                return ItemBuilder.getSkullFromName("MHF_PigZombie");
            case CREEPER:
                return ItemBuilder.getSkullFromName("MHF_Creeper");
            case SPIDER:
                return ItemBuilder.getSkullFromName("MHF_Spider");
            case ZOMBIE:
                return ItemBuilder.getSkullFromName("MHF_Zombie");
            case ENDERMAN:
                return ItemBuilder.getSkullFromName("MHF_Enderman");
            case SKELETON:
                return ItemBuilder.getSkullFromName("MHF_Skeleton");
            case WITCH:
                return ItemBuilder.getSkullFromName("MHF_Witch");
            case BLAZE:
                return ItemBuilder.getSkullFromName("MHF_Blaze");
            case IRON_GOLEM:
                return ItemBuilder.getSkullFromName("MHF_Golem");
            default:
                return null;
        }
    }
}
