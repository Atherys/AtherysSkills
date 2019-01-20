package com.atherys.skills.api.property;

import com.atherys.skills.api.skill.Castable;
import com.atherys.skills.api.skill.MouseButtonCombo;
import com.atherys.skills.api.util.PropertyUtils;
import com.google.gson.annotations.Expose;

import java.util.Map;

public abstract class AbstractSkillProperties<T extends Castable<T,P>, P extends CastableProperties<T,P>> implements CastableProperties<T,P> {

    @Expose
    private String permission;

    @Expose
    private double resCost;

    @Expose
    private long cooldownMillis;

    @Expose
    private MouseButtonCombo combo;

    @Expose
    private String description;

    @Expose
    private Map<String,Object> properties;

    protected AbstractSkillProperties(String permission, double resCost, long cooldownMillis, MouseButtonCombo combo, String description) {
        this.permission = permission;
        this.resCost = resCost;
        this.cooldownMillis = cooldownMillis;
        this.combo = combo;
        this.description = description;
    }

    @Override
    public String getPermission() {
        return permission;
    }

    @Override
    public double getResourceCost() {
        return resCost;
    }

    @Override
    public long getCooldown() {
        return cooldownMillis;
    }

    @Override
    public MouseButtonCombo getCombo() {
        return combo;
    }

    @Override
    public String getDescription() {
        return description;
    }

    @Override
    public Map<String, Object> getProperties() {
        return properties;
    }

    @Override
    public void addProperty(String key, Object value) {
        properties.put(key, value);
    }

    @Override
    public P inheritFrom(P parent) {
        return PropertyUtils.inherit((P) this, parent);
    }
}
