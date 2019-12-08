package com.atherys.skills.service;

import com.atherys.skills.AtherysSkills;
import com.atherys.skills.api.effect.Applyable;
import com.atherys.skills.api.effect.ApplyableCarrier;
import com.atherys.skills.effect.EntityEffectCarrier;
import com.google.inject.Singleton;
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

    public void registerEffects(Applyable... applyables) {
        for (Applyable applyable : applyables) {
            registerEffect(applyable);
        }
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

        cache.entrySet().removeIf(entry -> {
            if (entry.getValue().hasEffects()) {
                tickAllEffects(timestamp, entry.getValue());
                return false;
            } else {
                return true;
            }
        });
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

    public void applyEffect(Living entity, String effectId) {
        Applyable effect = effects.get(effectId);
        if (effect != null) {
            getOrCreateCarrier(entity).addEffect(effect);
        }
    }

    public boolean hasEffect(Living entity, Applyable applyable) {
        return getOrCreateCarrier(entity).hasEffect(applyable);
    }

    public boolean hasEffect(Living entity, String effectId) {
        return getOrCreateCarrier(entity).getEffects().stream().anyMatch(effect -> effectId.equals(effect.getId()));
    }

    public void removeEffect(Living entity, Applyable applyable) {
        getOrCreateCarrier(entity).removeEffect(applyable);
    }

    public void removeEffect(Living entity, String effectId) {
        Set<Applyable> effects = getOrCreateCarrier(entity).getEffects();

        effects.removeIf(applyable -> applyable.getId().equals(effectId));
    }

    public void clearEffects(Living entity) {
        getOrCreateCarrier(entity).clearEffects();
    }

    public Optional<Applyable> getNamedEffect(String id) {
        return Optional.ofNullable(effects.get(id));
    }

}
