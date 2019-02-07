package com.atherys.skills.skill;

import com.atherys.skills.api.exception.CastException;
import com.atherys.skills.api.property.AbstractSkillProperties;
import com.atherys.skills.api.skill.AbstractSkill;
import com.atherys.skills.api.skill.CastErrors;
import com.atherys.skills.api.skill.CastResult;
import com.atherys.skills.api.skill.MouseButtonCombo;
import org.spongepowered.api.entity.living.Living;
import org.spongepowered.api.entity.projectile.Snowball;
import org.spongepowered.api.event.Listener;
import org.spongepowered.api.event.entity.CollideEntityEvent;
import org.spongepowered.api.event.filter.cause.First;

public class FireballSkill extends AbstractSkill<FireballSkill, FireballSkill.Properties> {

    public static class Properties extends AbstractSkillProperties<FireballSkill, FireballSkill.Properties> {
        protected Properties() {
            super(
                    "atherysskills.skill.fireball",
                    10.0,
                    5000,
                    MouseButtonCombo.EMPTY,
                    "Fire a deadly fireball at your enemies and burn them alive!"
            );
        }

        @Override
        public Properties copy() {
            return new Properties();
        }
    }

    protected FireballSkill() {
        super(
                "fireball",
                "Fireball"
        );
    }

    @Override
    public CastResult cast(Living user, Properties properties, long timestamp, String... args) throws CastException {
        throw CastErrors.notImplemented();
    }

    @Listener
    public void onEntityCollision(CollideEntityEvent event, @First Snowball snowball) {
        // TODO
    }

    @Override
    public Properties getDefaultProperties() {
        return new Properties();
    }
}
