package com.atherys.skills.skill;

import com.atherys.skills.api.exception.CastException;
import com.atherys.skills.api.skill.CastErrors;
import com.atherys.skills.api.skill.CastResult;
import com.atherys.skills.api.skill.MouseButtonCombo;
import com.atherys.skills.api.skill.TargetedSkill;
import org.spongepowered.api.entity.living.Living;
import org.spongepowered.api.event.cause.entity.damage.source.DamageSources;
import org.spongepowered.api.text.Text;
import org.spongepowered.api.text.channel.MessageReceiver;

public class SimpleDamageSkill extends TargetedSkill {

    private final double defaultDamage = 5.0;

    public SimpleDamageSkill() {
        super(
                "simple-damage",
                "Simple Damage Skill"
        );
    }

    @Override
    public CastResult cast(Living user, Living target, long timestamp, String... args) throws CastException {
        if (args.length == 0) {
            throw CastErrors.invalidArguments();
        }

        double damage;

        try {
            damage = Double.parseDouble(args[0]);
        } catch (NumberFormatException e) {
            damage = defaultDamage;
        }

        if (user instanceof MessageReceiver) {
            ((MessageReceiver) user).sendMessage(Text.of("Dealing ", damage, " damage to ", target.getType().getName()));
        }

        target.damage(damage, DamageSources.VOID);

        return CastResult.success();
    }

    @Override
    public long getCooldown(Living user) {
        return 6000;
    }

    @Override
    public double getResourceCost(Living user) {
        return 20.0;
    }
}
