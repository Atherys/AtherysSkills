package com.atherys.skills.service;

import com.atherys.skills.AtherysSkills;
import com.atherys.skills.api.event.SkillCastEvent;
import com.atherys.skills.api.exception.CastException;
import com.atherys.skills.api.resource.ResourceUser;
import com.atherys.skills.api.skill.CastErrors;
import com.atherys.skills.api.skill.CastResult;
import com.atherys.skills.api.skill.Castable;
import com.google.inject.Inject;
import com.google.inject.Singleton;
import org.spongepowered.api.Sponge;
import org.spongepowered.api.entity.living.Living;
import org.spongepowered.api.service.permission.Subject;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Singleton
public class SkillService {

    private Map<String, Castable> skills = new HashMap<>();

    @Inject
    ResourceService resourceService;

    @Inject
    CooldownService cooldownService;

    SkillService() {
    }

    public void registerSkill(Castable castable) {
        skills.put(castable.getName().toLowerCase(), castable);
        Sponge.getEventManager().registerListeners(AtherysSkills.getInstance(), castable);
    }

    public void registerSkills(Castable... castables) {
        for (Castable castable : castables) {
            registerSkill(castable);
        }
    }

    public Optional<Castable> getSkillByName(String name) {
        return Optional.ofNullable(skills.get(name));
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
        SkillCastEvent.Pre preCastEvent = new SkillCastEvent.Pre(user, castable, timestamp);
        Sponge.getEventManager().post(preCastEvent);

        // If the pre-cast event was cancelled, throw a cancelled exception
        if (preCastEvent.isCancelled()) {
            throw CastErrors.cancelled(castable);
        }

        // Set the user, skill and skill properties to what was set in the pre-cast event
        user = preCastEvent.getUser();
        castable = preCastEvent.getSkill();

        // Validate
        if (validateSkillUse(user, castable, timestamp)) {

            // Cast the skill
            CastResult result = castable.cast(user, timestamp, args);

            // Set cooldown(s) and withdraw resources
            cooldownService.putOnGlobalCooldown(user, timestamp);
            cooldownService.setLastUsedTimestamp(user, castable, timestamp);
            resourceService.withdrawResource(user, castable.getResourceCost(user));

            // Trigger the post-cast event with the result
            SkillCastEvent.Post postCastEvent = new SkillCastEvent.Post(user, castable, timestamp, result);
            Sponge.getEventManager().post(postCastEvent);

            // Return the result
            return result;
        } else {
            throw CastErrors.internalError();
        }
    }

    private boolean validateSkillUse(Living user, Castable castable, long timestamp) throws CastException {
        boolean valid = validatePermission(user, castable);
        valid = valid && validateGlobalCooldown(user, timestamp);
        valid = valid && validateCooldown(user, castable, timestamp);
        valid = valid && validateResources(user, castable);

        return valid;
    }

    private boolean validatePermission(Living user, Castable skill) throws CastException {
        // If the user is a subject, check for permission.
        // If the user is not a subject, just return true ( is presumed to be non-player character )
        if (user instanceof Subject) {
            String permission = skill.getPermission();

            // If no permission is set, just return true
            if (permission == null) {
                return true;
            }

            boolean permitted = ((Subject) user).hasPermission(permission);

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

    private boolean validateCooldown(Living user, Castable castable, Long timestamp) throws CastException {
        long lastUsed = cooldownService.getLastUsedTimestamp(user, castable);
        long cooldownDuration = castable.getCooldown(user);

        if (cooldownService.isCooldownOngoing(timestamp, lastUsed, cooldownDuration)) {
            long cooldownEnd = cooldownService.getCooldownEnd(lastUsed, cooldownDuration);
            throw CastErrors.onCooldown(timestamp, castable, cooldownEnd);
        }

        return true;
    }

    private boolean validateResources(Living user, Castable castable) throws CastException {
        ResourceUser resourceUser = resourceService.getOrCreateUser(user);

        if (resourceUser.getCurrent() < castable.getResourceCost(user)) {
            throw CastErrors.insufficientResources(castable);
        }

        return true;
    }

    public Map<String, Castable> getAllSkills() {
        return skills;
    }
}
