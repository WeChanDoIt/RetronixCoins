package net.retronixmc.mobcoins.events;

import net.retronixmc.mobcoins.Main;
import net.retronixmc.mobcoins.RetronixMobcoinsAPI;
import net.retronixmc.mobcoins.generator.GeneratorManager;
import net.retronixmc.mobcoins.generator.MobCoinGenerator;
import net.retronixmc.mobcoins.gui.GUIManager;
import net.retronixmc.mobcoins.nbt.NBT;
import net.retronixmc.mobcoins.profile.Profile;
import net.retronixmc.mobcoins.shop.Category;
import net.retronixmc.mobcoins.shop.ShopItem;
import net.retronixmc.mobcoins.utils.ChatUtils;
import net.retronixmc.mobcoins.utils.DataHandler;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.util.ConcurrentModificationException;

public class PlayerEvents implements Listener {

    private GeneratorManager generatorManager;

    public PlayerEvents(GeneratorManager generatorManager) {
        this.generatorManager = generatorManager;
    }

    @EventHandler
    public static void onPlayerJoin(PlayerJoinEvent event)
    {
        Player player = event.getPlayer();
        DataHandler dataHandler = RetronixMobcoinsAPI.getDataHandler();
        Profile profile = new Profile(player.getUniqueId());
        if (dataHandler.getProfile(player) == null)
        {
            profile.setMobCoins(0);
            dataHandler.getProfiles().add(profile);
        }
    }

    @EventHandler
    public void onInventoryClick(InventoryClickEvent event)
    {
        Player player = (Player) event.getWhoClicked();
        Profile profile = RetronixMobcoinsAPI.getDataHandler().getProfile(player);
        try
        {
            Inventory inventory = event.getClickedInventory();
            ItemStack item = event.getCurrentItem();
            if (inventory.getTitle().equalsIgnoreCase(ChatUtils.chat("&8&nMobCoin Shop")))
            {
                event.setCancelled(true);
                for (Category category : Main.getInstance().getShopManager().getCategories())
                {
                    if (category.getIcon().isSimilar(item))
                    {
                        player.openInventory(category.getInventory());
                    }
                }
                return;
            }
            if (inventory.getTitle().equalsIgnoreCase(ChatUtils.chat("&8&nMobcoin Top")))
            {
                event.setCancelled(true);
                return;
            }

            if (inventory.getTitle().equalsIgnoreCase(ChatUtils.chat("&8&nMobCoin Generator")))
            {
                event.setCancelled(true);
                NBT nbt = NBT.get(item);
                if (nbt.getInt("mobcoins") > 0)
                {
                    MobCoinGenerator generator = generatorManager.getGeneratorAtLocation(new Location(Bukkit.getWorld(nbt.getString("world")), nbt.getInt("locx"), nbt.getInt("locy"), nbt.getInt("locz")));
                    int withdraw = generator.withdrawMobcoins();
                    profile.setMobCoins(profile.getMobCoins() + withdraw);
                    player.sendMessage(ChatUtils.chat("&3&l[!] &bYou have withdrawn " + withdraw + " mobcoins!"));
                    RetronixMobcoinsAPI.getDataHandler().saveData();
                    player.openInventory(GUIManager.openGeneratorInv(generator));
                }
                return;
            }

            for (Category category : Main.getInstance().getShopManager().getCategories())
            {
                if (ChatUtils.chat(category.getInventory().getTitle()).equalsIgnoreCase(inventory.getTitle()))
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
                            int mobcoins = profile.getMobCoins();
                            if (mobcoins < i.getPrice())
                            {
                                player.sendMessage(ChatUtils.chat("&4&l[!] &cYou do not have enough money (mobcoins) to buy this item!"));
                            } else
                            {
                                if (i.getType().equals("COMMAND"))
                                {
                                    for (String command : i.getCommands())
                                    {
                                        Bukkit.dispatchCommand(Bukkit.getConsoleSender(), command.replace("%player%", player.getName()));
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
                                profile.setMobCoins((int) (profile.getMobCoins() - i.getPrice()));
                                player.sendMessage(ChatUtils.chat("&3&l[!] &bYou bought a " + i.getItem().getItemMeta().getDisplayName() + " &bfor " + i.getPrice() + " mobcoins!"));
                                RetronixMobcoinsAPI.getDataHandler().saveData();
                            }
                        }
                    }
                }
            }
        } catch (NullPointerException e)
        {
            return;
        } catch (ConcurrentModificationException f)
        {
            return;
        }
    }
}
