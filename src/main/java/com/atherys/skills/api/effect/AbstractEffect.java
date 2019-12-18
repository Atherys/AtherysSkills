package com.atherys.skills.api.effect;

import java.util.Objects;

public abstract class AbstractEffect implements Applyable {

    private String id;
    private String name;
    private boolean isPositive;

    protected AbstractEffect(String id, String name, boolean isPositive) {
        this.id = id;
        this.name = name;
        this.isPositive = isPositive;
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
    public boolean isPositive() {
        return isPositive;
    }

    @Override
    public boolean equals(Object object) {
        if (this == object) return true;
        if (object == null || getClass() != object.getClass()) return false;
        AbstractEffect that = (AbstractEffect) object;
        return Objects.equals(getId(), that.getId()) &&
                Objects.equals(getName(), that.getName());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getName());
    }
}
