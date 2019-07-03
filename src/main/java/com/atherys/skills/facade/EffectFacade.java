package com.atherys.skills.facade;

import com.atherys.skills.api.effect.Applyable;
import com.atherys.skills.sevice.EffectService;
import com.google.inject.Inject;
import com.google.inject.Singleton;
import org.spongepowered.api.command.CommandException;
import org.spongepowered.api.entity.living.Living;
import org.spongepowered.api.text.Text;

import java.util.Optional;

@Singleton
public class EffectFacade {

    @Inject
    EffectService effectService;

    EffectFacade() {
    }

    public void applyEffect(Living target, String effectId) throws CommandException {
        Optional<Applyable> namedEffect = effectService.getNamedEffect(effectId);

        if ( !namedEffect.isPresent() ) {
            throw new CommandException(Text.of("No effect with an id of \"", effectId, "\" could be found."));
        }

        namedEffect.ifPresent(effect -> effectService.applyEffect(target, effect));
    }

    public void removeEffect(Living target, String effectId) throws CommandException {
        effectService.removeEffect(target, effectId);
    }

}
