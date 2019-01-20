package com.atherys.skills.api.effect;

import com.atherys.skills.api.util.LivingUtils;
import org.spongepowered.api.data.DataTransactionResult;
import org.spongepowered.api.effect.potion.PotionEffect;
import org.spongepowered.api.effect.potion.PotionEffectType;

/**
 * Wrapper around a {@link PotionEffect}
 */
public class TemporaryPotionEffect extends TemporaryEffect {

    private PotionEffect effect;

    public TemporaryPotionEffect(String id, String name, PotionEffect effect) {
        super(id, name, effect.getDuration() * 50);
        this.effect = effect;
    }

    public static TemporaryPotionEffect of(PotionEffectType effectType, int duration, int amplifier) {
        return new TemporaryPotionEffect(
                "atherys:" + effectType.getId() + "_effect",
                effectType.getName(),
                PotionEffect.of(effectType, duration, amplifier)
        );
    }

    @Override
    protected boolean apply(ApplyableCarrier<?> character) {
        return character.getLiving()
                .map(living -> LivingUtils.applyPotionEffect(living, effect))
                .map(DataTransactionResult::isSuccessful)
                .orElse(false);
    }

    @Override
    protected boolean remove(ApplyableCarrier<?> character) {
        return character.getLiving()
                .map(living -> LivingUtils.removePotionEffect(living, effect))
                .map(DataTransactionResult::isSuccessful)
                .orElse(false);
    }
}
