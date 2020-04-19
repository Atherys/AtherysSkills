package com.atherys.skills.api.skill;

import org.spongepowered.api.entity.living.Living;
import org.spongepowered.api.text.Text;

public abstract class AbstractSkill implements Castable {

    private String id;

    private String name;

    protected AbstractSkill(String id, String name) {
        this.id = id;
        this.name = name;
    }

    @Override
    public String getId() {
        return id;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public String getPermission() {
        return "atherysskills.skills." + id;
    }

    @Override
    public Text getDescription(Living user) {
        return null;
    }
}
