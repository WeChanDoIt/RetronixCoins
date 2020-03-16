package net.retronixmc.mobcoins.utils;

import net.retronixmc.mobcoins.objects.Profile;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

import java.io.File;
import java.io.IOException;
import java.util.*;

public class DataHandler {

    private File file = new File("plugins/RetronixMobcoins/", "data.yml");
    private FileConfiguration data = YamlConfiguration.loadConfiguration(file);
    private List<Profile> profiles = new ArrayList<Profile>();

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
            data.set("profiles." + profile.getUUID() + ".mobcoins", profile.getMobCoins());
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
                int mobcoins = data.getInt("profiles." + s + ".mobcoins");
                Profile profile = new Profile(UUID.fromString(s));
                profile.setMobCoins(mobcoins);
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

    public List<Profile> getProfiles() {
        return profiles;
    }

    public void setProfiles(List<Profile> profiles) {
        this.profiles = profiles;
    }
}
