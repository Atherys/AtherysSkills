package com.atherys.skills.facade;

import com.atherys.skills.api.exception.CastException;
import com.atherys.skills.api.skill.CastErrors;
import com.atherys.skills.api.skill.CastResult;
import com.atherys.skills.api.skill.Castable;
import com.atherys.skills.service.SkillService;
import com.google.inject.Inject;
import com.google.inject.Singleton;
import org.spongepowered.api.entity.living.Living;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.text.Text;
import org.spongepowered.api.text.channel.MessageReceiver;

@Singleton
public class SkillFacade {

    @Inject
    SkillService skillService;

    SkillFacade() {
    }

    public void playerCastSkill(Player caster, Castable skill, String... arguments) throws CastException {
        livingCastSkill(caster, skill, arguments);
    }

    public void livingCastSkill(Living caster, Castable skill, String... arguments) throws CastException {
        if (skill == null) {
            throw CastErrors.exceptionOf("Must provide valid skill.");
        }

        if (arguments == null) {
            arguments = new String[0];
        }

        CastResult castResult = skillService.castSkill(
                caster,
                skill,
                System.currentTimeMillis(),
                arguments
        );

        if (castResult == null) {
            throw CastErrors.internalError();
        } else {
            Text message = castResult.getMessage();

            if (!message.isEmpty() && caster instanceof MessageReceiver) {
                ((MessageReceiver) caster).sendMessage(message);
            }
        }
    }

}
