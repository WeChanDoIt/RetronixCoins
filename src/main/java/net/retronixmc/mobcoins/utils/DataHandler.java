package net.retronixmc.mobcoins.utils;

import net.retronixmc.mobcoins.profile.Profile;
import net.retronixmc.mobcoins.utils.MobcoinTop;
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
                int mobcoins = data.getInt("profiles." + s + ".mobcoins");
                boolean blacklisted = data.getBoolean("profiles." + s + ".isBlacklisted");
                Profile profile = new Profile(UUID.fromString(s));
                profile.setMobCoins(mobcoins);
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

    public List<Profile> getProfiles() {
        return profiles;
    }

    public void setProfiles(List<Profile> profiles) {
        this.profiles = profiles;
    }

    public List<Profile> getTopMobcoins() {
        HashMap<Profile, Integer> profileMap = new HashMap<Profile, Integer>();
        MobcoinTop mobcoinTop = new MobcoinTop(profileMap);
        TreeMap<Profile, Integer> sortedpoints = new TreeMap<Profile, Integer>(mobcoinTop);
        List<Profile> top = new ArrayList<Profile>();

        for (Profile profile : this.profiles)
        {
            if (!profile.isBlacklistedFromTop()) {
                Integer mobcoins = profile.getMobCoins();
                profileMap.put(profile, mobcoins);
            }
        }

        sortedpoints.putAll(profileMap);

        for(Profile profile : sortedpoints.keySet()) {
            top.add(profile);
        }
        return top;
    }
}
