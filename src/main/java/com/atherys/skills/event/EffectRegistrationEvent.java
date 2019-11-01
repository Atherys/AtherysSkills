package com.atherys.skills.event;

import com.atherys.skills.api.effect.Applyable;
import com.atherys.skills.service.EffectService;
import org.spongepowered.api.Sponge;
import org.spongepowered.api.event.Event;
import org.spongepowered.api.event.cause.Cause;

public class EffectRegistrationEvent implements Event {

    private Cause cause;

    private EffectService effectService;

    public EffectRegistrationEvent(EffectService effectService) {
        cause = Cause.of(Sponge.getCauseStackManager().getCurrentContext(), effectService);
        this.effectService = effectService;
    }

    public void registerEffects(Applyable... applyables) {
        effectService.registerEffects(applyables);
    }

    @Override
    public Cause getCause() {
        return null;
    }
}
