package com.atherys.skills.command.effect;

import com.atherys.core.command.PlayerCommand;
import com.atherys.core.command.annotation.Aliases;
import com.atherys.core.command.annotation.Permission;
import com.atherys.skills.AtherysSkills;
import org.spongepowered.api.command.CommandResult;
import org.spongepowered.api.command.args.CommandContext;
import org.spongepowered.api.entity.living.player.Player;

import javax.annotation.Nonnull;

@Aliases("removenegative")
@Permission("atherysskills.effect.negative")
public class RemoveNegativeEffectsCommand implements PlayerCommand {
    @Override
    @Nonnull
    public CommandResult execute(@Nonnull Player src, @Nonnull CommandContext args) {
        AtherysSkills.getInstance().getEffectService().clearNegativeEffects(src);
        return CommandResult.success();
    }
}
