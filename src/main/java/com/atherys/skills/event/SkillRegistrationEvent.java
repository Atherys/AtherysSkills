package com.atherys.skills.event;

import com.atherys.skills.api.skill.Castable;
import com.atherys.skills.service.SkillService;
import org.spongepowered.api.Sponge;
import org.spongepowered.api.event.Event;
import org.spongepowered.api.event.cause.Cause;

public class SkillRegistrationEvent implements Event {

    private Cause cause;

    private SkillService skillService;

    public SkillRegistrationEvent(SkillService skillService) {
        cause = Cause.of(Sponge.getCauseStackManager().getCurrentContext(), skillService);
        this.skillService = skillService;
    }

    public void registerSkills(Castable... castables) {
        skillService.registerSkills(castables);
    }

    @Override
    public Cause getCause() {
        return cause;
    }
}
