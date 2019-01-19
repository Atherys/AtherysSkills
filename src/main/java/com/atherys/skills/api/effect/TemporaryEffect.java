package com.atherys.skills.api.effect;

public abstract class TemporaryEffect extends AbstractEffect {

    private long timestampApplied;

    private long duration;

    private boolean applied;

    protected TemporaryEffect(String id, String name, long duration) {
        super(id, name);
        this.duration = duration;
        this.applied = false;
    }

    @Override
    public boolean canApply(long timestamp, ApplyableCarrier<?> character) {
        return !applied;
    }

    @Override
    public boolean apply(long timestamp, ApplyableCarrier<?> character) {
        this.timestampApplied = timestamp;
        this.applied = true;
        return apply(character);
    }

    @Override
    public boolean canRemove(long timestamp, ApplyableCarrier<?> character) {
        return timestampApplied + duration < timestamp && applied;
    }

    @Override
    public boolean remove(long timestamp, ApplyableCarrier<?> character) {
        return remove(character);
    }

    protected abstract boolean apply(ApplyableCarrier<?> character);

    protected abstract boolean remove(ApplyableCarrier<?> character);
}
