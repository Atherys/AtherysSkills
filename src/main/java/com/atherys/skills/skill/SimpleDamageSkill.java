package com.atherys.skills.skill;

import com.atherys.skills.api.exception.CastException;
import com.atherys.skills.api.property.AbstractSkillProperties;
import com.atherys.skills.api.skill.CastErrors;
import com.atherys.skills.api.skill.CastResult;
import com.atherys.skills.api.skill.MouseButtonCombo;
import com.atherys.skills.api.skill.TargetedSkill;
import org.spongepowered.api.entity.living.Living;
import org.spongepowered.api.event.cause.entity.damage.source.DamageSources;

public class SimpleDamageSkill extends TargetedSkill<SimpleDamageSkill, SimpleDamageSkill.Properties> {

    public static class Properties extends AbstractSkillProperties<SimpleDamageSkill, SimpleDamageSkill.Properties> {

        private static final double DEFAULT_RANGE = 60.0;

        private static final double DEFAULT_DAMAGE = 20.0;

        protected Properties() {
            super(
                    "atherysskills.skill.simpledamage",
                    15.0,
                    6000,
                    MouseButtonCombo.EMPTY,
                    "A simple damage skill. Aim at somebody, put in the amount of damage you want to do, and you're done."
            );

            addProperty("range", DEFAULT_RANGE);
            addProperty("default-damage", DEFAULT_DAMAGE);
        }

        public double getRange() {
            return getOrDefault("range", DEFAULT_RANGE);
        }

        public void setRange(double range) {
            addProperty("range", range);
        }

        public double getDefaultDamage() {
            return getOrDefault("default-damage", DEFAULT_DAMAGE);
        }

        public void setDefaultDamage(double defaultDamage) {
            addProperty("default-damage", defaultDamage);
        }

        @Override
        public Properties copy() {
            return new Properties();
        }
    }

    protected SimpleDamageSkill() {
        super(
                "simple-damage",
                "Simple Damage Skill"
        );
    }

    @Override
    public CastResult cast(Living user, Living target, Properties properties, long timestamp, String... args) throws CastException {
        if (args.length == 0) {
            throw CastErrors.invalidArguments();
        }

        double damage;

        try {
            damage = Double.parseDouble(args[0]);
        } catch (NumberFormatException e) {
            damage = properties.getDefaultDamage();
        }

        target.damage(damage, DamageSources.VOID);

        return CastResult.success();
    }

    @Override
    public Properties getDefaultProperties() {
        return new Properties();
    }

}
