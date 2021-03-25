package net.retronixmc.coins.mobdrops;

import org.bukkit.inventory.ItemStack;

public class MobDrop {
    private ItemStack item;
    private int minAmount;
    private int maxAmount;
    private double chance;
    private boolean cooked;
    public MobDrop(ItemStack item, int minAmount, int maxAmount, double chance, boolean cooked) {
        this.item = item;
        this.minAmount = minAmount;
        this.maxAmount = maxAmount;
        this.chance = chance;
        this.cooked = cooked;
    }

    public boolean isCooked() {
        return cooked;
    }

    public void setCooked(boolean cooked) {
        this.cooked = cooked;
    }

    public ItemStack getItem() {
        return item;
    }

    public void setItem(ItemStack item) {
        this.item = item;
    }

    public int getMinAmount() {
        return minAmount;
    }

    public void setMinAmount(int minAmount) {
        this.minAmount = minAmount;
    }

    public int getMaxAmount() {
        return maxAmount;
    }

    public void setMaxAmount(int maxAmount) {
        this.maxAmount = maxAmount;
    }

    public double getChance() {
        return chance;
    }

    public void setChance(double chance) {
        this.chance = chance;
    }
}
