package net.retronixmc.coins.commands;

import net.retronixmc.coins.Main;
import net.retronixmc.coins.RetronixCoinsAPI;
import net.retronixmc.coins.config.ConfigData;
import net.retronixmc.coins.gui.GUIManager;
import net.retronixmc.coins.profile.Profile;
import net.retronixmc.coins.utils.ChatUtils;
import net.retronixmc.coins.utils.DataHandler;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class Coins implements CommandExecutor {
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        DataHandler dataHandler = RetronixCoinsAPI.getDataHandler();
        if (sender instanceof Player) {
            Player player = (Player) sender;
            Profile profile = new Profile(player.getUniqueId());
            if (dataHandler.getProfile(player) == null) {
                profile.setCoins(0);
                dataHandler.getProfiles().add(profile);
            } else {
                profile = dataHandler.getProfile(player);
            }

            if (args.length == 0) {
                for (String message : ConfigData.help) {
                    player.sendMessage(ChatUtils.chat(message));
                }
            } else if (args.length == 1) {
                if (args[0].equalsIgnoreCase("shop")) {
                    player.openInventory(Main.getInstance().getShopManager().getCategoryInventory(player));
                } else if (args[0].equalsIgnoreCase("top")) {
                    player.openInventory(GUIManager.openTop());
                } else if (args[0].equalsIgnoreCase("help")) {
                    for (String message : ConfigData.help) {
                        player.sendMessage(ChatUtils.chat(message));
                    }
                } else {
                    if (Bukkit.getPlayer(args[0]) != null && dataHandler.getProfile(Bukkit.getPlayer(args[0])) != null) {
                        Player target = Bukkit.getPlayer(args[0]);
                        Profile targetProfile = dataHandler.getProfile(target);
                        player.sendMessage(ChatUtils.chat(ConfigData.playerTotalCoins.replaceAll("<player>", target.getName()).replaceAll("<amount>", String.valueOf(targetProfile.getCoins()))));
                    } else {
                        player.sendMessage(ChatUtils.chat(ConfigData.notValidPlayer.replaceAll("<player>", args[0])));
                    }
                }
            } else if (args.length == 2) {
                if (hasPermission(player)) {
                    if (args[0].equalsIgnoreCase("admin") && args[1].equalsIgnoreCase("reload")) {
                        Main.getInstance().reload();
                        System.out.println(ChatUtils.chat("&4&l[!] &cPlugin reloaded!"));
                        player.sendMessage(ChatUtils.chat("&4&l[!] &cPlugin reloaded!"));
                    }
                }
            } else if (args.length == 4) {
                if (hasPermission(player)) {
                    if (args[0].equalsIgnoreCase("admin")) {
                        if (args[1].equalsIgnoreCase("give")) {
                            try {
                                if (Bukkit.getPlayer(args[2]) != null && dataHandler.getProfile(Bukkit.getPlayer(args[2])) != null) {
                                    Player target = Bukkit.getPlayer(args[2]);
                                    Profile targetProfile = dataHandler.getProfile(target);
                                    targetProfile.setCoins(targetProfile.getCoins() + Integer.parseInt(args[3]));
                                    player.sendMessage(ChatUtils.chat(ConfigData.playerTotalCoins.replaceAll("<player>", target.getName()).replaceAll("<amount>", String.valueOf(targetProfile.getCoins()))));
                                    target.sendMessage(ChatUtils.chat(ConfigData.totalCoins.replaceAll("<amount>", String.valueOf(targetProfile.getCoins()))));
                                    dataHandler.saveData();
                                } else {
                                    player.sendMessage(ChatUtils.chat(ConfigData.notValidPlayer.replaceAll("<player>", args[2])));
                                }
                            } catch (NumberFormatException e) {
                                player.sendMessage(ChatUtils.chat(ConfigData.notValidNumber.replaceAll("<amount>", args[3])));
                            }
                        } else if (args[1].equalsIgnoreCase("set")) {
                            try {
                                if (Bukkit.getPlayer(args[2]) != null && dataHandler.getProfile(Bukkit.getPlayer(args[2])) != null) {
                                    Player target = Bukkit.getPlayer(args[2]);
                                    Profile targetProfile = dataHandler.getProfile(target);
                                    targetProfile.setCoins(Integer.parseInt(args[3]));
                                    player.sendMessage(ChatUtils.chat(ConfigData.playerTotalCoins.replaceAll("<player>", target.getName()).replaceAll("<amount>", String.valueOf(targetProfile.getCoins()))));
                                    target.sendMessage(ChatUtils.chat(ConfigData.totalCoins.replaceAll("<amount>", String.valueOf(targetProfile.getCoins()))));
                                } else {
                                    player.sendMessage(ChatUtils.chat(ConfigData.notValidPlayer.replaceAll("<player>", args[2])));
                                }
                            } catch (NumberFormatException e) {
                                player.sendMessage(ChatUtils.chat(ConfigData.notValidNumber.replaceAll("<amount>", args[3])));
                            }
                        } else if (args[1].equalsIgnoreCase("take")) {
                            try {
                                if (Bukkit.getPlayer(args[2]) != null && dataHandler.getProfile(Bukkit.getPlayer(args[2])) != null) {
                                    Player target = Bukkit.getPlayer(args[2]);
                                    Profile targetProfile = dataHandler.getProfile(target);
                                    targetProfile.setCoins(targetProfile.getCoins() - Integer.parseInt(args[3]));
                                    player.sendMessage(ChatUtils.chat(ConfigData.playerTotalCoins.replaceAll("<player>", target.getName()).replaceAll("<amount>", args[2])));
                                    target.sendMessage(ChatUtils.chat(ConfigData.totalCoins.replaceAll("<amount>", String.valueOf(targetProfile.getCoins()))));
                                } else {
                                    player.sendMessage(ChatUtils.chat(ConfigData.notValidPlayer.replaceAll("<player>", args[2])));
                                }
                            } catch (NumberFormatException e) {
                                player.sendMessage(ChatUtils.chat(ConfigData.notValidNumber.replaceAll("<amount>", args[3])));
                            }
                        }
                    }
                }

                if (args[0].equalsIgnoreCase("pay")) {
                    try {
                        if (Bukkit.getPlayer(args[1]) != null && dataHandler.getProfile(Bukkit.getPlayer(args[1])) != null && !(player.getName().equalsIgnoreCase(args[1]))) {
                            Player target = Bukkit.getPlayer(args[1]);
                            Profile targetProfile = dataHandler.getProfile(target);
                            if (Integer.parseInt(args[2]) > 0 && Integer.parseInt(args[2]) <= profile.getCoins()) {
                                targetProfile.setCoins(targetProfile.getCoins() + (int) Math.round(Integer.parseInt(args[2]) * 0.95));
                                player.sendMessage(ChatUtils.chat(ConfigData.playerTotalCoins.replaceAll("<player>", target.getName()).replaceAll("<amount>", String.valueOf(targetProfile.getCoins()))));
                                profile.setCoins(profile.getCoins() - Integer.parseInt(args[2]));
                                target.sendMessage(ChatUtils.chat(ConfigData.totalCoins.replaceAll("<amount>", String.valueOf(targetProfile.getCoins()))));

                            } else {
                                player.sendMessage(ChatUtils.chat(ConfigData.notValidNumber.replaceAll("<amount>", args[2])));
                            }
                        } else {
                            player.sendMessage(ChatUtils.chat(ConfigData.notValidPlayer.replaceAll("<player>", args[1])));
                        }
                    } catch (NumberFormatException e) {
                        player.sendMessage(ChatUtils.chat(ConfigData.notValidNumber.replaceAll("<amount>", args[2])));
                    }
                }
            }
            dataHandler.saveData();
        } else {
            if (args.length == 4) {
                if (hasPermission(sender)) {
                    if (args[0].equalsIgnoreCase("admin")) {
                        if (args[1].equalsIgnoreCase("give")) {
                            try {
                                if (Bukkit.getPlayer(args[2]) != null && dataHandler.getProfile(Bukkit.getPlayer(args[2])) != null) {
                                    Player target = Bukkit.getPlayer(args[2]);
                                    Profile targetProfile = dataHandler.getProfile(target);
                                    targetProfile.setCoins(targetProfile.getCoins() + Integer.parseInt(args[3]));
                                    sender.sendMessage(ChatUtils.chat(ConfigData.playerTotalCoins.replaceAll("<player>", target.getName()).replaceAll("<amount>", String.valueOf(targetProfile.getCoins()))));
                                    target.sendMessage(ChatUtils.chat(ConfigData.totalCoins.replaceAll("<amount>", String.valueOf(targetProfile.getCoins()))));
                                    dataHandler.saveData();
                                } else {
                                    sender.sendMessage(ChatUtils.chat(ConfigData.notValidPlayer.replaceAll("<player>", args[2])));
                                }
                            } catch (NumberFormatException e) {
                                sender.sendMessage(ChatUtils.chat(ConfigData.notValidNumber.replaceAll("<amount>", args[3])));
                            }
                        } else if (args[1].equalsIgnoreCase("set")) {
                            try {
                                if (Bukkit.getPlayer(args[2]) != null && dataHandler.getProfile(Bukkit.getPlayer(args[2])) != null) {
                                    Player target = Bukkit.getPlayer(args[2]);
                                    Profile targetProfile = dataHandler.getProfile(target);
                                    targetProfile.setCoins(Integer.parseInt(args[3]));
                                    sender.sendMessage(ChatUtils.chat(ConfigData.playerTotalCoins.replaceAll("<player>", target.getName()).replaceAll("<amount>", String.valueOf(targetProfile.getCoins()))));
                                    target.sendMessage(ChatUtils.chat(ConfigData.totalCoins.replaceAll("<amount>", String.valueOf(targetProfile.getCoins()))));
                                } else {
                                    sender.sendMessage(ChatUtils.chat(ConfigData.notValidPlayer.replaceAll("<player>", args[2])));
                                }
                            } catch (NumberFormatException e) {
                                sender.sendMessage(ChatUtils.chat(ConfigData.notValidNumber.replaceAll("<amount>", args[3])));
                            }
                        } else if (args[1].equalsIgnoreCase("take")) {
                            try {
                                if (Bukkit.getPlayer(args[2]) != null && dataHandler.getProfile(Bukkit.getPlayer(args[2])) != null) {
                                    Player target = Bukkit.getPlayer(args[2]);
                                    Profile targetProfile = dataHandler.getProfile(target);
                                    targetProfile.setCoins(targetProfile.getCoins() - Integer.parseInt(args[3]));
                                    sender.sendMessage(ChatUtils.chat(ConfigData.playerTotalCoins.replaceAll("<player>", target.getName()).replaceAll("<amount>", args[2])));
                                    target.sendMessage(ChatUtils.chat(ConfigData.totalCoins.replaceAll("<amount>", String.valueOf(targetProfile.getCoins()))));
                                } else {
                                    sender.sendMessage(ChatUtils.chat(ConfigData.notValidPlayer.replaceAll("<player>", args[2])));
                                }
                            } catch (NumberFormatException e) {
                                sender.sendMessage(ChatUtils.chat(ConfigData.notValidNumber.replaceAll("<amount>", args[3])));
                            }
                        }
                    }
                }
            }
            sender.sendMessage(ChatUtils.chat(ConfigData.notPlayer));
        }

        return true;
    }

    private boolean hasPermission(CommandSender sender) {
        return (sender.hasPermission("retronixcoins.admin") || sender.isOp());
    }
}
