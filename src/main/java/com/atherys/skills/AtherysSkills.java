package com.atherys.skills;

import com.atherys.skills.facade.EffectFacade;
import com.atherys.skills.facade.SkillFacade;
import com.atherys.skills.registry.EffectRegistry;
import com.atherys.skills.registry.SkillRegistry;
import com.atherys.skills.sevice.CooldownService;
import com.atherys.skills.sevice.EffectService;
import com.atherys.skills.sevice.ResourceService;
import com.atherys.skills.sevice.SkillService;
import com.google.inject.Inject;

public class AtherysSkills {

    private static AtherysSkills instance;

    private static class Components {

        @Inject
        EffectRegistry effectRegistry;

        @Inject
        SkillRegistry skillRegistry;

        @Inject
        EffectService effectService;

        @Inject
        SkillService skillService;

        @Inject
        CooldownService cooldownService;

        @Inject
        ResourceService resourceService;

        @Inject
        EffectFacade effectFacade;

        @Inject
        SkillFacade skillFacade;

    }

    private Components components;

    public static AtherysSkills getInstance() {
        return instance;
    }

    public EffectRegistry getEffectRegistry() {
        return components.effectRegistry;
    }

    public SkillRegistry getSkillRegistry() {
        return components.skillRegistry;
    }

    public EffectService getEffectService() {
        return components.effectService;
    }

    public SkillService getSkillService() {
        return components.skillService;
    }

    public CooldownService getCooldownService() {
        return components.cooldownService;
    }

    public ResourceService getResourceService() {
        return components.resourceService;
    }

    public EffectFacade getEffectFacade() {
        return components.effectFacade;
    }

    public SkillFacade getSkillFacade() {
        return components.skillFacade;
    }
}
