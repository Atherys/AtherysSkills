package com.atherys.skills;

import com.atherys.core.utils.PluginConfig;
import com.google.inject.Singleton;

import java.io.IOException;

@Singleton
public class AtherysSkillsConfig extends PluginConfig {

    public long GLOBAL_COOLDOWN = 500;

    public int RESOURCE_REGEN_TICK_INTERVAL = 20;

    protected AtherysSkillsConfig() throws IOException {
        super("config/" + AtherysSkills.ID, "config.conf");
    }
}
