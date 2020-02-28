package com.atherys.skills;

import com.atherys.core.utils.PluginConfig;
import com.google.inject.Singleton;
import ninja.leaping.configurate.objectmapping.Setting;
import org.spongepowered.api.text.format.TextColor;
import org.spongepowered.api.text.format.TextColors;

import java.io.IOException;

@Singleton
public class AtherysSkillsConfig extends PluginConfig {

    @Setting("global-cooldown")
    public long GLOBAL_COOLDOWN = 500;

    @Setting("resource-regen-interval-ticks")
    public int RESOURCE_REGEN_TICK_INTERVAL = 20;

    @Setting("resource-regen-rate")
    public double RESOURCE_REGEN_RATE = 5;

    @Setting("resource-limit")
    public double RESOURCE_LIMIT = 100.0d;

    @Setting("resource-name")
    public String RESOURCE_NAME = "Mana";

    @Setting("resource-colour")
    public TextColor RESOURCE_COLOR = TextColors.BLUE;

    protected AtherysSkillsConfig() throws IOException {
        super("config/" + AtherysSkills.ID, "config.conf");
    }
}
