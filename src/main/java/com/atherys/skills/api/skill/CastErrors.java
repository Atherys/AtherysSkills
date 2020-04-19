package com.atherys.skills.api.skill;

import com.atherys.skills.AtherysSkills;
import com.atherys.skills.api.exception.CastException;
import org.apache.commons.lang3.time.DurationFormatUtils;
import org.spongepowered.api.text.Text;
import org.spongepowered.api.text.format.TextColors;

public final class CastErrors {

    public static CastException exceptionOf(Object... message) {
        return new CastException(Text.of(message));
    }

    public static CastException failure(Castable castable) {
        return exceptionOf(TextColors.RED, "Failed to use ", TextColors.DARK_RED, castable.getName());
    }

    public static CastException cancelled(Castable castable) {
        return exceptionOf(TextColors.RED, "Cancelled ", TextColors.DARK_RED, castable.getName());
    }

    public static CastException onCooldown(long timestamp, Castable castable, long cooldownEnd) {
        return exceptionOf(TextColors.DARK_RED, castable.getName(), TextColors.RED, " is on cooldown for another ", TextColors.WHITE, formatDuration(cooldownEnd - timestamp));
    }

    public static CastException onGlobalCooldown(long timestamp, long cooldownEnd) {
        return exceptionOf(TextColors.RED, "You are on global cooldown for another ", TextColors.WHITE, formatDuration(cooldownEnd - timestamp));
    }

    public static CastException insufficientResources(Castable castable) {
        return exceptionOf(
                "You do not have enough ",
                AtherysSkills.getInstance().getConfig().RESOURCE_COLOR, AtherysSkills.getInstance().getConfig().RESOURCE_NAME,
                TextColors.RED, " to cast ", castable.getName()
        );
    }

    public static CastException blocked(Castable castable) {
        return exceptionOf(TextColors.DARK_RED, castable.getName(), TextColors.RED, " has been blocked.");
    }

    public static CastException noTarget() {
        return exceptionOf(TextColors.RED, "No target could be found.");
    }

    public static CastException invalidTarget() {
        return exceptionOf(TextColors.RED, "Target is not valid.");
    }

    public static CastException obscuredTarget() {
        return exceptionOf(TextColors.RED, "Target is obscured.");
    }

    public static CastException notImplemented() {
        return exceptionOf(TextColors.RED, "This skill is not implemented yet.");
    }

    public static CastException noSuchSkill() {
        return exceptionOf(TextColors.RED, "No such skill");
    }

    public static CastException internalError() {
        return exceptionOf(TextColors.RED, "An internal error occurred while casting this skill. Please report this.");
    }

    public static CastException noPermission(Castable castable) {
        return exceptionOf(TextColors.RED, "You lack the permission required to use the skill ", castable.getName());
    }

    public static CastException invalidArguments() {
        return exceptionOf(TextColors.RED, "Invalid arguments were supplied to the skill.");
    }

    private static String formatDuration(long duration) {
        String format = "H'h' m'm' s.S's'";

        if (duration < 60000) {
            format = "s.S's'";
        }

        if (duration >= 60000 && duration < 3600000) {
            format = "m'm' s.S's'";
        }

        if (duration >= 3600000) {
            format = "H'h' m'm' s.S's'";
        }

        return DurationFormatUtils.formatDuration(duration, format, false);
    }
}
