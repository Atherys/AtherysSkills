package com.atherys.skills.command.effect;

import com.atherys.core.command.ParameterizedCommand;
import com.atherys.core.command.annotation.Aliases;
import com.atherys.core.command.annotation.Permission;
import com.atherys.skills.AtherysSkills;
import org.spongepowered.api.command.CommandException;
import org.spongepowered.api.command.CommandResult;
import org.spongepowered.api.command.CommandSource;
import org.spongepowered.api.command.args.CommandContext;
import org.spongepowered.api.command.args.CommandElement;
import org.spongepowered.api.command.args.GenericArguments;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.text.Text;

import javax.annotation.Nonnull;
import javax.persistence.Parameter;

@Aliases("remove")
@Permission("atherysskills.effect.remove")
public class RemoveEffectCommand implements ParameterizedCommand {
    @Nonnull
    @Override
    public CommandResult execute(@Nonnull CommandSource source, @Nonnull CommandContext args) throws CommandException {
        Player player = args.<Player>getOne("player").orElse(null);
        String effect = args.<String>getOne("effect-id").orElse(null);
        AtherysSkills.getInstance().getEffectFacade().removeEffect(player, effect);
        return CommandResult.success();
    }

    @Override
    public CommandElement[] getArguments() {
        return new CommandElement[] {
                GenericArguments.player(Text.of("player")),
                GenericArguments.string(Text.of("effect-id"))
        };
    }
}
