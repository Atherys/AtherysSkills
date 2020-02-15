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
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.scheduler.Task;
import org.spongepowered.api.text.format.TextColors;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

@Singleton
public class ResourceService {

    private AtherysSkillsConfig config;

    private Map<UUID, ResourceUser> resourceUsers = new HashMap<>();

    private Task resourceRegenTask;

    @Inject
    ResourceService(AtherysSkillsConfig config) {
        this.config = config;
        this.resourceRegenTask = Task.builder()
                .name("atherysskills-resource-regen-task")
                .execute(this::regenResources)
                .intervalTicks(config.RESOURCE_REGEN_TICK_INTERVAL)
                .submit(AtherysSkills.getInstance());
    }

    public void regenResources() {
        resourceUsers.forEach((uuid, user) -> {
            Resource resource = user.getResource();

            // If the curent amount of resources is the same as the maximum, don't regen
            if (resource.getCurrent() >= resource.getMax()) {
                return;
            }

            double regenAmount = resource.getRegen();

            if (resource.getMax() - resource.getCurrent() <= regenAmount) {
                regenAmount = resource.getMax() - resource.getCurrent();
            }

            // TODO: Make this work for all living entities, not just players
            Optional<Player> entity = Sponge.getServer().getOnlinePlayers().stream().filter(p -> p.getUniqueId().equals(uuid)).findAny();

            ResourceRegenEvent event;
            if (entity.isPresent()) {
                event = new ResourceRegenEvent(entity.get(), user, regenAmount);
            } else {
                event = new ResourceRegenEvent(user, regenAmount);
            }

            Sponge.getEventManager().post(event);

            if (event.isCancelled()) {
                return;
            }

            user.addResource(event.getRegenAmount());
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
