package com.atherys.skills.command.effect;

import com.atherys.core.command.annotation.Aliases;
import com.atherys.core.command.annotation.Children;
import org.spongepowered.api.command.CommandException;
import org.spongepowered.api.command.CommandResult;
import org.spongepowered.api.command.CommandSource;
import org.spongepowered.api.command.args.CommandContext;
import org.spongepowered.api.command.spec.CommandExecutor;

@Aliases("effect")
@Children({
        ApplyEffectCommand.class,
        RemoveEffectCommand.class
})
public class EffectCommand implements CommandExecutor {
    @Override
    public CommandResult execute(CommandSource src, CommandContext args) throws CommandException {
        return CommandResult.empty();
    }
}
