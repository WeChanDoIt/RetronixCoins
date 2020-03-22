package net.retronixmc.mobcoins.commands;

import net.retronixmc.mobcoins.Main;
import net.retronixmc.mobcoins.RetronixMobcoinsAPI;
import net.retronixmc.mobcoins.gui.GUIManager;
import net.retronixmc.mobcoins.profile.Profile;
import net.retronixmc.mobcoins.utils.ChatUtils;
import net.retronixmc.mobcoins.utils.DataHandler;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class Coins implements CommandExecutor {
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (sender instanceof Player) {
            DataHandler dataHandler = RetronixMobcoinsAPI.getDataHandler();
            Player player = (Player) sender;
            Profile profile = new Profile(player.getUniqueId());
            if (dataHandler.getProfile(player) == null) {
                profile.setMobCoins(0);
                dataHandler.getProfiles().add(profile);
            } else {
                profile = dataHandler.getProfile(player);
            }

            if (args.length == 0) {
                player.sendMessage(new String[]{ChatUtils.chat("&8---------------"), ChatUtils.chat("&3&l[!] Mobcoins Commands:"), ChatUtils.chat("&b/coins [player]"), ChatUtils.chat("&b/coins pay [player] [amount]"), ChatUtils.chat("&b/coins shop"), ChatUtils.chat("&b/coins top"), ChatUtils.chat("&8---------------")});
            } else if (args.length == 1) {
                if (args[0].equalsIgnoreCase("shop")) {
                    player.openInventory(Main.getInstance().getShopManager().getCategoryInventory(player));
                } else if (args[0].equalsIgnoreCase("top")) {
                    player.openInventory(GUIManager.openTop());
                } else {
                    if (Bukkit.getPlayer(args[0]) != null && dataHandler.getProfile(Bukkit.getPlayer(args[0])) != null) {
                        Player target = Bukkit.getPlayer(args[0]);
                        Profile targetProfile = dataHandler.getProfile(target);
                        player.sendMessage(ChatUtils.chat("&3&l[!] &b" + target.getName() + " has " + targetProfile.getMobCoins() + " mobcoins."));
                    } else {
                        player.sendMessage(ChatUtils.chat("&4&l[!]&c " + args[0] + " is not a valid [player]!"));
                    }
                }
            } else if (args.length == 2) {
                if (player.hasPermission("retronixmobcoins.admin")) {
                    if (args[0].equalsIgnoreCase("admin") && args[1].equalsIgnoreCase("reload")) {
                        Main.getInstance().reload();
                        System.out.println(ChatUtils.chat("&4&l[!] &cPlugin reloaded!"));
                        player.sendMessage(ChatUtils.chat("&4&l[!] &cPlugin reloaded!"));
                    }
                }
            } else if (args.length == 3) {
                if (player.hasPermission("retronixmobcoins.admin")) {
                    if (args[0].equalsIgnoreCase("give")) {
                        try {
                            if (Bukkit.getPlayer(args[1]) != null && dataHandler.getProfile(Bukkit.getPlayer(args[1])) != null) {
                                Player target = Bukkit.getPlayer(args[1]);
                                Profile targetProfile = dataHandler.getProfile(target);
                                targetProfile.setMobCoins(targetProfile.getMobCoins() + Integer.parseInt(args[2]));
                                player.sendMessage(ChatUtils.chat("&3&l[!] &b" + target.getName() + " now has " + targetProfile.getMobCoins() + " mobcoins!"));
                                target.sendMessage(ChatUtils.chat("&3&l[!] &bYou now have " + targetProfile.getMobCoins() + " mobcoins!"));
                                dataHandler.saveData();
                            } else {
                                player.sendMessage(ChatUtils.chat("&4&l[!]&c " + args[1] + " is not a valid [player]!"));
                            }
                        } catch (NumberFormatException e) {
                            player.sendMessage(ChatUtils.chat("&4&l[!]&c " + args[2] + " is not a valid [amount]!"));
                        }
                    } else if (args[0].equalsIgnoreCase("set")) {
                        try {
                            if (Bukkit.getPlayer(args[1]) != null && dataHandler.getProfile(Bukkit.getPlayer(args[1])) != null) {
                                Player target = Bukkit.getPlayer(args[1]);
                                Profile targetProfile = dataHandler.getProfile(target);
                                targetProfile.setMobCoins(Integer.parseInt(args[2]));
                                player.sendMessage(ChatUtils.chat("&3&l[!] &b" + target.getName() + " now has " + targetProfile.getMobCoins() + " mobcoins!"));
                                target.sendMessage(ChatUtils.chat("&3&l[!] &bYou now have " + targetProfile.getMobCoins() + " mobcoins!"));
                                dataHandler.saveData();
                            } else {
                                player.sendMessage(ChatUtils.chat("&4&l[!]&c " + args[1] + " is not a valid [player]!"));
                            }
                        } catch (NumberFormatException e) {
                            player.sendMessage(ChatUtils.chat("&4&l[!]&c " + args[2] + " is not a valid [amount]!"));
                        }
                    } else if (args[0].equalsIgnoreCase("take")) {
                        try {
                            if (Bukkit.getPlayer(args[1]) != null && dataHandler.getProfile(Bukkit.getPlayer(args[1])) != null) {
                                Player target = Bukkit.getPlayer(args[1]);
                                Profile targetProfile = dataHandler.getProfile(target);
                                targetProfile.setMobCoins(targetProfile.getMobCoins() - Integer.parseInt(args[2]));
                                player.sendMessage(ChatUtils.chat("&3&l[!] &b" + target.getName() + " now has " + targetProfile.getMobCoins() + " mobcoins!"));
                                target.sendMessage(ChatUtils.chat("&3&l[!] &bYou now have " + targetProfile.getMobCoins() + " mobcoins!"));
                                dataHandler.saveData();
                            } else {
                                player.sendMessage(ChatUtils.chat("&4&l[!]&c " + args[1] + " is not a valid [player]!"));
                            }
                        } catch (NumberFormatException e) {
                            player.sendMessage(ChatUtils.chat("&4&l[!]&c " + args[2] + " is not a valid [amount]!"));
                        }
                    } else if (args[0].equalsIgnoreCase("admin") && args[1].equalsIgnoreCase("blacklist")) {

                        if (Bukkit.getPlayer(args[2]) != null && dataHandler.getProfile(Bukkit.getPlayer(args[2])) != null) {
                            Player target = Bukkit.getPlayer(args[2]);
                            Profile targetProfile = dataHandler.getProfile(target);
                            if (!targetProfile.isBlacklistedFromTop()) {
                                targetProfile.setBlacklistedFromTop(true);
                                player.sendMessage(ChatUtils.chat("&3&l[!] &b" + target.getName() + " is now blacklisted from /coins top!"));
                            } else
                            {
                                targetProfile.setBlacklistedFromTop(false);
                                player.sendMessage(ChatUtils.chat("&3&l[!] &b" + target.getName() + " has been unblacklisted from /coins top!"));
                            }
                            dataHandler.saveData();
                        } else {
                            player.sendMessage(ChatUtils.chat("&4&l[!]&c " + args[2] + " is not a valid [player]!"));
                        }

                    }
                }

                if (args[0].equalsIgnoreCase("pay")) {
                    try {
                        if (Bukkit.getPlayer(args[1]) != null && dataHandler.getProfile(Bukkit.getPlayer(args[1])) != null && !(player.getName().equalsIgnoreCase(args[1]))) {
                            Player target = Bukkit.getPlayer(args[1]);
                            Profile targetProfile = dataHandler.getProfile(target);
                            if (Integer.parseInt(args[2]) > 0 && Integer.parseInt(args[2]) <= profile.getMobCoins()) {
                                player.sendMessage(ChatUtils.chat("&3&l[!] &bSent " + args[2] + " mobcoins to " + target.getName() + "!"));
                                targetProfile.setMobCoins(targetProfile.getMobCoins() + Integer.parseInt(args[2]));
                                profile.setMobCoins(profile.getMobCoins() - Integer.parseInt(args[2]));
                                target.sendMessage(ChatUtils.chat("&3&l[!] &bYou now have " + targetProfile.getMobCoins() + " mobcoins!"));
                                dataHandler.saveData();
                            } else {
                                player.sendMessage(ChatUtils.chat("&4&l[!]&c " + args[2] + " is greater than your mobcoins: " + profile.getMobCoins() + " or is an invalid [amount]!"));
                            }
                        } else {
                            player.sendMessage(ChatUtils.chat("&4&l[!]&c " + args[1] + " is not a valid [player]!"));
                        }
                    } catch (NumberFormatException e) {
                        player.sendMessage(ChatUtils.chat("&4&l[!]&c " + args[2] + " is not a valid [amount]!"));
                    }
                }
            }
            dataHandler.saveData();
        } else {
            sender.sendMessage("You must be a player to use this command");
        }

        return true;
    }
}
