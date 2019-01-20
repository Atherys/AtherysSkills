package com.atherys.skills.sevice;

import com.atherys.skills.api.exception.CastException;
import com.atherys.skills.api.skill.CastResult;
import com.atherys.skills.api.skill.Castable;
import com.google.inject.Singleton;
import org.spongepowered.api.entity.living.Living;

@Singleton
public class SkillService {

    /**
     * Casts a castable with the properties stored within this carrier
     *
     * @param user      The caster casting the skill
     * @param castable  the castable skill
     * @param timestamp When the skill is being cast
     * @param args      arguments
     * @return a {@link CastResult}
     */
    CastResult castSkill(Living user, Castable castable, long timestamp, String... args) throws CastException {
        return CastResult.empty();
    }

}
