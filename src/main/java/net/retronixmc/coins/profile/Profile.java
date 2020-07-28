package net.retronixmc.coins.profile;

import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

public class Profile {
    private UUID uuid;
    private int coins;
    private boolean blacklistedFromTop = false;

    public Profile(UUID uuid) {
        this.uuid = uuid;
    }

    public boolean isBlacklistedFromTop() {
        return blacklistedFromTop;
    }

    public void setBlacklistedFromTop(boolean blacklistedFromTop) {
        this.blacklistedFromTop = blacklistedFromTop;
    }

    public UUID getUUID() {
        return this.uuid;
    }

    public Player getPlayer() {
        return Bukkit.getPlayer(this.uuid);
    }

    public int getCoins() {
        return this.coins;
    }

    public void setCoins(int mobCoins) {
        this.coins = mobCoins;
    }

    public boolean equals(Profile profile) {
        return (profile.getUUID().equals(uuid)) && (profile.getUUID().equals(coins));
    }

    public int compareTo(Profile profile) {
        return coins - profile.getCoins();
    }
}
