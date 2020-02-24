package com.atherys.skills.api.exception;

import com.atherys.skills.AtherysSkills;
import org.spongepowered.api.command.CommandException;
import org.spongepowered.api.text.Text;

public class CastException extends CommandException {

    public CastException(Text error) {
        super(AtherysSkills.getInstance().getMessagingFacade().formatInfo(error));
    }
}
