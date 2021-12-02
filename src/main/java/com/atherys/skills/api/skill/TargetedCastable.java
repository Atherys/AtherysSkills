package com.atherys.skills.api.skill;

import com.atherys.skills.AtherysSkills;
import com.atherys.skills.api.exception.CastException;
import org.spongepowered.api.block.BlockType;
import org.spongepowered.api.entity.living.Living;
import org.spongepowered.api.util.blockray.BlockRay;
import org.spongepowered.api.util.blockray.BlockRayHit;
import org.spongepowered.api.world.World;
import org.spongepowered.api.world.extent.EntityUniverse;

import java.util.Optional;
import java.util.function.Predicate;

public interface TargetedCastable extends Castable {
    Predicate<BlockRayHit<World>> blockFilter = hit -> {
        BlockType type = hit.getExtent().getBlockType(hit.getBlockPosition());
        return !AtherysSkills.getInstance().getConfig().PASSABLE_BLOCKS.contains(type.getId());
    };

    @Override
    default CastResult cast(Living user, long timestamp, String... args) throws CastException {
        double range = getRange(user);

        Optional<EntityUniverse.EntityHit> entityHit = user.getWorld()
                .getIntersectingEntities(user, range, eHit -> {
                    return eHit.getEntity() instanceof Living && user != eHit.getEntity();
                })
                .stream().reduce((accumulator, other) -> {
                    return accumulator.getDistance() < other.getDistance() ? accumulator : other;
                });

        if (!entityHit.isPresent()) throw CastErrors.noTarget();

        Living target = (Living) entityHit.get().getEntity();

        Optional<BlockRayHit<World>> end = BlockRay.from(user)
                .select(blockFilter)
                .distanceLimit(entityHit.get().getDistance())
                .build()
                .end();

        if (!end.isPresent()) {
            return cast(user, target, timestamp, args);
        }

        throw CastErrors.noTarget();
    }

    CastResult cast(Living user, Living target, long timestamp, String... args) throws CastException;

    default double getRange(Living user) {
        return 100.0;
    }
}
