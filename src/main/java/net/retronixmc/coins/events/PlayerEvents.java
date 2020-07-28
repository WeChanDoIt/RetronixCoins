package net.retronixmc.coins.events;

import com.songoda.ultimatestacker.UltimateStacker;
import com.songoda.ultimatestacker.spawner.SpawnerStack;
import net.retronixmc.coins.Main;
import net.retronixmc.coins.RetronixCoinsAPI;
import net.retronixmc.coins.gui.GUIManager;
import net.retronixmc.coins.nbt.NBT;
import net.retronixmc.coins.profile.Profile;
import net.retronixmc.coins.shop.Category;
import net.retronixmc.coins.shop.ShopItem;
import net.retronixmc.coins.utils.ChatUtils;
import net.retronixmc.coins.utils.DataHandler;
import net.retronixmc.coins.utils.EXPUtils;
import net.retronixmc.coins.utils.UMaterial;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.CreatureSpawner;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryView;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.EnchantmentStorageMeta;

import java.util.ConcurrentModificationException;
import java.util.Random;

public class PlayerEvents implements Listener {

    final private double conversionRate = 619.4;

    @EventHandler
    public static void onPlayerJoin(PlayerJoinEvent event)
    {
        Player player = event.getPlayer();
        DataHandler dataHandler = RetronixCoinsAPI.getDataHandler();
        Profile profile = new Profile(player.getUniqueId());
        if (dataHandler.getProfile(player) == null)
        {
            profile.setCoins(0);
            dataHandler.getProfiles().add(profile);
        }
    }

    @EventHandler
    public static void getSpawnerAmount(PlayerInteractEvent event)
    {
        Block block = event.getClickedBlock();
        Player player = event.getPlayer();
        if(block.getType().equals(UMaterial.SPAWNER.getMaterial()) && event.getAction() == Action.RIGHT_CLICK_BLOCK && event.getHand().equals(EquipmentSlot.HAND))
        {
            EntityType entity = ((CreatureSpawner) block.getState()).getSpawnedType();
            String name = entity.name();
            SpawnerStack stack = UltimateStacker.getInstance().getSpawnerStackManager().getSpawner(block);
            player.sendMessage(ChatUtils.chat("&bSpawners &8&m>&f There are " + stack.getAmount() + " " + name.substring(0,1) + name.substring(1).toLowerCase().replace("_"," ") + " Spawners"));
        }
    }

