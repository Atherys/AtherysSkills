package com.atherys.skills.api.skill;

import com.atherys.skills.AtherysSkills;
import com.atherys.skills.api.exception.CastException;
import com.flowpowered.math.imaginary.Quaterniond;
import com.flowpowered.math.vector.Vector2d;
import com.flowpowered.math.vector.Vector3d;
import com.flowpowered.math.vector.Vector3i;
import org.spongepowered.api.block.BlockType;
import org.spongepowered.api.entity.living.Living;
import org.spongepowered.api.util.blockray.BlockRay;
import org.spongepowered.api.util.blockray.BlockRayHit;
import org.spongepowered.api.world.World;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

public interface TargetedConeCastable extends TargetedCastable {
    @Override
    default CastResult cast(Living user, long timestamp, String... args) throws CastException {
        Collection<Living> nearby = user.getNearbyEntities(getRange(user)).stream()
                .filter(entity -> entity instanceof Living)
                .map(entity -> (Living) entity)
                .collect(Collectors.toList());

        if (nearby.isEmpty()) throw CastErrors.noTarget();

        /*
        BlockRay<World> blockRay = BlockRay.from(user).distanceLimit(range).build();
        Set<Vector3i> locations = new HashSet<>();

        // Cast ray once so we know what blocks are visible
        while (blockRay.hasNext()) {
            BlockRayHit<World> blockRayHit = blockRay.next();
            BlockType blockType = blockRayHit.getExtent().getBlockType(blockRayHit.getBlockPosition());

            if (!AtherysSkills.getInstance().getConfig().PASSABLE_BLOCKS.contains(blockType)) {
                break;
            }

            locations.add(blockRayHit.getBlockPosition());
        }
        */

        final Vector3d rotation = user.getHeadRotation();
        final Vector3d axis = Quaterniond.fromAxesAnglesDeg(rotation.getX(), -rotation.getY(), rotation.getZ()).getDirection();
        final Vector3d userPosition = user.getLocation().getPosition();

        Living finalTarget = null;
        double differenceSquared = Double.MAX_VALUE;

        for (Living target : nearby) {
            Vector3d targetPosition = target.getLocation().getPosition();
            Vector3d between = targetPosition.sub(userPosition);

            double dot = axis.dot(between.normalize());

            if (dot > Math.cos(getAngle(user))) {
                double lengthSquared = between.lengthSquared();
                if (finalTarget == null || lengthSquared < differenceSquared) {
                    differenceSquared = lengthSquared;
                    finalTarget = target;
                }
            }
        }

        if (finalTarget == null) {
            throw CastErrors.noTarget();
        }

        return cast(user, finalTarget, timestamp, args);
    }

    default double getAngle(Living user) {
        return 5;
    }
}
