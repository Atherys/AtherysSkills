package com.atherys.skills.sevice;

import com.atherys.skills.api.exception.CastException;
import com.atherys.skills.api.property.CastableProperties;
import com.atherys.skills.api.resource.ResourceUser;
import com.atherys.skills.api.skill.CastErrors;
import com.atherys.skills.api.skill.CastResult;
import com.atherys.skills.api.skill.Castable;
import com.atherys.skills.api.skill.MouseButtonCombo;
import com.atherys.skills.registry.SkillRegistry;
import com.google.inject.Inject;
import com.google.inject.Singleton;
import org.spongepowered.api.entity.living.Living;
import org.spongepowered.api.service.permission.Subject;

@Singleton
public class SkillService {

    @Inject
    SkillRegistry skillRegistry;

    @Inject
    ResourceService resourceService;

    @Inject
    CooldownService cooldownService;

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
        if (validatePermission(user, castable) && validateCooldown(user, castable, timestamp) && validateResources(user, castable)) {
            cooldownService.putOnCooldown(user, castable);
            resourceService.withdrawResource(user, castable);
            return castable.cast(user, getSkillProperties(user, castable), timestamp, args);
        } else {
            throw CastErrors.internalError();
        }
    }

    public boolean validatePermission(Living user, Castable skill) throws CastException {
        // If the user is a subject, check for permission.
        // If the user is not a subject, just return true
        if (user instanceof Subject) {
            boolean permitted = ((Subject) user).hasPermission(getSkillPermission(user, skill));

            if (!permitted) {
                throw CastErrors.noPermission(skill);
            }
        }

        return true;
    }

    private boolean validateCooldown(Living user, Castable castable, Long timestamp) throws CastException {
        long cooldownEnd = cooldownService.getCooldownEnd(user, castable);

        if (cooldownEnd != -1) {
            throw CastErrors.onCooldown(timestamp, castable, cooldownEnd);
        }

        return true;
    }

    public boolean validateResources(Living user, Castable castable) throws CastException {
        ResourceUser resourceUser = resourceService.getOrCreateUser(user);

        if (resourceUser.getResource().getCurrent() < getSkillCost(resourceUser, castable)) {
            throw CastErrors.insufficientResources(castable, resourceUser.getResource());
        }

        return true;
    }

    public double getSkillCost(ResourceUser user, Castable castable) {
        return castable.getDefaultProperties().getResourceCost();
    }

    public long getSkillCooldown(Living user, Castable castable) {
        return castable.getDefaultProperties().getCooldown();
    }

    public String getSkillPermission(Living user, Castable castable) {
        return castable.getDefaultProperties().getPermission();
    }

    public String getSkillDescription(Living user, Castable castable) {
        return castable.getDefaultProperties().getDescription();
    }

    public MouseButtonCombo getSkillMouseButtonCombo(Living user, Castable castable) {
        return castable.getDefaultProperties().getCombo();
    }

    public CastableProperties getSkillProperties(Living user, Castable castable) {
        return castable.getDefaultProperties();
    }
}
