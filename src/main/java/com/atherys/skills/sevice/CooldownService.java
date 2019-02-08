package com.atherys.skills.sevice;

import com.atherys.skills.api.skill.Castable;
import com.google.inject.Singleton;
import org.spongepowered.api.entity.living.Living;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Singleton
public class CooldownService {

    private Map<UUID,Long> globalCooldowns = new HashMap<>();

    public long getCooldown(Living user, Castable castable) {
        return 0;
    }

    public long getCooldownEnd(Living user, Castable castable) {
        return 0;
    }

    public void putOnCooldown(Living user, Castable castable) {

    }
}
