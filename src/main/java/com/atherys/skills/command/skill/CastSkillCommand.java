package com.atherys.skills.command.skill;

import com.atherys.core.command.ParameterizedCommand;
import com.atherys.core.command.PlayerCommand;
import com.atherys.core.command.annotation.Aliases;
import com.atherys.core.command.annotation.Permission;
import com.atherys.skills.AtherysSkills;
import org.spongepowered.api.command.CommandException;
import org.spongepowered.api.command.CommandResult;
import org.spongepowered.api.command.args.CommandContext;
import org.spongepowered.api.command.args.CommandElement;
import org.spongepowered.api.command.args.GenericArguments;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.text.Text;

import javax.annotation.Nonnull;

@Aliases("cast")
@Permission("atherysskills.skill.cast")
public class CastSkillCommand implements PlayerCommand, ParameterizedCommand {
    @Nonnull
    @Override
    public CommandResult execute(@Nonnull Player source, @Nonnull CommandContext args) throws CommandException {
        String skillId = args.<String>getOne("skill-id").orElse(null);
        String[] arguments = args.<String>getOne("arguments...").orElse("").split(" ");
        AtherysSkills.getInstance().getSkillFacade().playerCastSkill(source, skillId, arguments);
        return CommandResult.success();
    }

    @Override
    public CommandElement[] getArguments() {
        return new CommandElement[] {
                GenericArguments.string(Text.of("skill-id")),
                GenericArguments.optional(GenericArguments.remainingJoinedStrings(Text.of("arguments...")))
        };
    }
}
