package com.atherys.skills.facade;

import com.atherys.skills.api.event.ResourceRegenEvent;
import com.atherys.skills.api.resource.Resource;
import com.atherys.skills.resource.EntityResourceUser;
import com.google.inject.Singleton;
import org.spongepowered.api.Sponge;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.text.Text;
import org.spongepowered.api.text.title.Title;

import java.util.Optional;

@Singleton
public class ResourceFacade {

    public void onResourceRegen(ResourceRegenEvent event) {

        if (event.getRegenAmount() > 0 && event.getResourceUser() instanceof EntityResourceUser) {
            Optional<Player> player = Sponge.getServer().getPlayer(((EntityResourceUser) event.getResourceUser()).getId());

            player.ifPresent(p -> {
                Resource resource = event.getResourceUser().getResource();
                Text display = Text.of(resource.getColor(), resource.getCurrent() + event.getRegenAmount(), "/", resource.getMax(), " ", resource.getName());
                p.sendTitle(Title.builder().actionBar(display).fadeOut(100).build());
            });
        }
    }
}
