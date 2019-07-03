package com.atherys.skills.service;

import com.atherys.skills.AtherysSkills;
import com.atherys.skills.AtherysSkillsConfig;
import com.atherys.skills.api.event.ResourceRegenEvent;
import com.atherys.skills.api.resource.Resource;
import com.atherys.skills.api.resource.ResourceUser;
import com.atherys.skills.facade.SkillMessagingFacade;
import com.atherys.skills.resource.ActionPoints;
import com.atherys.skills.resource.EntityResourceUser;
import com.google.inject.Inject;
import com.google.inject.Singleton;
import org.spongepowered.api.Sponge;
import org.spongepowered.api.entity.living.Living;
import org.spongepowered.api.scheduler.Task;
import org.spongepowered.api.text.format.TextColors;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Singleton
public class ResourceService {

    private AtherysSkillsConfig config;

    private SkillMessagingFacade skillMessagingFacade;

    private Map<UUID, ResourceUser> resourceUsers = new HashMap<>();

    private Task resourceRegenTask;

    @Inject
    ResourceService(AtherysSkillsConfig config, SkillMessagingFacade skillMessagingFacade) {
        this.config = config;
        this.resourceRegenTask = Task.builder()
                .name("atherysskills-resource-regen-task")
                .execute(this::regenResources)
                .intervalTicks(config.RESOURCE_REGEN_TICK_INTERVAL)
                .submit(AtherysSkills.getInstance());

        // TODO: Move informational messages to facade layer
        this.skillMessagingFacade = skillMessagingFacade;
    }

    public void regenResources() {
        resourceUsers.forEach((uuid, user) -> {
            Resource resource = user.getResource();
            double regenAmount = resource.getRegen();

            ResourceRegenEvent event = new ResourceRegenEvent(user, regenAmount);
            Sponge.getEventManager().post(event);

            if (event.isCancelled()) {
                return;
            }

            user.addResource(event.getRegenAmount());

            // TODO: Move informational messages to facade layer
            if (user.getResource().getCurrent() != user.getResource().getMax()) {
                skillMessagingFacade.info(Sponge.getServer().getPlayer(uuid).get(), "Current ", user.getResource().getColor(), user.getResource().getName(), TextColors.RESET, " is ", user.getResource().getCurrent(), "/", user.getResource().getMax());
            }
        });
    }

    public ResourceUser getOrCreateUser(Living user) {
        ResourceUser resourceUser = resourceUsers.get(user.getUniqueId());

        if (resourceUser == null) {
            resourceUser = new EntityResourceUser(user, new ActionPoints(100.0));
            resourceUsers.put(user.getUniqueId(), resourceUser);
        }

        return resourceUser;
    }

    public void withdrawResource(Living user, double amount) {
        getOrCreateUser(user).removeResource(amount);
    }


}
