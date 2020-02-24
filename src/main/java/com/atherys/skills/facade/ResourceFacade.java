package com.atherys.skills.facade;

import com.atherys.skills.api.event.ResourceRegenEvent;
import com.atherys.skills.api.resource.Resource;
import com.atherys.skills.resource.EntityResourceUser;
import com.atherys.skills.service.ResourceService;
import com.google.inject.Inject;
import com.google.inject.Singleton;
import org.spongepowered.api.Sponge;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.text.Text;
import org.spongepowered.api.text.title.Title;

import java.util.Optional;

@Singleton
public class ResourceFacade {

    @Inject
    ResourceService resourceService;

    public void onResourceRegen(ResourceRegenEvent event) {

        if (event.getRegenAmount() > 0 && event.getResourceUser() instanceof EntityResourceUser) {
            Optional<Player> player = Sponge.getServer().getPlayer(((EntityResourceUser) event.getResourceUser()).getId());

            player.ifPresent(p -> {
                Resource resource = event.getResourceUser().getResource();
                int amount = (int) (event.getRegenAmount() + resource.getCurrent());
                amount = amount < resource.getMax() ? amount : (int) resource.getMax();

                Text display = Text.of(resource.getColor(), amount, "/", (int) resource.getMax(), " ", resource.getName());
                p.sendTitle(Title.builder().actionBar(display).fadeOut(100).build());
            });
        }
    }

    public void onPlayerJoin(Player player) {
        resourceService.getOrCreateUser(player);
    }
}
