package com.atherys.skills.sevice;

import com.atherys.skills.api.resource.ResourceUser;
import com.atherys.skills.api.skill.Castable;
import com.atherys.skills.registry.ResourceRegistry;
import com.atherys.skills.resource.ActionPoints;
import com.atherys.skills.resource.EntityResourceUser;
import com.google.inject.Inject;
import com.google.inject.Singleton;
import org.spongepowered.api.entity.living.Living;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Singleton
public class ResourceService {

    @Inject
    ResourceRegistry resourceRegistry;

    private Map<UUID, ResourceUser> resourceUsers = new HashMap<>();

    public ResourceUser getOrCreateUser(Living user) {
        ResourceUser resourceUser = resourceUsers.get(user.getUniqueId());

        if (resourceUser == null) {
            resourceUser = new EntityResourceUser(user, new ActionPoints(0.0));
            resourceUsers.put(user.getUniqueId(), resourceUser);
        }

        return resourceUser;
    }

    public void withdrawResource(Living user, Castable castable) {

    }
}
