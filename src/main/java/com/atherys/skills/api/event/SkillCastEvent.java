package com.atherys.skills.api.event;

import com.atherys.skills.api.property.CastableProperties;
import com.atherys.skills.api.skill.CastResult;
import com.atherys.skills.api.skill.Castable;
import org.spongepowered.api.Sponge;
import org.spongepowered.api.entity.living.Living;
import org.spongepowered.api.event.Cancellable;
import org.spongepowered.api.event.Event;
import org.spongepowered.api.event.cause.Cause;

public abstract class SkillCastEvent implements Event {

    private Cause cause;

    protected Living user;

    protected Castable skill;

    protected CastableProperties properties;

    private long timestamp;

    public SkillCastEvent(Living user, Castable skill, CastableProperties properties, long timestamp) {
        this.cause = Cause.of(Sponge.getCauseStackManager().getCurrentContext(), user, skill, properties);
        this.user = user;
        this.skill = skill;
        this.properties = properties;
        this.timestamp = timestamp;
    }

    public Living getUser() {
        return user;
    }

    public Castable getSkill() {
        return skill;
    }

    public CastableProperties getProperties() {
        return properties;
    }

    public long getTimestamp() {
        return timestamp;
    }

    @Override
    public Cause getCause() {
        return cause;
    }

    public static class Pre extends SkillCastEvent implements Cancellable {

        private boolean cancelled;

        public Pre(Living user, Castable skill, CastableProperties properties, long timestamp) {
            super(user, skill, properties, timestamp);
        }

        public void setUser(Living user) {
            this.user = user;
        }

        public void setSkill(Castable skill) {
            this.skill = skill;
        }

        public void setProperties(CastableProperties castableProperties) {
            this.properties = castableProperties;
        }

        @Override
        public boolean isCancelled() {
            return cancelled;
        }

        @Override
        public void setCancelled(boolean cancel) {
            this.cancelled = cancel;
        }
    }

    public static class Post extends SkillCastEvent {

        private CastResult result;

        public Post(Living user, Castable skill, CastableProperties properties, long timestamp, CastResult result) {
            super(user, skill, properties, timestamp);
            this.result = result;
        }

        public CastResult getResult() {
            return result;
        }
    }

}
