package net.retronixmc.coins.commands;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.util.StringUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class CoinsTab implements TabCompleter {
    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {

        List<String> completions = new ArrayList<>();
        if (args.length == 1) {
            completions.add("shop");
            completions.add("top");
            completions.add("pay");
            completions.add("help");
            if (hasPermission(sender)) completions.add("admin");
            return StringUtil.copyPartialMatches(args[0], completions, new ArrayList<>());
        } else if (args.length == 2) {
            if ("admin".equals(args[0].toLowerCase()) && hasPermission(sender)) {
                completions.add("reload");
                completions.add("give");
                completions.add("take");
                completions.add("set");
            } else if (args[0].toLowerCase().equals("pay")) {
                Bukkit.getOnlinePlayers().forEach(player -> completions.add(player.getName()));
            }

            return StringUtil.copyPartialMatches(args[1], completions, new ArrayList<>());
        } else if (args.length == 3) {
            if ("admin".equals(args[0].toLowerCase()) && hasPermission(sender)) {
                switch(args[1].toLowerCase())
                {
                    case "give":
                    case "take":
                    case "set":
                    case "blacklist":
                        Bukkit.getOnlinePlayers().forEach(player -> completions.add(player.getName()));
                }
            }
            return StringUtil.copyPartialMatches(args[2], completions, new ArrayList<>());
        }

        return new ArrayList<>();
    }

    private boolean hasPermission(CommandSender sender) {
        return (sender.hasPermission("retronixcoins.admin") || sender.isOp());
    }
}
