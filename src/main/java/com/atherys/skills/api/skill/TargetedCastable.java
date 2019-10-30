package com.atherys.skills.api.skill;

import com.atherys.skills.api.exception.CastException;
import org.spongepowered.api.entity.Entity;
import org.spongepowered.api.entity.living.Living;
import org.spongepowered.api.util.blockray.BlockRay;
import org.spongepowered.api.util.blockray.BlockRayHit;
import org.spongepowered.api.world.World;
import org.spongepowered.api.world.extent.EntityUniverse;

import java.util.Optional;

public interface TargetedCastable extends Castable {
    @Override
    default CastResult cast(Living user, long timestamp, String... args) throws CastException {
        // Cast an entity ray from the user, in the direction they are looking ( limited by the range )
        // If no hits are registered, throw CastErrors.noTarget()
        for (EntityUniverse.EntityHit hit : user.getWorld().getIntersectingEntities(user, getRange(user))) {
            Entity entity = hit.getEntity();

            if (entity.equals(user)) {
                continue;
            }

            // If a hit is registered
            if (entity instanceof Living) {

                /* TODO: Fix this
                // Cast a block ray from the registered target back to the user
                BlockRay<World> blockRay = BlockRay
                        .from(entity.getLocation())
                        .to(user.getLocation().getPosition())
                        .stopFilter(BlockRay.onlyAirFilter())
                        .build();

                Optional<BlockRayHit<World>> end = blockRay.end();

                // If the block ray has hit a valid non-air block, throw CastErrors.obscuredTarget()
                // Otherwise, cast the skill
                if (end.isPresent()) {
                    throw CastErrors.obscuredTarget();
                } else {
                    return cast(user, (Living) entity, timestamp, args);
                }
                */
                return cast(user, (Living) entity, timestamp, args);
            }
        }

        throw CastErrors.noTarget();
    }

    CastResult cast(Living user, Living target, long timestamp, String... args) throws CastException;

    default double getRange(Living user) {
        return 100.0;
    }
}
