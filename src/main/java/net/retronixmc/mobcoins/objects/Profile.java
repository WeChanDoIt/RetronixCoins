package net.retronixmc.mobcoins.objects;

import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

public class Profile {
    private UUID uuid;
    private int mobCoins;

    public Profile(UUID uuid) {
        this.uuid = uuid;
    }

    public UUID getUUID() {
        return this.uuid;
    }

    public Player getPlayer() {
        return Bukkit.getPlayer(this.uuid);
    }

    public int getMobCoins() {
        return this.mobCoins;
    }

    public void setMobCoins(int mobCoins) {
        this.mobCoins = mobCoins;
    }

    public boolean equals(Profile profile) {
        return (profile.getUUID().equals(uuid)) && (profile.getUUID().equals(mobCoins));
    }

    public int compareTo(Profile profile) {
        return mobCoins - profile.getMobCoins();
    }
}
