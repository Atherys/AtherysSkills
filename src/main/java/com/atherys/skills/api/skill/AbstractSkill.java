package com.atherys.skills.api.skill;

import com.atherys.skills.api.property.CastableProperties;

public abstract class AbstractSkill<T extends Castable<T, P>, P extends CastableProperties<T, P>> implements Castable<T,P> {

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
}
