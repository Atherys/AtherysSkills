package com.atherys.skills.facade;

import com.atherys.skills.api.exception.CastException;
import com.atherys.skills.api.skill.CastErrors;
import com.atherys.skills.api.skill.CastResult;
import com.atherys.skills.api.skill.Castable;
import com.atherys.skills.service.SkillService;
import com.google.inject.Inject;
import com.google.inject.Singleton;
import org.spongepowered.api.Sponge;
import org.spongepowered.api.entity.living.Living;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.text.Text;
import org.spongepowered.api.text.channel.MessageReceiver;

import java.util.Optional;

@Singleton
public class SkillFacade {

    @Inject
    SkillService skillService;

    SkillFacade() {
    }

    public void playerCastSkill(Player caster, String skillId, String... arguments) throws CastException {
        livingCastSkill(caster, skillId, arguments);
    }

    public void livingCastSkill(Living caster, String skillId, String... arguments) throws CastException {
        if (skillId == null || skillId.isEmpty()) {
            throw CastErrors.exceptionOf("Must provide valid skill id.");
        }

        if (arguments == null) {
            arguments = new String[0];
        }

        Optional<Castable> castable = skillService.getSkillById(skillId);

        if (!castable.isPresent()) {
            throw CastErrors.noSuchSkill();
        }

        CastResult castResult = skillService.castSkill(
                caster,
                castable.get(),
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
