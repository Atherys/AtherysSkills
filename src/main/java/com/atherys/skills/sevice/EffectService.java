package com.atherys.skills.sevice;

import com.atherys.skills.AtherysSkills;
import com.atherys.skills.api.effect.Applyable;
import com.atherys.skills.api.effect.ApplyableCarrier;
import com.atherys.skills.effect.EntityEffectCarrier;
import com.atherys.skills.registry.EffectRegistry;
import com.google.inject.Inject;
import com.google.inject.Singleton;
import org.spongepowered.api.entity.living.Living;
import org.spongepowered.api.registry.CatalogRegistryModule;
import org.spongepowered.api.scheduler.Task;

import java.util.*;

@Singleton
public class EffectService {

    @Inject
    EffectRegistry effectRegistry;

    private Task task;

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

    public ApplyableCarrier<?> getOrCreateCarrier(Living entity) {
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

    public boolean removeEffect(Living entity, String effectId) {
        return getOrCreateCarrier(entity).removeEffect(effectId, System.currentTimeMillis());
    }

    public Optional<Applyable> getNamedEffect(String id) {
        return effectRegistry.getById(id);
    }

    public void registerNamedEffect(Applyable effect) {
        effectRegistry.register(effect);
    }

}
