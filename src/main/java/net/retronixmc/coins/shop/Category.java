package net.retronixmc.coins.shop;

import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;

public class Category {
    private ItemStack icon;
    private ArrayList<ShopItem> categoryItems = new ArrayList<>();
    private Inventory inventory;
    private int slot;
    private String name;

    public Category(ItemStack itemStack, String name, int slot)
    {
        icon = itemStack;
        this.name = name;
        this.slot = slot;
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

    public void setName(String name)
    {
        this.name = name;
    }

    public String getName()
    {
        return name;
    }

    public int getSlot()
    {
        return slot;
    }

    public void setSlot(int slot)
    {
        this.slot = slot;
    }

    public void setInventory(Inventory inventory)
    {
        this.inventory = inventory;
    }

    public Inventory getInventory()
    {
        return inventory;
    }

}
