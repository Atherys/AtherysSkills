package com.atherys.skills.skill;

import com.atherys.skills.AtherysSkills;
import com.atherys.skills.api.effect.ApplyableCarrier;
import com.atherys.skills.api.effect.PeriodicEffect;
import com.atherys.skills.api.exception.CastException;
import com.atherys.skills.api.property.AbstractSkillProperties;
import com.atherys.skills.api.skill.CastErrors;
import com.atherys.skills.api.skill.CastResult;
import com.atherys.skills.api.skill.MouseButtonCombo;
import com.atherys.skills.api.skill.TargetedSkill;
import org.spongepowered.api.entity.living.Living;
import org.spongepowered.api.event.cause.entity.damage.DamageTypes;
import org.spongepowered.api.event.cause.entity.damage.source.DamageSources;
import org.spongepowered.api.text.Text;
import org.spongepowered.api.text.channel.MessageReceiver;

public class SimpleDamageEffectSkill extends TargetedSkill<SimpleDamageEffectSkill, SimpleDamageEffectSkill.Properties> {

    public static class Properties extends AbstractSkillProperties<SimpleDamageEffectSkill, Properties> {

        protected Properties() {
            super(
                    "atherysskills.skill.simpledamage",
                    15.0,
                    6000,
                    MouseButtonCombo.EMPTY,
                    "A simple damage skill. Aim at somebody, put in the amount of damage you want to do, and you're done."
            );

            addProperty("range", 60.0);
            addProperty("default-damage", 20.0);
            addProperty("default-duration", 20);
        }

        public double getDefaultDamage() {
            return getOrDefault("default-damage", 20.0);
        }

        public int getDefaultDuration() {
            return getOrDefault("default-seconds", 5);
        }

        @Override
        public Properties copy() {
            return new Properties();
        }
    }

    public static class PeriodicSimpleDamageEffect extends PeriodicEffect {

        private double damage;

        protected PeriodicSimpleDamageEffect(long timeBetweenTicks, int ticks, double damage) {
            super("periodic-simple-damage-effect", "Periodic Simple Damage Effect", timeBetweenTicks, ticks);
            this.damage = damage;
        }

        @Override
        protected boolean apply(ApplyableCarrier<?> character) {
            System.out.println("Damaging for " + damage);
            character.getLiving().ifPresent(living -> {
                living.damage(damage, DamageSources.GENERIC);
            });

            return true;
        }

        @Override
        protected boolean remove(ApplyableCarrier<?> character) {
            System.out.println("Removing");
            character.getLiving().ifPresent(living -> {
            });

            return true;
        }
    }

    public SimpleDamageEffectSkill() {
        super(
                "simple-damage-effect",
                "Simple Damage Skill"
        );
    }

    @Override
    public CastResult cast(Living user, Living target, Properties properties, long timestamp, String... args) throws CastException {
        if (args.length == 0) {
            throw CastErrors.invalidArguments();
        }

        double damage;
        int ticks;

        try {
            damage = Double.parseDouble(args[0]);
            ticks = Integer.parseInt(args[1]);
        } catch (NumberFormatException e) {
            damage = properties.getDefaultDamage();
            ticks = properties.getDefaultDuration();
        }

        if (user instanceof MessageReceiver) {
            ((MessageReceiver) user).sendMessage(Text.of("Applying effect which will deal ", damage, " damage every second for ", ticks, " ticks to ", target.getType().getName()));
        }

        PeriodicSimpleDamageEffect effect = new PeriodicSimpleDamageEffect(1000, ticks, damage);
        AtherysSkills.getInstance().getEffectService().applyEffect(target, effect);

        return CastResult.success();
    }

    @Override
    public Properties getDefaultProperties() {
        return new Properties();
    }

}
