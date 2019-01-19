package com.atherys.skills.api.util;

import org.spongepowered.api.data.key.Keys;
import org.spongepowered.api.entity.living.Living;
import org.spongepowered.api.event.cause.entity.damage.source.DamageSource;
import org.spongepowered.api.util.Identifiable;

import java.util.Optional;

/**
 * An object which can be represented as a Sponge {@link Living} entity.
 */
public interface LivingRepresentable<T extends Living> extends Identifiable {
    /**
     * Get the underlying Living entity from this carrier
     *
     * @return The Living instance. Empty optional if it is not present.
     */
    Optional<T> asLiving();

    /**
     * Damages the Living entity represented by this LivingRepresentable
     *
     * @param source the source of the damage
     * @param amount The amount of damage
     * @return Whether or not damaging was a success
     */
    default boolean damage(DamageSource source, double amount) {
        return asLiving().map(living -> living.damage(amount, source)).orElse(false);
    }

    /**
     * Heals the Living entity represented by this LivingRepresentable
     *
     * @param amount the amount to heal
     * @return Whether healing was a success or not
     */
    default boolean heal(double amount) {
        return asLiving().map(living -> {

            double health = living.health().get();
            double maxHealth = living.maxHealth().get();

            double result = health + amount;

            if ( result >= maxHealth ) result = maxHealth;
            if ( result <= 0.0d ) result = 0.0d;

            return living.offer(Keys.HEALTH_SCALE, 20.0d).isSuccessful() &&
                    living.offer(Keys.HEALTH, result).isSuccessful();
        }).orElse(false);
    }


}
