package com.atherys.skills.effect;

import com.atherys.skills.api.effect.Applyable;
import com.atherys.skills.api.effect.ApplyableCarrier;
import org.spongepowered.api.entity.living.Living;

import javax.annotation.Nonnull;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;

public class EntityEffectCarrier implements ApplyableCarrier<Living> {

    private Living cachedLiving;

    private Set<Applyable> effects = new HashSet<>();

    public EntityEffectCarrier(Living living) {
        this.cachedLiving = living;
    }

    @Override
    public Set<Applyable> getEffects() {
        return effects;
    }

    @Override
    public Optional<Living> getLiving() {
        if ( cachedLiving == null ) {
            return Optional.empty();
        }

        if ( cachedLiving.isRemoved() || !cachedLiving.isLoaded() ) {
            return Optional.empty();
        }

        return Optional.of(cachedLiving);
    }

    @Nonnull
    @Override
    public UUID getId() {
        return cachedLiving.getUniqueId();
    }
}
