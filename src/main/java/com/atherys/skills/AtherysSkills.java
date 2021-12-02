package com.atherys.skills;

import com.atherys.core.AtherysCore;
import com.atherys.core.command.CommandService;
import com.atherys.skills.api.skill.Castable;
import com.atherys.skills.command.effect.EffectCommand;
import com.atherys.skills.command.skill.SkillCommand;
import com.atherys.skills.event.EffectRegistrationEvent;
import com.atherys.skills.event.SkillRegistrationEvent;
import com.atherys.skills.facade.EffectFacade;
import com.atherys.skills.facade.ResourceFacade;
import com.atherys.skills.facade.SkillFacade;
import com.atherys.skills.facade.SkillMessagingFacade;
import com.atherys.skills.listener.EntityListener;
import com.atherys.skills.listener.ResourceListener;
import com.atherys.skills.service.CooldownService;
import com.atherys.skills.service.EffectService;
import com.atherys.skills.service.ResourceService;
import com.atherys.skills.service.SkillService;
import com.atherys.skills.skill.SimpleDamageEffectSkill;
import com.atherys.skills.skill.SimpleDamageSkill;
import com.google.inject.Inject;
import com.google.inject.Injector;
import org.slf4j.Logger;
import org.spongepowered.api.Sponge;
import org.spongepowered.api.event.Listener;
import org.spongepowered.api.event.Order;
import org.spongepowered.api.event.game.GameRegistryEvent;
import org.spongepowered.api.event.game.state.GameConstructionEvent;
import org.spongepowered.api.event.game.state.GameInitializationEvent;
import org.spongepowered.api.event.game.state.GamePreInitializationEvent;
import org.spongepowered.api.event.game.state.GameStartedServerEvent;
import org.spongepowered.api.plugin.Dependency;
import org.spongepowered.api.plugin.Plugin;

import static com.atherys.skills.AtherysSkills.*;

@Plugin(id = ID, name = NAME, description = DESCRIPTION, version = VERSION, dependencies = {@Dependency(id = "atheryscore")})
public class AtherysSkills {

    public static final String ID = "atherysskills";

    public static final String NAME = "Atherys Skills";

    public static final String DESCRIPTION = "A skill plugin for the A'therys Horizons server";

    public static final String VERSION = "%PROJECT_VERSION%";

    private static AtherysSkills instance;

    private static boolean init;

    @Inject
    private Logger logger;

    @Inject
    private Injector spongeInjector;

    private Injector skillsInjector;

    private Components components;

    @Listener(order = Order.EARLY)
    public void onPreInit(GamePreInitializationEvent event) {
        instance = this;

        components = new Components();

        skillsInjector = spongeInjector.createChildInjector(new AtherysSkillsModule());
        skillsInjector.injectMembers(components);

        Sponge.getEventManager().registerListeners(this, components.entityListener);
        Sponge.getEventManager().registerListeners(this, components.resourceListener);

        init = true;
    }

    @Listener
    public void onStart(GameStartedServerEvent event) {
        if (!init) return;

        Sponge.getEventManager().post(new EffectRegistrationEvent(components.effectService));
        Sponge.getEventManager().post(new SkillRegistrationEvent(components.skillService));

        components.skillService.registerSkill(new SimpleDamageSkill());
        components.skillService.registerSkill(new SimpleDamageEffectSkill());

        try {
            AtherysCore.getCommandService().register(new SkillCommand(), this);
            AtherysCore.getCommandService().register(new EffectCommand(), this);
        } catch (CommandService.AnnotatedCommandException e) {
            e.printStackTrace();
        }
    }

    @Listener
    public void onSkillResgistration(GameRegistryEvent.Register<Castable> event) {
        logger.info("Registering skills...");
    }

    public static AtherysSkills getInstance() {
        return instance;
    }

    public AtherysSkillsConfig getConfig() {
        return components.config;
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

    public SkillMessagingFacade getMessagingFacade() {
        return components.skillMessagingFacade;
    }

    public Logger getLogger() {
        return logger;
    }

    private static class Components {

        @Inject
        AtherysSkillsConfig config;

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

        @Inject
        SkillMessagingFacade skillMessagingFacade;

        @Inject
        ResourceFacade resourceFacade;

        @Inject
        EntityListener entityListener;

        @Inject
        ResourceListener resourceListener;
    }
}
