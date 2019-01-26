package com.atherys.skills.facade;

import com.atherys.skills.api.exception.CastException;
import com.atherys.skills.api.skill.CastErrors;
import com.atherys.skills.api.skill.CastResult;
import com.atherys.skills.api.skill.Castable;
import com.atherys.skills.sevice.SkillService;
import com.google.inject.Inject;
import com.google.inject.Singleton;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.text.Text;

import java.util.Optional;

@Singleton
public class SkillFacade {

    @Inject
    SkillService skillService;

    public void playerCastSkill(Player caster, String skillId, String... arguments) throws CastException {
        Optional<Castable> castable = skillService.getById(skillId);

        if ( !castable.isPresent() ) {
            throw CastErrors.noSuchSkill();
        }

        CastResult castResult = skillService.castSkill(
                caster,
                castable.get(),
                System.currentTimeMillis(),
                arguments
        );

        if ( castResult == null ) {
            throw CastErrors.internalError();
        } else {
            Text message = castResult.getMessage();

            if ( !message.isEmpty() ) {
                caster.sendMessage(message);
            }
        }

    }

}
