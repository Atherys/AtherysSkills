package com.atherys.skills.service;

import com.atherys.skills.AtherysSkillsConfig;
import com.atherys.skills.api.skill.Castable;
import com.google.inject.Inject;
import com.google.inject.Singleton;
import org.spongepowered.api.entity.living.Living;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Singleton
public class CooldownService {

    @Inject
    AtherysSkillsConfig config;

    private Map<UUID, Long> globalCooldowns = new HashMap<>();

    private Map<UUID, Map<Castable, Long>> lastUsed = new HashMap<>();

    CooldownService() {
    }

    /**
     * Get when the provided user's last global cooldown began
     *
     * @param user The user to check
     * @return When the user's last global cooldown began, or 0L if the player has never been put on global cooldown
     */
    public long getLastGlobalCooldownStart(Living user) {
        return globalCooldowns.getOrDefault(user.getUniqueId(), 0L);
    }

    /**
     * Get when ( in unix timestamp form ) the provided user's last global cooldown is/was due to end
     *
     * @param user The user to check
     * @return The cooldown end
     */
    public long getLastGlobalCooldownEnd(Living user) {
        return getLastGlobalCooldownStart(user) + config.GLOBAL_COOLDOWN;
    }

    /**
     * Put the user on a global cooldown
     *
     * @param user      The user to put on cooldown
     * @param timestamp The current timestamp
     */
    public void putOnGlobalCooldown(Living user, long timestamp) {
        globalCooldowns.put(user.getUniqueId(), timestamp);
    }

    /**
     * Set the timestamp for when the last time was that the provided user cast the skill
     *
     * @param user      The user who cast the skill
     * @param castable  The skill to put on cooldown
     * @param timestamp The current timestamp
     */
    public void setLastUsedTimestamp(Living user, Castable castable, long timestamp) {
        Map<Castable, Long> userSkillCooldowns;

        if (lastUsed.containsKey(user.getUniqueId())) {
            userSkillCooldowns = lastUsed.get(user.getUniqueId());
        } else {
            userSkillCooldowns = new HashMap<>();
        }

        userSkillCooldowns.put(castable, timestamp);
        lastUsed.put(user.getUniqueId(), userSkillCooldowns);
    }

    /**
     * Retrieve the last time a user used the provided skill
     *
     * @param user     The user to check
     * @param castable The skill to check
     * @return The timestamp, or 0L if never used before
     */
    public long getLastUsedTimestamp(Living user, Castable castable) {
        Map<Castable, Long> lastUsedSkills = lastUsed.get(user.getUniqueId());

        if (lastUsedSkills == null) {
            return 0L;
        } else {
            return lastUsedSkills.getOrDefault(castable, 0L);
        }
    }

    /**
     * Check if the user is on a global cooldown
     *
     * @param user The user to check
     * @return Whether the user is on a global cooldown
     */
    public boolean isOnGlobalCooldown(Living user, long currentTimestamp) {
        return isCooldownOngoing(currentTimestamp, getLastGlobalCooldownStart(user), config.GLOBAL_COOLDOWN);
    }

    public long getCooldownEnd(long cooldownStart, long duration) {
        return cooldownStart + duration;
    }

    public boolean isCooldownOngoing(long currentTimestamp, long cooldownStart, long cooldownDuration) {
        return cooldownStart > 0L && currentTimestamp < (cooldownStart + cooldownDuration);
    }
}
