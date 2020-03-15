package com.atherys.skills.api.skill;

import com.atherys.skills.AtherysSkills;
import com.atherys.skills.api.exception.CastException;
import com.flowpowered.math.vector.Vector3d;
import org.spongepowered.api.block.BlockType;
import org.spongepowered.api.data.property.AbstractProperty;
import org.spongepowered.api.data.property.entity.EyeLocationProperty;
import org.spongepowered.api.entity.Entity;
import org.spongepowered.api.entity.living.Living;
import org.spongepowered.api.util.AABB;
import org.spongepowered.api.util.blockray.BlockRay;
import org.spongepowered.api.util.blockray.BlockRayHit;
import org.spongepowered.api.world.Location;
import org.spongepowered.api.world.World;
import org.spongepowered.api.world.extent.EntityUniverse;

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

                // Cast a block ray from the registered target back to the user
                Vector3d to = entity.getBoundingBox().map(AABB::getCenter).orElse(entity.getLocation().getPosition());

                Location<World> from = user.getWorld().getLocation(
                        user.getProperty(EyeLocationProperty.class)
                                .map(AbstractProperty::getValue)
                                .orElse(user.getLocation().getPosition().add(Vector3d.UNIT_Y))
                );

                BlockRay<World> blockRay = BlockRay
                        .from(from)
                        .to(to)
                        .build();

                while (blockRay.hasNext()) {
                    BlockRayHit<World> rayHit = blockRay.next();
                    BlockType blockType = rayHit.getExtent().getBlockType(rayHit.getBlockPosition());

                    if (!AtherysSkills.getInstance().getConfig().PASSABLE_BLOCKS.contains(blockType)) {
                        throw CastErrors.obscuredTarget();
                    }
                }

                // If the block ray has hit a valid non-air block, throw CastErrors.obscuredTarget()
                // Otherwise, cast the skill
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
