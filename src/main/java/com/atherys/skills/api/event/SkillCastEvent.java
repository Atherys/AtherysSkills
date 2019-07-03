package com.atherys.skills.api.event;

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

    private long timestamp;

    public SkillCastEvent(Living user, Castable skill, long timestamp) {
        this.cause = Cause.of(Sponge.getCauseStackManager().getCurrentContext(), user, skill);
        this.user = user;
        this.skill = skill;
        this.timestamp = timestamp;
    }

    public Living getUser() {
        return user;
    }

    public Castable getSkill() {
        return skill;
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

        public Pre(Living user, Castable skill, long timestamp) {
            super(user, skill, timestamp);
        }

        public void setUser(Living user) {
            this.user = user;
        }

        public void setSkill(Castable skill) {
            this.skill = skill;
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

        public Post(Living user, Castable skill, long timestamp, CastResult result) {
            super(user, skill, timestamp);
            this.result = result;
        }

        public CastResult getResult() {
            return result;
        }
    }

}
