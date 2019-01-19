package com.atherys.skills.api;

import com.atherys.skills.api.exception.CastException;
import com.atherys.skills.api.skill.CastResult;
import com.atherys.skills.api.skill.Castable;
import com.atherys.skills.api.skill.CastableCarrier;

public interface SkillService {

    /**
     * Casts a castable with the properties stored within this carrier
     *
     * @param caster    The caster casting the skill
     * @param castable  the castable skill
     * @param timestamp When the skill is being cast
     * @param args      arguments
     * @return a {@link CastResult}
     */
    default CastResult castSkill(CastableCarrier caster, Castable castable, long timestamp, String... args) throws CastException {
        return CastResult.empty();
    }

}
