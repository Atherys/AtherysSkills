package com.atherys.skills.registry;

import com.atherys.skills.api.resource.Resource;
import com.atherys.skills.resource.ActionPoints;
import com.atherys.skills.resource.Mana;
import com.atherys.skills.resource.Rage;
import org.spongepowered.api.registry.CatalogRegistryModule;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class ResourceRegistry implements CatalogRegistryModule<Resource> {

    private Map<String, Resource> resources = new HashMap<>();

    public ResourceRegistry() {
    }

    @Override
    public void registerDefaults() {
        resources.put(ActionPoints.ID, new ActionPoints(0.0));
        resources.put(Mana.ID, new Mana(0.0));
        resources.put(Rage.ID, new Rage(0.0));
    }

    @Override
    public Optional<Resource> getById(String id) {
        Resource resource = resources.get(id);

        if ( resource != null ) {
            return Optional.of(resource.copy());
        } else {
            return Optional.empty();
        }
    }

    @Override
    public Collection<Resource> getAll() {
        return resources.values();
    }

}
