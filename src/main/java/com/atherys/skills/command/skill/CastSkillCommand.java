package com.atherys.skills.command.skill;

import com.atherys.core.command.ParameterizedCommand;
import com.atherys.core.command.PlayerCommand;
import com.atherys.core.command.annotation.Aliases;
import com.atherys.core.command.annotation.Description;
import com.atherys.core.command.annotation.Permission;
import com.atherys.skills.AtherysSkills;
import com.atherys.skills.api.skill.Castable;
import com.atherys.skills.command.SkillCommandElement;
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
@Description("Casts a given skill. You must have permission to use the skill.")
public class CastSkillCommand implements PlayerCommand, ParameterizedCommand {
    @Nonnull
    @Override
    public CommandResult execute(@Nonnull Player source, @Nonnull CommandContext args) throws CommandException {
        Castable skill = args.<Castable>getOne("skill-name").get();
        String[] arguments = args.<String>getOne("arguments...").orElse("").split(" ");
        AtherysSkills.getInstance().getSkillFacade().playerCastSkill(source, skill, arguments);
        return CommandResult.success();
    }

    @Override
    public CommandElement[] getArguments() {
        return new CommandElement[] {
                new SkillCommandElement(Text.of("skill-name")),
                GenericArguments.optional(GenericArguments.remainingJoinedStrings(Text.of("arguments...")))
        };
    }
}
