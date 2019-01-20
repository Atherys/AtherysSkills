package com.atherys.skills.event;

import com.atherys.skills.api.skill.Castable;
import com.atherys.skills.registry.SkillRegistry;
import org.spongepowered.api.Sponge;
import org.spongepowered.api.event.Event;
import org.spongepowered.api.event.cause.Cause;

public class SkillRegistrationEvent implements Event {

    private Cause cause;

    private SkillRegistry registry;

    public SkillRegistrationEvent(SkillRegistry registry) {
        cause = Cause.of(Sponge.getCauseStackManager().getCurrentContext(), registry);
        this.registry = registry;
    }

    public void registerSkill(Castable castable) {
        registry.register(castable);
    }

    @Override
    public Cause getCause() {
        return cause;
    }
}
