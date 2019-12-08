package com.atherys.skills;

import com.atherys.skills.facade.EffectFacade;
import com.atherys.skills.facade.SkillFacade;
import com.atherys.skills.listener.EntityListener;
import com.atherys.skills.registry.ResourceRegistry;
import com.atherys.skills.service.CooldownService;
import com.atherys.skills.service.EffectService;
import com.atherys.skills.service.ResourceService;
import com.atherys.skills.service.SkillService;
import com.google.inject.AbstractModule;
import com.google.inject.Scopes;

public class AtherysSkillsModule extends AbstractModule {
    @Override
    protected void configure() {
        // Config
        bind(AtherysSkillsConfig.class);

        // Registries
        bind(ResourceRegistry.class).in(Scopes.SINGLETON);

        // Listeners
        bind(EntityListener.class).in(Scopes.SINGLETON);

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