    @EventHandler
    public void onInventoryClick(InventoryClickEvent event)
    {
        Player player = (Player) event.getWhoClicked();
        Profile profile = RetronixCoinsAPI.getDataHandler().getProfile(player);
        try
        {
            Inventory inventory = event.getClickedInventory();
            InventoryView view = event.getView();
            ItemStack item = event.getCurrentItem();
            NBT nbt = NBT.get(item);
            if (view.getTopInventory().getTitle().equalsIgnoreCase(ChatUtils.chat("&8&nCoin Shop")))
            {
                event.setCancelled(true);
                if (item.getItemMeta().getDisplayName().equalsIgnoreCase(ChatUtils.chat("&3&l[!] Convert XP TO COINS")))
                {
                    player.openInventory(GUIManager.openConversionInventory(player));
                    return;
                }
                for (Category category : Main.getInstance().getShopManager().getCategories())
                {
                    if (category.getIcon().isSimilar(item))
                    {
                        player.openInventory(category.getInventory());
                    }
                }
                return;
            }
            if (view.getTopInventory().getTitle().equalsIgnoreCase(ChatUtils.chat("&8&nCoin Top")))
            {
                event.setCancelled(true);
                return;
            }

            if (view.getTopInventory().getTitle().equalsIgnoreCase(ChatUtils.chat("&8&nConvert XP to Coins")))
            {
                event.setCancelled(true);
                if(nbt.getInt("value") == 100)
                {
                    if (EXPUtils.getTotalExperience(player) >= conversionRate * 50)
                    {
                        EXPUtils.setTotalExperience(player, (int) Math.floor(EXPUtils.getTotalExperience(player) - (conversionRate * 50)));
                        profile.setCoins(profile.getCoins() + 50);
                        player.sendMessage(ChatUtils.chat("&3&l[!] &bYou now have " + EXPUtils.getTotalExperience(player) + " experience."));
                        player.sendMessage(ChatUtils.chat("&3&l[!] &bYou now have " + profile.getCoins() + " coins."));
                    } else
                    {
                        player.sendMessage(ChatUtils.chat("&4&l[!] &cYou do not have enough xp!"));
                    }
                }
                if(nbt.getInt("value") == 50)
                {
                    if (EXPUtils.getTotalExperience(player) >= conversionRate * 8)
                    {
                        EXPUtils.setTotalExperience(player, (int) Math.floor(EXPUtils.getTotalExperience(player) - (conversionRate * 8)));
                        profile.setCoins(profile.getCoins() + 8);
                        player.sendMessage(ChatUtils.chat("&3&l[!] &bYou now have " + EXPUtils.getTotalExperience(player) + " experience."));
                        player.sendMessage(ChatUtils.chat("&3&l[!] &bYou now have " + profile.getCoins() + " coins."));
                    } else
                    {
                        player.sendMessage(ChatUtils.chat("&4&l[!] &cYou do not have enough xp!"));
                    }
                }
                if(nbt.getInt("value") == 30)
                {
                    if (EXPUtils.getTotalExperience(player) >= conversionRate * 2)
                    {
                        EXPUtils.setTotalExperience(player, (int) Math.floor(EXPUtils.getTotalExperience(player) - (conversionRate * 2)));
                        profile.setCoins(profile.getCoins() + 2);
                        player.sendMessage(ChatUtils.chat("&3&l[!] &bYou now have " + EXPUtils.getTotalExperience(player) + " experience."));
                        player.sendMessage(ChatUtils.chat("&3&l[!] &bYou now have " + profile.getCoins() + " coins."));
                    } else
                    {
                        player.sendMessage(ChatUtils.chat("&4&l[!] &cYou do not have enough xp!"));
                    }
                }
                player.openInventory(GUIManager.openConversionInventory(player));
                return;
            }

            for (Category category : Main.getInstance().getShopManager().getCategories())
            {
                if (ChatUtils.chat(category.getIcon().getItemMeta().getDisplayName()).equalsIgnoreCase(view.getTitle()))
                {
                    event.setCancelled(true);

                    if (item.getItemMeta().getDisplayName().equals(ChatUtils.chat("&c&lBACK")))
                    {
                        player.openInventory(Main.getInstance().getShopManager().getCategoryInventory(player));
                        return;
                    }

                    for (ShopItem i : category.getCategoryItems())
                    {
                        if (i.getItem().getItemMeta().getDisplayName().equals(item.getItemMeta().getDisplayName()))
                        {
                            int mobcoins = profile.getCoins();
                            if (mobcoins < i.getPrice())
                            {
                                player.sendMessage(ChatUtils.chat("&4&l[!] &cYou do not have enough money (coins) to buy this item!"));
                            } else
                            {
                                if (i.getType().equals("COMMAND"))
                                {
                                    for (String command : i.getCommands())
                                    {
                                        Bukkit.dispatchCommand(Bukkit.getConsoleSender(), command.replace("%player%", player.getName()));
                                    }
                                } else if (i.getType().equals("RANDOMENCHANTBOOK"))
                                {
                                    ItemStack book = new ItemStack(Material.ENCHANTED_BOOK, 1);
                                    Random random = new Random();
                                    int level = random.nextInt(i.getRandMax()) + i.getRandMin();
                                    EnchantmentStorageMeta meta = (EnchantmentStorageMeta)book.getItemMeta();
                                    meta.addStoredEnchant(i.getEnchantment(), level, true);
                                    book.setItemMeta(meta);
                                    if (player.getInventory().firstEmpty() != -1)
                                    {
                                        player.getInventory().addItem(book);
                                    } else
                                    {
                                        player.getLocation().getWorld().dropItemNaturally(player.getLocation(), book);
                                    }
                                } else
                                {
                                    if (player.getInventory().firstEmpty() != -1)
                                    {
                                        player.getInventory().addItem(i.getItem());
                                    } else
                                    {
                                        player.getLocation().getWorld().dropItemNaturally(player.getLocation(), i.getItem());
                                    }
                                }
                                profile.setCoins((int) (profile.getCoins() - i.getPrice()));
                                player.sendMessage(ChatUtils.chat("&3&l[!] &bYou bought a " + i.getItem().getItemMeta().getDisplayName() + " &bfor " + i.getPrice() + " coins!"));
                                RetronixCoinsAPI.getDataHandler().saveData();
                            }
                        }
                    }
                }
            }
        } catch (NullPointerException e)
        {
            e.printStackTrace();
            return;
        } catch (ConcurrentModificationException f)
        {
            return;
        }
    }
}
