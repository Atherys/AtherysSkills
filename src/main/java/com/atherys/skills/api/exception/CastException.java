package com.atherys.skills.api.exception;

import org.spongepowered.api.command.CommandException;
import org.spongepowered.api.text.Text;

public class CastException extends CommandException {

    public CastException(Text error) {
        super(error);
    }

}
