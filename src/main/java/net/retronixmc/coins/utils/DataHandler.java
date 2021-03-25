package net.retronixmc.coins.utils;

import net.retronixmc.coins.profile.Profile;
import net.retronixmc.coins.utils.MobcoinTop;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

import java.io.File;
import java.io.IOException;
import java.util.*;

public class DataHandler {

    private File file = new File("plugins/RetronixCoins/", "data.yml");
    private FileConfiguration data = YamlConfiguration.loadConfiguration(file);
    private ArrayList<Profile> profiles = new ArrayList<Profile>();

    public void saveData() {

        data.set("profiles", new ArrayList<String>());
        try {
            data.save(file);
        } catch (IOException e) {
            e.printStackTrace();
        }
        if (profiles == null || profiles.isEmpty()) {
            return;
        }
        for (Profile profile : profiles) {
            data.set("profiles." + profile.getUUID() + ".coins", profile.getCoins());
            data.set("profiles." + profile.getUUID() + ".isBlacklisted", profile.isBlacklistedFromTop());
        }
        try {
            data.save(file);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void retrieveData() {
        if (data.getConfigurationSection("profiles") == null) {
            return;
        }
        for (String s : data.getConfigurationSection("profiles").getKeys(false)) {
            try {
                int coins = data.getInt("profiles." + s + ".coins");
                boolean blacklisted = data.getBoolean("profiles." + s + ".isBlacklisted");
                Profile profile = new Profile(UUID.fromString(s));
                profile.setCoins(coins);
                profile.setBlacklistedFromTop(blacklisted);
                profiles.add(profile);
            } catch (IllegalArgumentException e)
            {
                continue;
            }
        }
    }

    public Profile getProfile(UUID uuid)
    {
        for (Profile profile : profiles)
        {
            if (profile.getUUID().equals(uuid)) return profile;
        }
        return null;
    }

    public Profile getProfile(Player player)
    {
        UUID uuid = player.getUniqueId();
        return getProfile(uuid);
    }

    public List<Profile> getSortedProfiles()
    {
        ArrayList<Profile> clone = (ArrayList<Profile>) profiles.clone();
        Collections.sort(clone);
        return clone;
    }

    public List<Profile> getProfiles() {
        return profiles;
    }

    public void setProfiles(ArrayList<Profile> profiles) {
        this.profiles = profiles;
    }
}
