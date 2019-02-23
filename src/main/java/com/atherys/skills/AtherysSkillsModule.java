package com.atherys.skills;

import com.atherys.skills.facade.EffectFacade;
import com.atherys.skills.facade.SkillFacade;
import com.atherys.skills.registry.EffectRegistry;
import com.atherys.skills.registry.ResourceRegistry;
import com.atherys.skills.registry.SkillRegistry;
import com.atherys.skills.sevice.CooldownService;
import com.atherys.skills.sevice.EffectService;
import com.atherys.skills.sevice.ResourceService;
import com.atherys.skills.sevice.SkillService;
import com.google.inject.AbstractModule;
import com.google.inject.Scopes;

public class AtherysSkillsModule extends AbstractModule {
    @Override
    protected void configure() {
        // Config
        bind(AtherysSkillsConfig.class);

        // Registries
        bind(ResourceRegistry.class).in(Scopes.SINGLETON);
        bind(SkillRegistry.class).in(Scopes.SINGLETON);
        bind(EffectRegistry.class).in(Scopes.SINGLETON);

        // Services
        bind(CooldownService.class).in(Scopes.SINGLETON);
        bind(EffectService.class).in(Scopes.SINGLETON);
        bind(ResourceService.class).in(Scopes.SINGLETON);
        bind(SkillService.class).in(Scopes.SINGLETON);

        // Facades
        bind(SkillFacade.class).in(Scopes.SINGLETON);
        bind(EffectFacade.class).in(Scopes.SINGLETON);
    }
}
