package net.retronixmc.mobcoins.shop;

import org.bukkit.inventory.ItemStack;

import java.util.List;

public class ShopItem {

    private ItemStack item;
    private int price;
    private String type;
    private int slot;
    private List<String> commands;

    public List<String> getCommands() {
        return commands;
    }

    public void setCommands(List<String> commands) {
        this.commands = commands;
    }

    public ShopItem(ItemStack i, int p, int s)
    {
        item = i;
        price = p;
        type = "ITEM";
        slot = s;
    }

    public ItemStack getItem() {
        return item;
    }

    public void setItem(ItemStack item) {
        this.item = item;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public void setType(String type)
    {
        this.type = type;
    }

    public String getType()
    {
        return type;
    }

    public int getSlot()
    {
        return slot;
    }

    public void setSlot(int s)
    {
        slot = s;
    }

}
