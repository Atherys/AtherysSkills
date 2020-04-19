package com.atherys.skills.facade;

import com.atherys.skills.AtherysSkillsConfig;
import com.atherys.skills.api.event.ResourceEvent;
import com.atherys.skills.api.resource.ResourceUser;
import com.atherys.skills.resource.EntityResourceUser;
import com.atherys.skills.service.ResourceService;
import com.google.common.collect.ImmutableMap;
import com.google.inject.Inject;
import com.google.inject.Singleton;
import org.spongepowered.api.Sponge;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.text.Text;
import org.spongepowered.api.text.title.Title;

import java.util.Optional;

import static org.spongepowered.api.text.format.TextColors.GRAY;

@Singleton
public class ResourceFacade {

    private ResourceService resourceService;
    private AtherysSkillsConfig config;

    private final ImmutableMap<Integer, Text> BARS;

    @Inject
    public ResourceFacade(ResourceService resourceService, AtherysSkillsConfig config) {
        this.resourceService = resourceService;
        this.config = config;

        BARS = ImmutableMap.<Integer, Text>builder()
                .put(0, Text.of(GRAY, "▒▒▒▒▒▒▒▒▒▒"))
                .put(1, Text.of(config.RESOURCE_COLOR, "▉", GRAY, "▒▒▒▒▒▒▒▒▒"))
                .put(2, Text.of(config.RESOURCE_COLOR, "▉▉", GRAY, "▒▒▒▒▒▒▒▒"))
                .put(3, Text.of(config.RESOURCE_COLOR, "▉▉▉", GRAY, "▒▒▒▒▒▒▒"))
                .put(4, Text.of(config.RESOURCE_COLOR, "▉▉▉▉", GRAY, "▒▒▒▒▒▒"))
                .put(5, Text.of(config.RESOURCE_COLOR, "▉▉▉▉▉", GRAY, "▒▒▒▒▒"))
                .put(6, Text.of(config.RESOURCE_COLOR, "▉▉▉▉▉▉", GRAY, "▒▒▒▒"))
                .put(7, Text.of(config.RESOURCE_COLOR, "▉▉▉▉▉▉▉", GRAY, "▒▒▒"))
                .put(8, Text.of(config.RESOURCE_COLOR, "▉▉▉▉▉▉▉▉", GRAY, "▒▒"))
                .put(9, Text.of(config.RESOURCE_COLOR, "▉▉▉▉▉▉▉▉▉", GRAY, "▒"))
                .put(10, Text.of(config.RESOURCE_COLOR, "▉▉▉▉▉▉▉▉▉▉"))
                .build();
    }


    public void onResourceRegen(ResourceEvent.Regen event) {

        if (event.getRegenAmount() > 0 && event.getResourceUser() instanceof EntityResourceUser) {
            Optional<Player> player = Sponge.getServer().getPlayer(((EntityResourceUser) event.getResourceUser()).getId());
            ResourceUser user = event.getResourceUser();

            player.ifPresent(p -> {
                int amount = (int) (event.getRegenAmount() + user.getCurrent());
                amount = amount < user.getMax() ? amount : (int) user.getMax();
                int bars = (int) ((amount / user.getMax()) * 10);

                Text display = Text.of(BARS.get(bars), " ", config.RESOURCE_COLOR, amount, "/", (int) user.getMax(), " ", config.RESOURCE_NAME);
                p.sendTitle(Title.builder().title(Text.EMPTY).subtitle(Text.EMPTY).actionBar(display).fadeOut(100).build());
            });
        }
    }

    public void onPlayerJoin(Player player) {
        resourceService.getOrCreateUser(player);
    }
}
