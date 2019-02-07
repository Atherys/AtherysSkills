package com.atherys.skills.registry;

import com.atherys.skills.AtherysSkills;
import com.atherys.skills.api.skill.Castable;
import com.atherys.skills.event.SkillRegistrationEvent;
import com.google.inject.Singleton;
import org.spongepowered.api.Sponge;
import org.spongepowered.api.registry.CatalogRegistryModule;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Singleton
public class SkillRegistry implements CatalogRegistryModule<Castable> {

    private Map<String, Castable> registry = new HashMap<>();

    @Override
    public void registerDefaults() {
        SkillRegistrationEvent event = new SkillRegistrationEvent(this);
        Sponge.getEventManager().post(event);
    }

    @Override
    public Optional<Castable> getById(String id) {
        return Optional.ofNullable(registry.get(id));
    }

    public void register(Castable castable) {
        Sponge.getEventManager().registerListeners(AtherysSkills.getInstance(), castable);
        registry.put(castable.getId(), castable);
    }

    @Override
    public Collection<Castable> getAll() {
        return registry.values();
    }
}
