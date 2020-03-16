package net.retronixmc.mobcoins.utils;

import org.bukkit.ChatColor;

public class ChatUtils {
    public static String chat(String message)
    {
        return ChatColor.translateAlternateColorCodes('&', message);
    }
}
