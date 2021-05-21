package com.atherys.skills.api.util;

import org.spongepowered.api.data.DataTransactionResult;
import org.spongepowered.api.data.key.Keys;
import org.spongepowered.api.data.manipulator.mutable.PotionEffectData;
import org.spongepowered.api.effect.potion.PotionEffect;
import org.spongepowered.api.entity.living.Living;
import org.spongepowered.api.event.cause.entity.damage.source.DamageSource;

public class LivingUtils {

    public static DataTransactionResult applyPotionEffect(Living living, PotionEffect effect) {
        PotionEffectData effects = living.getOrCreate(PotionEffectData.class).get();
        effects.addElement(effect);
        return living.offer(effects);
    }

    public static DataTransactionResult removePotionEffect(Living living, PotionEffect effect) {
        PotionEffectData effects = living.getOrCreate(PotionEffectData.class).get();
        effects.removeAll(listEffect -> listEffect.getType() == effect.getType());
        return living.offer(effects);
    }

    public static boolean damageLiving(Living living, DamageSource source, double amount) {
        return living.damage(amount, source);
    }

    public static boolean healLiving(Living living, double amount) {

        double health = living.health().get();
        double maxHealth = living.maxHealth().get();

        double result = health + amount;

        if ( result >= maxHealth ) result = maxHealth;
        if ( result <= 0.0d ) result = 0.0d;

        return living.offer(Keys.HEALTH_SCALE, 20.0d).isSuccessful() &&
                living.offer(Keys.HEALTH, result).isSuccessful();
    }

}
