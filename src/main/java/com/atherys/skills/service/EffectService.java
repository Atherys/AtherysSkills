package com.atherys.skills.service;

import com.atherys.skills.AtherysSkills;
import com.atherys.skills.api.effect.Applyable;
import com.atherys.skills.api.effect.ApplyableCarrier;
import com.atherys.skills.effect.EntityEffectCarrier;
import com.google.inject.Singleton;
import org.spongepowered.api.Sponge;
import org.spongepowered.api.entity.living.Living;
import org.spongepowered.api.scheduler.Task;

import java.util.*;

@Singleton
public class EffectService {

    private Task task;

    private Map<String, Applyable> effects = new HashMap<>();

    private Map<UUID, ApplyableCarrier> cache = new HashMap<>();

    public void registerEffect(Applyable applyable) {
        effects.put(applyable.getId(), applyable);
    }

    public Optional<Applyable> getEffectById(String id) {
        return Optional.ofNullable(effects.get(id));
    }

    EffectService() {
        task = Task.builder()
                .name("effect-service-task")
                .intervalTicks(1)
                .execute(this::tick)
                .submit(AtherysSkills.getInstance());
    }

    private void tick() {
        long timestamp = System.currentTimeMillis();

        for (Map.Entry<UUID, ApplyableCarrier> entry : cache.entrySet()) {
            if (!entry.getValue().hasEffects()) {
                cache.remove(entry.getKey());
                continue;
            }

            tickAllEffects(timestamp, entry.getValue());
        }
    }

    public ApplyableCarrier<?> getOrCreateCarrier(Living entity) {
        if (cache.containsKey(entity.getUniqueId())) {
            return cache.get(entity.getUniqueId());
        } else {
            EntityEffectCarrier newCarrier = new EntityEffectCarrier(entity);
            cache.put(entity.getUniqueId(), newCarrier);
            return newCarrier;
        }
    }

    private void tickAllEffects(long timestamp, ApplyableCarrier<?> carrier) {
        carrier.getEffects().forEach(effect -> tickEffect(timestamp, carrier, effect));
    }

    private void tickEffect(long timestamp, ApplyableCarrier<?> carrier, Applyable effect) {
        if (effect.canApply(timestamp, carrier)) {
            effect.apply(timestamp, carrier);
        }

        if (effect.canRemove(timestamp, carrier)) {
            effect.remove(timestamp, carrier);

            if (carrier.hasEffect(effect)) {
                carrier.removeEffect(effect);
            }
        }
    }

    public void applyEffect(Living entity, Applyable applyable) {
        getOrCreateCarrier(entity).addEffect(applyable);
    }

    public boolean hasEffect(Living entity, Applyable applyable) {
        return getOrCreateCarrier(entity).hasEffect(applyable);
    }

    public void removeEffect(Living entity, Applyable applyable) {
        getOrCreateCarrier(entity).removeEffect(applyable);
    }

    public void removeEffect(Living entity, String effectId) {
        Set<Applyable> effects = getOrCreateCarrier(entity).getEffects();

        for (Applyable applyable : effects) {
            if (applyable.getId().equals(effectId)) {
                effects.remove(applyable);
            }
        }
    }

    public Optional<Applyable> getNamedEffect(String id) {
        return Sponge.getRegistry().getType(Applyable.class, id);
    }

}
