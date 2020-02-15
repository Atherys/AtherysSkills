package com.atherys.skills.api.event;

import com.atherys.skills.api.resource.ResourceUser;
import org.spongepowered.api.Sponge;
import org.spongepowered.api.entity.living.Living;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.event.Cancellable;
import org.spongepowered.api.event.Event;
import org.spongepowered.api.event.cause.Cause;

import java.util.Optional;

public class ResourceRegenEvent implements Event, Cancellable {

    private boolean cancelled;

    private Cause cause;

    private Living entity;

    private ResourceUser resourceUser;

    private double regenAmount;

    public ResourceRegenEvent(Living entity, ResourceUser resourceUser, double regenAmount) {
        this.cause = Cause.of(Sponge.getCauseStackManager().getCurrentContext(), entity, resourceUser);
        this.entity = entity;
        this.resourceUser = resourceUser;
        this.regenAmount = regenAmount;
    }

    public ResourceRegenEvent(ResourceUser resourceUser, double regenAmount) {
        this.cause = Cause.of(Sponge.getCauseStackManager().getCurrentContext(), resourceUser);
        this.resourceUser = resourceUser;
        this.regenAmount = regenAmount;
    }

    public ResourceUser getResourceUser() {
        return resourceUser;
    }

    public Optional<Living> getEntity() {
        return Optional.ofNullable(entity);
    }

    public double getRegenAmount() {
        return regenAmount;
    }

    public void setRegenAmount(double regenAmount) {
        this.regenAmount = regenAmount;
    }

    @Override
    public boolean isCancelled() {
        return cancelled;
    }

    @Override
    public void setCancelled(boolean cancel) {
        this.cancelled = cancel;
    }

    @Override
    public Cause getCause() {
        return cause;
    }
}
