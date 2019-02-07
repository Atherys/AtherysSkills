package com.atherys.skills.api.skill;

import com.atherys.skills.api.exception.CastException;
import com.atherys.skills.api.property.CastableProperties;
import org.spongepowered.api.entity.Entity;
import org.spongepowered.api.entity.living.Living;
import org.spongepowered.api.world.extent.EntityUniverse;

public abstract class TargetedSkill<T extends Castable<T,P>,P extends CastableProperties<T,P>> extends AbstractSkill<T,P> {
    protected TargetedSkill(String id, String name) {
        super(id, name);
    }

    @Override
    public CastResult cast(Living user, P properties, long timestamp, String... args) throws CastException {
        for (EntityUniverse.EntityHit hit : user.getWorld().getIntersectingEntities(user, properties.getOrDefault("range", 100.0))) {
            Entity entity = hit.getEntity();
            if ( entity instanceof Living ) {
                return cast(user, (Living) entity, properties, timestamp, args);
            }
        }

        throw CastErrors.noTarget();
    }

    public abstract CastResult cast(Living user, Living target, P properties, long timestamp, String... args) throws CastException;
}
