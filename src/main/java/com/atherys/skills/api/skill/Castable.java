package com.atherys.skills.api.skill;

import com.atherys.skills.api.exception.CastException;
import org.spongepowered.api.CatalogType;
import org.spongepowered.api.entity.living.Living;
import org.spongepowered.api.text.Text;

/**
 * A Castable is something which a {@link Living} may invoke.
 */
public interface Castable extends CatalogType {

    String getPermission();

    Text getDescription(Living user);

    long getCooldown(Living user);

    double getResourceCost(Living user);

    /**
     * Triggers the use of this Castable by the provided Living
     *
     * @param user      The user of the skill
     * @param timestamp The timestamp of when the skill is being triggered
     * @param args      Arguments
     * @return A {@link CastResult}
     * @throws CastException If a skill-related error occurs
     */
    CastResult cast(Living user, long timestamp, String... args) throws CastException;

}
