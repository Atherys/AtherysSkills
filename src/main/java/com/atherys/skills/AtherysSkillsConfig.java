package com.atherys.skills;

import com.atherys.core.utils.PluginConfig;
import com.google.inject.Singleton;
import ninja.leaping.configurate.objectmapping.Setting;

import java.io.IOException;

@Singleton
public class AtherysSkillsConfig extends PluginConfig {

    @Setting("global-cooldown")
    public long GLOBAL_COOLDOWN = 500;

    @Setting("resource-regen-interval-ticks")
    public int RESOURCE_REGEN_TICK_INTERVAL = 20;

    protected AtherysSkillsConfig() throws IOException {
        super("config/" + AtherysSkills.ID, "config.conf");
    }
}
