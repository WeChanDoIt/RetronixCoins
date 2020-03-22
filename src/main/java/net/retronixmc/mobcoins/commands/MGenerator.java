package net.retronixmc.mobcoins.commands;

import net.retronixmc.mobcoins.utils.ChatUtils;
import net.retronixmc.mobcoins.utils.ItemBuilder;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class MGenerator
        implements CommandExecutor {
    private int amount;

    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        String playername = "CONSOLE";
        int amount = 1, level = 1;
        if (args.length > 1) {
            Player target = Bukkit.getPlayer(args[1]);
            if (sender instanceof Player) playername = sender.getName();
            if (args[0].equalsIgnoreCase("give") && (!(target == null))) {
                if (args.length == 3) {
                    try {
                        amount = Integer.parseInt(args[2]);
                    } catch (NumberFormatException e) {
                        amount = 1;
                    }
                }
                target.getInventory().addItem(ItemBuilder.getGeneratorStack(amount, level));
                sender.sendMessage(
                        ChatUtils.chat("&3&l[!] &bYou gave " + target.getName() + " " + amount + "x &c&lMobCoin generator&b!"));
                target.sendMessage(
                        ChatUtils.chat("&3&l[!] &b" + playername + " has given you " + amount + "x &c&lMobCoin generators&b!"));
                return true;
            } else {
                if (sender.hasPermission("retromobcoins.give")) {
                    sender.sendMessage(ChatUtils.chat("&4&l[!] &c/mg give [player] [amount]"));
                } else {
                    sender.sendMessage(ChatUtils.chat("&4&l[!] &cYou do not have permission to use this command!"));
                }
            }
        } else {
            sender.sendMessage(ChatUtils.chat("&c&l[!] &cUsage: /mg give [player] [amount]"));
        }
        return true;
    }
}

