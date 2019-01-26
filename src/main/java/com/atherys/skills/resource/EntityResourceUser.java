package com.atherys.skills.resource;

import com.atherys.core.db.SpongeIdentifiable;
import com.atherys.skills.api.resource.Resource;
import com.atherys.skills.api.resource.ResourceUser;
import org.spongepowered.api.entity.living.Living;

import javax.annotation.Nonnull;
import java.util.UUID;

public class EntityResourceUser implements SpongeIdentifiable, ResourceUser {

    private UUID uuid;

    private Resource resource;

    public EntityResourceUser(Living entity, Resource resource) {
        this.uuid = entity.getUniqueId();
        this.resource = resource;
    }

    @Override
    public Resource getResource() {
        return resource;
    }

    @Override
    public void setResource(Resource resource) {
        this.resource = resource;
    }

    @Nonnull
    @Override
    public UUID getId() {
        return uuid;
    }
}
