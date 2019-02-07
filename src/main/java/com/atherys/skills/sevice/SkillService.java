package com.atherys.skills.sevice;

import com.atherys.skills.api.exception.CastException;
import com.atherys.skills.api.skill.CastResult;
import com.atherys.skills.api.skill.Castable;
import com.atherys.skills.registry.SkillRegistry;
import com.google.inject.Inject;
import com.google.inject.Singleton;
import org.spongepowered.api.entity.living.Living;
import org.spongepowered.api.registry.CatalogRegistryModule;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Singleton
public class SkillService {

    @Inject
    SkillRegistry skillRegistry;

    private Map<String,Castable> castables = new HashMap<>();

    /**
     * Casts a castable with the properties stored within this carrier
     *
     * @param user      The caster casting the skill
     * @param castable  the castable skill
     * @param timestamp When the skill is being cast
     * @param args      arguments
     * @return a {@link CastResult}
     */
    public CastResult castSkill(Living user, Castable castable, long timestamp, String... args) throws CastException {
        return castable.cast(user, timestamp, args);
    }

    public Optional<Castable> getSkillById(String skillId) {
        return skillRegistry.getById(skillId);
    }
}
