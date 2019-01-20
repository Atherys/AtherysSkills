package com.atherys.skills.event;

import com.atherys.skills.api.effect.Applyable;
import com.atherys.skills.registry.EffectRegistry;
import org.spongepowered.api.Sponge;
import org.spongepowered.api.event.Event;
import org.spongepowered.api.event.cause.Cause;

public class EffectRegistrationEvent implements Event {

    private Cause cause;

    private EffectRegistry registry;

    public EffectRegistrationEvent(EffectRegistry registry) {
        cause = Cause.of(Sponge.getCauseStackManager().getCurrentContext(), registry);
        this.registry = registry;
    }

    public void registerEffect(Applyable applyable) {
        registry.register(applyable);
    }

    @Override
    public Cause getCause() {
        return cause;
    }

}
