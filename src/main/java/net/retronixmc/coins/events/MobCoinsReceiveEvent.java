package net.retronixmc.coins.events;

import net.retronixmc.coins.profile.Profile;
import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

public class MobCoinsReceiveEvent
        extends Event implements Cancellable {
    private static final HandlerList handlers = new HandlerList();

    private boolean isCancelled;

    private Profile profile;
    private int amount;

    public MobCoinsReceiveEvent(Profile profile, int amount, boolean isCancelled) {
        this.profile = profile;
        this.amount = amount;
        this.isCancelled = isCancelled;
    }


    public Profile getProfile() { return this.profile; }



    public int getAmount() { return this.amount; }



    public void setAmount(int amount) { this.amount = amount; }
    
    public static HandlerList getHandlerList() { return handlers; }




    public boolean isCancelled() { return this.isCancelled; }




    public void setCancelled(boolean isCancelled) { this.isCancelled = isCancelled; }

    @Override
    public HandlerList getHandlers() {
        return handlers;
    }
}
