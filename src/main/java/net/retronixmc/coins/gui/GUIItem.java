package net.retronixmc.coins.gui;

import org.bukkit.inventory.ItemStack;

import java.util.List;

public class GUIItem {
    private ItemStack itemStack;
    private List<Integer> slots;

    public GUIItem(ItemStack itemStack, List<Integer> slots) {
        this.itemStack = itemStack;
        this.slots = slots;
    }

    public ItemStack getItemStack() {
        return itemStack;
    }

    public void setItemStack(ItemStack itemStack) {
        this.itemStack = itemStack;
    }

    public List<Integer> getSlots() {
        return slots;
    }

    public void setSlots(List<Integer> slots) {
        this.slots = slots;
    }
}
