package com.atherys.skills.api.util;

import org.spongepowered.api.data.DataTransactionResult;
import org.spongepowered.api.data.key.Keys;
import org.spongepowered.api.effect.potion.PotionEffect;
import org.spongepowered.api.entity.living.Living;
import org.spongepowered.api.event.cause.entity.damage.source.DamageSource;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

public class LivingUtils {

    public static DataTransactionResult applyPotionEffect(Living living, PotionEffect effect) {
        Optional<List<PotionEffect>> potionEffects = living.get(Keys.POTION_EFFECTS);

        if (potionEffects.isPresent()) {
            List<PotionEffect> effects = potionEffects.get();
            effects.add(effect);
            return living.offer(Keys.POTION_EFFECTS, effects);
        } else {
            return living.offer(Keys.POTION_EFFECTS, Arrays.asList(effect));
        }
    }

    public static DataTransactionResult removePotionEffect(Living living, PotionEffect effect) {
        Optional<List<PotionEffect>> potionEffects = living.get(Keys.POTION_EFFECTS);

        if (potionEffects.isPresent()) {
            List<PotionEffect> effects = potionEffects.get();
            effects.remove(effect);
            return living.offer(Keys.POTION_EFFECTS, effects);
        } else {
            return DataTransactionResult.failNoData();
        }
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
