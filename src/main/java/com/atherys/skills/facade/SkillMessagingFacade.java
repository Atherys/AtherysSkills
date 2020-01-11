package com.atherys.skills.facade;

import com.atherys.core.utils.AbstractMessagingFacade;
import com.google.inject.Singleton;

@Singleton
public class SkillMessagingFacade extends AbstractMessagingFacade {
    SkillMessagingFacade() {
        super("Skills");
    }
}
