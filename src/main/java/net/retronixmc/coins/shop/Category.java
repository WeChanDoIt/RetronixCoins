package net.retronixmc.coins.shop;

import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;

public class Category {
    private ItemStack icon;
    private ArrayList<ShopItem> categoryItems = new ArrayList<>();
    private Inventory inventory;
    private int invSize;
    private int slot;
    private String name;

    public Category(ItemStack itemStack, String name, int slot, int invSize)
    {
        icon = itemStack;
        this.name = name;
        this.slot = slot;
        this.invSize = invSize;
    }

    public int getInvSize() {
        return invSize;
    }

    public void setInvSize(int invSize) {
        this.invSize = invSize;
    }

    public ItemStack getIcon() {
        return icon;
    }

    public void setIcon(ItemStack icon) {
        this.icon = icon;
    }

    public ArrayList<ShopItem> getCategoryItems()
    {
        return categoryItems;
    }

    public void setCategoryItems(ArrayList<ShopItem> items)
    {
        categoryItems = items;
    }

    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    public int getSlot()
    {
        return slot;
    }

    public void setSlot(int slot)
    {
        this.slot = slot;
    }

    public Inventory getInventory()
    {
        return inventory;
    }

    public void setInventory(Inventory inventory)
    {
        this.inventory = inventory;
    }

}
