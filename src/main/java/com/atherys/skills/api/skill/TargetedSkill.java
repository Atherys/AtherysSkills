package com.atherys.skills.api.skill;

import com.atherys.skills.api.exception.CastException;
import org.spongepowered.api.entity.Entity;
import org.spongepowered.api.entity.living.Living;
import org.spongepowered.api.world.extent.EntityUniverse;

public abstract class TargetedSkill extends AbstractSkill {
    protected TargetedSkill(String id, String name) {
        super(id, name);
    }

    @Override
    public CastResult cast(Living user, long timestamp, String... args) throws CastException {
        for (EntityUniverse.EntityHit hit : user.getWorld().getIntersectingEntities(user, getRange(user))) {
            Entity entity = hit.getEntity();

            if (entity.equals(user)) {
                continue;
            }

            if ( entity instanceof Living ) {
                return cast(user, (Living) entity, timestamp, args);
            }
        }

        throw CastErrors.noTarget();
    }

    public abstract CastResult cast(Living user, Living target, long timestamp, String... args) throws CastException;

    public double getRange(Living user) {
        return 100.0;
    }
}
