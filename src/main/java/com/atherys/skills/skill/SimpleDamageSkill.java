package com.atherys.skills.skill;

import com.atherys.skills.api.exception.CastException;
import com.atherys.skills.api.property.AbstractSkillProperties;
import com.atherys.skills.api.skill.AbstractSkill;
import com.atherys.skills.api.skill.CastErrors;
import com.atherys.skills.api.skill.CastResult;
import com.atherys.skills.api.skill.MouseButtonCombo;
import org.spongepowered.api.entity.living.Living;
import org.spongepowered.api.text.format.TextColors;
import org.spongepowered.api.util.blockray.BlockRay;
import org.spongepowered.api.world.extent.EntityUniverse;

public class SimpleDamageSkill extends AbstractSkill<SimpleDamageSkill, SimpleDamageSkill.Properties> {

    public static class Properties extends AbstractSkillProperties<SimpleDamageSkill, SimpleDamageSkill.Properties> {

        private double range = 60.0;

        protected Properties() {
            super(
                    "atherysskills.skill.simpledamage",
                    15.0,
                    6000,
                    MouseButtonCombo.EMPTY,
                    "A simple damage skill. Aim at somebody, put in the amount of damage you want to do, and you're done."
            );
        }

        public double getRange() {
            return range;
        }

        public void setRange(double range) {
            this.range = range;
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
    public CastResult cast(Living user, Properties properties, long timestamp, String... args) throws CastException {
        if (args.length == 0) {
            throw CastErrors.invalidArguments();
        }

        try {
            Double amount = Double.parseDouble(args[0]);

            BlockRay.from(user).distanceLimit(properties.getRange()).build().forEachRemaining(hit -> {

            });

        } catch (NumberFormatException e) {
            throw CastErrors.exceptionOf(TextColors.RED, "First argument must be amount of damage to deal to target.");
        }

        throw CastErrors.notImplemented();
    }

    @Override
    public Properties getDefaultProperties() {
        return new Properties();
    }

}
