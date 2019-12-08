package com.atherys.skills.listener;

import com.atherys.skills.facade.EffectFacade;
import com.google.inject.Singleton;
import org.spongepowered.api.entity.living.Living;
import org.spongepowered.api.event.Listener;
import org.spongepowered.api.event.entity.DestructEntityEvent;
import org.spongepowered.api.event.filter.Getter;

import javax.inject.Inject;

@Singleton
public class EntityListener {
    @Inject
    EffectFacade effectFacade;

    @Listener
    public void onEntityDeath(DestructEntityEvent event, @Getter("getTargetEntity") Living entity) {
        effectFacade.onEntityDeath(entity);
    }
}
