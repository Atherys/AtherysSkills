package com.atherys.skills.api.effect;

import org.spongepowered.api.entity.living.Living;

import java.util.Optional;
import java.util.Set;

/**
 * Represents a {@link Living} object which can carry and be effected by {@link Applyable}s.
 */
public interface ApplyableCarrier<T extends Living> {

    Optional<T> getLiving();

    void addEffect(Applyable applyable);

    void removeEffect(Applyable applyable);

    boolean hasEffect(Applyable applyable);

    boolean hasEffects();

    Set<Applyable> getEffects();
}
