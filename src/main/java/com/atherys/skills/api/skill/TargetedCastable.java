package com.atherys.skills.api.skill;

import com.atherys.skills.AtherysSkills;
import com.atherys.skills.api.exception.CastException;
import com.flowpowered.math.imaginary.Quaterniond;
import com.flowpowered.math.vector.Vector3d;
import com.flowpowered.math.vector.Vector3i;
import org.spongepowered.api.block.BlockType;
import org.spongepowered.api.data.property.entity.EyeLocationProperty;
import org.spongepowered.api.entity.Entity;
import org.spongepowered.api.entity.living.Living;
import org.spongepowered.api.util.Tuple;
import org.spongepowered.api.util.blockray.BlockRay;
import org.spongepowered.api.util.blockray.BlockRayHit;
import org.spongepowered.api.world.World;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

public interface TargetedCastable extends Castable {
    @Override
    default CastResult cast(Living user, long timestamp, String... args) throws CastException {
        double range = getRange(user);
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

        final Vector3d rotation = user.getHeadRotation();
        final Vector3d direction = Quaterniond.fromAxesAnglesDeg(rotation.getX(), -rotation.getY(), rotation.getZ()).getDirection();

        // default to location if eyes can't be found for some reason
        Optional<EyeLocationProperty> eyeLocationProperty = user.getProperty(EyeLocationProperty.class);
        final Vector3d eyeLocation = eyeLocationProperty.map(EyeLocationProperty::getValue).orElse(user.getLocation().getPosition());

        Entity closest = null;
        double distance = 0;

        for (Entity entity : user.getNearbyEntities(getRange(user))) {
            if (entity.equals(user)) {
                continue;
            }

            if (!(entity instanceof Living)) {
                continue;
            }

            if (!entity.getBoundingBox().isPresent()) {
                continue;
            }

            Optional<Tuple<Vector3d, Vector3d>> intersection = entity.getBoundingBox().get().intersects(eyeLocation, direction);
            if (!intersection.isPresent()) {
                continue;
            }

            Vector3d hitLoc = intersection.get().getFirst();
            if (!locations.contains(hitLoc.toInt())) {
                continue;
            }

            double newDistance = hitLoc.distanceSquared(eyeLocation);
            if (closest == null || newDistance < distance) {
                distance = newDistance;
                closest = entity;
            }
        }

        if (closest == null) {
            throw CastErrors.noTarget();
        }

        return cast(user, (Living) closest, timestamp, args);
    }

    CastResult cast(Living user, Living target, long timestamp, String... args) throws CastException;

    default double getRange(Living user) {
        return 100.0;
    }
}
