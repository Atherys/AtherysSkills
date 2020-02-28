package com.atherys.skills.facade;

import com.atherys.skills.AtherysSkillsConfig;
import com.atherys.skills.api.event.ResourceEvent;
import com.atherys.skills.api.resource.ResourceUser;
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

    @Inject
    AtherysSkillsConfig config;

    public void onResourceRegen(ResourceEvent.Regen event) {

        if (event.getRegenAmount() > 0 && event.getResourceUser() instanceof EntityResourceUser) {
            Optional<Player> player = Sponge.getServer().getPlayer(((EntityResourceUser) event.getResourceUser()).getId());
            ResourceUser user = event.getResourceUser();

            player.ifPresent(p -> {
                int amount = (int) (event.getRegenAmount() + user.getCurrent());
                amount = amount < user.getMax() ? amount : (int) user.getMax();

                Text display = Text.of(config.RESOURCE_COLOR, amount, "/", (int) user.getMax(), " ", config.RESOURCE_NAME);
                p.sendTitle(Title.builder().actionBar(display).fadeOut(100).build());
            });
        }
    }

    public void onPlayerJoin(Player player) {
        resourceService.getOrCreateUser(player);
    }
}
