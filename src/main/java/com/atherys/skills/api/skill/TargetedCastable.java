package com.atherys.skills.api.skill;

import com.atherys.skills.AtherysSkills;
import com.atherys.skills.api.exception.CastException;
import com.flowpowered.math.vector.Vector3i;
import org.spongepowered.api.block.BlockType;
import org.spongepowered.api.entity.Entity;
import org.spongepowered.api.entity.living.Living;
import org.spongepowered.api.util.blockray.BlockRay;
import org.spongepowered.api.util.blockray.BlockRayHit;
import org.spongepowered.api.world.World;

import java.util.HashSet;
import java.util.Set;

public interface TargetedCastable extends Castable {
    @Override
    default CastResult cast(Living user, long timestamp, String... args) throws CastException {
        double range = getRange(user);
        BlockRay<World> blockRay = BlockRay.from(user).distanceLimit(range).build();
        Set<Vector3i> locations = new HashSet<>();

        while (blockRay.hasNext()) {
            BlockRayHit<World> blockRayHit = blockRay.next();
            BlockType blockType = blockRayHit.getExtent().getBlockType(blockRayHit.getBlockPosition());

            if (!AtherysSkills.getInstance().getConfig().PASSABLE_BLOCKS.contains(blockType)) {
                break;
            }

            locations.add(blockRayHit.getBlockPosition());
            locations.add(blockRayHit.getBlockPosition().add(0, 1, 0));
            locations.add(blockRayHit.getBlockPosition().add(0, -1, 0));
        }

        for (Entity entity : user.getNearbyEntities(getRange(user))) {

            if (entity.equals(user)) {
                continue;
            }

            if (entity instanceof Living) {
                if (locations.contains(entity.getLocation().getBlockPosition())) {
                    return cast(user, (Living) entity, timestamp, args);
                }
            }
        }

        throw CastErrors.noTarget();
    }

    CastResult cast(Living user, Living target, long timestamp, String... args) throws CastException;

    default double getRange(Living user) {
        return 100.0;
    }
}
