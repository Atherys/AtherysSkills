package com.atherys.skills.service;

import com.atherys.skills.AtherysSkills;
import com.atherys.skills.api.effect.Applyable;
import com.atherys.skills.api.effect.ApplyableCarrier;
import com.atherys.skills.effect.EntityEffectCarrier;
import com.google.inject.Singleton;
import org.spongepowered.api.entity.living.Living;
import org.spongepowered.api.scheduler.Task;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Singleton
public class EffectService {

    private Task task;

    private Map<String,Applyable> namedEffects = new HashMap<>();

    private Map<UUID,ApplyableCarrier> cache = new HashMap<>();

    private EffectService() {
        task = Task.builder()
                .name("effect-service-task")
                .intervalTicks(1)
                .execute(this::tick)
                .submit(AtherysSkills.getInstance());
    }

    private void tick() {
        long timestamp = System.currentTimeMillis();

        for (Map.Entry<UUID, ApplyableCarrier> entry : cache.entrySet()) {
            if ( entry.getValue().getEffects().isEmpty() ) {
                cache.remove(entry.getKey());
                continue;
            }

            tickEffects(timestamp, entry.getValue());
        }
    }

    private ApplyableCarrier<?> getOrCreateCarrier(Living entity) {
        if ( cache.containsKey(entity.getUniqueId()) ) {
            return cache.get(entity.getUniqueId());
        } else {
            EntityEffectCarrier newCarrier = new EntityEffectCarrier(entity);
            cache.put(entity.getUniqueId(), newCarrier);
            return newCarrier;
        }
    }

    private void tickEffects(long timestamp, ApplyableCarrier<?> carrier) {
        carrier.getEffects().forEach(effect -> {
            carrier.applyEffect(effect, timestamp);
            carrier.removeEffect(effect, timestamp);
        });
    }

    public boolean applyEffect(Living entity, Applyable applyable) {
        return getOrCreateCarrier(entity).applyEffect(applyable, System.currentTimeMillis());
    }

    public boolean hasEffect(Living entity, Applyable applyable) {
        return getOrCreateCarrier(entity).hasEffect(applyable);
    }

    public boolean removeEffect(Living entity, Applyable applyable) {
        return getOrCreateCarrier(entity).removeEffect(applyable, System.currentTimeMillis());
    }
}
