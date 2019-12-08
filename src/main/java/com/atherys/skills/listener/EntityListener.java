package com.atherys.skills.listener;

import com.atherys.skills.facade.EffectFacade;
import com.google.inject.Singleton;
import org.spongepowered.api.entity.living.Living;
import org.spongepowered.api.event.entity.DestructEntityEvent;
import org.spongepowered.api.event.filter.cause.Root;

import javax.inject.Inject;

@Singleton
public class EntityListener {
    @Inject
    EffectFacade effectFacade;

    public void onEntityDeath(DestructEntityEvent.Death event, @Root Living entity) {
        effectFacade.onEntityDeath(entity);
    }
}
