package com.atherys.skills.registry;

import com.atherys.skills.api.effect.Applyable;
import com.atherys.skills.event.EffectRegistrationEvent;
import com.google.inject.Singleton;
import org.spongepowered.api.Sponge;
import org.spongepowered.api.registry.CatalogRegistryModule;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Singleton
public class EffectRegistry implements CatalogRegistryModule<Applyable> {

    private Map<String,Applyable> registry = new HashMap<>();

    @Override
    public void registerDefaults() {
        EffectRegistrationEvent event = new EffectRegistrationEvent(this);
        Sponge.getEventManager().post(event);
    }

    @Override
    public Optional<Applyable> getById(String id) {
        return Optional.ofNullable(registry.get(id));
    }

    public void register(Applyable applyable) {
        registry.put(applyable.getId(), applyable);
    }

    @Override
    public Collection<Applyable> getAll() {
        return registry.values();
    }
}
