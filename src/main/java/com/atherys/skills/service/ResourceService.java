package com.atherys.skills.service;

import com.atherys.skills.AtherysSkills;
import com.atherys.skills.AtherysSkillsConfig;
import com.atherys.skills.api.event.ResourceEvent;
import com.atherys.skills.api.resource.ResourceUser;
import com.atherys.skills.resource.EntityResourceUser;
import com.google.inject.Inject;
import com.google.inject.Singleton;
import org.spongepowered.api.Sponge;
import org.spongepowered.api.entity.living.Living;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.scheduler.Task;

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
            // If the curent amount of resources is the same as the maximum, don't regen
            if (user.getCurrent() >= user.getMax()) {
                return;
            }

            double regenAmount = config.RESOURCE_REGEN_RATE;

            if (user.getMax() - user.getCurrent() <= regenAmount) {
                regenAmount = user.getMax() - user.getCurrent();
            }

            // TODO: Make this work for all living entities, not just players
            Optional<Player> entity = Sponge.getServer().getPlayer(uuid);

            ResourceEvent.Regen event;
            if (entity.isPresent()) {
                event = new ResourceEvent.Regen(entity.get(), user, regenAmount);
            } else {
                event = new ResourceEvent.Regen(user, regenAmount);
            }

            Sponge.getEventManager().post(event);

            if (event.isCancelled()) {
                return;
            }

            user.fill(event.getRegenAmount());
        });
    }

    public ResourceUser getOrCreateUser(Living user) {
        ResourceUser resourceUser = resourceUsers.get(user.getUniqueId());

        if (resourceUser == null) {
            resourceUser = new EntityResourceUser(user);
            resourceUser.setMax(config.RESOURCE_LIMIT);
            resourceUser.fill();
            resourceUsers.put(user.getUniqueId(), resourceUser);

            Sponge.getEventManager().post(new ResourceEvent.Create(user, resourceUser));
        }

        return resourceUser;
    }

    public void withdrawResource(Living user, double amount) {
        getOrCreateUser(user).drain(amount);
    }
}
