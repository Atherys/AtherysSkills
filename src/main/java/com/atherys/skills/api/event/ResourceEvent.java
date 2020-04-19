package com.atherys.skills.api.event;

import com.atherys.skills.api.resource.ResourceUser;
import org.spongepowered.api.Sponge;
import org.spongepowered.api.entity.living.Living;
import org.spongepowered.api.event.Cancellable;
import org.spongepowered.api.event.Event;
import org.spongepowered.api.event.cause.Cause;

import java.util.Optional;

public class ResourceEvent implements Event, Cancellable {

    private boolean cancelled;

    private Cause cause;

    private Living entity;

    private ResourceUser resourceUser;


    public ResourceEvent(Living entity, ResourceUser resourceUser) {
        this.cause = Cause.of(Sponge.getCauseStackManager().getCurrentContext(), entity, resourceUser);
        this.entity = entity;
        this.resourceUser = resourceUser;
    }

    public ResourceEvent(ResourceUser resourceUser) {
        this.cause = Cause.of(Sponge.getCauseStackManager().getCurrentContext(), resourceUser);
        this.resourceUser = resourceUser;
    }

    public ResourceUser getResourceUser() {
        return resourceUser;
    }

    public Optional<Living> getEntity() {
        return Optional.ofNullable(entity);
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

    public static class Create extends ResourceEvent {
        public Create(Living entity, ResourceUser resourceUser) {
            super(entity, resourceUser);
        }

        public Create(ResourceUser resourceUser) {
            super(resourceUser);
        }
    }

    public static class Regen extends ResourceEvent implements Cancellable {
        private double regenAmount;

        public Regen(Living entity, ResourceUser resourceUser, double regenAmount) {
            super(entity, resourceUser);
            this.regenAmount = regenAmount;
        }

        public Regen(ResourceUser resourceUser, double regenAmount) {
            super(resourceUser);
            this.regenAmount = regenAmount;
        }

        public void setRegenAmount(double regenAmount) {
            this.regenAmount = regenAmount;
        }

        public double getRegenAmount() {
            return regenAmount;
        }
    }
}
