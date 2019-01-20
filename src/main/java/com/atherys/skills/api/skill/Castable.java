package com.atherys.skills.api.skill;

import com.atherys.skills.api.exception.CastException;
import com.atherys.skills.api.property.CastableProperties;
import org.spongepowered.api.CatalogType;
import org.spongepowered.api.entity.living.Living;

/**
 * A Castable is something which a {@link Living} may invoke.
 */
public interface Castable<T extends Castable<T, P>, P extends CastableProperties<T, P>> extends CatalogType {

    /**
     * Triggers the use of this Castable by the provided Living, at the specified timestamp with the provided arguments
     *
     * @param user      The user of the skill
     * @param timestamp The timestamp of when the skill is being triggered
     * @param args      The arguments
     * @return A {@link CastResult}
     * @throws CastException If a skill-related error occurs
     */
    CastResult cast(Living user, P properties, long timestamp, String... args) throws CastException;

    /**
     * Triggers the use of this Castable by the provided Living, with default properties
     *
     * @param user      The user of the skill
     * @param timestamp The timestamp of when the skill is being triggered
     * @param args      Arguments
     * @return A {@link CastResult}
     * @throws CastException If a skill-related error occurs
     */
    default CastResult cast(Living user, long timestamp, String... args) throws CastException {
        return cast(user, getDefaultProperties(), timestamp, args);
    }

    /**
     * Retrieves the default properties of this skill
     *
     * @return The default properties
     */
    P getDefaultProperties();

}
