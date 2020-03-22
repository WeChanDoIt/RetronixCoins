package net.retronixmc.mobcoins.generator;

import com.bgsoftware.superiorskyblock.api.SuperiorSkyblockAPI;
import com.bgsoftware.superiorskyblock.api.island.Island;
import org.bukkit.Location;

public class MobCoinGenerator {
    private int amountGenerated; // total amount generated
    private int mobcoins; // amount in storage
    private Location location;
    private int level;
    private int howManyToGenerate;
    private int time;
    private String id;

    public MobCoinGenerator(Location loc, int level, int timeMinutes, String id)
    {
        location = loc;
        this.id = id;
        amountGenerated = 0;
        mobcoins = 0;
        this.level = level;
        howManyToGenerate = timeMinutes;
        time = 0;
    }

    public MobCoinGenerator(Location loc, int level, String id)
    {
        location = loc;
        this.id = id;
        amountGenerated = 0;
        mobcoins = 0;
        this.level = level;
        howManyToGenerate = 2 * level;
        time = 0;
    }

    public Island getIsland()
    {
        return SuperiorSkyblockAPI.getIslandAt(location);
    }

    public String getID()
    {
        return id;
    }

    public void setID(String string)
    {
        id = string;
    }

    public int getHowManyToGenerate()
    {
        return howManyToGenerate;
    }

    public Location getLocation()
    {
        return location;
    }

    public int getMobcoins()
    {
        return mobcoins;
    }

    public void setMobcoins(int amount)
    {
        mobcoins = amount;
    }

    public int getAmountGenerated()
    {
        return amountGenerated;
    }

    public void setAmountGenerated(int amountGenerated)
    {
        this.amountGenerated = amountGenerated;
    }

    public int getLevel()
    {
        return level;
    }

    public void setLevel(int l)
    {
        level = l;
    }

    public void generateMobcoins(int amount)
    {
        mobcoins += amount;
        amountGenerated += amount;
    }

    public int withdrawMobcoins()
    {
        int withdrawn = mobcoins;
        mobcoins = 0;
        return withdrawn;
    }

    public boolean update()
    {
        time++;
        if (time % (60/(2*level)) == 0 && time != 0)
        {
            time = 0;
            return true;
        }
        return false;
    }
}
