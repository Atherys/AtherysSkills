package com.atherys.skills.sevice;

import com.atherys.skills.api.event.SkillCastEvent;
import com.atherys.skills.api.exception.CastException;
import com.atherys.skills.api.property.CastableProperties;
import com.atherys.skills.api.resource.ResourceUser;
import com.atherys.skills.api.skill.CastErrors;
import com.atherys.skills.api.skill.CastResult;
import com.atherys.skills.api.skill.Castable;
import com.google.inject.Inject;
import com.google.inject.Singleton;
import org.spongepowered.api.Sponge;
import org.spongepowered.api.entity.living.Living;
import org.spongepowered.api.service.permission.Subject;

@Singleton
public class SkillService {

    @Inject
    ResourceService resourceService;

    @Inject
    CooldownService cooldownService;

    SkillService() {
    }

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
        // Trigger the pre-cast event
        SkillCastEvent.Pre preCastEvent = new SkillCastEvent.Pre(user, castable, castable.getDefaultProperties(), timestamp);
        Sponge.getEventManager().post(preCastEvent);

        // If the pre-cast event was cancelled, throw a cancelled exception
        if (preCastEvent.isCancelled()) {
            throw CastErrors.cancelled(castable);
        }

        // Set the user, skill and skill properties to what was set in the pre-cast event
        user = preCastEvent.getUser();
        castable = preCastEvent.getSkill();
        CastableProperties properties = preCastEvent.getProperties();

        // Validate
        if (validateSkillUse(user, castable, properties, timestamp)) {

            // Set cooldown(s) and withdraw resources
            cooldownService.putOnGlobalCooldown(user, timestamp);
            cooldownService.setLastUsedTimestamp(user, castable, timestamp);
            resourceService.withdrawResource(user, properties.getResourceCost());

            // Cast the skill
            CastResult result = castable.cast(user, properties, timestamp, args);

            // Trigger the post-cast event with the result
            SkillCastEvent.Post postCastEvent = new SkillCastEvent.Post(user, castable, properties, timestamp, result);
            Sponge.getEventManager().post(postCastEvent);

            // Return the result
            return result;
        } else {
            throw CastErrors.internalError();
        }
    }

    private boolean validateSkillUse(Living user, Castable castable, CastableProperties properties, long timestamp) throws CastException {
        boolean valid = validatePermission(user, castable, properties);
        valid = valid && validateGlobalCooldown(user, timestamp);
        valid = valid && validateCooldown(user, castable, properties, timestamp);
        valid = valid && validateResources(user, castable, properties);

        return valid;
    }

    private boolean validatePermission(Living user, Castable skill, CastableProperties properties) throws CastException {
        // If the user is a subject, check for permission.
        // If the user is not a subject, just return true
        if (user instanceof Subject) {
            boolean permitted = ((Subject) user).hasPermission(properties.getPermission());

            if (!permitted) {
                throw CastErrors.noPermission(skill);
            }
        }

        return true;
    }

    private boolean validateGlobalCooldown(Living user, Long timestamp) throws CastException {
        if (cooldownService.isOnGlobalCooldown(user, timestamp)) {
            long cooldownEnd = cooldownService.getLastGlobalCooldownEnd(user);
            throw CastErrors.onGlobalCooldown(timestamp, cooldownEnd);
        }

        return true;
    }

    private boolean validateCooldown(Living user, Castable castable, CastableProperties properties, Long timestamp) throws CastException {
        long lastUsed = cooldownService.getLastUsedTimestamp(user, castable);
        long cooldownDuration = properties.getCooldown();

        if (cooldownService.isCooldownOngoing(timestamp, lastUsed, cooldownDuration)) {
            long cooldownEnd = cooldownService.getCooldownEnd(lastUsed, cooldownDuration);
            throw CastErrors.onCooldown(timestamp, castable, cooldownEnd);
        }

        return true;
    }

    private boolean validateResources(Living user, Castable castable, CastableProperties properties) throws CastException {
        ResourceUser resourceUser = resourceService.getOrCreateUser(user);

        if (resourceUser.getResource().getCurrent() < properties.getResourceCost()) {
            throw CastErrors.insufficientResources(castable, resourceUser.getResource());
        }

        return true;
    }
}
