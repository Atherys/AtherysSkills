package com.atherys.skills.api.util;

import com.atherys.core.db.SpongeIdentifiable;
import org.spongepowered.api.entity.living.Living;

import java.util.Optional;

/**
 * An object which can be represented as a Sponge {@link Living} entity.
 */
public interface LivingRepresentable<T extends Living> extends SpongeIdentifiable {

    /**
     * Get the underlying Living entity from this carrier
     *
     * @return The Living instance. Empty optional if it is not present.
     */
    Optional<T> getLiving();

}
