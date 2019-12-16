package com.atherys.skills.api.effect;

import com.atherys.skills.service.EffectService;
import org.spongepowered.api.CatalogType;
/**
 * An object which can be applied to a character and cause various temporary effects upon their game experience.
 * Applyables are not persisted across server restarts.
 */
public interface Applyable extends CatalogType {

    /**
     * Checks if the given Applyable can be applied to the character.
     *
     * @param timestamp When this Applyable is going to be applied, in the form of a UNIX timestamp.
     * @param character The RPGCharacter this Applyable is to be applied on
     * @return Whether or not the Applyable can be applied.
     */
    boolean canApply(long timestamp, ApplyableCarrier<?> character);

    boolean apply(long timestamp, ApplyableCarrier<?> character);

    boolean canRemove(long timestamp, ApplyableCarrier<?> character);

    /**
     * Will be called when {@link #canRemove(long, ApplyableCarrier)} is true.
     *
     * WARNING: Do not remove the effect from the carrier here. The {@link EffectService} will take care of that.
     */
    boolean remove(long timestamp, ApplyableCarrier<?> character);

    /**
     * @return whether this effect does something positive to the character or not.
     */
    boolean isPositive();

    /**
     * Sets this effect to be removed in the next tick. Use this to end an effect early.
     */
    void setRemoved();
}
