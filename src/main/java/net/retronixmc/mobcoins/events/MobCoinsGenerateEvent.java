package net.retronixmc.mobcoins.events;

import net.retronixmc.mobcoins.generator.MobCoinGenerator;
import net.retronixmc.mobcoins.profile.Profile;
import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

public class MobCoinsGenerateEvent extends Event implements Cancellable {
    private static final HandlerList handlers = new HandlerList();

    private boolean isCancelled;

    private MobCoinGenerator generator;
    private int amount;

    public MobCoinsGenerateEvent(MobCoinGenerator generator, int amount) {
        this.generator = generator;
        this.amount = amount;
    }


    public MobCoinGenerator getGenerator() { return this.generator; }



    public int getAmount() { return this.amount; }



    public void setAmount(int amount) { this.amount = amount; }




    public HandlerList getHandlers() { return handlers; }



    public static HandlerList getHandlerList() { return handlers; }




    public boolean isCancelled() { return this.isCancelled; }




    public void setCancelled(boolean isCancelled) { this.isCancelled = isCancelled; }
}
