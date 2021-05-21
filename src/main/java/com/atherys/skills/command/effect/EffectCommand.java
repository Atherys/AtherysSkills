package com.atherys.skills.command.effect;

import com.atherys.core.command.annotation.Aliases;
import com.atherys.core.command.annotation.Children;
import com.atherys.core.command.annotation.Permission;
import org.spongepowered.api.command.CommandException;
import org.spongepowered.api.command.CommandResult;
import org.spongepowered.api.command.CommandSource;
import org.spongepowered.api.command.args.CommandContext;
import org.spongepowered.api.command.spec.CommandExecutor;

@Aliases("effect")
@Children({
        ApplyEffectCommand.class,
        RemoveEffectCommand.class,
        RemoveNegativeEffectsCommand.class
})
@Permission("atherysskills.effect.base")
public class EffectCommand implements CommandExecutor {
    @Override
    public CommandResult execute(CommandSource src, CommandContext args) throws CommandException {
        return CommandResult.empty();
    }
}
